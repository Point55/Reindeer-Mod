package com.reindeermod.point5.entity.reindeer;

import com.reindeermod.point5.util.Reference;

import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ModelReindeer extends AnimatedGeoModel
{
	@Override
	public ResourceLocation getModelLocation(Object entity)
	{
//		if (object.isChild()) {
//            return new ResourceLocation(Reference.MOD_ID, "geo/entity/reindeer/babyreindeer.geo.json");
//        }
		return new ResourceLocation(Reference.ModID, "geo/entity/modelreindeer.geo.json");
	}
	
	@Override
	public ResourceLocation getTextureLocation(Object entity)
	{
//		if (object.isChild()) {
//            return new ResourceLocation(Reference.MOD_ID, "textures/entity/reindeer/babyreindeer.png");
//        }
		return new ResourceLocation(Reference.ModID, "textures/entity/reindeer/reindeer.png");
	}
	
	@Override
	public ResourceLocation getAnimationFileLocation(Object animatable)
	{
		return new ResourceLocation(Reference.ModID, "animations/entity/reindeer/modelreindeer.json");
	}

//	@Override
//	public void setLivingAnimations(IAnimatable entity, Integer uniqueID, AnimationEvent customPredicate)
//	{
//		super.setLivingAnimations(entity, uniqueID, customPredicate);
//		IBone head = this.getAnimationProcessor().getBone("head");
//
//		EntityLivingBase entityIn = (EntityLivingBase) entity;
//		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
//		head.setRotationX(extraData.headPitch * ((float)Math.PI / 180F));
//		head.setRotationY(extraData.netHeadYaw * ((float)Math.PI / 180F));
//	}
}
