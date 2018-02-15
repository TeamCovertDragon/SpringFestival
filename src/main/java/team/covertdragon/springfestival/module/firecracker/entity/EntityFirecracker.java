/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.firecracker.entity;

import javax.annotation.Nullable;

//import elucent.albedo.lighting.ILightProvider;
//import elucent.albedo.lighting.Light;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import team.covertdragon.springfestival.internal.SpringFestivalUtil;
import team.covertdragon.springfestival.module.firecracker.ModuleFirecracker;
import team.covertdragon.springfestival.module.material.MaterialRegistry;

public class EntityFirecracker extends EntityThrowable /*implements ILightProvider*/ {
    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(EntityFirecracker.class, DataSerializers.VARINT);
    /** How long the fuse is */
    private int fuse;
    @Nullable
    private EntityLivingBase igniter;

    public EntityFirecracker(World worldIn)
    {
        super(worldIn);
        this.fuse = 40;
        this.preventEntitySpawning = true;
        this.isImmuneToFire = true;
    }
    
    public EntityFirecracker(World worldIn, double x, double y, double z, EntityLivingBase igniter)
    {
        this(worldIn);
        this.setPosition(x, y, z);
        float f = (float)(rand.nextGaussian() * (Math.PI * 2D));
        this.motionX = (double)(-((float)Math.sin((double)f)) * 0.02F);
        this.motionY = 0.1D;
        this.motionZ = (double)(-((float)Math.cos((double)f)) * 0.02F);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.igniter = igniter;
    }

    @Override
    protected void entityInit()
    {
        this.dataManager.register(FUSE, 40);
    }
    
    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (!this.hasNoGravity())
        {
            this.motionY -= 0.03999999910593033D;
        }

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
        }

        --this.fuse;
//        if (this.world.isRemote && this.fuse <= 4 && ModuleFirecracker.useFancyLighting)
//        {
//            Light l = new elucent.albedo.lighting.Light.Builder().color(1F, 0.7F, 1F).pos(this).radius(10F).build();
//            elucent.albedo.lighting.LightManager.addLight(l);
//            elucent.albedo.lighting.LightManager.update(world);
//        }
        if (this.fuse <= 0)
        {
            this.setDead();

            if (!this.world.isRemote)
            {
                this.explode();
            }
        }
        else
        {
            this.handleWaterMovement();
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }
    
    private void explode()
    {
        SpringFestivalUtil.createNonDestructiveExplosion(this.world, this.getPosition(), 2.0F, igniter);
        EntityItem e = new EntityItem(world, this.posX, this.posY, this.posZ, new ItemStack(MaterialRegistry.itemRedPaperBroken, 1));
        e.setDefaultPickupDelay();
        this.world.spawnEntity(e);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.setFuse(compound.getShort("Fuse"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setShort("Fuse", (short)this.getFuse());
    }

    @Override
    public float getEyeHeight()
    {
        return 0.0F;
    }

    public void setFuse(int fuseIn)
    {
        this.dataManager.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key)
    {
        if (FUSE.equals(key))
        {
            this.fuse = this.getFuseDataManager();
        }
    }

    public int getFuseDataManager()
    {
        return this.dataManager.get(FUSE);
    }

    public int getFuse()
    {
        return this.fuse;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null && this.fuse > 2)
        {
            this.fuse = 2;
        }
    }

//    @Override
//    public Light provideLight() {
//        if (this.fuse <= 2 && ModuleFirecracker.useFancyLighting)
//        {
//            return new Light.Builder().color(1F, 0.7F, 1F).pos(this).radius(10F).build();
//        }
//        return null;
//    }
}
