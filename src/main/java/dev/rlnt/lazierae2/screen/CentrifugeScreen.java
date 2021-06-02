package dev.rlnt.lazierae2.screen;

import static dev.rlnt.lazierae2.Constants.CENTRIFUGE_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.rlnt.lazierae2.container.CentrifugeContainer;
import dev.rlnt.lazierae2.screen.base.ProcessorScreen;
import dev.rlnt.lazierae2.util.TypeEnums.PROGRESS_BAR_TYPE;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class CentrifugeScreen extends ProcessorScreen<CentrifugeContainer> {

    public static final int ATLAS_WIDTH = 200;
    public static final int PROGRESS_BAR_WIDTH = 22;
    public static final int PROGRESS_BAR_HEIGHT = 14;
    private static final int PROGRESS_BAR_POS_X = 80;
    private static final int PROGRESS_BAR_POS_Y = 36;

    public CentrifugeScreen(CentrifugeContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title, CENTRIFUGE_ID, ATLAS_WIDTH);
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
