package dev.rlnt.lazierae2.screen.components;

import static dev.rlnt.lazierae2.Constants.MOD_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.rlnt.lazierae2.screen.base.AbstractButton;
import dev.rlnt.lazierae2.screen.base.ProcessorScreen;
import dev.rlnt.lazierae2.util.IOUtil;
import dev.rlnt.lazierae2.util.TextUtil;
import dev.rlnt.lazierae2.util.TypeEnums.IO_SETTING;
import dev.rlnt.lazierae2.util.TypeEnums.IO_SIDE;
import dev.rlnt.lazierae2.util.TypeEnums.TRANSLATE_TYPE;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class IOButton extends AbstractButton {

    private static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/component/io.png");
    private static final int BUTTON_POS_X = 146;
    private static final int BUTTON_POS_Y = 7;
    private static final int BUTTON_WIDTH = 17;
    private static final int BUTTON_HEIGHT = 17;
    private static final int FIELD_SIZE_INNER = 5;
    private static final int FIELD_SIZE = FIELD_SIZE_INNER + 1;
    private final ProcessorScreen<?> parent;
    private final IOField[] ioFields;
    private final Map<IO_SIDE, IO_SETTING> currentIOSettings;

    public IOButton(ProcessorScreen<?> parent, IPressable onPress) {
        super(parent, BUTTON_POS_X, BUTTON_POS_Y, BUTTON_WIDTH, BUTTON_HEIGHT, false, onPress);
        this.parent = parent;
        ioFields = setupIOFields();
        currentIOSettings = getSyncedSideConfig();
    }

    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrix, mouseX, mouseY, partialTicks);
        // io fields
        for (IOField field : ioFields) renderIOField(matrix, field);
        // tooltips
        if (isHovered) renderToolTip(matrix, mouseX, mouseY);
    }

    @Override
    protected int getTextureWidth() {
        return width + FIELD_SIZE_INNER;
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }

    @Override
    protected int getUOffset() {
        return 0;
    }

    @Override
    public void renderToolTip(MatrixStack matrix, int mouseX, int mouseY) {
        for (IOField field : ioFields) {
            if (!field.mouseOver(mouseX, mouseY)) continue;
            List<ITextComponent> tooltips = new ArrayList<>();
            tooltips.add(TextUtil.translate(TRANSLATE_TYPE.BUTTON, "io_header", TextFormatting.GOLD));
            tooltips.add(
                TextUtil
                    .translate(TRANSLATE_TYPE.BUTTON, "io_side", TextFormatting.GREEN)
                    .append(
                        TextUtil.colorize(
                            String.format(
                                " %s",
                                TextUtil
                                    .translate(TRANSLATE_TYPE.IO_SIDE, TextUtil.translateIOSide(field.side))
                                    .getString()
                            ),
                            TextFormatting.WHITE
                        )
                    )
            );
            tooltips.add(
                TextUtil
                    .translate(TRANSLATE_TYPE.BUTTON, "io_mode", TextFormatting.GREEN)
                    .append(
                        TextUtil.colorize(
                            String.format(
                                " %s",
                                TextUtil
                                    .translate(
                                        TRANSLATE_TYPE.IO_SETTING,
                                        TextUtil.translateIOSetting(field.getSettingForSide())
                                    )
                                    .getString()
                            ),
                            TextFormatting.WHITE
                        )
                    )
            );
            parent.renderComponentTooltip(matrix, tooltips, mouseX, mouseY);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        for (IOField field : ioFields) {
            if (field.mouseOver((int) mouseX, (int) mouseY)) {
                field.changeMode(Screen.hasShiftDown());
                break;
            }
        }
        super.onClick(mouseX, mouseY);
    }

    /**
     * Fills the fields array with all io fields and their position and the io side.
     * @return the filled io field array
     */
    private IOField[] setupIOFields() {
        IOField[] fields = new IOField[6];
        int i = -1;
        // top io config
        fields[++i] = new IOField(IO_SIDE.TOP, x + FIELD_SIZE, y + 1);
        // left io config
        fields[++i] = new IOField(IO_SIDE.LEFT, x + 1, y + FIELD_SIZE);
        // front io config
        fields[++i] = new IOField(IO_SIDE.FRONT, x + FIELD_SIZE, y + FIELD_SIZE);
        // right io config
        fields[++i] = new IOField(IO_SIDE.RIGHT, x + FIELD_SIZE + FIELD_SIZE_INNER, y + FIELD_SIZE);
        // bottom io config
        fields[++i] = new IOField(IO_SIDE.BOTTOM, x + FIELD_SIZE, y + FIELD_SIZE + FIELD_SIZE_INNER);
        // back io config
        fields[++i] = new IOField(IO_SIDE.BACK, x + FIELD_SIZE + FIELD_SIZE_INNER, y + FIELD_SIZE + FIELD_SIZE_INNER);
        return fields;
    }

    /**
     * Handles rendering of an io field.
     * @param matrix the matrix stack
     * @param field the io field to render
     */
    private void renderIOField(MatrixStack matrix, IOField field) {
        int sideOffset = field.getVOffset();
        if (sideOffset >= 0) blit(
            matrix,
            field.x,
            field.y,
            width,
            sideOffset,
            FIELD_SIZE_INNER,
            FIELD_SIZE_INNER,
            width + FIELD_SIZE_INNER,
            height
        );
    }

    public Map<IO_SIDE, IO_SETTING> getCurrentIOSettings() {
        return currentIOSettings;
    }

    private EnumMap<IO_SIDE, IO_SETTING> getSyncedSideConfig() {
        return parent.getMenu().getSideConfig();
    }

    private class IOField {

        private final IO_SIDE side;
        private final int x;
        private final int y;

        IOField(IO_SIDE side, int x, int y) {
            this.side = side;
            this.x = x;
            this.y = y;
        }

        /**
         * Checks if the passed in mouse position is on the field position.
         * @param mouseX the mouse position x-axis
         * @param mouseY the mouse position y-axis
         * @return true if the mouse is on the field, false otherwise
         */
        private boolean mouseOver(int mouseX, int mouseY) {
            return (mouseX >= x && mouseX < x + FIELD_SIZE_INNER && mouseY >= y && mouseY < y + FIELD_SIZE_INNER);
        }

        /**
         * Gets the current offset of the io setting texture depending
         * on the io configuration for the given site.
         * @return the offset of the io texture, negative number when io setting is NONE
         */
        private int getVOffset() {
            return (IOUtil.getIOSettingsMap().inverse().get(getSettingForSide()) * FIELD_SIZE_INNER - FIELD_SIZE_INNER);
        }

        /**
         * Gets the current io setting for the current side.
         * @return the current io setting
         */
        private IO_SETTING getSettingForSide() {
            IO_SETTING setting = getSyncedSideConfig().get(side);
            currentIOSettings.replace(side, setting);
            return setting;
        }

        /**
         * Changes the mode of an I/O field depending on its current state.
         * Will reset the field if shift is pressed while clicking.
         * @param reset whether the field should be reset or not
         */
        private void changeMode(boolean reset) {
            if (reset) {
                currentIOSettings.replace(side, IO_SETTING.NONE);
                return;
            }
            IO_SETTING setting = getSettingForSide();
            switch (setting) {
                case NONE:
                    setting = IO_SETTING.INPUT;
                    break;
                case INPUT:
                    setting = IO_SETTING.OUTPUT;
                    break;
                case OUTPUT:
                    setting = IO_SETTING.IO;
                    break;
                case IO:
                    setting = IO_SETTING.NONE;
                    break;
                default:
                    throw new IllegalArgumentException("There is no IO mode called " + setting);
            }
            currentIOSettings.replace(side, setting);
        }
    }
}
