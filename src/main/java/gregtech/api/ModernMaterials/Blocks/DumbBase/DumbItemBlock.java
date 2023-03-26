package gregtech.api.ModernMaterials.Blocks.DumbBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.ModernMaterials.Blocks.BlocksEnum;
import gregtech.api.ModernMaterials.ModernMaterial;
import gregtech.api.ModernMaterials.ModernMaterialUtilities;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

import static gregtech.api.ModernMaterials.ModernMaterialUtilities.tooltipGenerator;

public abstract class DumbItemBlock extends ItemBlock {

    public DumbItemBlock(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(GregTech_API.TAB_GREGTECH); // todo add new gt frame tab.
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    // Tooltip information.
    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltipList, boolean aF3_H)  {

        final ModernMaterial material = ModernMaterialUtilities.materialIDToMaterial.get(itemStack.getItemDamage());

        for (String line : tooltipGenerator(material)) {
            tooltipList.add(line);
        }
    }

    @Override
    public abstract String getItemStackDisplayName(ItemStack itemStack);

    public abstract BlocksEnum getBlockEnum();
}