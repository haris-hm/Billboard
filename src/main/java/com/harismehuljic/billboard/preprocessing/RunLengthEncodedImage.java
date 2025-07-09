package com.harismehuljic.billboard.preprocessing;

import com.harismehuljic.billboard.preprocessing.data.ImagePixel;
import com.harismehuljic.billboard.preprocessing.data.PixelConnections;
import com.harismehuljic.billboard.rendering.Canvas;
import com.harismehuljic.billboard.rendering.CanvasPixel;

import java.awt.image.BufferedImage;
import net.minecraft.entity.decoration.DisplayEntity;

/**
 * RunLengthEncodedImage is a specialized image class that processes an image using run-length encoding.
 *
 * @implNote This enables the {@link Canvas} to render each {@link CanvasPixel} more efficiently, as pixels that are
 * adjacent and of the same color in each row are connected, decreasing the number of
 * {@link DisplayEntity.TextDisplayEntity}s that are spawned into the world.
 */
public class RunLengthEncodedImage extends Image {
    /**
     * Constructs a RunLengthEncodedImage from a BufferedImage.
     * @param image The BufferedImage to be processed.
     */
    public RunLengthEncodedImage(BufferedImage image) {
        super(image);
    }

    /**
     * Constructs a RunLengthEncodedImage from a BufferedImage and resizes it to the specified width and height.
     * @param image The BufferedImage to be processed.
     * @param width The desired width of the resized image.
     * @param height The desired height of the resized image.
     */
    public RunLengthEncodedImage(BufferedImage image, int width, int height) {
        super(image, width, height);
    }

    /**
     * Processes the image by applying run-length encoding to the pixel data.
     * @param image The BufferedImage to be processed.
     *
     * @implNote This method saves the pixel data in a way that adjacent pixels of the same color in each row
     * can be easily identified.
     */
    @Override
    public void processImage(BufferedImage image) {
        for (int y = 0; y < this.height; y++) {
            ImagePixel lastPixel = new ImagePixel(image.getRGB(0, y));

            for (int x = 0; x < this.width; x++) {
                int pixelColor = image.getRGB(x, y);
                ImagePixel currentPixel = new ImagePixel(pixelColor);

                if (x > 0 && pixelColor == lastPixel.getColor().getRGB()) {
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
