package com.harismehuljic.billboard.rendering;

import com.harismehuljic.billboard.preprocessing.Image;
import com.harismehuljic.billboard.preprocessing.data.Color;
import com.harismehuljic.billboard.preprocessing.data.ImagePixel;
import com.harismehuljic.billboard.impl.CanvasServer;
import com.harismehuljic.billboard.preprocessing.data.PixelConnections;
import com.harismehuljic.billboard.util.Serializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a canvas in the Minecraft world that can render an image using {@link CanvasPixel}s.
 * Each pixel in the canvas is represented by a {@link CanvasPixel}, which is rendered as a block in the world.
 *
 * @implNote The canvas is defined by its width, height, position in the world, and pixel scale.
 * The canvas pixels are created based on the provided image and rendered in the world.
 */
public class Canvas {
    private final String canvasUUID = UUID.randomUUID().toString();
    private final int height;
    private final int width;
    private final float pixelScale;

    transient private final Vec3d pos;
    transient final World world;

    transient private final CanvasPixel[][] worldImageCanvasPixels;
    private final ArrayList<String> pixelUUIDs = new ArrayList<>();

    /**
     * Creates a new Canvas instance.
     * @param width The width of the canvas in terms of pixels.
     * @param height The height of the canvas in terms of pixels.
     * @param pos The position of the top-left corner of the canvas in the world.
     * @param pixelScale The scale of each canvasPixel in the canvas, determining how large each canvasPixel appears in the world.
     * @param world The Minecraft world where the canvas will be rendered.
     */
    public Canvas(int width, int height, Vec3d pos, float pixelScale, World world, Image image) throws IllegalArgumentException {
        this.width = width;
        this.height = height;
        this.pos = pos;
        this.pixelScale = pixelScale;
        this.world = world;

        this.worldImageCanvasPixels = new CanvasPixel[this.height][this.width];

        this.definePixels(image);

        MinecraftServer server = this.world.getServer();
        assert server != null;

        CanvasServer canvasServer = (CanvasServer) server;
        canvasServer.billboard$getCanvasManager().addCanvas(this.canvasUUID, this);

        Path savePath = Serializer.getSavePath(server).resolve("canvas");
        Serializer.serialize(this, savePath, this.canvasUUID);
    }

    /**
     * Defines the pixels of the canvas based on the provided image.
     * @param image The image to be rendered on the canvas.
     */
    private void definePixels(Image image) {
        Vec3d pos = new Vec3d(this.pos.getX(), this.pos.getY(), this.pos.getZ());
        float coordStep = CanvasPixel.getPixelBlocks(this.pixelScale);

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < image.getRowPixelCount(y); x++) {
                ImagePixel imagePixel = image.getPixel(x, y);
                int rgb = imagePixel.getColor().getRGB();

                if (imagePixel.getPixelConnections().isConnected(PixelConnections.ConnectionDirection.LEFT)) {
                    this.handleConnectedPixels(imagePixel, x, y);
                }
                else {
                    CanvasPixel canvasPixel = new CanvasPixel(pos, this.world, this.pixelScale, new Color(rgb));
                    this.worldImageCanvasPixels[y][x] = canvasPixel;
                    this.pixelUUIDs.add(canvasPixel.getUUID());
                }

                pos = pos.add(coordStep, 0, 0);
            }
            pos = new Vec3d(this.pos.getX(), pos.getY() - coordStep, pos.getZ());
        }
    }

    private void handleConnectedPixels(ImagePixel imagePixel, int x, int y) {
        //TODO: Handle different types of connections other than LEFT
        CanvasPixel canvasPixel = this.worldImageCanvasPixels[y][x-1];
        canvasPixel.setLength(canvasPixel.getLength() + 1);
        this.worldImageCanvasPixels[y][x] = canvasPixel;
    }

    /**
     * Renders the canvas in the Minecraft world.
     *
     * @see CanvasPixel#render()
     */
    public void render() {
        for (int y = 0; y < this.height; y++) {
            for (CanvasPixel canvasPixel : this.worldImageCanvasPixels[y]) {
                if (canvasPixel == null) {
                    continue;
                }
                canvasPixel.render();
            }
        }
    }


    /**
     * Destroys all the canvas pixels linked to this canvas from the world.
     *
     * @see CanvasPixel#destroy()
     */
    public void destroy() {
        for (CanvasPixel[] row : this.worldImageCanvasPixels) {
            for (CanvasPixel canvasPixel : row) {
                if (canvasPixel != null) {
                    canvasPixel.destroy();
                }
            }
        }
    }
}
