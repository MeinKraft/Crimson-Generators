package crimsonfluff.crimsongenerators;

import crimsonfluff.crimsongenerators.init.blocksInit;
import crimsonfluff.crimsongenerators.init.containersInit;
import crimsonfluff.crimsongenerators.init.itemsInit;
import crimsonfluff.crimsongenerators.init.tilesInit;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.state.BooleanProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("crimsongenerators")
public class CrimsonGenerators {
    public static final String MOD_ID = "crimsongenerators";
    public static final Logger LOGGER = LogManager.getLogger("CrimsonGenerators");
    final IEventBus MOD_EVENTBUS = FMLJavaModLoadingContext.get().getModEventBus();

    public static final BooleanProperty GENERATOR_PROPERTY_LIT = BooleanProperty.create("lit");
    public static final BooleanProperty GENERATOR_PROPERTY_SOUL = BooleanProperty.create("soul");

    public CrimsonGenerators() {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            // Client setup
            MOD_EVENTBUS.addListener(this::setupClient);
        });
        MOD_EVENTBUS.addListener(this::setup);

        blocksInit.BLOCKS.register(MOD_EVENTBUS);
        itemsInit.ITEMS.register(MOD_EVENTBUS);
        tilesInit.TILES.register(MOD_EVENTBUS);
        containersInit.CONTAINERS.register(MOD_EVENTBUS);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @OnlyIn(Dist.CLIENT)
    private void setupClient(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(containersInit.GENERATOR.get(), GeneratorChestScreen::new);
	}

	private void setup(final FMLCommonSetupEvent event) {

    }
}
