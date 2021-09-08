package coda.thecrabshack.common.entities;

import coda.thecrabshack.common.init.TCSItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class YetiCrabEntity extends WaterMobEntity {
    private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.defineId(YetiCrabEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.defineId(YetiCrabEntity.class, DataSerializers.BOOLEAN);

    public YetiCrabEntity(EntityType<? extends WaterMobEntity> type, World world) {
        super(type, world);
        this.moveControl = new YetiCrabEntity.MoveHelperController(this);
        this.maxUpStep = 0.7f;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(1, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
    }

    protected PathNavigator createNavigation(World world) {
        return new GroundPathNavigator(this, world);
    }

    @Override
    public void travel(Vector3d p_213352_1_) {
        super.travel(p_213352_1_);
        if (isInWater()) {
            getDeltaMovement().multiply(205.5D, 0.0D, 25.5D);
        }
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return !isFromBucket();
    }

    protected ItemStack getFishBucket() {
        return new ItemStack(TCSItems.YETI_CRAB_BUCKET.get());
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 4).add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(TCSItems.YETI_CRAB_SPAWN_EGG.get());
    }

    @Override
    protected void handleAirSupply(int p_209207_1_) {
    }

    public boolean requiresCustomPersistence() {
        if (this.isFromBucket()) {
            return false;
        } else {
            return true;
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
        this.entityData.define(SHEARED, false);
    }

    private boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean p_203706_1_) {
        this.entityData.set(FROM_BUCKET, p_203706_1_);
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.isFromBucket());
        compound.putBoolean("Sheared", this.isSheared());
    }

    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSheared(compound.getBoolean("Sheared"));
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    protected ActionResultType mobInteract(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        Item item = itemstack.getItem();
        if (itemstack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getFishBucket();
            this.setBucketData(itemstack1);
            if (!this.level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity) p_230254_1_, itemstack1);
            }

            if (itemstack.isEmpty()) {
                p_230254_1_.setItemInHand(p_230254_2_, itemstack1);
            } else if (!p_230254_1_.inventory.add(itemstack1)) {
                p_230254_1_.drop(itemstack1, false);
            }

            this.remove();
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else if (itemstack.getItem() == Items.SEAGRASS) {
            if (random.nextFloat() > 0.9F) {
                this.setSheared(false);
            }
            p_230254_1_.swing(p_230254_2_);
            if (!p_230254_1_.abilities.instabuild) {
                itemstack.shrink(1);
            }
        }
        else if (!isSheared() && item == Items.SHEARS && !level.isClientSide && !isBaby()) {
            shear();
            playSound(SoundEvents.SHEEP_SHEAR, getSoundVolume(), 1);
            p_230254_1_.getItemInHand(p_230254_2_).hurtAndBreak(1, p_230254_1_, (p_213613_1_) -> {
                p_213613_1_.broadcastBreakEvent(p_230254_2_);
            });
            spawnAtLocation(new ItemStack(TCSItems.YETI_CRAB_FLUFF.get(), random.nextInt(2) + 1), 1);
            return ActionResultType.SUCCESS;
        }
        return super.mobInteract(p_230254_1_, p_230254_2_);
    }

    public void shear() {
        if (!level.isClientSide) {
            setSheared(true);
        }
    }

    protected void setBucketData(ItemStack bucket) {
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }
    }

    public boolean isSheared() {
        return this.entityData.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.entityData.set(SHEARED, sheared);
    }

    static class MoveHelperController extends MovementController {
        private final YetiCrabEntity crab;

        MoveHelperController(YetiCrabEntity crab) {
            super(crab);
            this.crab = crab;
        }

        public void tick() {
            if (this.crab.isEyeInFluid(FluidTags.WATER)) {
                this.crab.setDeltaMovement(this.crab.getDeltaMovement().add(0.0D, 0.0D, 0.0D));
            }

            if (this.operation == Action.MOVE_TO && !this.crab.getNavigation().isDone()) {
                double d0 = this.wantedX - this.crab.getX();
                double d1 = this.wantedY - this.crab.getY();
                double d2 = this.wantedZ - this.crab.getZ();
                double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.crab.yRot = this.rotlerp(this.crab.yRot, f, 90.0F);
                this.crab.yBodyRot = this.crab.yRot;
                float f1 = (float) (this.speedModifier * this.crab.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.crab.setSpeed(MathHelper.lerp(0.125F, this.crab.getSpeed(), f1));
                this.crab.setDeltaMovement(this.crab.getDeltaMovement().add(0.0D, (double) this.crab.getSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.crab.setSpeed(0.0F);
            }
        }
    }
}