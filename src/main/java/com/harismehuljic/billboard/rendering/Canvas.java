package com.harismehuljic.billboard.rendering;

import com.harismehuljic.billboard.image.ImagePixel;
import com.harismehuljic.billboard.impl.CanvasServer;
import com.harismehuljic.billboard.util.Serializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

public class Canvas {
    private final String canvasUUID = UUID.randomUUID().toString();
    private final int height;
    private final int width;
    private final float pixelScale;

    transient private final Vec3d pos;
    transient final World world;

    transient private final Pixel[][] worldImagePixels;
    private final ArrayList<String> pixelUUIDs = new ArrayList<>();

    /**
     * Creates a new Canvas instance.
     * @param width The width of the canvas in terms of pixels.
     * @param height The height of the canvas in terms of pixels.
     * @param pos The position of the top-left corner of the canvas in the world.
     * @param pixelScale The scale of each pixel in the canvas, determining how large each pixel appears in the world.
     * @param world The Minecraft world where the canvas will be rendered.
     */
    public Canvas(int width, int height, Vec3d pos, float pixelScale, World world, ImagePixel[][] imagePixels) throws IllegalArgumentException {
        this.width = width;
        this.height = height;
        this.pos = pos;
        this.pixelScale = pixelScale;
        this.world = world;

        this.worldImagePixels = new Pixel[this.height][this.width];

        this.definePixels(imagePixels);

        MinecraftServer server = this.world.getServer();
        assert server != null;

        CanvasServer canvasServer = (CanvasServer) server;
        canvasServer.billboard$getCanvasManager().addCanvas(this.canvasUUID, this);

        Path savePath = Serializer.getSavePath(server).resolve("canvas");
        Serializer.serialize(this, savePath, this.canvasUUID);
    }

    private void definePixels(ImagePixel[][] pixels) {
        Vec3d pos = new Vec3d(this.pos.getX(), this.pos.getY(), this.pos.getZ());
        float coordStep = Pixel.getPixelBlocks(this.pixelScale);

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                int rgbValue = pixels[y][x].getRGB();
                int pixelLength = pixels[y][x].getLength();

                Pixel pixel = new Pixel(pos, this.world, this.pixelScale, rgbValue, pixelLength);
                this.worldImagePixels[y][x] = pixel;
                this.pixelUUIDs.add(pixel.getUUID());

                pos = pos.add(coordStep*pixelLength, 0, 0);
            }
            pos = new Vec3d(this.pos.getX(), pos.getY() - coordStep, pos.getZ());
        }
    }

    public void render() {
        for (int y = 0; y < this.height; y++) {
            for (Pixel pixel : this.worldImagePixels[y]) {
                if (pixel == null) {
                    continue;
                }
                pixel.render();
            }
        }
    }

    public void destroy() {
        for (Pixel[] row : this.worldImagePixels) {
            for (Pixel pixel : row) {
                if (pixel != null) {
                    pixel.destroy();
                }
            }
        }
    }
}
