package net.therealvoin.notjustspoiled.compat.mixin.thirstwastaken;

import dev.ghen.thirst.content.thirst.PlayerThirst;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.therealvoin.notjustspoiled.common.foodspoilage.FoodEnvironment;
import net.therealvoin.notjustspoiled.common.foodspoilage.FoodSpoilageManager;
import net.therealvoin.notjustspoiled.common.foodspoilage.FoodStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerThirst.class, remap = false)
public abstract class PlayerThirstMixin {
    @Inject(method = "drink(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)V", at = @At("HEAD"))
    private static void applySpoilageEffects(ItemStack itemStack, Player player, CallbackInfo ci) {
        Level level = player.level();
        FoodStatus foodStatus = FoodSpoilageManager.getFoodStatus(itemStack, level);
        if (foodStatus == null) {
            return;
        }

        FoodSpoilageManager.updateFoodLifetime(itemStack, FoodEnvironment.INVENTORY, level);
        foodStatus.applyEffects(player);
    }
}
