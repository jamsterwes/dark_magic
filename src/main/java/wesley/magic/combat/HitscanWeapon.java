package wesley.magic.combat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import wesley.magic.utils.DarkMagicItem;
import wesley.magic.utils.Hitscan;

public abstract class HitscanWeapon extends DarkMagicItem {

    public HitscanWeapon(Lore lore, Settings settings) {
        super(lore, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient) return super.use(world, user, hand);

        // Perform hitscan
        EntityHitResult hit = Hitscan.playerHitscan(user, 50.0f);

        if (hit != null) {
            onHit(user, hit);
        }

        return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
    }

    public abstract void onHit(PlayerEntity player, EntityHitResult target);
}
