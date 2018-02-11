package team.covertdragon.springfestival.module;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import team.covertdragon.springfestival.SpringFestivalConstants;

import java.util.*;

public class ModuleLoader {

    public static List<AbstractSpringFestivalModule> readASMDataTable(ASMDataTable table){
        return getInstances(table, SpringFestivalModule.class, AbstractSpringFestivalModule.class);
    }

    private static <T> List<T> getInstances(ASMDataTable asmDataTable, Class annotationClass, Class<T> instanceClass) {
        String annotationClassName = annotationClass.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDatas = asmDataTable.getAll(annotationClassName);
        List<T> instances = new ArrayList<>();
        for (ASMDataTable.ASMData asmData : asmDatas) {
            try {
                Class<?> asmClass = Class.forName(asmData.getClassName());
                Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
                T instance = asmInstanceClass.newInstance();
                instances.add(instance);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                SpringFestivalConstants.logger.error("Failed to load: {}", asmData.getClassName(), e);
            }
        }
        return instances;
    }
}
