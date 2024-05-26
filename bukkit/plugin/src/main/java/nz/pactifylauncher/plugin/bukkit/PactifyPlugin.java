package nz.pactifylauncher.plugin.bukkit;

import lombok.Getter;
import nz.pactifylauncher.plugin.bukkit.api.PactifyAPI;
import nz.pactifylauncher.plugin.bukkit.command.CheckCommand;
import nz.pactifylauncher.plugin.bukkit.command.ListCommand;
import nz.pactifylauncher.plugin.bukkit.command.StatsCommand;
import nz.pactifylauncher.plugin.bukkit.player.PPlayersService;
import nz.pactifylauncher.plugin.bukkit.util.BukkitUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class PactifyPlugin extends JavaPlugin implements PactifyAPI.Impl {
    private final @Getter PLSPMessenger plspMessenger = new PLSPMessenger(this);
    private final @Getter PPlayersService playersService = new PPlayersService(this);
    public static PactifyPlugin pp;

    @Override
    public void onLoad() {
        // Initialize unsafe utilities (those with reflection)
        try {
            Class.forName(BukkitUtil.class.getName(), true, getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        PactifyAPI.setImplementation(this);
    }

    @Override
    public void onEnable() {
        pp = this;
        plspMessenger.enable();
        playersService.enable();
        registerCommands();
        getLogger().info("Enabled!");
    }

    @Override
    public void onDisable() {
        playersService.disable();
        plspMessenger.disable();
        getLogger().info("Disabled!");
    }

    private void registerCommands() {
        getCommand("pactifycheck").setExecutor(new CheckCommand(this));
        getCommand("pactifylist").setExecutor(new ListCommand(this));
        getCommand("pactifystats").setExecutor(new StatsCommand(this));
    }

    public PLSPMessenger getPlspMessenger() {
        return plspMessenger;
    }

    public static PactifyPlugin getPp() {
        return pp;
    }
}
