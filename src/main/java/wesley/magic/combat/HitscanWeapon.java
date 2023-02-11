package wesley.magic.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import wesley.magic.DarkMagicMod;
import wesley.magic.utils.Hitscan;

public abstract class HitscanWeapon extends Item {
    public HitscanWeapon(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient) return super.use(world, user, hand);

        // Perform hitscan
        EntityHitResult hit = Hitscan.playerHitscan(user, 20.0f);
        DarkMagicMod.LOGGER.info("SHOOTIN");

        if (hit != null) {

            DarkMagicMod.LOGGER.info(hit.toString());

            onHit(user, hit.getEntity());
        }

        return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
    }

    public abstract void onHit(PlayerEntity player, Entity target);
}
