package crimsonfluff.crimsongenerators;

import crimsonfluff.crimsongenerators.init.itemsInit;
import crimsonfluff.crimsongenerators.init.blocksInit;
import crimsonfluff.crimsongenerators.init.tilesInit;

import crimsonfluff.crimsongenerators.items.ItemCoalGen;
import crimsonfluff.crimsongenerators.items.ItemUpgradeTier1;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("crimsongenerators")
public class CrimsonGenerators {
    public static final String MOD_ID = "crimsongenerators";
    public static final Logger LOGGER = LogManager.getLogger("CrimsonGenerators");
    final IEventBus MOD_EVENTBUS = FMLJavaModLoadingContext.get().getModEventBus();

    public static final IntegerProperty TIER = IntegerProperty.create("tier",0,3);

    public CrimsonGenerators() {
        MOD_EVENTBUS.addListener(this::setup);
        MOD_EVENTBUS.addListener(this::doClientStuff);

        tilesInit.TILES.register(MOD_EVENTBUS);
        blocksInit.BLOCKS.register(MOD_EVENTBUS);
        itemsInit.ITEMS.register(MOD_EVENTBUS);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLClientSetupEvent event) { }
    private void doClientStuff(final FMLClientSetupEvent event) { }

    public static final ItemGroup TAB = new ItemGroup("Crimson Generators") {
        @OnlyIn(Dist.CLIENT)
        @Override
        public ItemStack createIcon() { return new ItemStack(blocksInit.COAL_GEN_BLOCK.get()); }
    };
}