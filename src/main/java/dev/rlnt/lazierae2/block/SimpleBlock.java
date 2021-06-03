package dev.rlnt.lazierae2.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class SimpleBlock extends Block {

    public SimpleBlock() {
        super(Properties.of(Material.METAL).strength(3f).harvestTool(ToolType.PICKAXE).sound(SoundType.METAL));
    }
}
