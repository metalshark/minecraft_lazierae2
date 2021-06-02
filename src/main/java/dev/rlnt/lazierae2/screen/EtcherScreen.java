package dev.rlnt.lazierae2.screen;

import static dev.rlnt.lazierae2.Constants.ETCHER_ID;

import com.mojang.blaze3d.matrix.MatrixStack;
import dev.rlnt.lazierae2.container.EtcherContainer;
import dev.rlnt.lazierae2.screen.base.ProcessorScreen;
import dev.rlnt.lazierae2.util.TypeEnums.PROGRESS_BAR_TYPE;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class EtcherScreen extends ProcessorScreen<EtcherContainer> {

    public static final int ATLAS_WIDTH = 222;
    public static final int PROGRESS_BAR_WIDTH = 22;
    public static final int PROGRESS_BAR_HEIGHT = 14;
    private static final int PROGRESS_BAR_POS_X = 84;
    private static final int PROGRESS_BAR_POS_Y = 36;

    public EtcherScreen(EtcherContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title, ETCHER_ID, ATLAS_WIDTH);
    }

    @Override
    protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrix, partialTicks, mouseX, mouseY);

        // primary progress bar
        drawProgressBar(
            matrix,
            PROGRESS_BAR_POS_X,
            PROGRESS_BAR_POS_Y,
            PROGRESS_BAR_WIDTH,
            PROGRESS_BAR_HEIGHT,
            PROGRESS_BAR_TYPE.PRIMARY
        );

        // secondary progress bar
        drawProgressBar(matrix, 55, 22, PROGRESS_BAR_WIDTH, 42, PROGRESS_BAR_TYPE.SECONDARY);
    }
}
