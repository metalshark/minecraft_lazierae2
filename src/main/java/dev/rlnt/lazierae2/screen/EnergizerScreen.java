package dev.rlnt.lazierae2.screen;

import static dev.rlnt.lazierae2.Constants.ENERGIZER_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.rlnt.lazierae2.container.EnergizerContainer;
import dev.rlnt.lazierae2.screen.base.ProcessorScreen;
import dev.rlnt.lazierae2.util.TypeEnums.PROGRESS_BAR_TYPE;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class EnergizerScreen extends ProcessorScreen<EnergizerContainer> {

    public static final int ATLAS_WIDTH = 200;
    public static final int PROGRESS_BAR_WIDTH = 22;
    public static final int PROGRESS_BAR_HEIGHT = 29;
    private static final int PROGRESS_BAR_POS_X = 81;
    private static final int PROGRESS_BAR_POS_Y = 29;

    public EnergizerScreen(EnergizerContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title, ENERGIZER_ID, ATLAS_WIDTH);
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
