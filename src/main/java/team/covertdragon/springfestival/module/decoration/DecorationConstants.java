package team.covertdragon.springfestival.module.decoration;

import team.covertdragon.springfestival.SpringFestivalConstants;

public class DecorationConstants {
    public static final BlockFuDoor blockFuDoor = (BlockFuDoor) new BlockFuDoor().setRegistryName(SpringFestivalConstants.MOD_ID, "block_fu_door");

    public static final ItemFuDoor itemFuDoor = (ItemFuDoor) new ItemFuDoor(blockFuDoor).setRegistryName(SpringFestivalConstants.MOD_ID, "item_fu_door");
}
