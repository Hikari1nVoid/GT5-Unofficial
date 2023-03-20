package gregtech.api.ModernMaterials;

import static gregtech.api.ModernMaterials.Fluids.FluidEnum.Molten;
import static gregtech.api.ModernMaterials.Fluids.FluidEnum.Plasma;
import static gregtech.api.ModernMaterials.PartsClasses.PartsEnum.*;

public class ModernMaterialsQuickMaterialGenerator {

    public ModernMaterial simpleMetal(final String materialName, final int red, final int green, final int blue) {

        return new ModernMaterial().setName(materialName).setColor(red, green, blue, 255)
                .addParts(
                        Dust,
                        SolderingHead,
                        PickaxeHead,
                        HoeHead,
                        PurifiedDust,
                        PurifiedOre,
                        ImpureDust,
                        Spring,
                        Ring,
                        BuzzSaw,
                        Rotor,
                        LongRod,
                        CrushedOre,
                        FineWire,
                        SmallSpring,
                        Foil,
                        Plate,
                        ScrewdriverHead,
                        ShovelHead,
                        Gear,
                        Round,
                        ElectricWrenchHead,
                        TurbineBlade,
                        DensePlate,
                        ArrowHead,
                        CrushedCentrifugedOre,
                        AxeHead,
                        ChainSaw,
                        SenseHead,
                        FileHead,
                        TinyDust,
                        Screw,
                        SmallDust,
                        TinyDust,
                        SwordBlade,
                        HammerHead,
                        Nugget,
                        SawBlade,
                        Rod,
                        DrillTip,
                        SmallGear,
                        PlowHead,
                        Ingot)
                .addFluids(Molten, Plasma).build();

    }

    public ModernMaterial gem(final String materialName, final int red, final int green, final int blue) {

        return new ModernMaterial().setName(materialName).setColor(red, green, blue, 255)
                .addParts(
                        Dust,
                        ImpureDust,
                        PurifiedOre,
                        PurifiedDust,
                        CrushedOre,
                        Bolt,
                        Rod,
                        Screw,
                        Plate,
                        Lens,
                        ExquisiteGem,
                        FlawlessGem,
                        Gem,
                        FlawedGem,
                        ChippedGem)
                .addFluids(Molten, Plasma).build();

    }

}