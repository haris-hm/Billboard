package com.harismehuljic.billboard.preprocessing;

import com.harismehuljic.billboard.preprocessing.data.ImagePixel;

import java.awt.image.BufferedImage;

public class RawImage extends Image {
    public RawImage(BufferedImage image) {
        super(image);
    }

    @Override
    public void processImage(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                pixelData[y][x] = new ImagePixel(rgb);
            }
        }
    }
}
