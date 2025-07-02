package com.harismehuljic.billboard;

import com.harismehuljic.billboard.command.BillboardCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Registries {
    public static void registerAll() {
        registerCommands();
    }

    private static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(BillboardCommand::register);
    }
}
