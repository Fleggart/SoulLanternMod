package com.example.soullantern.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
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

    public BlockSoulLantern() {
        super(Material.IRON);
        setHardness(3.5F);
        setResistance(3.5F);
        setSoundType(SoundType.METAL);  // ✅ 修复：使用原版音效
        setLightLevel(0.7F);
        setTranslationKey("soul_lantern");
        setRegistryName("soul_lantern");
        setDefaultState(this.blockState.getBaseState().withProperty(HANGING, false));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, 
                                            float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getDefaultState().withProperty(HANGING, facing == EnumFacing.DOWN);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return canBlockStay(world, pos, getDefaultState());
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if (!canBlockStay(world, pos, state)) {
            dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
        }
    }

    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return !world.isAirBlock(pos.up()) && world.getBlockState(pos.up()).isFullCube();
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
        return new BlockStateContainer(this, new IProperty[]{HANGING});
    }
}
