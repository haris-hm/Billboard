package com.harismehuljic.billboard.preprocessing;

import com.harismehuljic.billboard.preprocessing.data.ImagePixel;

import java.awt.image.BufferedImage;

public abstract class Image {
    protected final int width;
    protected final int height;
    protected final ImagePixel[][] pixelData;

    public Image(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.pixelData = new ImagePixel[image.getHeight()][image.getWidth()];
        this.processImage(image);
    }

    public abstract void processImage(BufferedImage image);

    public ImagePixel[][] getPixelData() {
        return this.pixelData;
    }

    public ImagePixel getPixel(int x, int y) throws IllegalArgumentException {
        if (x < 0 || x > this.width) {
            throw new IllegalArgumentException(String.format("Specified x (%s) is out of bounds. Bounds are: %s < x <= %s.", x, 0, this.width));
        }
        else if (y < 0 || y > this.height) {
            throw new IllegalArgumentException(String.format("Specified y (%s) is out of bounds. Bounds are: %s < y <= %s.", y, 0, this.width));
        }

        return this.pixelData[y][x];
    }

    public int getRowPixelCount(int y) throws IllegalArgumentException {
        if (y < 0 || y > this.height) {
            throw new IllegalArgumentException(String.format("Specified y (%s) is out of bounds. Bounds are: %s < y <= %s.", y, 0, this.width));
        }

        return this.pixelData[y].length;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
