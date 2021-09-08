package coda.thecrabshack.client.renderer;

import coda.thecrabshack.TheCrabShack;
import coda.thecrabshack.client.model.ChocolateChipStarfishModel;
import coda.thecrabshack.common.entities.ChocolateChipStarfishEntity;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.util.Map;

public class ChocolateChipStarfishRenderer extends MobRenderer<ChocolateChipStarfishEntity, ChocolateChipStarfishModel<ChocolateChipStarfishEntity>> {
    public static final Map<Integer, ResourceLocation> TEXTURES = Util.make(Maps.newHashMap(), (hashMap) -> {
        hashMap.put(0, new ResourceLocation(TheCrabShack.MOD_ID, "textures/entity/chocolate_chip_starfish/starfish_1.png"));
        hashMap.put(1, new ResourceLocation(TheCrabShack.MOD_ID, "textures/entity/chocolate_chip_starfish/starfish_2.png"));
        hashMap.put(2, new ResourceLocation(TheCrabShack.MOD_ID, "textures/entity/chocolate_chip_starfish/starfish_3.png"));
        hashMap.put(3, new ResourceLocation(TheCrabShack.MOD_ID, "textures/entity/chocolate_chip_starfish/starfish_4.png"));
        hashMap.put(4, new ResourceLocation(TheCrabShack.MOD_ID, "textures/entity/chocolate_chip_starfish/starfish_5.png"));
    });

    public ChocolateChipStarfishRenderer(EntityRendererManager manager) {
        super(manager, new ChocolateChipStarfishModel<>(), 0.225f);
    }

    @Override
    public ResourceLocation getTextureLocation(ChocolateChipStarfishEntity entity) {
        return TEXTURES.getOrDefault(entity.getVariant(), TEXTURES.get(0));
    }
}
