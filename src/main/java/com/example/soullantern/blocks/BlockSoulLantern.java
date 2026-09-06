package com.example.soullantern.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSoulLantern extends Block {
    public static final PropertyBool HANGING = PropertyBool.create("hanging");
    
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);
    protected static final AxisAlignedBB AABB_HANGING = new AxisAlignedBB(0.3D, 0.1D, 0.3D, 0.7D, 0.6D, 0.7D);

    public BlockSoulLantern() {
        super(Material.IRON);
        setHardness(3.5F);
        setResistance(3.5F);
        setSoundType(SoundType.METAL);
        setLightLevel(0.7F);
        setTranslationKey("soul_lantern");
        setRegistryName("soul_lantern");
        setDefaultState(this.blockState.getBaseState().withProperty(HANGING, false));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(HANGING) ? AABB_HANGING : AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        // 检查上方是否能挂（吊挂模式）
        if (canPlaceOn(world, pos.up(), EnumFacing.DOWN)) {
            return true;
        }
        // 检查下方是否能放（站立模式）
        if (canPlaceOn(world, pos.down(), EnumFacing.UP)) {
            return true;
        }
        return false;
    }

    /**
     * ✅ 检测方块是否能支撑灯笼
     * 支持：完整方块、栅栏、墙、潜影盒、铁砧等
     */
    private boolean canPlaceOn(World world, BlockPos pos, EnumFacing facing) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        
        if (block == null || block == Blocks.AIR) return false;
        
        // 潜影盒可以支撑
        if (block instanceof net.minecraft.block.BlockShulkerBox) return true;
        
        // 铁砧可以支撑
        if (block == Blocks.ANVIL) return true;
        
        // ✅ 栅栏和墙可以支撑
        if (block instanceof BlockFence || block instanceof BlockWall) return true;
        
        // 检查方块是否完整（能提供支撑面）
        BlockFaceShape shape = state.getBlockFaceShape(world, pos, facing.getOpposite());
        if (shape == BlockFaceShape.SOLID || shape == BlockFaceShape.MIDDLE_POLE) {
            return true;
        }
        
        // 标准完整方块检测
        if (state.isFullBlock() || state.isFullCube()) {
            return true;
        }
        
        // 允许铁轨、按钮等特殊方块（可选）
        if (state.isTopSolid()) {
            return true;
        }
        
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, 
                                            float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        // 如果点击的是方块顶部 → 悬挂（灯笼挂在下方）
        if (facing == EnumFacing.DOWN) {
            if (canPlaceOn(world, pos.up(), EnumFacing.DOWN)) {
                return getDefaultState().withProperty(HANGING, true);
            }
        }
        // 如果点击的是方块底部 → 站立（灯笼放在上方）
        if (facing == EnumFacing.UP) {
            if (canPlaceOn(world, pos.down(), EnumFacing.UP)) {
                return getDefaultState().withProperty(HANGING, false);
            }
        }
        
        // 自动选择：先试悬挂
        if (canPlaceOn(world, pos.up(), EnumFacing.DOWN)) {
            return getDefaultState().withProperty(HANGING, true);
        }
        // 再试站立
        if (canPlaceOn(world, pos.down(), EnumFacing.UP)) {
            return getDefaultState().withProperty(HANGING, false);
        }
        
        // 无法放置，返回默认（会被 canPlaceBlockAt 拦截）
        return getDefaultState().withProperty(HANGING, false);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (!canBlockStay(world, pos, state)) {
            dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        boolean isHanging = state.getValue(HANGING);
        if (isHanging) {
            return canPlaceOn(world, pos.up(), EnumFacing.DOWN);
        } else {
            return canPlaceOn(world, pos.down(), EnumFacing.UP);
        }
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HANGING) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(HANGING, meta == 1);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HANGING);
    }
}
