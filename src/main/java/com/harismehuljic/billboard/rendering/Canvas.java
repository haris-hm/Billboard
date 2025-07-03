package com.harismehuljic.billboard.rendering;

import com.harismehuljic.billboard.Billboard;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Canvas {
    private final int width;
    private final int height;
    private final Vec3d pos;
    private final float pixelScale;
    private final World world;

    private final Pixel[][] imagePixels;

    /**
     * Creates a new Canvas instance.
     * @param width The width of the canvas in terms of pixels.
     * @param height The height of the canvas in terms of pixels.
     * @param pos The position of the top-left corner of the canvas in the world.
     * @param pixelScale The scale of each pixel in the canvas, determining how large each pixel appears in the world.
     * @param world The Minecraft world where the canvas will be rendered.
     */
    public Canvas(int width, int height, Vec3d pos, float pixelScale, World world, int[][] rgbValues) throws IllegalArgumentException {
        this.width = width;
        this.height = height;
        this.pos = pos;
        this.pixelScale = pixelScale;
        this.world = world;

        this.imagePixels = new Pixel[this.height][this.width];

        Billboard.LOGGER.info("Width: {}, Height: {}, rgbValues length: {}, rgbValues[0].length: {}", width, height, rgbValues.length, rgbValues[0].length);

        if (rgbValues.length != this.height || rgbValues[0].length != this.width) {
            throw new IllegalArgumentException("RGB values dimensions must match canvas dimensions.");
        }
        this.definePixels(rgbValues);
    }

    private void definePixels(int[][] rgbValues) {
        Vec3d pos = new Vec3d(this.pos.getX(), this.pos.getY(), this.pos.getZ());
        float coordStep = Pixel.getBlockConstant() * this.pixelScale;

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.imagePixels[y][x] = new Pixel(pos, this.pixelScale, rgbValues[y][x], this.world);
                pos = pos.add(coordStep, 0, 0);
            }
            pos = new Vec3d(this.pos.getX(), pos.getY() - coordStep, pos.getZ());
        }
    }

    public void render() {
        for (int y = 0; y < this.height; y++) {
            for (Pixel pixel : this.imagePixels[y]) {
                pixel.render();
            }
        }
    }
}
