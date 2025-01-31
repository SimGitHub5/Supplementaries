package net.mehvahdjukaar.supplementaries.setup;

import net.mehvahdjukaar.supplementaries.Supplementaries;
import net.mehvahdjukaar.supplementaries.datagen.types.VanillaWoodTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
public class RemapHandler {

    private static final Map<String, ResourceLocation> itemReMap = new HashMap<>();

    private static final Map<String, ResourceLocation> fullReMap = new HashMap<>();

    static {

        fullReMap.put("orange_trader", ModRegistry.RED_MERCHANT.getId());

        fullReMap.put("piston_launcher", ModRegistry.SPRING_LAUNCHER.getId());
        fullReMap.put("piston_launcher_arm", ModRegistry.SPRING_LAUNCHER_ARM.getId());
        fullReMap.put("piston_launcher_head", ModRegistry.SPRING_LAUNCHER_HEAD.getId());

        itemReMap.put("jar_full", ModRegistry.JAR_ITEM.getId());
        itemReMap.put("jar_full_tinted", ModRegistry.JAR_ITEM_TINTED.getId());
        itemReMap.put("cage_full", ModRegistry.CAGE_ITEM.getId());


        itemReMap.put(ModRegistry.WALL_LANTERN_NAME, Items.LANTERN.getRegistryName());
        itemReMap.put(ModRegistry.STICK_NAME, Items.STICK.getRegistryName());
        itemReMap.put(ModRegistry.BLAZE_ROD_NAME, Items.BLAZE_ROD.getRegistryName());
        itemReMap.put(ModRegistry.HANGING_FLOWER_POT_NAME, Items.FLOWER_POT.getRegistryName());
        itemReMap.put(ModRegistry.GUNPOWDER_BLOCK_NAME, Items.GUNPOWDER.getRegistryName());
        for (RegistryObject<Block> banner : ModRegistry.CEILING_BANNERS.values()) {
            itemReMap.put(banner.getId().getPath(), new ResourceLocation("minecraft",
                    banner.getId().getPath().replace("ceiling_banner_", "") + "_banner"));
        }
        itemReMap.put(ModRegistry.DIRECTIONAL_CAKE_NAME, Items.CAKE.getRegistryName());

        itemReMap.putAll(fullReMap);

    }

    @SubscribeEvent
    public static void onRemapBlocks(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getMappings(Supplementaries.MOD_ID)) {
            String k = mapping.key.getPath();
            if (fullReMap.containsKey(k)) {
                var i = fullReMap.get(k);
                try {
                    Supplementaries.LOGGER.warn("Remapping block '{}' to '{}'", mapping.key, i);
                    mapping.remap(ForgeRegistries.BLOCKS.getValue(i));
                } catch (Throwable t) {
                    Supplementaries.LOGGER.warn("Remapping block '{}' to '{}' failed: {}", mapping.key, i, t);
                }
            } else if (k.contains("hanging_sign")) {
                try {
                    mapping.remap(ModRegistry.HANGING_SIGNS.get(VanillaWoodTypes.OAK).get());
                } catch (Exception ignored) {
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRemapTiles(RegistryEvent.MissingMappings<BlockEntityType<?>> event) {
        for (RegistryEvent.MissingMappings.Mapping<BlockEntityType<?>> mapping : event.getMappings(Supplementaries.MOD_ID)) {
            String k = mapping.key.getPath();
            if (fullReMap.containsKey(k)) {
                var i = fullReMap.get(k);
                try {
                    Supplementaries.LOGGER.warn("Remapping tile entity '{}' to '{}'", mapping.key, i);
                    mapping.remap(ForgeRegistries.BLOCK_ENTITIES.getValue(i));
                } catch (Throwable t) {
                    Supplementaries.LOGGER.warn("Remapping  tile entity '{}' to '{}' failed: {}", mapping.key, i, t);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRemapItems(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getMappings(Supplementaries.MOD_ID)) {
            String k = mapping.key.getPath();
            if (itemReMap.containsKey(k)) {
                var i = itemReMap.get(k);
                try {
                    Supplementaries.LOGGER.warn("Remapping item '{}' to '{}'", mapping.key, i);
                    mapping.remap(ForgeRegistries.ITEMS.getValue(i));
                } catch (Throwable t) {
                    Supplementaries.LOGGER.warn("Remapping item '{}' to '{}' failed: {}", mapping.key, i, t);
                }
            } else if (k.contains("hanging_sign")) {
                try {
                    mapping.remap(ModRegistry.HANGING_SIGNS_ITEMS.get(VanillaWoodTypes.OAK).get());
                } catch (Exception ignored) {
                }
            } else if (k.contains("sign_post")) {
                try {
                    mapping.remap(ModRegistry.SIGN_POST_ITEMS.get(VanillaWoodTypes.OAK).get());
                } catch (Exception ignored) {
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRemapEntityTypes(RegistryEvent.MissingMappings<EntityType<?>> event) {
        for (RegistryEvent.MissingMappings.Mapping<EntityType<?>> mapping : event.getMappings(Supplementaries.MOD_ID)) {
            if (fullReMap.containsKey(mapping.key.getPath())) {
                try {
                    Supplementaries.LOGGER.warn("Remapping entity '{}' to '{}'", mapping.key, fullReMap.get(mapping.key.getPath()));
                    mapping.remap(ForgeRegistries.ENTITIES.getValue(fullReMap.get(mapping.key.getPath())));
                } catch (Throwable t) {
                    Supplementaries.LOGGER.warn("Remapping entity '{}' to '{}' failed: {}", mapping.key,
                            fullReMap.get(mapping.key.getPath()), t);
                }
            }
        }
    }
}
