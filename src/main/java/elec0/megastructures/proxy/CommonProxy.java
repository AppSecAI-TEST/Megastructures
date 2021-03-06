package elec0.megastructures.proxy;

import elec0.megastructures.EventHandlerCommon;
import elec0.megastructures.Megastructures;
import elec0.megastructures.blocks.ModBlocks;
import elec0.megastructures.blocks.TerminalBlock;
import elec0.megastructures.items.ModItems;
import elec0.megastructures.network.PacketHandler;
import elec0.megastructures.tileentities.TerminalTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;


@Mod.EventBusSubscriber
public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		ModItems.init();
		ModBlocks.init();

		OBJLoader.INSTANCE.addDomain(Megastructures.MODID);
		NetworkRegistry.INSTANCE.registerGuiHandler(Megastructures.instance, new GUIProxy());

		// Initialize our packet handler. Make sure the name is 20 characters or less!
		PacketHandler.registerMessages("megastructures");
	}

	public void init(FMLInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(new EventHandlerCommon());
		//MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		//CapabilityManager.INSTANCE.register(IPowerData.class, new PowerDataStorage(), PowerData.class);
	}

	public void postInit(FMLPostInitializationEvent e)
	{
		ModBlocks.initItemModels();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new TerminalBlock());
		GameRegistry.registerTileEntity(TerminalTileEntity.class, Megastructures.MODID + "_terminalblock");
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().register(new ItemBlock(ModBlocks.terminalBlock).setRegistryName(ModBlocks.terminalBlock.getRegistryName()));
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		ModBlocks.initModels();
		ModItems.initModels();
	}


}