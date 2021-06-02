package dev.rlnt.lazierae2.util;

import javax.annotation.Nullable;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

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
        return ServerLifecycleHooks.getCurrentServer().getRecipeManager();
    }
}
