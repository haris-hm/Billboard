package com.harismehuljic.billboard.mixin;

import com.harismehuljic.billboard.impl.CanvasServer;
import com.harismehuljic.billboard.rendering.CanvasManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.util.thread.ReentrantThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * Mixin for the {@link MinecraftServer} class to provide access to the {@link CanvasManager}.
 * This allows the server to manage canvases and their associated data.
 */
@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin extends ReentrantThreadExecutor<ServerTask> implements CanvasServer {
    @Unique private final CanvasManager canvasManager = new CanvasManager();

    public MinecraftServerMixin(String string) {
        super(string);
    }

    /**
     * Returns the {@link CanvasManager} instance associated with this server.
     * This method is used to access the canvas management functionality.
     *
     * @return The {@link CanvasManager} instance.
     */
    @Unique
    public CanvasManager billboard$getCanvasManager() {
        return this.canvasManager;
    }
}
