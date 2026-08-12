package net.therealvoin.notjustspoiled.compat.mixin.thirstwastaken;

import dev.ghen.thirst.api.ThirstHelper;
import net.minecraft.world.item.ItemStack;
import net.therealvoin.notjustspoiled.common.foodspoilage.FoodSpoilageManager;
import net.therealvoin.notjustspoiled.common.foodspoilage.FoodStatus;
import net.therealvoin.notjustspoiled.common.util.NJSUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ThirstHelper.class, remap = false)
public abstract class ThirstHelperMixin {
    @Inject(method = "getThirst(Lnet/minecraft/world/item/ItemStack;)I", at = @At("RETURN"), cancellable = true)
    private static void modifyThirst(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        FoodStatus foodStatus = FoodSpoilageManager.getFoodStatus(itemStack, NJSUtils.getLevelWithoutContext());
        if (foodStatus == null) {
            return;
        }

        cir.setReturnValue(foodStatus.getModifiedNutrition(cir.getReturnValue()));
    }

    @Inject(method = "getQuenched(Lnet/minecraft/world/item/ItemStack;)I", at = @At("RETURN"), cancellable = true)
    private static void modifyQuenched(ItemStack itemStack, CallbackInfoReturnable<Integer> cir) {
        FoodStatus foodStatus = FoodSpoilageManager.getFoodStatus(itemStack, NJSUtils.getLevelWithoutContext());
        if (foodStatus == null) {
            return;
        }

        cir.setReturnValue(foodStatus.getModifiedNutrition(cir.getReturnValue()));
    }
}
