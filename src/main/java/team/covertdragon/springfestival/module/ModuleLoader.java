/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import net.minecraftforge.fml.loading.toposort.TopologicalSort;
import team.covertdragon.springfestival.module.decoration.ModuleDecoration;
import team.covertdragon.springfestival.module.firecracker.ModuleFirecracker;
import team.covertdragon.springfestival.module.food.ModuleFood;
import team.covertdragon.springfestival.module.fortune.ModuleFortune;
import team.covertdragon.springfestival.module.material.ModuleMaterial;
import team.covertdragon.springfestival.module.monster.ModuleMonster;
import team.covertdragon.springfestival.module.music.ModuleMusic;
import team.covertdragon.springfestival.module.redpacket.ModuleRedPacket;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class ModuleLoader {

    private static boolean dataHarvested = false;

    private ModuleLoader() {
        throw new UnsupportedOperationException("No instance");
    }

    public static List<ISpringFestivalModule> readASMDataTable(/*ASMDataTable table*/) {
        if (dataHarvested) {
            throw new IllegalStateException("Modules are already initialized");
        }
        dataHarvested = true;
        List<ISpringFestivalModule> modules = new ArrayList<>(Arrays.asList(
                new ModuleDecoration(),
                new ModuleFirecracker(),
                new ModuleFood(),
                new ModuleFortune(),
                new ModuleMaterial(),
                new ModuleMonster(),
                new ModuleMusic(),
                new ModuleRedPacket()/*,
                new ModuleTrick()*/));
        return new ModuleSorter(modules).sort();
    }

    /*private static <T> List<T> getInstances(ASMDataTable asmDataTable, Class annotationClass, Class<T> instanceClass) {
        String annotationClassName = annotationClass.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDataSet = asmDataTable.getAll(annotationClassName);
        final List<String> expectedModules = SpringFestivalConfig.MODULES.entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        // We use a LinkedList here to preserve insertion order, so that we have deterministic iteration order.
        List<T> instances = new LinkedList<>();
        for (ASMDataTable.ASMData asmData : asmDataSet) {
            try {
                if (!expectedModules.contains(asmData.getAnnotationInfo().get("name").toString())) {
                    continue;
                }
                if (asmData.getAnnotationInfo().get("dependencies") != null && !expectedModules.containsAll(sanitize(asmData.getAnnotationInfo().get("dependencies")))) {
                    continue;
                }
                Class<? extends T> targetClass = Class.forName(asmData.getClassName()).asSubclass(instanceClass);
                instances.add(targetClass.newInstance());
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException("Failed to load: " + asmData.getClassName() + " " + e.toString());
            }
        }
        return Collections.emptyList();
    }*/

    /**
     * Work around against <code>-Dfml.enableJsonAnnotations=true</code> system properties.
     *
     * @param o Unknown object to be evaluated as List&lt;String&gt;
     * @return a list of string if o is either string array or already a list of string;
     *         an empty list if otherwise.
     *
     * @see <a href="https://github.com/MinecraftForge/MinecraftForge/issues/5021">Forge Issue Ticket #5021</a>
     */
    @SuppressWarnings("unchecked")
    private static List<String> sanitize(Object o) {
        if (o instanceof String[]) {
            return Arrays.asList((String[])o);
        } else if (o instanceof List) {
            return (List<String>)o;
        } else {
            // TODO (3TUSK): shouldn't be possible unless call on wrong thing, worth logging
            return Collections.emptyList();
        }
    }

    private static List<String> getModuleDependencies(ISpringFestivalModule instance) {
        return Arrays.asList(instance.getClass().getAnnotation(SpringFestivalModule.class).dependencies());
    }

    public static String getModuleName(ISpringFestivalModule instance) {
        return instance.getClass().getAnnotation(SpringFestivalModule.class).name();
    }

    @Nullable
    private static ISpringFestivalModule findModule(List<ISpringFestivalModule> modules, String name) {
        for (ISpringFestivalModule module : modules) {
            if (getModuleName(module).equals(name)) {
                return module;
            }
        }
        return null;
    }

    private static class ModuleSorter {
        private MutableGraph<ISpringFestivalModule> moduleGraph;
        //private TopologicalSort.DirectedGraph<ISpringFestivalModule> moduleGraph;

        ModuleSorter(List<ISpringFestivalModule> modules) {
            buildGraph(modules);
        }

        private void buildGraph(List<ISpringFestivalModule> modules) {
            moduleGraph = GraphBuilder.directed().expectedNodeCount(modules.size()).build();

            for (ISpringFestivalModule module : modules) {
                moduleGraph.addNode(module);
            }

            for (ISpringFestivalModule module : modules) {
                Collection<?> dependencies = getModuleDependencies(module);

                if (dependencies.size() == 0) {
                    continue;
                }

                dependencies.forEach(dep -> moduleGraph.putEdge(findModule(modules, (String) dep), module));
            }
        }

        List<ISpringFestivalModule> sort() {
            return TopologicalSort.topologicalSort(moduleGraph, Comparator.comparing(ModuleLoader::getModuleName));
        }
    }
}
