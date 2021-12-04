package com.reindeermod.point5.entity.reindeer;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

import com.google.common.collect.Sets;
import com.reindeermod.point5.config.ReindeerConfig;
import com.reindeermod.point5.util.handlers.LootTableHandler;
import com.reindeermod.point5.util.handlers.SoundsHandler;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollow;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.entity.ai.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityReindeer extends EntityTameable implements IAnimatable
{
	private AnimationFactory factory = new AnimationFactory(this);

	public EntityAIEatGrass entityAIEatGrass;
	private int deerTimer;
    private static final DataParameter<Integer> VARIANT = EntityDataManager.<Integer>createKey(EntityReindeer.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GENDER = EntityDataManager.<Integer>createKey(EntityReindeer.class, DataSerializers.VARINT);
    private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.CARROT, Items.APPLE, Items.BREAD, Items.WHEAT);
    protected static final DataParameter<Boolean> WANDERING = EntityDataManager.createKey(EntityReindeer.class, DataSerializers.BOOLEAN);
   
	public EntityReindeer(World worldIn)
	{
		super(worldIn);
		this.setSize(1.5F, 2F);
	}
	
	@Override
	protected void initEntityAI()
    {
		clearAITasks();
		this.aiSit = new EntityAISit(this);
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.tasks.addTask(0, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        if (ReindeerConfig.reindeerFollow == true) {
            this.tasks.addTask(2, new EntityAIFollowOwner(this, 1.0D, 5.0F, 1.0F)); }
        this.tasks.addTask(4, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(5, new EntityAITempt(this, 1.1D, Items.APPLE, false));
        this.tasks.addTask(6, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(7, this.entityAIEatGrass);
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        this.tasks.addTask(10, new EntityAIFollow(this, (float) 1, 10, 5));
    }
	
	@Override
	public void registerControllers(AnimationData data)
	{
		data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
	}
	
	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
	{
		if (event.isMoving() && !this.isBeingRidden()){
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.reindeer.walk", true));
		return PlayState.CONTINUE;
		} 
		if ((this.isBeingRidden() && event.isMoving())){
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.reindeer.trot", true));
			return PlayState.CONTINUE;
		}
		if (this.deerTimer > 4 && this.deerTimer <= 40 && !event.isMoving()){
			event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.reindeer.grass", true));
			return PlayState.CONTINUE;
		}
		return PlayState.STOP;
	}
	
	@Override
	public AnimationFactory getFactory()
	{
		return this.factory;
	}
	
	@Override
    protected ResourceLocation getLootTable()
    {
        return LootTableHandler.REINDEER;
    }

	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
	    this.setVariant(this.rand.nextInt(4));
	    this.setGender(this.rand.nextInt(3));
	    return super.onInitialSpawn(difficulty, livingdata);
	}	
	
	 protected void applyEntityAttributes() 
	 	{
			super.applyEntityAttributes();
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.21D);
		}
	 
	 protected PathNavigate createNavigator(World worldIn)
	    {
	        PathNavigateGround pathnavigateground = new PathNavigateGround(this, worldIn);
	        return pathnavigateground;
	    }
	 
	 public void onLivingUpdate()
	    {
	        if (this.world.isRemote)
	        {
	            this.deerTimer = Math.max(0, this.deerTimer - 1);
	        }

	        super.onLivingUpdate();
	    }
	 
	 public boolean processInteract(EntityPlayer player, EnumHand hand) 
		{
			ItemStack itemstack = player.getHeldItem(hand);
		
			 if (!this.isTamed() && TAME_ITEMS.contains(itemstack.getItem()))
		     {
		            if (!player.capabilities.isCreativeMode)
		            {
		                itemstack.shrink(1);
		            }

//		            if (!this.isSilent())
//		            {
//		                this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, ENTITY_HORSE_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
//		            }

		            if (!this.world.isRemote)
		            {
		                if (this.rand.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
		                {
		                    this.setTamedBy(player);
		                    this.playTameEffect(true);
		                    this.world.setEntityState(this, (byte)7);
		                }
		                else
		                {
		                    this.playTameEffect(false);
		                    this.world.setEntityState(this, (byte)6);
		                }
		            }

		            return true;
		     }
			 
			 else if (itemstack.getItem() instanceof ItemFood)
	         { 
	                ItemFood itemfood = (ItemFood)itemstack.getItem();
	
	                if (itemfood == Items.APPLE && getHealth() < getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue());
	                {
	                	heal(itemfood.getHealAmount(itemstack));
	                    return true;
	                } 
	         }

		     else if (itemstack.getItem() == Items.BOOK)
		     {
		            if (this.getGender() == 1) 
		            {
		                Minecraft mc = Minecraft.getMinecraft();
		                if (this.world.isRemote) 
		                {
		                    mc.player.sendMessage(new TextComponentTranslation("message.creatures.male"));
		                }

		            }
		            else 
		            {
		                Minecraft mc = Minecraft.getMinecraft();
		                if (this.world.isRemote) 
		                {
		                    mc.player.sendMessage(new TextComponentTranslation("message.creatures.female"));
		                }
		            }
		            return true;
		     }

		     else if (itemstack.getItem() == Items.STICK && this.isTamed()) 
		     {
		            if (this.isWandering() == false) 
		            {
		                for (Object entry : this.tasks.taskEntries.toArray()) 
		                {
		                    EntityAIBase ai = ((EntityAITasks.EntityAITaskEntry) entry).action;
		                    if (ai instanceof EntityAIFollowOwner || ai instanceof EntityAIFollowOwner) this.tasks.removeTask(ai);
		                    this.setWandering(true);
		                }
		                Minecraft mc = Minecraft.getMinecraft();
		                if (this.world.isRemote) 
		                {
		                    mc.player.sendMessage(new TextComponentTranslation("Set to wandering"));
		                }
		                return true;
		            }
		            
		            else
		            {
		                this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
		                this.tasks.addTask(2, new EntityAIFollowOwner(this, 1.0D, 5.0F, 1.0F));
		                this.setWandering(false);
		                Minecraft mc = Minecraft.getMinecraft();
		                if (this.world.isRemote) 
		                {
		                    mc.player.sendMessage(new TextComponentTranslation("Set to following"));
		                }
		                return true;
		            }
		     }
			 
		     else if (!player.isSneaking())
			 {	
						player.startRiding(this);
						int x = (int) this.posX;
						int y = (int) this.posY;
						int z = (int) this.posZ;
						return true;
			 }
		            
		            return super.processInteract(player, hand);
		}
	
	 public boolean isBreedingItem(ItemStack stack)
	    {
	        return TAME_ITEMS.contains(stack.getItem());
	    }

	 public void fall(float x, float y) {
		 int z = MathHelper.ceil((x * 0.5F - 3.0F) * y);
		 	if (z > 0) {
		 		if (x >= 6.0F) {
		 			attackEntityFrom(DamageSource.FALL, z);
		 			if (isBeingRidden()) {
		 				Iterator<Entity> var4 = getRecursivePassengers().iterator();
		   
		 				while (var4.hasNext()) {
		 					Entity lvt_5_1_ = var4.next();
		 					lvt_5_1_.attackEntityFrom(DamageSource.FALL, z);
		 				} 
		 			} 
		 		} 
		      
		 		IBlockState blockState = this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - this.prevRotationYaw, this.posZ));
		 		Block lvt_5_2_ = blockState.getBlock();
		 		if (blockState.getMaterial() != Material.AIR && !isSilent()) {
		 			SoundType lvt_6_1_ = lvt_5_2_.getSoundType();
		 			this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, lvt_6_1_.getStepSound(), getSoundCategory(), lvt_6_1_.getVolume() * 0.5F, lvt_6_1_.getPitch() * 0.75F);
		 		} 
		 	} 
		 }
	    
	 protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
	    {
	    }

	 public boolean canMateWith(EntityAnimal otherAnimal)
	    {
	        if (otherAnimal == this)
	        {
	            return false;
	        }
	        else if (!(otherAnimal instanceof EntityReindeer))
	        {
	            return false;
	        }
	        else
	        {
	            EntityReindeer entityreindeer = (EntityReindeer)otherAnimal;
	            if (this.getGender() == entityreindeer.getGender()) {
	                return false;
	            }
	            else {
	            return this.isInLove() && entityreindeer.isInLove();
	            }
	        }
	    }
	    
	 @Nullable
	    public EntityAgeable createChild(EntityAgeable ageable)
	    {
	        EntityReindeer entityreindeer = new EntityReindeer(this.world);
	        entityreindeer.setVariant(this.getVariant());
	        int j = this.rand.nextInt(2);
	        if (j == 0) {
	            entityreindeer.setGender(1);
	        } else {
	            entityreindeer.setGender(2);
	        }

	        return entityreindeer;
	    }
	 
	 public boolean canBePushed()
	    {
	        return true;
	    }
	 
	 protected void collideWithEntity(Entity entityIn)
	    {
	        if (!(entityIn instanceof EntityPlayer))
	        {
	            super.collideWithEntity(entityIn);
	        }
	    }
	 
	 public boolean attackEntityFrom(DamageSource source, float amount)
	    {
	        if (this.isEntityInvulnerable(source))
	        {
	            return false;
	        }
	        else
	        {
	            return super.attackEntityFrom(source, amount);
	        }
	    }
	 
	 public int getVariant()
	    {
	        return MathHelper.clamp(((Integer)this.dataManager.get(VARIANT)).intValue(), 1, 4);
	    }
	 
	 public void setVariant(int p_191997_1_)
	    {
	        this.dataManager.set(VARIANT, Integer.valueOf(p_191997_1_));
	    }

	 public int getGender()
	    {
	        return MathHelper.clamp(((Integer)this.dataManager.get(GENDER)).intValue(), 1, 3);
	    }

	 public void setGender(int p_191997_1_)
	    {
	        this.dataManager.set(GENDER, Integer.valueOf(p_191997_1_));
	    }
	    
	 protected void entityInit()
	    {
	        super.entityInit();
	        this.dataManager.register(VARIANT, Integer.valueOf(0));
	        this.dataManager.register(WANDERING, Boolean.valueOf(true));
	        this.dataManager.register(GENDER, Integer.valueOf(0));
	    }    
	 
	 public void writeEntityToNBT(NBTTagCompound compound)
	    {
	        super.writeEntityToNBT(compound);
	        compound.setInteger("Variant", this.getVariant());
	        compound.setBoolean("Wandering", this.isWandering());
	        compound.setInteger("Gender", this.getGender());
	    }

	 public void readEntityFromNBT(NBTTagCompound compound) {
	        super.readEntityFromNBT(compound);
	        this.setVariant(compound.getInteger("Variant"));
	        this.setWandering(compound.getBoolean("Wandering"));
	        this.setGender(compound.getInteger("Gender"));
	    }

	 public void setWandering(boolean value) {
	        this.dataManager.set(WANDERING, Boolean.valueOf(value));
	    }

	 public boolean isWandering() {
	        return this.dataManager.get(WANDERING);
	    }
	     
	@Override
	protected void updateAITasks()
    	{
        this.deerTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    	}
	
	protected void clearAITasks()
		{
		tasks.taskEntries.clear();
		targetTasks.taskEntries.clear();
		}
	
	public static int getRandomSpecies(Random random)
		{
			int i = random.nextInt(100);

			if (i < 45)
			{
				return 1;
			}
			else if (i < 99)
			{
				return 2;
			}
			else
			{
				return 3;
			}
		}
	 
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	    {
	        if (id == 10)
	        {
	            this.deerTimer = 40;
	        }
	        else
	        {
	            super.handleStatusUpdate(id);
	        }
	    }

	@Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
		{
        return SoundsHandler.REINDEER_HURT;
		}
	
	@Override
    protected SoundEvent getDeathSound()
		{
        return SoundsHandler.REINDEER_DEATH;
		}
	
	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
		{
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
		}

	public boolean canBeLeashedTo(EntityPlayer player)
	    {
	    return super.canBeLeashedTo(player);
	    }
	
	public void setAIMoveSpeed(float speed) 
		{
			if (isBeingRidden()) 
			{
				super.setAIMoveSpeed(speed * 1.0F);
			} 
			else 
			{
				super.setAIMoveSpeed(speed);
			} 
		}
	
	protected double followLeashSpeed() 
		{
		return 2.0D;
		}

	@Override
	protected Item getDropItem() 
		{
		return new ItemStack(Items.MUTTON, (int) (1)).getItem();
		}
	
	@Nullable
	public void updatePassenger(Entity entity) 
		{
		 	if (isPassenger(entity)) 
		 	{
		 		float x = MathHelper.cos(this.renderYawOffset * 0.017453292F);
		 		float y = MathHelper.sin(this.renderYawOffset * 0.017453292F);
		 		float z = 0.3F;
		 		entity.setPosition(this.posX + (0.3F * y), this.posY + getMountedYOffset() + entity.getYOffset(), this.posZ - (0.3F * x));
		 	} 
	 	}
	
	 public double getMountedYOffset() 
	 	{
		return this.height * 0.78D;
	 	}

	public boolean canBeSteered() 
		{
		return true;
		}
	  
//    @Override
//    public boolean isNoDespawnRequired()
//    {
//        return isTamed();
//    }

//	
//	@Override
//	public void travel(float ti, float tj, float tk) {
//		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);
//		if (this.isBeingRidden()) {
//			this.rotationYaw = entity.rotationYaw;
//			this.prevRotationYaw = this.rotationYaw;
//			this.rotationPitch = entity.rotationPitch * 0.5F;
//			this.setRotation(this.rotationYaw, this.rotationPitch);
//			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.15F;
//			this.renderYawOffset = entity.rotationYaw;
//			this.rotationYawHead = entity.rotationYaw;
//			this.stepHeight = 1.0F;
//			if (entity instanceof EntityLivingBase) {
//				this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
//				float forward = ((EntityLivingBase) entity).moveForward;
//				float strafe = 0;
//				super.travel(strafe, 0, forward);
//			}
//			this.prevLimbSwingAmount = this.limbSwingAmount;
//			double d1 = this.posX - this.prevPosX;
//			double d0 = this.posZ - this.prevPosZ;
//			float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
//			if (f1 > 1.0F)
//				f1 = 1.0F;
//			this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
//			this.limbSwing += this.limbSwingAmount;
//			return;
//		}
//		this.stepHeight = 0.5F;
//		this.jumpMovementFactor = 0.02F;
//		super.travel(ti, tj, tk);
//	}
	
}

