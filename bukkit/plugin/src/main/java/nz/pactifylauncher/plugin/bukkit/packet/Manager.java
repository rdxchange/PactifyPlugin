package nz.pactifylauncher.plugin.bukkit.packet;

import nz.pactifylauncher.plugin.bukkit.PLSPMessenger;
import nz.pactifylauncher.plugin.bukkit.PactifyPlugin;
import nz.pactifylauncher.plugin.bukkit.player.PPactifyPlayer;
import nz.pactifylauncher.plugin.bukkit.util.NotchianChatComponent;
import org.bukkit.entity.Player;
import pactify.client.api.plsp.packet.client.PLSPPacketPopupAlert;

public class Manager {

    public static void create(Player player){
        NotchianChatComponent ncc = new NotchianChatComponent("CLOSE");
        NotchianChatComponent notchianChatComponent = new NotchianChatComponent("OK");

        pactify.client.api.mcprotocol.model.NotchianChatComponent notchianChatComponent1 = new pactify.client.api.mcprotocol.model.NotchianChatComponent() {};

        pactify.client.api.mcprotocol.model.NotchianChatComponent notchianChatComponent2 = new pactify.client.api.mcprotocol.model.NotchianChatComponent() {};

        PLSPPacketPopupAlert p = new PLSPPacketPopupAlert(notchianChatComponent1, notchianChatComponent2);

        PLSPMessenger plspMessenger = new PLSPMessenger(PactifyPlugin.getPp());
        plspMessenger.sendPLSPMessage(player, p);

    }

}
