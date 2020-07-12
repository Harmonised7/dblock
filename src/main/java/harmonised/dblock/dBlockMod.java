package harmonised.dblock;

import harmonised.dblock.events.EventHandler;
import harmonised.dblock.util.Reference;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod( modid = Reference.MOD_ID, version = Reference.VERSION )
public class dBlockMod
{
    public dBlockMod()
    {
    }

    @Mod.EventHandler
    public static void init( FMLInitializationEvent event )
    {
        MinecraftForge.EVENT_BUS.register( harmonised.dblock.events.EventHandler.class );
    }
}
