package com.harismehuljic.billboard.rendering;

import com.harismehuljic.billboard.Billboard;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;

public class CanvasBuilder {
    private int width;
    private int height;
    private Vec3d pos;
    private float pixelScale;
    private World world;
    private int[][] rgbValues;

    public CanvasBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public CanvasBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public CanvasBuilder setPixelScale(float pixelScale) {
        this.pixelScale = pixelScale;
        return this;
    }

    public CanvasBuilder setPos(Vec3d pos) {
        this.pos = pos;
        return this;
    }

    public CanvasBuilder setWorld(World world) {
        this.world = world;
        return this;
    }

    public CanvasBuilder setImage(BufferedImage image) throws IllegalStateException {
        if (this.height <= 0 || this.width <= 0) {
            throw new IllegalStateException("Canvas width or height must be defined and greater than 0 before setting the image.");
        }

        BufferedImage resizedImage = resizeImage(image, this.width, this.height);
        this.rgbValues = new int[this.height][this.width];
        HashSet<Integer> uniqueColors = new HashSet<>();

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.rgbValues[y][x] = resizedImage.getRGB(x, y);
                uniqueColors.add(this.rgbValues[y][x]);
            }
        }

        Billboard.LOGGER.info("Unique colors in the image: {}, Total pixels: {}", uniqueColors.size(), this.width * this.height);

        return this;
    }

    /**
     * Generates an image full of random noise, used primarily for testing purposes.
     * @return This builder instance for method chaining.
     * @throws IllegalStateException If the height and width have not been properly set before defining the image to render.
     */
    public CanvasBuilder setImage() throws IllegalStateException {
        if (this.height <= 0 || this.width <= 0) {
            throw new IllegalStateException("Canvas width or height must be defined and greater than 0 before setting the image.");
        }

        this.rgbValues = new int[this.height][this.width];

        int maxColors = 0x1000000; // 16,777,216
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.rgbValues[y][x] = (int) Math.floor(maxColors * Math.random());
            }
        }

        return this;
    }

    public Canvas build() {
        return new Canvas(
            this.width,
            this.height,
            this.pos,
            this.pixelScale,
            this.world,
            this.rgbValues
        );
    }

    private BufferedImage resizeImage(BufferedImage image, int width, int height) {
        Image resultingImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}
