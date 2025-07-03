package com.harismehuljic.billboard.rendering;

import com.harismehuljic.billboard.image.ImagePixel;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Canvas {
    private final int width;
    private final int height;
    private final Vec3d pos;
    private final float pixelScale;
    private final World world;

    private final Pixel[][] worldImagePixels;

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
    }

    private void definePixels(ImagePixel[][] pixels) {
        Vec3d pos = new Vec3d(this.pos.getX(), this.pos.getY(), this.pos.getZ());
        float coordStep = Pixel.getPixelBlocks(this.pixelScale);

        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                int rgbValue = pixels[y][x].getRGB();
                int pixelLength = pixels[y][x].getLength();

                this.worldImagePixels[y][x] = new Pixel(pos, this.world, this.pixelScale, rgbValue, pixelLength);

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
}
