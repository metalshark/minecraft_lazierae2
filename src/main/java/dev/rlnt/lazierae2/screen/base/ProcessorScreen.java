package dev.rlnt.lazierae2.screen.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.rlnt.lazierae2.container.base.ProcessorContainer;
import dev.rlnt.lazierae2.inventory.component.UpgradeSlot;
import dev.rlnt.lazierae2.network.EnergyResetPacket;
import dev.rlnt.lazierae2.network.ExtractTogglePacket;
import dev.rlnt.lazierae2.network.IOUpdatePacket;
import dev.rlnt.lazierae2.network.PacketHandler;
import dev.rlnt.lazierae2.screen.components.DumpButton;
import dev.rlnt.lazierae2.screen.components.ExtractButton;
import dev.rlnt.lazierae2.screen.components.IOButton;
import dev.rlnt.lazierae2.setup.ModConfig;
import dev.rlnt.lazierae2.util.IOUtil;
import dev.rlnt.lazierae2.util.TextUtil;
import dev.rlnt.lazierae2.util.TypeEnums.PROGRESS_BAR_TYPE;
import dev.rlnt.lazierae2.util.TypeEnums.TRANSLATE_TYPE;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ProcessorScreen<C extends ProcessorContainer<?>> extends MachineScreen<C> {

    private static final int ENERGY_BAR_POS_X = 166;
    private static final int ENERGY_BAR_POS_Y = 78;
    private static final int ENERGY_BAR_OFFSET_U = 176;
    private static final int ENERGY_BAR_WIDTH = 2;
    private static final int ENERGY_BAR_HEIGHT = 70;
    private static final int PROGRESS_BAR_OFFSET_U = 178;

    protected ProcessorScreen(
        C container,
        PlayerInventory inventory,
        ITextComponent title,
        String texture,
        int atlasWidth
    ) {
        super(container, inventory, title, texture, atlasWidth);
    }

    public static TranslationTextComponent getDetailsTooltip() {
        return (TranslationTextComponent) TextUtil
            .translate(TRANSLATE_TYPE.TOOLTIP, "shift_details_1", TextFormatting.GRAY)
            .append(" ")
            .append(
                TextUtil.colorize(
                    InputMappings.getKey("key.keyboard.left.shift").getDisplayName().getString(),
                    TextFormatting.AQUA
                )
            )
            .append(" ")
            .append(TextUtil.translate(TRANSLATE_TYPE.TOOLTIP, "shift_details_2", TextFormatting.GRAY));
    }

    @Override
    protected void init() {
        super.init();
        addButton(new DumpButton(this, button -> PacketHandler.channel.sendToServer(new EnergyResetPacket())));
        addButton(new ExtractButton(this, button -> PacketHandler.channel.sendToServer(new ExtractTogglePacket())));
        addButton(
            new IOButton(
                this,
                button ->
                    PacketHandler.channel.sendToServer(
                        new IOUpdatePacket(IOUtil.serializeSideConfig(((IOButton) button).getCurrentIOSettings()))
                    )
            )
        );
    }

    @Override
    protected void renderTooltip(MatrixStack matrix, int mouseX, int mouseY) {
        // energy bar tooltip
        if (
            isWithinRegion(
                mouseX,
                mouseY,
                ENERGY_BAR_POS_X - 1,
                ENERGY_BAR_POS_X + ENERGY_BAR_WIDTH + 1,
                ENERGY_BAR_POS_Y - ENERGY_BAR_HEIGHT - 1,
                ENERGY_BAR_POS_Y + 1
            )
        ) {
            List<ITextComponent> tooltips = new ArrayList<>();
            // header
            tooltips.add(TextUtil.translate(TRANSLATE_TYPE.TOOLTIP, "energy_buffer", TextFormatting.GOLD));
            // blank line
            tooltips.add(StringTextComponent.EMPTY);
            // information
            tooltips.add(
                TextUtil
                    .translate(TRANSLATE_TYPE.TOOLTIP, "energy_stored", TextFormatting.GREEN)
                    .append(
                        TextUtil.colorize(
                            String.format(
                                " %s / %s",
                                TextUtil.formatEnergy(menu.getEnergyStored(), hasShiftDown()),
                                TextUtil.formatEnergy(menu.getEnergyCapacity(), hasShiftDown())
                            ),
                            TextFormatting.WHITE
                        )
                    )
            );
            tooltips.add(
                TextUtil
                    .translate(TRANSLATE_TYPE.TOOLTIP, "energy_rate", TextFormatting.GREEN)
                    .append(
                        TextUtil.colorize(
                            String.format(
                                " %s/t",
                                TextUtil.formatEnergy(menu.getEffectiveEnergyConsumption(), hasShiftDown())
                            ),
                            TextFormatting.WHITE
                        )
                    )
            );
            // more details line with blank line only when shift is not pressed
            if (!hasShiftDown()) {
                tooltips.add(StringTextComponent.EMPTY);
                tooltips.add(getDetailsTooltip());
            }
            // render the tooltip
            renderComponentTooltip(matrix, tooltips, mouseX, mouseY);
            return;
        }

        // upgrade slot tooltip
        if (hoveredSlot instanceof UpgradeSlot) {
            int upgradeAmount = hoveredSlot.getItem().getCount();
            List<ITextComponent> tooltips = new ArrayList<>();
            // header
            tooltips.add(TextUtil.translate(TRANSLATE_TYPE.TOOLTIP, "upgrade_slot", TextFormatting.GOLD));
            // information
            tooltips.add(
                TextUtil
                    .translate(TRANSLATE_TYPE.TOOLTIP, "upgrade_amount", TextFormatting.GREEN)
                    .append(
                        TextUtil.colorize(
                            String.format(" %s / %s", upgradeAmount, ModConfig.PROCESSING.processorUpgradeSlots.get()),
                            TextFormatting.WHITE
                        )
                    )
            );
            // only if there is at least one upgrade in the slot
            if ((hoveredSlot.hasItem())) {
                tooltips.add(StringTextComponent.EMPTY);
                tooltips.add(
                    TextUtil
                        .translate(TRANSLATE_TYPE.TOOLTIP, "upgrade_processing_multiplier", TextFormatting.GREEN)
                        .append(
                            TextUtil.colorize(
                                String.format(
                                    " x%s",
                                    TextUtil.formatNumber(
                                        menu.getProcessTimeMultiplier(upgradeAmount),
                                        1,
                                        hasShiftDown() ? 6 : 3
                                    )
                                ),
                                TextFormatting.WHITE
                            )
                        )
                );
                tooltips.add(
                    TextUtil
                        .translate(TRANSLATE_TYPE.TOOLTIP, "upgrade_consumption_multiplier", TextFormatting.GREEN)
                        .append(
                            TextUtil.colorize(
                                String.format(
                                    " x%s",
                                    TextUtil.formatNumber(
                                        menu.getEnergyConsumptionMultiplier(),
                                        1,
                                        hasShiftDown() ? 6 : 3
                                    )
                                ),
                                TextFormatting.WHITE
                            )
                        )
                );
                tooltips.add(
                    TextUtil
                        .translate(TRANSLATE_TYPE.TOOLTIP, "upgrade_capacity_additional", TextFormatting.GREEN)
                        .append(
                            TextUtil.colorize(
                                String.format(
                                    " %s",
                                    TextUtil.formatEnergy(menu.getEnergyCapacityAdditional(), hasShiftDown())
                                ),
                                TextFormatting.WHITE
                            )
                        )
                );
                // more details line with blank line only when shift is not pressed
                if (!hasShiftDown()) {
                    tooltips.add(StringTextComponent.EMPTY);
                    tooltips.add(getDetailsTooltip());
                }
            }
            // render the tooltip
            renderComponentTooltip(matrix, tooltips, mouseX, mouseY);
            return;
        }

        // continue drawing other tooltips like item names
        super.renderTooltip(matrix, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrix, partialTicks, mouseX, mouseY);

        // energy bar
        draw(
            matrix,
            ENERGY_BAR_POS_X,
            ENERGY_BAR_POS_Y - getEnergyBarHeight(),
            ENERGY_BAR_OFFSET_U,
            ENERGY_BAR_WIDTH,
            getEnergyBarHeight()
        );
    }

    /**
     * Handles drawing the progress bar in a processor depending on its position,
     * the height and which type it is.
     */
    protected void drawProgressBar(
        MatrixStack matrix,
        int posX,
        int posY,
        int width,
        int height,
        PROGRESS_BAR_TYPE type
    ) {
        if (!menu.isProcessing()) return;
        int offsetU = PROGRESS_BAR_OFFSET_U;
        float progress = menu.getProgress();
        float processTime = menu.getEffectiveProcessTime();
        float length;

        switch (type) {
            case PRIMARY:
                length = (progress - processTime / 2) * width / (processTime / 2);
                break;
            case SECONDARY:
                length = progress * 2 * width / processTime;
                offsetU = 200;
                break;
            default:
                length = progress * width / processTime;
                break;
        }
        length++;

        draw(matrix, posX, posY, offsetU, MathHelper.clamp((int) length, 0, width), height);
    }

    /**
     * Calculates the energy bar height in pixels depending on the
     * current stored energy and the maximum capacity.
     * @return the energy bar height in pixels
     */
    private int getEnergyBarHeight() {
        int max = menu.getEnergyCapacity();
        int energy = MathHelper.clamp(menu.getEnergyStored(), 0, max);
        return max > 0 ? energy * ENERGY_BAR_HEIGHT / max : 0;
    }

    /**
     * Checks if the mouse cursor is within a specified region.
     * @param mouseX mouse position on the x-axis
     * @param mouseY mouse position on the y-axis
     * @param leftBound left boundary on the x-axis
     * @param rightBound right boundary on the x-axis
     * @param topBound top boundary on the y-axis
     * @param bottomBound bottom boundary on the y-axis
     * @return true if the curser is within the region, false otherwise
     */
    @SuppressWarnings("SameParameterValue")
    private boolean isWithinRegion(
        int mouseX,
        int mouseY,
        int leftBound,
        int rightBound,
        int topBound,
        int bottomBound
    ) {
        return (
            mouseX >= leftPos + leftBound &&
            mouseX <= leftPos + rightBound &&
            mouseY >= topPos + topBound &&
            mouseY <= topPos + bottomBound
        );
    }
}
