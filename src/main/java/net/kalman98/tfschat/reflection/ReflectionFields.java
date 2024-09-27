package net.kalman98.tfschat.reflection;

import java.lang.reflect.Field;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class ReflectionFields
{
	public static Field guichatDefaultInputFieldText;
	public static Field c01MessageField;
	
	public static void setup()
	{
		guichatDefaultInputFieldText = ReflectionHelper.findField(GuiChat.class, "defaultInputFieldText", "field_146409_v");
		guichatDefaultInputFieldText.setAccessible(true);
		
    	c01MessageField = ReflectionHelper.findField(C01PacketChatMessage.class, "message", "field_149440_a");
    	c01MessageField.setAccessible(true);
	}
}
