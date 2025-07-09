package com.harismehuljic.billboard.preprocessing.data;

/**
 * Represents a pixel in an image with its RGB value and connections to neighboring pixels.
 */
public class ImagePixel {
    private final Color color;
    private PixelConnections pixelConnections = new PixelConnections();

    /**
     * Constructs an ImagePixel with the specified {@link Color} object.
     *
     * @param color The {@link Color} object representing the pixel's color.
     */
    public ImagePixel(Color color) {
        this.color = color;
    }

    /**
     * Constructs an ImagePixel with the specified RGB value.
     *
     * @param rgb The RGB value of the pixel.
     */
    public ImagePixel(int rgb) {
        this.color = new Color(rgb);
    }

    /**
     * Constructs an ImagePixel with the specified RGB values for red, green, and blue channels.
     *
     * @param red   The red channel value (0-255).
     * @param green The green channel value (0-255).
     * @param blue  The blue channel value (0-255).
     */
    public ImagePixel(int red, int green, int blue) {
        this.color = new Color(red, green, blue);
    }

    /**
     * Constructs an ImagePixel with the specified color and pixel connections.
     *
     * @param color             The RGB value of the pixel.
     * @param pixelConnections  The connections to neighboring pixels.
     *
     * @apiNote This should not be used to construct an instance of {@link ImagePixel} directly, this is used by the
     * {@link ImagePixel#copy()} method to create a copy of the pixel with its connections.
     */
    private ImagePixel(Color color, PixelConnections pixelConnections) {
        this.color = color;
        this.pixelConnections = pixelConnections;
    }

    /**
     * Sets the color of this pixel to the specified {@link Color} object.
     *
     * @param color The {@link Color} object to set the pixel's color to.
     *
     * @apiNote This method resets the pixel connections, as the color change may affect the connections. The image will
     * have to be reprocessed to update the connections accordingly.
     */
    public void setColor(Color color) {
        this.pixelConnections = new PixelConnections();
        this.color.setColor(color);
    }

    /**
     * Returns the RGB value of the pixel.
     *
     * @return The RGB value of the pixel as an integer.
     */
    public Color getColor() {
        return this.color;
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
     * Creates a copy of this ImagePixel with the same color value and pixel connections.
     *
     * @return A new ImagePixel instance with the same color value and pixel connections.
     */
    public ImagePixel copy() {
        return new ImagePixel(this.color, this.pixelConnections);
    }
}
