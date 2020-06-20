package harmonised.dblock;

import harmonised.dblock.config.Config;
import harmonised.dblock.events.EventHandler;
import harmonised.dblock.util.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod( Reference.MOD_ID )
public class dBlockMod
{
    public dBlockMod()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener( this::modsLoading );
        MinecraftForge.EVENT_BUS.addListener( this::serverStart );
        Config.init();
    }

    private void modsLoading( FMLCommonSetupEvent event )
    {
        MinecraftForge.EVENT_BUS.register( harmonised.dblock.events.EventHandler.class );
    }

    private void serverStart( FMLServerAboutToStartEvent event )
    {
        EventHandler.init();
    }
}
