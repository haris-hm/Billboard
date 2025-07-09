package com.harismehuljic.billboard.preprocessing;

/**
 * Functional interface for extracting color from an image at a specific pixel location.
 * <p>This interface is used to provide a method for retrieving the color value of a pixel
 * given its x and y coordinates.</p>
 */
@FunctionalInterface
public interface ColorExtractable {
    /**
     * Retrieves the color of a pixel at the specified coordinates.
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return The color of the pixel as an integer RGB value.
     */
    int getColor(int x, int y);
}
