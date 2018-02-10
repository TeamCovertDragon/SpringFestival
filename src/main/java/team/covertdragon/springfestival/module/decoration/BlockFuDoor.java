package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import team.covertdragon.springfestival.SpringFestivalConstants;

import java.util.ArrayList;
import java.util.List;

public class BlockFuDoor extends BlockDoor {

    // TODO: Maybe we should make it accessible by using NBT?

    public BlockFuDoor() {
        super(Material.WOOD);
        setHarvestLevel("axe", 0);
        setHardness(1.5F);
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + "_block_fu_door");
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return true;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.005F);

        if (this.canSilkHarvest(world, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
            List<ItemStack> drops = new ArrayList<ItemStack>();
            ItemStack silkTouchDrop = new ItemStack(this);
            if (te != null && te instanceof TileFuDoor) {
                silkTouchDrop.setTagCompound(((TileFuDoor) te).getOriginalDoor().serializeNBT());
                NBTUtil.writeBlockState(silkTouchDrop.getTagCompound(), ((TileFuDoor) te).getOriginalBlockState());
            }

            drops.add(silkTouchDrop);

            ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, 0, 1.0f, true, player);
            for (ItemStack item : drops) {
                spawnAsEntity(world, pos, item);
            }
        } else {
            harvesters.set(player);

            List<ItemStack> drops = new ArrayList<>();
            //Add drops here
            drops.add(((TileFuDoor) te).getOriginalDoor());
            drops.add(new ItemStack(DecorationConstants.itemFu, 1));

            System.out.println(drops);

            for (ItemStack drop : drops) {
                EntityItem entity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), drop);
                entity.setDefaultPickupDelay();
                world.spawnEntity(entity);
            }

            harvesters.set(null);
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileFuDoor(world, new ItemStack(Items.OAK_DOOR), null);
    }
}
