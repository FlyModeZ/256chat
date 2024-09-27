package net.kalman98.tfschat;

import net.kalman98.tfschat.commands.*;
import net.kalman98.tfschat.gui.TFSGuiChat;
import net.kalman98.tfschat.reflection.ReflectionFields;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class CoreEventHandler
{
	public static boolean isConnectedToHypixelNetwork = false;
	@SubscribeEvent
	public void onRenderGameOverlay(GuiOpenEvent e)
	{
		try
		{
			if (!Minecraft.getMinecraft().thePlayer.worldObj.isRemote)
				return;
		}
		catch (NullPointerException exc)
		{
			return;
		}
		
		if (e.gui instanceof GuiChat && TFS.INSTANCE.isEnabled())
		{
			e.setCanceled(true);
	        GuiScreen old = Minecraft.getMinecraft().currentScreen;
			GuiScreen guiScreenIn = e.gui;
	        if (old != null && guiScreenIn != old)
	        {
	            old.onGuiClosed();
	        }
	        
	        String defaultText = "";
	        
	        try {
	        	defaultText = (String)ReflectionFields.guichatDefaultInputFieldText.get(guiScreenIn);
	        } catch (IllegalAccessException err) {
	            TFSChat.logger.error("Error getting default input field text, leaving blank.", err);
	        }
	        
	        TFSGuiChat newGui = new TFSGuiChat(defaultText);
	      
	        Minecraft.getMinecraft().currentScreen = (GuiScreen)newGui;

            Minecraft.getMinecraft().setIngameNotInFocus();
            ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            ((GuiScreen)newGui).setWorldAndResolution(Minecraft.getMinecraft(), i, j);
            Minecraft.getMinecraft().skipRenderWorld = false;
		}
	}
	
	@SubscribeEvent
	public void onConnectToServer(ClientConnectedToServerEvent e)
	{
		IThreadListener mainThread = Minecraft.getMinecraft();
		mainThread.addScheduledTask(new Runnable() {

			@Override
			public void run()
			{
				String serverAddress = Minecraft.getMinecraft().getCurrentServerData().serverIP;
				if (serverAddress == null)
				{
					TFSChat.logger.error("Could not detect server address. Disabling longer chat.");
					CoreEventHandler.isConnectedToHypixelNetwork = false;
					return;
				}
				String serverName = serverAddress.split(":")[0];
				if (serverName.endsWith("hypixel.net"))
				{
					CoreEventHandler.isConnectedToHypixelNetwork = true;
					TFSChat.logger.info("Detected connection to the Hypixel network! Enabling longer chat!");
				}
				else
				{
					CoreEventHandler.isConnectedToHypixelNetwork = false;
					TFSChat.logger.info("Detected connection to non-Hypixel server! Disabling longer chat.");
				}
			}
		});
	}
}
