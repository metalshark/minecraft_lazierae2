package dev.rlnt.lazierae2.screen.components;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.rlnt.lazierae2.screen.base.AbstractButton;
import dev.rlnt.lazierae2.screen.base.ProcessorScreen;
import dev.rlnt.lazierae2.util.TextUtil;
import dev.rlnt.lazierae2.util.TypeEnums.TRANSLATE_TYPE;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class ExtractButton extends AbstractButton {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/component/extract.png");
    private static final int BUTTON_POS_X = 145;
    private static final int BUTTON_POS_Y = 25;
    private static final int BUTTON_WIDTH = 18;
    private static final int BUTTON_HEIGHT = 17;
    private final ProcessorScreen<?> parent;

    public ExtractButton(ProcessorScreen<?> parent, IPressable onPress) {
        super(parent, BUTTON_POS_X, BUTTON_POS_Y, BUTTON_WIDTH, BUTTON_HEIGHT, true, onPress);
        this.parent = parent;
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int mouseX, int mouseY) {
        List<ITextComponent> tooltips = new ArrayList<>();
        tooltips.add(TextUtil.translate(TRANSLATE_TYPE.BUTTON, "extract_header", TextFormatting.GOLD));
        tooltips.add(
            TextUtil
                .translate(TRANSLATE_TYPE.BUTTON, "extract_mode", TextFormatting.GREEN)
                .append(
                    TextUtil.colorize(
                        String.format(
                            " %s",
                            TextUtil
                                .translate(
                                    TRANSLATE_TYPE.EXTRACT_SETTING,
                                    parent.getMenu().isAutoExtracting() ? "activated" : "deactivated"
                                )
                                .getString()
                        ),
                        TextFormatting.WHITE
                    )
                )
        );
        parent.renderComponentTooltip(matrix, tooltips, mouseX, mouseY);
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    protected int getUOffset() {
        return parent.getMenu().isAutoExtracting() ? width : 0;
    }
}
