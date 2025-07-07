package com.harismehuljic.billboard.preprocessing;

import com.harismehuljic.billboard.preprocessing.data.ImagePixel;
import com.harismehuljic.billboard.preprocessing.data.PixelConnections;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RunLengthEncodedImage extends Image {
    public RunLengthEncodedImage(BufferedImage image) {
        super(image);
    }

    @Override
    public void processImage(BufferedImage image) {
        for (int y = 0; y < this.height; y++) {
            ImagePixel lastPixel = new ImagePixel(image.getRGB(0, y));

            for (int x = 0; x < this.width; x++) {
                int pixelColor = image.getRGB(x, y);
                ImagePixel currentPixel = new ImagePixel(pixelColor);

                if (x > 0 && pixelColor == lastPixel.getRGB()) {
                    PixelConnections lastPixelConnections = lastPixel.getPixelConnections();
                    PixelConnections currentPixelConnections = currentPixel.getPixelConnections();

                    lastPixelConnections.setPixel(PixelConnections.ConnectionDirection.RIGHT, currentPixel);
                    currentPixelConnections.setPixel(PixelConnections.ConnectionDirection.LEFT, lastPixel);
                }

                this.pixelData[y][x] = currentPixel.copy();
                lastPixel = currentPixel;
            }
        }
    }
}
