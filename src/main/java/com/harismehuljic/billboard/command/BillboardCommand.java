package com.harismehuljic.billboard.command;

import com.harismehuljic.billboard.rendering.Pixel;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.literal;

public class BillboardCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
                                CommandManager.RegistrationEnvironment environment) {
        final LiteralCommandNode<ServerCommandSource> testCommand = dispatcher.register(literal("billboard")
                .then(literal("render")
                        .then(CommandManager.argument("color", StringArgumentType.string())
                                .then(CommandManager.argument("scale", FloatArgumentType.floatArg())
                                        .executes(BillboardCommand::renderPixel)
                                )
                        )
                )
        );
    }

    private static int renderPixel(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        World world = Objects.requireNonNull(context.getSource().getWorld());
        final String arg = StringArgumentType.getString(context, "color");
        final float scale = FloatArgumentType.getFloat(context, "scale");
        final int color = Integer.parseInt(arg, 16); // Convert hex string to int

        assert player != null;
        Pixel pixel = new Pixel(player.getPos(), scale, color, world);
        pixel.render();
        return 0;
    }
}
