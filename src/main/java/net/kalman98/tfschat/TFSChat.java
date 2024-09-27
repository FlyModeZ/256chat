package net.kalman98.tfschat;

import org.apache.logging.log4j.Logger;

import net.kalman98.tfschat.commands.TFSCommand;
import net.kalman98.tfschat.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TFSChat.MODID,
	 version = TFSChat.VERSION)
public class TFSChat
{
    public static final String MODID = "tfschat";
    public static final String VERSION = "1.1b.1";
	
	@Mod.Instance(TFSChat.MODID)
	public static TFSChat instance;

	@SidedProxy(clientSide="net.kalman98.tfschat.proxy.ClientProxy", serverSide="net.kalman98.tfschat.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	public static Logger logger;
	
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
    	logger = e.getModLog();
    	proxy.preInit(e);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
    	proxy.init(e);
		ClientCommandHandler.instance.registerCommand(new TFSCommand());
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
    	proxy.postInit(e);
    }
}
