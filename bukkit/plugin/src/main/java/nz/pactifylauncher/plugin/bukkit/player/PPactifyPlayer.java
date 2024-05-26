package nz.pactifylauncher.plugin.bukkit.player;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import nz.pactifylauncher.plugin.bukkit.api.player.PactifyPlayer;
import nz.pactifylauncher.plugin.bukkit.util.BukkitUtil;
import nz.pactifylauncher.plugin.bukkit.util.SchedulerUtil;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import pactify.client.api.plsp.packet.client.PLSPPacketConfFlag;
import pactify.client.api.plsp.packet.client.PLSPPacketConfFlags;
import pactify.client.api.plsp.packet.client.PLSPPacketReset;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Data
public class PPactifyPlayer implements PactifyPlayer {
    private static final Pattern PACTIFY_HOSTNAME_PATTERN = Pattern.compile("[\u0000\u0002]PAC([0-9A-F]{5})[\u0000\u0002]");

    private final PPlayersService service;
    private final Player player;
    private final Set<Integer> scheduledTasks = new HashSet<>();
    private boolean joined;
    private int launcherProtocolVersion;

    public void init() {
        List<MetadataValue> hostnameMeta = player.getMetadata("PactifyPlugin:hostname");
        if (!hostnameMeta.isEmpty()) {
            String hostname = hostnameMeta.get(0).asString();
            Matcher m = PACTIFY_HOSTNAME_PATTERN.matcher(hostname);
            if (m.find()) {
                launcherProtocolVersion = Math.max(1, Integer.parseInt(m.group(1), 16));
            }
        } else {
            service.getPlugin().getLogger().warning("Unable to verify the launcher of " + player.getName()
                    + ": it probably logged when the plugin was disabled!");
        }

        BukkitUtil.addChannel(player, "PLSP"); // Register the PLSP channel for the Player.sendPluginMessage calls
    }

    public void join() {
        joined = true;

        // This client can come from another server if BungeeCord is used, so we reset it to ensure a clean state!
        service.getPlugin().getPlspMessenger().sendPLSPMessage(player, new PLSPPacketReset());
    }

    public void free(boolean onQuit) {
        SchedulerUtil.cancelTasks(service.getPlugin(), scheduledTasks);
        if (!onQuit) {
            service.getPlugin().getPlspMessenger().sendPLSPMessage(player, new PLSPPacketReset());
        }
    }

    @Override
    public boolean hasLauncher() {
        return launcherProtocolVersion > 0;
    }

    @Override
    public int getLauncherProtocolVersion() {
        return launcherProtocolVersion;
    }
}
