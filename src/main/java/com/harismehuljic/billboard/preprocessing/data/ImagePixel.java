package com.harismehuljic.billboard.preprocessing.data;

/**
 * Represents a pixel in an image with its RGB value and connections to neighboring pixels.
 */
public class ImagePixel {
    private final int rgb;
    private PixelConnections pixelConnections = new PixelConnections();

    /**
     * Constructs an ImagePixel with the specified RGB value.
     *
     * @param rgb The RGB value of the pixel.
     */
    public ImagePixel(int rgb) {
        this.rgb = rgb;
    }

    /**
     * Constructs an ImagePixel with the specified RGB values for red, green, and blue channels.
     *
     * @param red   The red channel value (0-255).
     * @param green The green channel value (0-255).
     * @param blue  The blue channel value (0-255).
     */
    public ImagePixel(int red, int green, int blue) {
        this.rgb = (red << 16) | (green << 8) | blue;
    }

    /**
     * Constructs an ImagePixel with the specified RGB value and pixel connections.
     *
     * @param rgb               The RGB value of the pixel.
     * @param pixelConnections  The connections to neighboring pixels.
     *
     * @apiNote This should not be used to construct an instance of {@link ImagePixel} directly, this is used by the
     * {@link ImagePixel#copy()} method to create a copy of the pixel with its connections.
     */
    private ImagePixel(int rgb, PixelConnections pixelConnections) {
        this.rgb = rgb;
        this.pixelConnections = pixelConnections;
    }

    /**
     * Returns the RGB value of the pixel.
     *
     * @return The RGB value of the pixel as an integer.
     */
    public int getRGB() {
        return rgb;
    }

    /**
     * Returns the red channel value of the pixel.
     *
     * @return The red channel value (0-255).
     */
    public int getRedChannel() {
        return (rgb >> 16) & 0xFF;
    }

    /**
     * Returns the green channel value of the pixel.
     *
     * @return The green channel value (0-255).
     */
    public int getGreenChannel() {
        return (rgb >> 8) & 0xFF;
    }

    /**
     * Returns the blue channel value of the pixel.
     *
     * @return The blue channel value (0-255).
     */
    public int getBlueChannel() {
        return rgb & 0xFF;
    }

    /**
     * Returns the pixel connections for this pixel.
     *
     * @return The PixelConnections object containing connections to neighboring pixels.
     *
     * @see PixelConnections
     */
    public PixelConnections getPixelConnections() {
        return pixelConnections;
    }

    /**
     * Creates a copy of this ImagePixel with the same RGB value and pixel connections.
     *
     * @return A new ImagePixel instance with the same RGB value and pixel connections.
     */
    public ImagePixel copy() {
        return new ImagePixel(this.rgb, this.pixelConnections);
    }
}
