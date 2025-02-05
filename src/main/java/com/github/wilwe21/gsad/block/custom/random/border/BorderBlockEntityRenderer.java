package com.github.wilwe21.gsad.block.custom.random.border;

import com.github.wilwe21.gsad.Gsad;
import com.github.wilwe21.gsad.render.CustomRenderLayers;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import org.joml.Matrix4f;

public class BorderBlockEntityRenderer<T extends BorderBlockEntity> implements BlockEntityRenderer<T> {
    public BorderBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    public void render(T entity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        try {
            BlockState state = entity.getWorld().getBlockState(entity.getPos());
            if (!(state.getBlock() instanceof AirBlock)) {
                Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
                this.renderSides(entity, matrix4f, vertexConsumerProvider.getBuffer(this.getLayer()));
            }
        } catch (Exception e) {
            Gsad.LOGGER.error(e.toString());
        }
    }

    private void renderSides(T entity, Matrix4f matrix, VertexConsumer vertexConsumer) {
        // DOWN
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.1F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.DOWN);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 0.1F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.DOWN);
        this.renderSide(entity, matrix, vertexConsumer, 1.0F, 1.0F, 0.1F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.DOWN);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 0.0F, 0.0F, 0.1F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.DOWN);
        // UP
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 0.9F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.UP);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.9F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.UP);
        this.renderSide(entity, matrix, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.9F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.UP);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 0.0F, 0.9F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.UP);
        // RIGHT
        this.renderSide(entity, matrix, vertexConsumer, 0.9F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 0.1F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
        this.renderSide(entity, matrix, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F, Direction.EAST);
        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.1F, 0.0F, 1.0F, 1.0F, Direction.WEST);

        // LEFT
//        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 0.1F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);

//        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.SOUTH);
//        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.NORTH);
//        this.renderSide(entity, matrix, vertexConsumer, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.EAST);
//        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.WEST);
//        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, Direction.DOWN);
//        this.renderSide(entity, matrix, vertexConsumer, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, Direction.UP);
    }

    private void renderSide(
            T entity, Matrix4f pose, VertexConsumer consumer, float x0, float x1, float y0, float y1, float z0, float z1, float z2, float z3, Direction side
    ) {
        if (shoudRender(entity, side)) {
            consumer.vertex(pose, x0, y0, z0);
            consumer.vertex(pose, x1, y0, z1);
            consumer.vertex(pose, x1, y1, z2);
            consumer.vertex(pose, x0, y1, z3);
        }
    }
    private boolean shoudRender(T entity, Direction dir) {
        boolean ret = false;
        switch (dir) {
            case WEST -> ret = rend(entity, entity.getWorld().getBlockState(entity.getPos().west(1)).getBlock());
            case EAST -> ret = rend(entity, entity.getWorld().getBlockState(entity.getPos().east(1)).getBlock());
            case UP -> ret = rend(entity, entity.getWorld().getBlockState(entity.getPos().up(1)).getBlock());
            case DOWN -> ret = rend(entity, entity.getWorld().getBlockState(entity.getPos().down(1)).getBlock());
            case NORTH -> ret = rend(entity, entity.getWorld().getBlockState(entity.getPos().north(1)).getBlock());
            case SOUTH -> ret = rend(entity, entity.getWorld().getBlockState(entity.getPos().south(1)).getBlock());
        }
        return ret;
    }
    private boolean rend(T entity, Block block) {
        return !(block instanceof BorderBlock) && (block instanceof AirBlock || block instanceof Waterloggable
                || block instanceof FenceGateBlock || block instanceof DoorBlock
                || block instanceof TorchBlock || block instanceof ButtonBlock
                || block instanceof RedstoneWireBlock || block instanceof RepeaterBlock
                || block instanceof ComparatorBlock || block instanceof TranslucentBlock
                || block instanceof BlockWithEntity
        );
    }

    protected RenderLayer getLayer() {
        return CustomRenderLayers.BORDER;
    }
}
