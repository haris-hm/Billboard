package com.harismehuljic.billboard.preprocessing.data;

/**
 * Represents a color in RGB format.
 *
 * <p>The color is stored as an integer in the format 0xRRGGBB, where RR, GG, and BB are the red, green,
 * and blue channel values respectively.</p>
 */
public class Color {
    public int rgb;

    /**
     * Constructs a Color object with the specified RGB value.
     *
     * @param rgb The RGB value of the color as an integer.
     */
    public Color(int rgb) {
        this.rgb = rgb;
    }

    /**
     * Constructs a Color object with the specified RGB value.
     *
     * @param red   The red channel value (0-255).
     * @param green The green channel value (0-255).
     * @param blue  The blue channel value (0-255).
     * @throws IllegalArgumentException If any of the RGB values are outside the range 0-255.
     */
    public Color(int red, int green, int blue) throws IllegalArgumentException {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            throw new IllegalArgumentException("RGB values must be in the range 0-255.");
        }

        this.rgb = (red << 16) | (green << 8) | blue;
    }

    /**
     * Sets the color of this Color object to the color of the specified Color object.
     *
     * @param color The Color object to copy the color from.
     */
    public void setColor(Color color) {
        this.rgb = color.rgb;
    }

    /**
     * Sets the color of this Color object to the specified RGB value.
     *
     * @param rgb The RGB value to set the color to, as an integer.
     */
    public void setColor(int rgb) {
        this.rgb = rgb;
    }

    /**
     * Returns the red channel value of the color.
     *
     * @return The red channel value as an integer (0-255).
     */
    public int getRedChannel() {
        return (rgb >> 16) & 0xFF;
    }

    /**
     * Returns the green channel value of the color.
     *
     * @return The green channel value as an integer (0-255).
     */
    public int getGreenChannel() {
        return (rgb >> 8) & 0xFF;
    }

    /**
     * Returns the blue channel value of the color.
     *
     * @return The blue channel value as an integer (0-255).
     */
    public int getBlueChannel() {
        return rgb & 0xFF;
    }

    /**
     * Returns the value of the specified color channel.
     *
     * @param channel The color channel to retrieve (RED, GREEN, or BLUE).
     * @return The value of the specified color channel as an integer.
     *
     * @see ColorChannel
     */
    public int getChannelValue(ColorChannel channel) {
        return switch (channel) {
            case RED -> getRedChannel();
            case GREEN -> getGreenChannel();
            case BLUE -> getBlueChannel();
        };
    }

    /**
     * Returns the RGB value of the color.
     *
     * @return The RGB value of the color as an integer.
     */
    public int getRGB() {
        return rgb;
    }

    /**
     * Calculates the squared Euclidean distance between this color and another color.
     *
     * @param otherColor The other color to compare against.
     * @return The squared Euclidean distance between the two colors.
     */
    public int getDistance(Color otherColor) {
        int redDiff = otherColor.getRedChannel() - this.getRedChannel();
        int greenDiff = otherColor.getGreenChannel() - this.getGreenChannel();
        int blueDiff = otherColor.getBlueChannel() - this.getBlueChannel();

        return (int) (Math.pow(redDiff, 2) + Math.pow(greenDiff, 2) + Math.pow(blueDiff, 2));
    }

    /**
     * Returns a string representation of the color, with values for the individual RGB channels, as well as the
     * hexadecimal representation of the color.
     *
     * @return The color in hex format, e.g., "#FF5733".
     */
    public String toString() {
        return String.format("Color{red=%d, green=%d, blue=%d, hex:%s}", getRedChannel(), getGreenChannel(), getBlueChannel(),
                             String.format("#%06X", this.rgb & 0xFFFFFF));
    }
}
