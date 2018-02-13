package team.covertdragon.springfestival.module.firecracker.firework;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import team.covertdragon.springfestival.module.firecracker.FirecrackerRegistry;

import javax.annotation.Nonnull;

public class TileFireworkBox extends TileEntity implements ITickable {
    private int count = 64;
    private int tick = 30;
    private boolean isActive = false;

    public TileFireworkBox() {
    }

    @Override
    public void update() {
        if (isActive) {
            tick--;
            if (tick <= 0 && count > 0) {//But when will tick < 0?
                FirecrackerRegistry.blockFirework.launchFireWork(this.getWorld(), this.getPos());
                tick = 30;
                count--;
            }
        }
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound tag) {
        tag.setInteger("count", count);
        tag.setBoolean("active", isActive);
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        this.count = tag.getInteger("count");
        this.isActive = tag.getBoolean("active");
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getCount() {
        return count;
    }

    public boolean isActive() {
        return isActive;
    }

    public void dropBlockAsItem() {
        ItemStack stack = new ItemStack(FirecrackerRegistry.itemFireWorkBox, 1);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("count", count);
        stack.setTagCompound(tag);

        EntityItem entity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        entity.setDefaultPickupDelay();
        world.spawnEntity(entity);
    }
}
