package dev.rlnt.lazierae2.screen;

import static dev.rlnt.lazierae2.Constants.AGGREGATOR_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.rlnt.lazierae2.container.AggregatorContainer;
import dev.rlnt.lazierae2.screen.base.ProcessorScreen;
import dev.rlnt.lazierae2.util.TypeEnums.PROGRESS_BAR_TYPE;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class AggregatorScreen extends ProcessorScreen<AggregatorContainer> {

    public static final int ATLAS_WIDTH = 202;
    public static final int PROGRESS_BAR_WIDTH = 24;
    public static final int PROGRESS_BAR_HEIGHT = 14;
    private static final int PROGRESS_BAR_POS_X = 84;
    private static final int PROGRESS_BAR_POS_Y = 36;

    public AggregatorScreen(AggregatorContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title, AGGREGATOR_ID, ATLAS_WIDTH);
    }

    @Override
    protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrix, partialTicks, mouseX, mouseY);

        // main progress bar
        drawProgressBar(
            matrix,
            PROGRESS_BAR_POS_X,
            PROGRESS_BAR_POS_Y,
            PROGRESS_BAR_WIDTH,
            PROGRESS_BAR_HEIGHT,
            PROGRESS_BAR_TYPE.SINGLE
        );
    }
}
