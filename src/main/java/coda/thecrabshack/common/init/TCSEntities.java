package coda.thecrabshack.common.init;

import coda.thecrabshack.TheCrabShack;
import coda.thecrabshack.common.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TCSEntities {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, TheCrabShack.MOD_ID);

    public static final RegistryObject<EntityType<RubberDuckyIsopodEntity>> RUBBER_DUCKY_ISOPOD = create("rubber_ducky_isopod", EntityType.Builder.of(RubberDuckyIsopodEntity::new, EntityClassification.CREATURE).sized(0.4f, 0.3f));
    public static final RegistryObject<EntityType<YetiCrabEntity>> YETI_CRAB = create("yeti_crab", EntityType.Builder.of(YetiCrabEntity::new, EntityClassification.WATER_CREATURE).sized(0.7f, 0.2f));
    public static final RegistryObject<EntityType<ChocolateChipStarfishEntity>> CHOCOLATE_CHIP_STARFISH = create("chocolate_chip_starfish", EntityType.Builder.of(ChocolateChipStarfishEntity::new, EntityClassification.CREATURE).sized(0.7f, 0.2f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTER.register(name, () -> builder.build(TheCrabShack.MOD_ID + "." + name));
    }
}