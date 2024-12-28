package com.github.wilwe21.gsad.block.custom.tv;

import com.github.wilwe21.gsad.block.types.BlockTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class TvEntity extends BlockEntity {
    public TvEntity(BlockPos pos, BlockState state) {
        super(BlockTypes.TV, pos, state);
    }
}
