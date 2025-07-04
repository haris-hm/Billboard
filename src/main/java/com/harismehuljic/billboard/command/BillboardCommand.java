package com.harismehuljic.billboard.command;

import com.harismehuljic.billboard.image.*;
import com.harismehuljic.billboard.impl.CanvasServer;
import com.harismehuljic.billboard.rendering.Canvas;
import com.harismehuljic.billboard.rendering.CanvasBuilder;
import com.harismehuljic.billboard.rendering.Pixel;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static net.minecraft.server.command.CommandManager.literal;

public class BillboardCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess,
                                CommandManager.RegistrationEnvironment environment) {
        final LiteralCommandNode<ServerCommandSource> testCommand = dispatcher.register(literal("billboard")
                .then(literal("pixel")
                        .then(CommandManager.argument("color", StringArgumentType.string())
                                .then(CommandManager.argument("scale", FloatArgumentType.floatArg(0))
                                        .then(CommandManager.argument("length", IntegerArgumentType.integer(0))
                                                .executes(BillboardCommand::renderPixel)
                                        )
                                )
                        )
                )

                .then(literal("canvas")
                        .then(CommandManager.argument("width", IntegerArgumentType.integer(0))
                                .then(CommandManager.argument("height", IntegerArgumentType.integer(0))
                                        .then(CommandManager.argument("scale", FloatArgumentType.floatArg(0.1F))
                                                .executes(BillboardCommand::renderCanvas)
                                        )
                                )
                        )
                )

                .then(literal("image")
                        .then(CommandManager.argument("width", IntegerArgumentType.integer(0))
                                .then(CommandManager.argument("height", IntegerArgumentType.integer(0))
                                        .then(CommandManager.argument("scale", FloatArgumentType.floatArg(0.0000001F))
                                                .then(CommandManager.argument("imageType", StringArgumentType.string())
                                                        .suggests((context, builder) -> CommandSource.suggestMatching(new String[]{"rle", "raw"}, builder))
                                                        .then(CommandManager.argument("url", StringArgumentType.greedyString())
                                                                .executes(BillboardCommand::renderImage)
                                                        )
                                                )
                                        )
                                )
                        )
                )

                .then(literal("resizedImage")
                        .then(CommandManager.argument("resizeFactor", FloatArgumentType.floatArg(0.001F))
                            .then(CommandManager.argument("scale", FloatArgumentType.floatArg(0.0000001F))
                                    .then(CommandManager.argument("imageType", StringArgumentType.string())
                                            .suggests((context, builder) -> CommandSource.suggestMatching(new String[]{"rle", "raw"}, builder))
                                            .then(CommandManager.argument("url", StringArgumentType.greedyString())
                                                    .executes(BillboardCommand::renderResizedImage)
                                            )
                                    )
                            )
                        )
                )

                .then(literal("remove")
                        .then(CommandManager.argument("uuid", StringArgumentType.greedyString())
                                .suggests(CANVAS_UUID_PROVIDER)
                                .executes(BillboardCommand::removeCanvas)
                        )
                )
        );
    }

    private static int renderPixel(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        World world = Objects.requireNonNull(context.getSource().getWorld());
        final String arg = StringArgumentType.getString(context, "color");
        final float scale = FloatArgumentType.getFloat(context, "scale");
        final int length = IntegerArgumentType.getInteger(context, "length");
        final int color = Integer.parseInt(arg, 16); // Convert hex string to int

        assert player != null;
        Pixel pixel = new Pixel(player.getPos(), world, scale, color, length);
        pixel.render();
        return 0;
    }

    private static int renderCanvas(CommandContext<ServerCommandSource> context) {
        ServerPlayerEntity player = context.getSource().getPlayer();
        World world = Objects.requireNonNull(context.getSource().getWorld());

        final int width = IntegerArgumentType.getInteger(context, "width");
        final int height = IntegerArgumentType.getInteger(context, "height");
        final float scale = FloatArgumentType.getFloat(context, "scale");

        assert player != null;
        Canvas canvas = new CanvasBuilder()
                .setWidth(width)
                .setHeight(height)
                .setPixelScale(scale)
                .setPos(player.getPos())
                .setWorld(world)
                .setImage()
                .build();

        canvas.render();

        return 0;
    }

    private static int renderImage(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = context.getSource().getPlayer();
        World world = Objects.requireNonNull(context.getSource().getWorld());

        final int width = IntegerArgumentType.getInteger(context, "width");
        final int height = IntegerArgumentType.getInteger(context, "height");
        final float scale = FloatArgumentType.getFloat(context, "scale");
        final String url = StringArgumentType.getString(context, "url");
        final String imageType = StringArgumentType.getString(context, "imageType");

        assert player != null;

        ImageRequester.getImage(url).orTimeout(60, TimeUnit.SECONDS).handleAsync((image, ex) -> {
            CompletableFuture.supplyAsync(() -> {
                if (ex != null) {
                    source.sendError(Text.literal("Failed to load image: " + ex.getMessage()));
                    return null;
                }
                ImageTypes type;
                switch (imageType.toLowerCase()) {
                    case "rle":
                        type = ImageTypes.RLE;
                        break;
                    case "raw":
                        type = ImageTypes.RAW;
                        break;
                    default:
                        source.sendError(Text.literal("Unsupported image type: " + imageType));
                        return null;
                }
                Canvas canvas = new CanvasBuilder()
                        .setWidth(width)
                        .setHeight(height)
                        .setPixelScale(scale)
                        .setPos(player.getPos())
                        .setWorld(world)
                        .setImage(image, type)
                        .build();
                canvas.render();
                return null;
            }, source.getServer());
            return 0;
        }, source.getServer());
        return 1;
    }

    private static int renderResizedImage(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = context.getSource().getPlayer();
        World world = Objects.requireNonNull(context.getSource().getWorld());

        final float resizeFactor = FloatArgumentType.getFloat(context, "resizeFactor");
        final float scale = FloatArgumentType.getFloat(context, "scale");
        final String url = StringArgumentType.getString(context, "url");
        final String imageType = StringArgumentType.getString(context, "imageType");

        assert player != null;

        ImageRequester.getImage(url).orTimeout(60, TimeUnit.SECONDS).handleAsync((image, ex) -> {
            CompletableFuture.supplyAsync(() -> {
                if (ex != null) {
                    source.sendError(Text.literal("Failed to load image: " + ex.getMessage()));
                    return null;
                }
                ImageTypes type;
                switch (imageType.toLowerCase()) {
                    case "rle":
                        type = ImageTypes.RLE;
                        break;
                    case "raw":
                        type = ImageTypes.RAW;
                        break;
                    default:
                        source.sendError(Text.literal("Unsupported image type: " + imageType));
                        return null;
                }
                Canvas canvas = new CanvasBuilder()
                        .setPixelScale(scale)
                        .setWidth(image.getWidth())
                        .setHeight(image.getHeight())
                        .setPos(player.getPos())
                        .setWorld(world)
                        .setImage(image, type, resizeFactor)
                        .build();
                canvas.render();
                return null;
            }, source.getServer());
            return 0;
        }, source.getServer());
        return 1;
    }

    private static int removeCanvas(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        MinecraftServer server = source.getServer();
        CanvasServer canvasServer = (CanvasServer) server;

        String uuidString = StringArgumentType.getString(context, "uuid");

        if (canvasServer.billboard$getCanvasManager().removeCanvas(uuidString)) {
            source.sendFeedback(() -> Text.literal("Canvas with UUID " + uuidString + " has been removed.").formatted(Formatting.GREEN), false);
        } else {
            source.sendError(Text.literal("No canvas found with UUID: " + uuidString).formatted(Formatting.RED));
        }
        return 1;
    }

    private static final SuggestionProvider<ServerCommandSource> CANVAS_UUID_PROVIDER = (source, builder) -> {
        MinecraftServer server = source.getSource().getServer();
        CanvasServer canvasServer = (CanvasServer) server;

        return CommandSource.suggestMatching(canvasServer.billboard$getCanvasManager().getCanvasUUIDs(), builder);
    };
}
