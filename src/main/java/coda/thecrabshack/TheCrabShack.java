package coda.thecrabshack;

import coda.thecrabshack.client.ClientEvents;
import coda.thecrabshack.common.entities.ChocolateChipStarfishEntity;
import coda.thecrabshack.common.entities.RubberDuckyIsopodEntity;
import coda.thecrabshack.common.entities.YetiCrabEntity;
import coda.thecrabshack.common.init.TCSEntities;
import coda.thecrabshack.common.init.TCSItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(TheCrabShack.MOD_ID)
public class TheCrabShack {
    public static final String MOD_ID = "thecrabshack";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final List<Runnable> CALLBACKS = new ArrayList<>();

    public TheCrabShack() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        bus.addListener(this::registerClient);
        bus.addListener(this::registerEntityAttributes);
        bus.addListener(this::registerCommon);

        forgeBus.addListener(this::onBiomeLoading);
        forgeBus.addListener(this::onLogStripped);

        TCSItems.REGISTER.register(bus);
        TCSEntities.REGISTER.register(bus);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(TCSEntities.RUBBER_DUCKY_ISOPOD.get(), RubberDuckyIsopodEntity.createAttributes().build());
        event.put(TCSEntities.YETI_CRAB.get(), YetiCrabEntity.createAttributes().build());
        event.put(TCSEntities.CHOCOLATE_CHIP_STARFISH.get(), ChocolateChipStarfishEntity.createAttributes().build());
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        EntitySpawnPlacementRegistry.register(TCSEntities.RUBBER_DUCKY_ISOPOD.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RubberDuckyIsopodEntity::checkIsopodSpawnRules);
        EntitySpawnPlacementRegistry.register(TCSEntities.YETI_CRAB.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChocolateChipStarfishEntity::checkStarfishSpawnRules);
        EntitySpawnPlacementRegistry.register(TCSEntities.CHOCOLATE_CHIP_STARFISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ChocolateChipStarfishEntity::checkStarfishSpawnRules);
    }

    private void onBiomeLoading(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.Category.JUNGLE)) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(TCSEntities.RUBBER_DUCKY_ISOPOD.get(), 1, 2, 4));
        }
    }

    // TODO: fix this
    private void onLogStripped(BlockEvent.BlockToolInteractEvent event) {
        ToolType type = event.getToolType();
        PlayerEntity player = event.getPlayer();

        if (type == ToolType.AXE) {
            ItemEntity item = EntityType.ITEM.create(player.getCommandSenderWorld());
            item.setItem(new ItemStack(TCSItems.BARK.get()));
            item.moveTo(player.getX(), player.getY(), player.getZ());
            player.level.addFreshEntity(item);
        }
    }

    private void registerClient(FMLClientSetupEvent event) {
        ClientEvents.clientSetup();
        CALLBACKS.forEach(Runnable::run);
        CALLBACKS.clear();
    }
}