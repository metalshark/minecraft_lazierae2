package dev.rlnt.lazierae2.setup;

import dev.rlnt.lazierae2.Constants;
import javax.annotation.Nonnull;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModTab extends ItemGroup {

    static final ModTab TAB = new ModTab(Constants.MOD_ID);

    private ModTab(String label) {
        super(label);
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModItems.FLUIX_STEEL.get());
    }
}
