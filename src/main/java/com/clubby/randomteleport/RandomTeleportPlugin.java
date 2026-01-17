package com.clubby.randomteleport;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public class RandomTeleportPlugin extends JavaPlugin {
    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public RandomTeleportPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Loaded %s v%s",
                this.getName(),
                this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
        getCommandRegistry().registerCommand(new WildCommand());
    }
}