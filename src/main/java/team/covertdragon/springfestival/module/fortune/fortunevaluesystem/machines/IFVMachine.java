package team.covertdragon.springfestival.module.fortune.fortunevaluesystem.machines;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFVMachine {
    public int getRequiredFV();

    public void onFVProvided();

    public int getId();

    public void setId(int id);

    public BlockPos getPos();

    public World getWorld();
}
