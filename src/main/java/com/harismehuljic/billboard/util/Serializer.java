package com.harismehuljic.billboard.util;

import com.google.gson.Gson;
import com.harismehuljic.billboard.Billboard;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Serializer {
    public static void serialize(Object object, Path savePath, String fileName) {
        Gson gson = new Gson();
        File file = new File(savePath.toFile(), fileName + ".json");

        file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            Billboard.LOGGER.error(e.getMessage());
        }
    }

    public static Path getSavePath(MinecraftServer server) {
        return server.getSavePath(WorldSavePath.ROOT).resolve("billboard");
    }
}
