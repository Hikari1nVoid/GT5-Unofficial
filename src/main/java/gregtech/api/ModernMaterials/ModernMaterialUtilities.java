package gregtech.api.ModernMaterials;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.ModernMaterials.Blocks.BlocksEnum;
import gregtech.api.ModernMaterials.Blocks.DumbBase.Simple.DumbItemBlock;
import gregtech.api.ModernMaterials.Blocks.FrameBox.*;
import gregtech.api.ModernMaterials.Fluids.ModernMaterialFluid;
import gregtech.api.ModernMaterials.PartProperties.Rendering.ModernMaterialItemRenderer;
import gregtech.api.ModernMaterials.PartRecipeGenerators.ModernMaterialsPlateRecipeGenerator;
import gregtech.api.ModernMaterials.PartsClasses.IGetItem;
import gregtech.api.ModernMaterials.PartsClasses.MaterialPart;
import gregtech.api.ModernMaterials.PartsClasses.MaterialPartsEnum;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static gregtech.api.enums.ConfigCategories.ModernMaterials.materialID;

public class ModernMaterialUtilities {

    private static final List<ModernMaterial> newMaterials = new ArrayList<>();
    public static final HashMap<Integer, ModernMaterial> materialIDToMaterial = new HashMap<>();
    public static final HashMap<String, ModernMaterial> materialNameToMaterialMap = new HashMap<>();

    public static void registerMaterial(ModernMaterial material) {
        final int tCurrentMaterialID = GregTech_API.modernMaterialIDs.mConfig
                .get(materialID.name(), material.getMaterialName(), -1).getInt();
        if (tCurrentMaterialID == -1) {
            newMaterials.add(material);
        } else {
            material.setMaterialID(tCurrentMaterialID);
            materialIDToMaterial.put(tCurrentMaterialID, material);
            if (tCurrentMaterialID > GregTech_API.lastMaterialID) {
                GregTech_API.lastMaterialID = tCurrentMaterialID;
            }
        }
        materialNameToMaterialMap.put(material.getMaterialName(), material);
    }

    public static void registerAllMaterialsBlocks() {

    }

    public static void registerAllMaterialsItems() {
        for (ModernMaterial tMaterial : newMaterials) {
            tMaterial.setMaterialID(++GregTech_API.lastMaterialID);
            GregTech_API.modernMaterialIDs.mConfig.get(materialID.name(), tMaterial.getMaterialName(), 0)
                    .set(GregTech_API.lastMaterialID);
            materialIDToMaterial.put(GregTech_API.lastMaterialID, tMaterial);
        }

        for (MaterialPartsEnum part : MaterialPartsEnum.values()) {

            MaterialPart materialPart = new MaterialPart(part);
            materialPart.setUnlocalizedName(part.partName);

            // Registers the item with the game, only available in preInit.
            GameRegistry.registerItem(materialPart, part.partName);

            // Store the Item so these parts can be retrieved later for recipe generation etc.
            part.setAssociatedItem(materialPart);

            // Registers the renderer which allows for part colouring.
            MinecraftForgeClient.registerItemRenderer(materialPart, new ModernMaterialItemRenderer());
        }

        // Register all material parts.
        for (ModernMaterial material : materialIDToMaterial.values()) {
            registerAllMaterialPartRecipes(material);
        }

    }

    public static void registerAllMaterialsFluids() {

        // Register the fluids with forge.
        for (ModernMaterial material : materialIDToMaterial.values()) {
            for (ModernMaterialFluid fluid : material.existingFluids) {
                FluidRegistry.registerFluid(fluid);
            }
        }

        BlocksEnum.FrameBox.getAssociatedMaterials().addAll(materialIDToMaterial.values());

        //GameRegistry.registerBlock(new FrameBoxBlock2(), FrameBoxItemBlock.class, "exampleBlock");
        //GameRegistry.registerTileEntity(FrameBoxTileEntity.class, "exampleTileEntity");



        //(new FrameBoxBlock()).registerBlock(FrameBoxTileEntity.class, FrameBoxItemBlock.class);
        //new FrameBoxRenderer();
        // GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + W + ".name", "Any Sub Block of this one");

        FrameBoxBlock frame = new FrameBoxBlock();
        frame.registerBlock(FrameBoxTileEntity.class, FrameBoxItemBlock.class, new FrameBoxSimpleBlockRenderer());

        //GameRegistry.registerBlock(frame, FrameBoxItemBlock.class, "frameBoxBlock");
        //GameRegistry.registerTileEntity(FrameBoxTileEntity.class, "frameBoxBlockTile");
        //MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(frame), new FrameBoxItemRenderer());

    }

    private static void registerAllMaterialPartRecipes(ModernMaterial material) {
        new ModernMaterialsPlateRecipeGenerator().run(material);
    }

    public static ItemStack getPart(final ModernMaterial material, final IGetItem part, final int stackSize) {
        return new ItemStack(part.getItem(), stackSize, material.getMaterialID());
    }

    public static ItemStack getPart(final String materialName, final IGetItem part, final int stackSize) {
        return getPart(getMaterialFromName(materialName), part, stackSize);
    }

    public static ModernMaterial getMaterialFromName(final String materialName) {

        ModernMaterial modernMaterial = materialNameToMaterialMap.getOrDefault(materialName, null);

        if (modernMaterial == null) {
            throw new IllegalArgumentException("Material % does not exist. Make sure you spelt it correctly.".replace("%", materialName));
        }

        return modernMaterial;
    }

    public static ModernMaterial getMaterialFromID(final int materialID) {

        ModernMaterial modernMaterial = materialIDToMaterial.getOrDefault(materialID, null);

        if (modernMaterial == null) {
            throw new IllegalArgumentException("Material with ID " + materialID + " does not exist.");
        }

        return modernMaterial;
    }

    public static ArrayList<String> tooltipGenerator(Item part, ModernMaterial material) {
        // Todo, this is just temporary as a proof of concept/debug info.
        // Probably will put radioactive warning here. Not sure what else yet, if any.

        ArrayList<String> tooltip = new ArrayList<>();

        tooltip.add("Generic Tooltip");
        tooltip.add("Material Name: " + material.getMaterialName());

        if (part instanceof DumbItemBlock blockPart) {
            tooltip.add("Material Part Type: " + "Blah blah do later");
        } else if (part instanceof MaterialPart itemPart) {
            tooltip.add("Material Part Type: " + material.getCustomPartInfo(itemPart.getPart()).getTextureType());
        }

        return tooltip;
    }

    public static void drawBlock(Block block, int meta, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;

        tessellator.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));

        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));

        tessellator.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));

        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));

        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));

        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));

    }
}
