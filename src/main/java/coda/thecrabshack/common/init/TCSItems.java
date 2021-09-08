package coda.thecrabshack.common.init;

import coda.thecrabshack.TheCrabShack;
import coda.thecrabshack.common.items.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TCSItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, TheCrabShack.MOD_ID);
    public final static ItemGroup GROUP = new ItemGroup(TheCrabShack.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(TCSItems.RUBBER_DUCKY_ISOPOD_SPAWN_EGG.get());
        }
    };

    // Gear
    public static final RegistryObject<Item> DUCKY_MASK = REGISTER.register("ducky_mask", () -> new DuckyMaskArmorItem(EquipmentSlotType.HEAD));

    // Buckets
    public static final RegistryObject<Item> YETI_CRAB_BUCKET = REGISTER.register("yeti_crab_bucket", () -> new TCSBucketItem(TCSEntities.YETI_CRAB, () -> Fluids.WATER, new Item.Properties().tab(GROUP).stacksTo(1)));

    // Materials
    public static final RegistryObject<Item> BARK = REGISTER.register("bark", () -> new Item(new Item.Properties().tab(GROUP)));
    public static final RegistryObject<Item> RUBBER_DUCKY_ISOPOD_MOLT = REGISTER.register("rubber_ducky_isopod_molt", () -> new Item(new Item.Properties().tab(GROUP)));
    public static final RegistryObject<Item> YETI_CRAB_FLUFF = REGISTER.register("yeti_crab_fluff", () -> new Item(new Item.Properties().tab(GROUP)));

    // Food
    public static final RegistryObject<Item> RAW_CRAB_LEG = REGISTER.register("raw_crab_leg", () -> new Item(new Item.Properties().tab(GROUP).food(new Food.Builder().nutrition(2).saturationMod(0.25F).build())));
    public static final RegistryObject<Item> COOKED_CRAB_LEG = REGISTER.register("cooked_crab_leg", () -> new Item(new Item.Properties().tab(GROUP).food(new Food.Builder().nutrition(7).saturationMod(0.55F).build())));

    // Spawn Eggs
    public static final RegistryObject<Item> RUBBER_DUCKY_ISOPOD_SPAWN_EGG = REGISTER.register("rubber_ducky_isopod_spawn_egg", () -> new TCSSpawnEggItem(TCSEntities.RUBBER_DUCKY_ISOPOD, 0x625949, 0xefcc63, new Item.Properties().tab(GROUP)));
    public static final RegistryObject<Item> YETI_CRAB_SPAWN_EGG = REGISTER.register("yeti_crab_spawn_egg", () -> new TCSSpawnEggItem(TCSEntities.YETI_CRAB, 0xe7d0bb, 0xb19b77, new Item.Properties().tab(GROUP)));
    public static final RegistryObject<Item> CHOCOLATE_CHIP_STARFISH_SPAWN_EGG = REGISTER.register("chocolate_chip_starfish_spawn_egg", () -> new TCSSpawnEggItem(TCSEntities.CHOCOLATE_CHIP_STARFISH, 0xd0c7ad, 0x564f42, new Item.Properties().tab(GROUP)));
}