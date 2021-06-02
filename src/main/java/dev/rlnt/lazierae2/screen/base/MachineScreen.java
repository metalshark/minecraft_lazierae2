package dev.rlnt.lazierae2.screen.base;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.rlnt.lazierae2.container.base.MachineContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class MachineScreen<C extends MachineContainer<?>> extends ContainerScreen<C> {

    private static final int ATLAS_HEIGHT = 166;
    private final ResourceLocation texture;
    private final int atlasWidth;

    MachineScreen(C container, PlayerInventory inventory, ITextComponent title, String texture, int atlasWidth) {
        super(container, inventory, title);
        this.texture = new ResourceLocation(MOD_ID, "textures/gui/" + texture + ".png");
        this.atlasWidth = atlasWidth;
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        renderTooltip(matrix, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        if (minecraft == null) return;
        //noinspection deprecation
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        minecraft.getTextureManager().bind(texture);
        draw(matrix, 0, 0, 0, imageWidth, imageHeight);
    }

    /**
     * Calls blit method with a set texture atlas from the respective machine.
     */
    void draw(MatrixStack matrix, int posX, int posY, int uOffset, int width, int height) {
        blit(matrix, leftPos + posX, topPos + posY, uOffset, 0, width, height, atlasWidth, ATLAS_HEIGHT);
    }
}
