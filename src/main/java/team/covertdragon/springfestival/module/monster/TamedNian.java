package team.covertdragon.springfestival.module.monster;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.world.World;

import java.util.UUID;

public class TamedNian extends EntityTameable {

    public TamedNian(World world) {
        super(world);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        // TODO What does tamed nian do when everything is safe?
        // Tamed Nian of course will defend for its owner
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtTarget(this));
        // Tamed Nian also defends for itself
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageableEntity) {
        TamedNian entity = new TamedNian(this.world);
        UUID owner = entity.getOwnerId();
        if (owner != null) {
            entity.setOwnerId(owner);
            entity.setTamed(true);
        }
        return entity;
    }
}
