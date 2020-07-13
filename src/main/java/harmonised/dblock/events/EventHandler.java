package harmonised.dblock.events;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler
{
    private static final int oneInJumps = 100;

    @SubscribeEvent
    public static void jumped( LivingEvent.LivingJumpEvent event )
    {
        if( !event.getEntity().world.isRemote )
        {
            if( event.getEntity() instanceof EntityPlayer && !( event.getEntity() instanceof FakePlayer) )
            {
                EntityPlayer player = (EntityPlayer) event.getEntity();
                Vec3d vec = player.getPositionVector();
                double x = vec.x % 1;
                double y = vec.y % 1;
                double z = vec.z % 1;
                if( x < 0 )
                    x = 1 + x;
                if( z < 0 )
                    z = 1 + z;

                if( (int) ( Math.random() * oneInJumps ) == 0 )
                {
                    BlockPos pos = new BlockPos( player.getPositionVector() ).up( 2 );
                    IBlockState state = Blocks.STONE_SLAB.getDefaultState().withProperty( BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP );

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
                        state = Blocks.STONE_SLAB.getDefaultState().withProperty( BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM );
                    }

                    if( player.world.getBlockState( pos ).getBlock().equals( Blocks.AIR ) )
                    {
                        player.world.setBlockState(pos, state);
                        player.world.playSound(null, new BlockPos( event.getEntity().getPositionVector() ), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 5, 25);
                    }
                }
            }
        }
    }
}
