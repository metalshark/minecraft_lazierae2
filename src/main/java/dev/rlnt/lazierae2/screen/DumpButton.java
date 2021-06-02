package dev.rlnt.lazierae2.screen;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.rlnt.lazierae2.screen.base.ProcessorScreen;
import dev.rlnt.lazierae2.util.TextUtil;
import dev.rlnt.lazierae2.util.TypeEnums.TRANSLATE_TYPE;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class DumpButton extends Button {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/component/dump.png");
    private static final int BUTTON_TIMEOUT = 10;
    private static final int BUTTON_POS_X = 145;
    private static final int BUTTON_POS_Y = 50;
    private static final int BUTTON_WIDTH = 18;
    private static final int BUTTON_HEIGHT = 10;
    private final ProcessorScreen<?> parent;
    private int buttonTimeout = BUTTON_TIMEOUT;

    public DumpButton(ProcessorScreen<?> parent, IPressable onPress) {
        super(
            parent.getGuiLeft() + BUTTON_POS_X,
            parent.getGuiTop() + BUTTON_POS_Y,
            BUTTON_WIDTH,
            BUTTON_HEIGHT,
            StringTextComponent.EMPTY,
            onPress
        );
        this.parent = parent;
    }

    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        Minecraft.getInstance().getTextureManager().bind(TEXTURE);
        blit(matrix, x, y, buttonTimeout < BUTTON_TIMEOUT ? width : 0, 0, width, height, width * 2, height);
        if (buttonTimeout < BUTTON_TIMEOUT) buttonTimeout++;
        if (isHovered) renderToolTip(matrix, mouseX, mouseY);
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int mouseX, int mouseY) {
        List<ITextComponent> tooltips = new ArrayList<>();
        tooltips.add(TextUtil.translate(TRANSLATE_TYPE.BUTTON, "dump_header", TextFormatting.RED));
        tooltips.add(TextUtil.translate(TRANSLATE_TYPE.BUTTON, "dump_info", TextFormatting.WHITE));
        parent.renderComponentTooltip(matrix, tooltips, mouseX, mouseY);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        buttonTimeout = 0;
        super.onClick(mouseX, mouseY);
    }
}
