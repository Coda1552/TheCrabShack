package coda.thecrabshack.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class HydrothermalVentBlock extends Block {
    private final boolean spawnParticles;

    public HydrothermalVentBlock(boolean spawnParticles, Properties properties) {
        super(properties);
        this.spawnParticles = spawnParticles;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        Random random = world.getRandom();
        BasicParticleType basicparticletype = ParticleTypes.CAMPFIRE_SIGNAL_SMOKE;
        world.addAlwaysVisibleParticle(basicparticletype, true, (double) pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), (double) pos.getY() + random.nextDouble() + random.nextDouble(), (double) pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
    }

}
