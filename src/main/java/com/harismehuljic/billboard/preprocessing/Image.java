package com.harismehuljic.billboard.preprocessing;

import com.harismehuljic.billboard.preprocessing.data.ImagePixel;

import java.awt.image.BufferedImage;
import java.util.stream.Stream;

/**
 * Abstract class representing an image that can be processed to extract pixel data.
 * This class provides methods to access pixel data, dimensions, and to resize images.
 */
public abstract class Image {
    protected final int width;
    protected final int height;
    protected final ImagePixel[][] pixelData;

    /**
     * Constructor that initializes the image with the given BufferedImage.
     * @param image The BufferedImage to be processed.
     */
    protected Image(BufferedImage image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.pixelData = new ImagePixel[image.getHeight()][image.getWidth()];
        this.processImage(image);
    }

    /**
     * Constructor that initializes the image with the given BufferedImage and resizes it to the specified width and height.
     * @param image The BufferedImage to be processed.
     * @param width The desired width of the resized image.
     * @param height The desired height of the resized image.
     */
    protected Image(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = resizeImage(image, width, height);

        this.width = resizedImage.getWidth();
        this.height = resizedImage.getHeight();
        this.pixelData = new ImagePixel[resizedImage.getHeight()][resizedImage.getWidth()];
        this.processImage(resizedImage);
    }

    /**
     * Abstract method to process the image and extract pixel data.
     * @param image The BufferedImage to be processed.
     */
    public abstract void processImage(BufferedImage image);

    /**
     * Returns the pixel data of the image at the specified coordinates.
     * @param x The x-coordinate of the pixel. Must be within the bounds of the image width.
     * @param y The y-coordinate of the pixel. Must be within the bounds of the image height.
     * @return A 2D array of ImagePixel objects representing the pixel data.
     * @throws IllegalArgumentException if the specified coordinates are out of bounds.
     */
    public ImagePixel getPixel(int x, int y) throws IllegalArgumentException {
        if (x < 0 || x > this.width) {
            throw new IllegalArgumentException(String.format("Specified x (%s) is out of bounds. Bounds are: %s < x <= %s.", x, 0, this.width));
        }
        else if (y < 0 || y > this.height) {
            throw new IllegalArgumentException(String.format("Specified y (%s) is out of bounds. Bounds are: %s < y <= %s.", y, 0, this.width));
        }

        return this.pixelData[y][x];
    }

    /**
     * Returns the amount of pixels in the specified row of the image.
     * @param y The y-coordinate of the row. Must be within the bounds of the image height.
     * @return The number of pixels in the specified row.
     * @throws IllegalArgumentException if the specified y-coordinate is out of bounds.
     */
    public int getRowPixelCount(int y) throws IllegalArgumentException {
        if (y < 0 || y > this.height) {
            throw new IllegalArgumentException(String.format("Specified y (%s) is out of bounds. Bounds are: %s < y <= %s.", y, 0, this.width));
        }

        return this.pixelData[y].length;
    }

    /**
     * Returns the width of the image.
     * @return The width of the image in pixels.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the image.
     * @return The height of the image in pixels.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns a flattened array of all pixel data in the image.
     * @return An array of ImagePixel objects containing all pixel data.
     */
    public ImagePixel[] getFlattenedPixelData() {
        return Stream.of(this.pixelData).flatMap(Stream::of).toArray(ImagePixel[]::new);
    }

    /**
     * Resizes the given image to the specified width and height.
     * @param image The image to resize, provided as a BufferedImage.
     * @param width The desired width of the resized image.
     * @param height The desired height of the resized image.
     * @return A new BufferedImage instance containing the resized image.
     */
    private static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        java.awt.Image resultingImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}
