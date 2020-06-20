package harmonised.dblock.events;

import harmonised.dblock.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventHandler
{
    private static int oneInJumps;

    public static void init()
    {
        oneInJumps = Config.config.oneInJumps.get();
    }

    @SubscribeEvent
    public static void jumped( LivingEvent.LivingJumpEvent event )
    {
        if( !event.getEntity().world.isRemote() )
        {
            if( event.getEntity() instanceof PlayerEntity && !( event.getEntity() instanceof FakePlayer) )
            {
                PlayerEntity player = (PlayerEntity) event.getEntity();
                Vec3d vec = player.getPositionVec();
                double x = vec.getX() % 1;
                double y = vec.getY() % 1;
                double z = vec.getZ() % 1;
                if( x < 0 )
                    x = 1 + x;
                if( z < 0 )
                    z = 1 + z;

                if( (int) ( Math.random() * oneInJumps ) == 0 )
                {
                    BlockPos pos = player.getPosition().up( 2 );
                    BlockState state = Blocks.POLISHED_DIORITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP );

                    if( player.isSprinting() )
                    {
//                        System.out.println( x + " " + z );
                        switch( player.getHorizontalFacing() )
                        {
                            case NORTH:
                                if( z < 0.2 || z > 0.8 )
                                    pos = pos.north();
                                break;

                            case SOUTH:
                                if( z < 0.2 || z > 0.8 )
                                    pos = pos.south();
                                break;

                            case EAST:
                                if( x < 0.2 || x > 0.8 )
                                    pos = pos.east();
                                break;

                            case WEST:
                                if( x < 0.2 || x > 0.8 )
                                    pos = pos.west();
                                break;
                        }
                    }

                    if( y >= 0.5 )
                    {
                        pos = pos.up();
                        state = Blocks.POLISHED_DIORITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM );
                    }

                    if( player.world.getBlockState( pos ).getBlock().equals( Blocks.AIR ) || player.world.getBlockState( pos ).getBlock().equals( Blocks.CAVE_AIR ) )
                    {
                        player.world.setBlockState(pos, state);
                        player.world.playSound(null, event.getEntity().getPosition(), SoundEvents.BLOCK_BELL_USE, SoundCategory.BLOCKS, 5, 25);
                    }
                }
            }
        }
    }
}
