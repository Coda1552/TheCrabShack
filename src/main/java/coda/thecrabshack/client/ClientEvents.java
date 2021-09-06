package coda.thecrabshack.client;

import coda.thecrabshack.TheCrabShack;
import coda.thecrabshack.client.renderer.RubberDuckyIsopodRenderer;
import coda.thecrabshack.common.items.TCSSpawnEggItem;
import coda.thecrabshack.common.init.TCSEntities;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = TheCrabShack.MOD_ID)
public class ClientEvents {

    public static void clientSetup() {
        RenderingRegistry.registerEntityRenderingHandler(TCSEntities.RUBBER_DUCKY_ISOPOD.get(), RubberDuckyIsopodRenderer::new);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void itemColors(ColorHandlerEvent.Item event) {
        ItemColors handler = event.getItemColors();
        IItemColor eggColor = (stack, tintIndex) -> ((TCSSpawnEggItem) stack.getItem()).getColor(tintIndex);
        for (TCSSpawnEggItem e : TCSSpawnEggItem.UNADDED_EGGS) handler.register(eggColor, e);
    }
}
