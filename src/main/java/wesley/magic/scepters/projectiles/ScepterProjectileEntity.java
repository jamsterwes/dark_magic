package wesley.magic.scepters.projectiles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import wesley.magic.scepters.DarkMagicScepters;

public class ScepterProjectileEntity extends ThrownItemEntity {

	public ScepterProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}
 
	public ScepterProjectileEntity(World world, LivingEntity owner) {
		super(DarkMagicScepters.ScepterProjectileEntityType, owner, world); // null will be changed later
	}
 
	public ScepterProjectileEntity(World world, double x, double y, double z) {
		super(DarkMagicScepters.ScepterProjectileEntityType, x, y, z, world); // null will be changed later
	}
 
	@Override
	protected Item getDefaultItem() {
		return Items.DIAMOND; // We will configure this later, once we have created the ProjectileItem.
	}
 
	protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
		int i = entity instanceof BlazeEntity ? 3 : 0; // sets i to 3 if the Entity instance is an instance of BlazeEntity
		entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), (float)i); // deals damage
 
		if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
			livingEntity.damage(DamageSource.player((PlayerEntity)getOwner()), 7.0f);
			// livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 3, 0))); // applies a status effect
			// livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 2))); // applies a status effect
			// livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 20 * 3, 1))); // applies a status effect
			// livingEntity.playSound(SoundEvents.AMBIENT_CAVE, 2F, 1F); // plays a sound for the entity hit only
		}
	}
 
	protected void onCollision(HitResult hitResult) { // called on collision with a block
		super.onCollision(hitResult);
		if (!this.world.isClient) { // checks if the world is client
			this.world.sendEntityStatus(this, (byte)3); // particle?
			this.kill(); // kills the projectile
		}
 
	}
}