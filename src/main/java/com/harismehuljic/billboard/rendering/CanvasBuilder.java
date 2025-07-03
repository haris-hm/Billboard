package com.harismehuljic.billboard.rendering;

import com.harismehuljic.billboard.Billboard;
import com.harismehuljic.billboard.image.ImagePixel;
import com.harismehuljic.billboard.image.ProcessedImage;
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
    private ImagePixel[][] imagePixels;

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

    public CanvasBuilder setImage(ProcessedImage image) throws IllegalStateException {
        if (this.height <= 0 || this.width <= 0) {
            throw new IllegalStateException("Canvas width or height must be defined and greater than 0 before setting the image.");
        }
        else if (image.getWidth() != this.width || image.getHeight() != this.height) {
            throw new IllegalArgumentException("Image dimensions must match the canvas dimensions. Image width: " + image.getWidth() + ", height: " + image.getHeight() + ", Canvas width: " + this.width + ", height: " + this.height);
        }

        this.imagePixels = image.getPixelData();

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

        this.imagePixels = new ImagePixel[this.height][this.width];

        int maxColors = 0x1000000; // 16,777,216
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                this.imagePixels[y][x] = new ImagePixel((int) Math.floor(maxColors * Math.random()));
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
            this.imagePixels
        );
    }


}
