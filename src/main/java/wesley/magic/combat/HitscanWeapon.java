package wesley.magic.combat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import wesley.magic.DarkMagicMod;
import wesley.magic.registries.DarkMagicItems;
import wesley.magic.utils.DarkMagicItem;
import wesley.magic.utils.Hitscan;

public abstract class HitscanWeapon extends DarkMagicItem {

    public HitscanWeapon(Lore lore, Settings settings) {
        super(lore, settings);
    }

    // Used to enable alt fire
    protected void enableAltFire(Item thisItem, SoundEvent altFireSound) {
        // Register alt fire
        DarkMagicKeybinds.listen(DarkMagicKeybinds.altFire, client -> {
            // If no player, fail
            if (client.player == null) return;

            // ONLY ALT FIRE FROM MAIN HAND
            ItemStack inHand = client.player.getStackInHand(Hand.MAIN_HAND);
            if (inHand.getItem() == thisItem) {
                // Check cooldown
                if (!client.player.getItemCooldownManager().isCoolingDown(this)) {
                    altFire(client.player);

                    // TODO: move this elsewhere?
                    client.player.playSound(altFireSound, 1.0f, 1.0f);
                }
            }
        });
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient) return super.use(world, user, hand);

        // TODO: move this somewhere else
        float rangeMultiplier = 1.0f;
        if (user.getInventory().contains(new ItemStack(DarkMagicItems.CRYSTAL_OF_BALOR))) {
            rangeMultiplier += 1.0f;
        }

        // Perform hitscan
        EntityHitResult hit = Hitscan.playerHitscan(user, 25.0f * rangeMultiplier);

        if (hit != null) {
            onHit(user, hit);
            return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
        }
        
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    public abstract void onHit(PlayerEntity player, EntityHitResult target);
    public void altFire(PlayerEntity player) {}
}
