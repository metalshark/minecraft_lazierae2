package dev.rlnt.lazierae2.screen.base;

import com.mojang.blaze3d.matrix.MatrixStack;

public abstract class ClickButton extends AbstractButton {

    private static final int BUTTON_TIMEOUT = 10;
    private int buttonTimeout = BUTTON_TIMEOUT;

    protected ClickButton(
        ProcessorScreen<?> parent,
        int posX,
        int posY,
        int width,
        int height,
        boolean tooltipDrawing,
        IPressable onPress
    ) {
        super(parent, posX, posY, width, height, tooltipDrawing, onPress);
    }

    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrix, mouseX, mouseY, partialTicks);
        if (buttonTimeout < BUTTON_TIMEOUT) buttonTimeout++;
    }

    @Override
    protected int getUOffset() {
        return buttonTimeout < BUTTON_TIMEOUT ? width : 0;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        buttonTimeout = 0;
        super.onClick(mouseX, mouseY);
    }
}
