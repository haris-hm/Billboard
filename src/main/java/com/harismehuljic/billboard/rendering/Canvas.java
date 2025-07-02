package com.harismehuljic.billboard.rendering;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Canvas {
    private final int width;
    private final int height;
    private final Vec3d pos;
    private final float pixelScale;
    private final World world;

    private final Pixel[][] pixels;

    /**
     * Creates a new Canvas instance.
     * @param width The width of the canvas in terms of pixels.
     * @param height The height of the canvas in terms of pixels.
     * @param pos The position of the top-left corner of the canvas in the world.
     * @param pixelScale The scale of each pixel in the canvas, determining how large each pixel appears in the world.
     * @param world The Minecraft world where the canvas will be rendered.
     */
    public Canvas(int width, int height, Vec3d pos, float pixelScale, World world) {
        this.width = width;
        this.height = height;
        this.pos = pos;
        this.pixelScale = pixelScale;
        this.world = world;

        this.pixels = new Pixel[this.width][this.height];
    }

    public void render() {

    }
}
