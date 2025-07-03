package com.harismehuljic.billboard.image;

import java.awt.image.BufferedImage;

public class Image implements ProcessedImage {
    protected final BufferedImage image;
    private final ImagePixel[][] pixelData;

    public Image(BufferedImage image) {
        this.image = image;
        this.pixelData = new ImagePixel[image.getHeight()][image.getWidth()];
        this.processImage();
    }

    @Override
    public void processImage() {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                pixelData[y][x] = new ImagePixel(rgb);
            }
        }
    }

    @Override
    public ImagePixel[][] getPixelData() {
        return this.pixelData;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }
}
