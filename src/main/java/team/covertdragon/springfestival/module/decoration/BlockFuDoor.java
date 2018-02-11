package team.covertdragon.springfestival.module.decoration;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.material.ModuleMaterial;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockFuDoor extends BlockDoor {

    // TODO: Maybe we should make it accessible by using NBT?

    public BlockFuDoor() {
        super(Material.WOOD);
        setHarvestLevel("axe", 0);
        setHardness(1.5F);
        setUnlocalizedName(SpringFestivalConstants.MOD_ID + "_block_fu_door");
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? ModuleMaterial.redPaper : Items.AIR;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(new ItemStack(ModuleMaterial.redPaperBroken));
        TileEntity te = world.getTileEntity(pos);
        if (te == null) {
            te = world.getTileEntity(pos.add(0, 1, 0));
        }
        if (te != null) {
            drops.add(((TileFuDoor) te).getOriginalDoor());
        } else {
            throw new NullPointerException();
        }

    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.getValue(HALF) == EnumDoorHalf.UPPER;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return state.getValue(HALF) == EnumDoorHalf.UPPER ? new TileFuDoor(world, null, null) : null;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && player.getHeldItem(hand).isEmpty() && player.isSneaking()) {
            TileFuDoor te = (TileFuDoor) world.getTileEntity(pos);
            BlockPos position = pos;
            //TileEntity will be null if the block is the lower part of the door
            if (te == null) {
                position = position.add(0, 1, 0);
                te = (TileFuDoor) world.getTileEntity(position);
            }

            if (te != null && te.getOriginalBlockStateUpper() != null) {
                //Set door
                ItemDoor.placeDoor(world, position.add(0, -1, 0), te.getOriginalBlockStateUpper().getValue(BlockDoor.FACING), te.getOriginalBlockStateUpper().getBlock(), te.getOriginalBlockStateUpper().getValue(BlockDoor.HINGE) == BlockDoor.EnumHingePosition.RIGHT);

                //Drop Fu
                EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModuleMaterial.redPaper, 1));
                entityItem.setDefaultPickupDelay();
                world.spawnEntity(entityItem);
                return true;
            }
        }
        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);

    }
}
