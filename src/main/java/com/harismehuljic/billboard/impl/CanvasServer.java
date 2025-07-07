package com.harismehuljic.billboard.impl;

import net.minecraft.server.MinecraftServer;

import com.harismehuljic.billboard.rendering.CanvasManager;
import com.harismehuljic.billboard.mixin.MinecraftServerMixin;

/**
 * Interface for the {@link MinecraftServerMixin} which allows the user to cast an instance of {@link MinecraftServer}
 * to this interface to access the {@link CanvasManager}.
 */
public interface CanvasServer {
    /**
     * Returns the {@link CanvasManager} instance associated with this server.
     * @return The {@link CanvasManager} instance.
     */
    CanvasManager billboard$getCanvasManager();
}
