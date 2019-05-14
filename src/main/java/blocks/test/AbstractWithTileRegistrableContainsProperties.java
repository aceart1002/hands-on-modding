package blocks.test;

import javax.annotation.Nullable;

import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import registry.Registrable;
import tiles.ContainsTile;
import tiles.TileRemote;

public abstract class AbstractWithTileRegistrableContainsProperties<T extends TileEntity>
extends BlockRedstoneDiode implements Registrable, ContainsTile {
	
	private String registryName;
	
	
	protected AbstractWithTileRegistrableContainsProperties(boolean powered, String registryName) {
		super(powered);
		
		setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		
		setCustomRegistryName(registryName);
		
		 
	}
	
	
	@Override
	public void setCustomRegistryName(String newName) {
		registryName = newName;
		
	}
	
	@Override
	public String getCustomRegistryName() {
		return registryName;
	}
		    
		    protected BlockStateContainer createBlockState()
		    {
		        return new BlockStateContainer(this, new IProperty[] {FACING});
		    }
		    
		    /**
		     * Convert the BlockState into the correct metadata value
		     */
		    public int getMetaFromState(IBlockState state)
		    {
		        int i = 0;
		        i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
		        return i;
		    }
		    
		    /**
		     * Convert the given metadata into a BlockState for this Block
		     */
		    public IBlockState getStateFromMeta(int meta)
		    {
		        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
		    }
		    
		    @Nullable
			@Override
			public abstract T createTileEntity(World world, IBlockState blockState);
		    
		    public abstract Class<T> getTileEntityClass();
		    
		    public T getTileEntity(IBlockAccess world, BlockPos position) {

				return (T) world.getTileEntity(position);
			}

		    @Override
			public boolean hasTileEntity(IBlockState blockState) {

				return true;
			}
}
