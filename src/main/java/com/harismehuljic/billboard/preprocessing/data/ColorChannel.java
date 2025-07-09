package com.harismehuljic.billboard.preprocessing.data;

/**
 * Enum representing the color channels in an RGB color model.
 * Each channel corresponds to a primary color: red, green, and blue.
 */
public enum ColorChannel {
    RED,
    GREEN,
    BLUE;

    /**
     * Returns the name of the color channel in lowercase.
     *
     * @return The name of the color channel.
     */
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
