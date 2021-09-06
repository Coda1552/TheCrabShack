package coda.thecrabshack.common.init;

import coda.thecrabshack.TheCrabShack;
import coda.thecrabshack.common.items.*;
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

    // Materials
    public static final RegistryObject<Item> BARK = REGISTER.register("bark", () -> new Item(new Item.Properties().tab(GROUP)));
    public static final RegistryObject<Item> RUBBER_DUCKY_ISOPOD_MOLT = REGISTER.register("rubber_ducky_isopod_molt", () -> new Item(new Item.Properties().tab(GROUP)));

    // Spawn Eggs
    public static final RegistryObject<Item> RUBBER_DUCKY_ISOPOD_SPAWN_EGG = REGISTER.register("rubber_ducky_isopod_spawn_egg", () -> new TCSSpawnEggItem(TCSEntities.RUBBER_DUCKY_ISOPOD, 0x625949, 0xefcc63, new Item.Properties().tab(GROUP)));
}