/*
 * Copyright (c) 2018 CovertDragon Team.
 * Copyright (c) 2018 Contributors of SpringFestival.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package team.covertdragon.springfestival.module.monster;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import team.covertdragon.springfestival.SpringFestivalConstants;
import team.covertdragon.springfestival.module.firecracker.entity.EntityFirecracker;

public class Nian extends EntityMob implements IMob {

    private static final ResourceLocation NIAN_DROPS = new ResourceLocation(SpringFestivalConstants.MOD_ID, "entities/nian");

    static final EntityType<Nian> NIAN_TYPE_TOKEN = EntityType.Builder.create(Nian.class, Nian::new)
            .tracker(80, 5, true)
            .build("springfestival.nian");

    public Nian(World world) {
        super(NIAN_TYPE_TOKEN, world);
    }

    // TODO Pending on detailed documentation, just leave this stub here

    @Override
    protected ResourceLocation getLootTable() {
        return NIAN_DROPS;
    }

    @Override
    protected void initEntityAI() {
        // In case there is something happening there.
        super.initEntityAI();
        // Nian attacks village.
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityVillager.class, 32F));
        // Nian can be dispelled by firecrackers.
        this.tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityFirecracker.class, 16F, 1F, 2F));
        // Nian will wander if it has nothing to do.
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 1F));
        // Nian will just look around if it feels bored.
        this.tasks.addTask(3, new EntityAILookIdle(this));

        // Nian always try attacking villagers first.
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, true));
        // Nian also try attacking iron golems.
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
        // Nian also defends itself.
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        // Nina also attacks players.
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }
}
