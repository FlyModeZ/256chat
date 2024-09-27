package net.kalman98.tfschat.gui;

import net.kalman98.tfschat.TFSChat;
import net.kalman98.tfschat.reflection.ReflectionFields;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class TFSGuiChat extends GuiChat
{	
    public TFSGuiChat()
    {
    	super();

    }

    public TFSGuiChat(String defaultText)
    {
    	super(defaultText);
    }
    
    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
	@Override
    public void initGui()
    {
		super.initGui();
        this.inputField.setMaxStringLength(256);
    }
	
	@Override
	public void sendChatMessage(String msg)
    {
        this.sendChatMessage(msg, true);
    }

	@Override
    public void sendChatMessage(String msg, boolean addToChat)
    {
        if (addToChat)
        {
            this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
        }
        if (net.minecraftforge.client.ClientCommandHandler.instance.executeCommand(mc.thePlayer, msg) != 0) return;
        
        if (msg.length() > 256)
        {
            msg = msg.substring(0, 256);
        }

        C01PacketChatMessage packet = new C01PacketChatMessage();
        
        try {
        	ReflectionFields.c01MessageField.set(packet, msg);
        } catch (IllegalAccessException e) {
        	TFSChat.logger.error("Error setting message length, sticking with 100.", e);
        	packet = new C01PacketChatMessage(msg);
        }
        
        this.mc.thePlayer.sendQueue.addToSendQueue(packet);
    }
}
