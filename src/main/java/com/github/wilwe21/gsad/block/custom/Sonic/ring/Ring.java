package com.github.wilwe21.gsad.block.custom.Sonic.ring;

import com.github.wilwe21.gsad.Helpers;
import com.github.wilwe21.gsad.block.ModBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Ring extends BlockWithEntity {
    public static final float minX = 0.2f;
    public static final float minY = 0.15f;
    public static final float minZ = 0.2f;
    public static final float maxX = 0.8f;
    public static final float maxY = 0.7f;
    public static final float maxZ = 0.8f;
    public Ring(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof PlayerEntity ent) {
            if (Helpers.isIntersect(world, ent, state, pos)) {
                world.playSound(entity, pos, SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlock.RING)));
            }
        }
    }

    @Override
    protected MapCodec<? extends Ring> getCodec() {
        return createCodec(Ring::new);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RingEntity(pos, state);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context) {
            return VoxelShapes.cuboid(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
