package com.example.soullantern.blocks;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSoulTorch extends BlockTorch {
    public BlockSoulTorch() {
        super();
        setHardness(0.0F);
        setSoundType(SoundType.WOOD);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setLightLevel(0.7F);
        setTranslationKey("soul_torch");
        setRegistryName("soul_torch");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        EnumFacing enumfacing = stateIn.getValue(FACING);
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;

        if (enumfacing.getAxis().isHorizontal()) {
            EnumFacing enumfacing1 = enumfacing.getOpposite();
            double dx = d0 + 0.27D * enumfacing1.getXOffset();
            double dy = d1 + 0.22D;
            double dz = d2 + 0.27D * enumfacing1.getZOffset();
            
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, dx, dy, dz, 0.0D, 0.0D, 0.0D);
            spawnSoulFlameParticle(worldIn, dx, dy, dz);
        } else {
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            spawnSoulFlameParticle(worldIn, d0, d1, d2);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnSoulFlameParticle(World world, double x, double y, double z) {
        // 使用原版粒子近似灵魂火焰
        world.spawnParticle(EnumParticleTypes.PORTAL, x, y, z, 0.0D, 0.0D, 0.0D);
    }
}
