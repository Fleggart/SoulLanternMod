package com.example.soullantern.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockSoulLantern extends Block {
    public static final PropertyBool IS_HANGING = PropertyBool.create("hanging");
    
    protected static final AxisAlignedBB LANTERN_AABB = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);
    protected static final AxisAlignedBB LANTERN_HANGING_AABB = new AxisAlignedBB(0.3D, 0.1D, 0.3D, 0.7D, 0.7D, 0.7D);

    public BlockSoulLantern() {
        super(Material.IRON);
        setHardness(3.5F);
        setResistance(3.5F);
        setSoundType(SoundType.LANTERN);
        setLightLevel(0.7F);
        setTranslationKey("soul_lantern");
        setRegistryName("soul_lantern");
        setDefaultState(this.blockState.getBaseState().withProperty(IS_HANGING, false));
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return isValidBlock(worldIn, pos, worldIn.getBlockState(pos.up()), true) ||
               isValidBlock(worldIn, pos, worldIn.getBlockState(pos.down()), false);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(IS_HANGING) ? LANTERN_HANGING_AABB : LANTERN_AABB;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, 
                                            float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (facing == EnumFacing.UP) {
            return getDefaultState().withProperty(IS_HANGING, false);
        }
        if (facing == EnumFacing.DOWN) {
            return getDefaultState().withProperty(IS_HANGING, true);
        }
        return getStateFromMeta(meta);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!canBlockStay(worldIn, pos, state)) {
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        boolean isHanging = state.getValue(IS_HANGING);
        return isValidBlock(worldIn, pos, worldIn.getBlockState(pos.up()), true);
    }

    public boolean isValidBlock(World worldIn, BlockPos pos, IBlockState state, boolean hanging) {
        if (state.getBlock() instanceof BlockShulkerBox) return true;
        Block block = state.getBlock();
        if (block == Blocks.ANVIL) return true;
        BlockFaceShape shape = state.getBlockFaceShape(worldIn, pos, hanging ? EnumFacing.DOWN : EnumFacing.UP);
        return shape != BlockFaceShape.UNDEFINED;
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
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(IS_HANGING) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(IS_HANGING, meta == 1);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{IS_HANGING});
    }
}
