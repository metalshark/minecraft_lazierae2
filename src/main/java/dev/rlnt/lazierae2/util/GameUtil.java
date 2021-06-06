package dev.rlnt.lazierae2.util;

import appeng.core.Api;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;

public class GameUtil {

    private GameUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets the recipe manager for the current world.
     * If world is null, return the recipe manager of
     * the game server.
     * @param world the world to get the recipe manager from
     * @return the recipe manager
     */
    public static RecipeManager getRecipeManager(@Nullable World world) {
        if (world != null && world.getServer() != null) return world.getServer().getRecipeManager();
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager();
    }

    /**
     * Checks if the item is a valid processor upgrade.
     * @param stack the ItemStack to check
     * @return true if the item is a valid processor upgrade, false otherwise
     */
    public static boolean isUpgrade(ItemStack stack) {
        return stack.getItem() == Api.instance().definitions().materials().cardSpeed().item();
    }
}
