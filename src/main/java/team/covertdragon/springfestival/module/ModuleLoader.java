package team.covertdragon.springfestival.module;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import team.covertdragon.springfestival.SpringFestivalConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleLoader {
    public static void readASMDataTable(ASMDataTable table){
        Map<String, List<AbstractSpringFestivalModule>> modules = new HashMap<>();

        for (ASMDataTable.ASMData data : table.getAll(SpringFestivalModule.class.getName())){
            //
        }
    }
}
