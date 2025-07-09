package com.harismehuljic.billboard.preprocessing;

import com.harismehuljic.billboard.preprocessing.data.ImagePixel;
import com.harismehuljic.billboard.rendering.Canvas;

import java.awt.image.BufferedImage;

/**
 * Represents a raw image without any processing.
 * This class extends the Image class and processes the image
 * by simply copying pixel data without any additional transformations.
 *
 * @implNote This class is essentially a wrapper around a {@link BufferedImage} used solely to enable the {@link Canvas}
 * to render non-processed images the same way as processed images.
 */
public class RawImage extends Image {
    /**
     * Constructs a RawImage from a BufferedImage.
     *
     * @param image The BufferedImage to be processed.
     */
    public RawImage(BufferedImage image) {
        super(image);
    }

    /**
     * Constructs a RawImage from a BufferedImage and resizes it to the specified width and height.
     *
     * @param image The BufferedImage to be processed.
     * @param width The desired width of the resized image.
     * @param height The desired height of the resized image.
     */
    public RawImage(BufferedImage image, int width, int height) {
        super(image, width, height);
    }

    /**
     * Constructs a RawImage from another Image instance.
     *
     * @param image The Image to be processed.
     */
    public RawImage(Image image) {
        super(image);
    }

    /**
     * Processes the image by copying pixel data from the BufferedImage.
     *
     * @param image The BufferedImage to be processed.
     *
     * @implNote This method iterates through each pixel of the image and creates an {@link ImagePixel} object
     */
    @Override
    public void processImage(ColorExtractable image) {
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                int rgb = image.getColor(x, y);
                pixelData[y][x] = new ImagePixel(rgb);
            }
        }
    }
}
