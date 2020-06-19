package harmonised.dblock.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class Config
{
    public static ConfigImplementation config;

    public static void init()
    {
        config = ConfigHelper.register( ModConfig.Type.COMMON, ConfigImplementation::new );
    }

    public static class ConfigImplementation
    {
        //Global
        public ConfigHelper.ConfigValueListener<Integer> oneInJumps;

        public ConfigImplementation(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
        {
            builder.push( "Global" );
            {
                this.oneInJumps = subscriber.subscribe(builder
                        .comment( "What is the chance for a Dick Block to appear? (1 in x)" )
                        .translation( "dblock.oneInJumps" )
                        .defineInRange( "oneInJumps", 100, 0, Integer.MAX_VALUE ) );

                builder.pop();
            }
        }
    }
}
