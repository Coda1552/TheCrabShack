package coda.thecrabshack.common.init;

import coda.thecrabshack.TheCrabShack;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TCSFeatures {
    public static final DeferredRegister<Feature<?>> REGISTER = DeferredRegister.create(ForgeRegistries.FEATURES, TheCrabShack.MOD_ID);

    //public static final RegistryObject<HydrothermalVentFeature> HYDROTHERMAL_VENT = REGISTER.register("hydrothermal_vent", HydrothermalVentFeature::new);
}
