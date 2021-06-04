package dev.rlnt.lazierae2.screen.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public abstract class AbstractButton extends Button {

    private final boolean tooltipDrawing;

    protected AbstractButton(
        ProcessorScreen<?> parent,
        int posX,
        int posY,
        int width,
        int height,
        boolean tooltipDrawing,
        IPressable onPress
    ) {
        super(parent.getGuiLeft() + posX, parent.getGuiTop() + posY, width, height, StringTextComponent.EMPTY, onPress);
        this.width = width;
        this.height = height;
        this.tooltipDrawing = tooltipDrawing;
    }

    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        Minecraft.getInstance().getTextureManager().bind(getTexture());
        blit(matrix, x, y, getUOffset(), 0, width, height, getTextureWidth(), height);
        // tooltips
        if (tooltipDrawing && isHovered) renderToolTip(matrix, mouseX, mouseY);
    }

    protected int getTextureWidth() {
        return width * 2;
    }

    protected abstract ResourceLocation getTexture();

    protected abstract int getUOffset();
}
