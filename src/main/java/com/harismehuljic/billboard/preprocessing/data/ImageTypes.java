package com.harismehuljic.billboard.preprocessing.data;

/**
 * Enum representing the types of images supported in the preprocessing module.
 * Each type corresponds to a specific image format.
 */
public enum ImageTypes {
    RAW("raw"),
    RLE("rle");

    private final String type;

    /**
     * Constructor for the ImageTypes enum.
     *
     * @param type The string representation of the image type.
     *
     * @apiNote Accepted string representations are "raw" and "rle".
     */
    ImageTypes(String type) {
        this.type = type;
    }

    /**
     * Returns the string representation of the image type.
     *
     * @return the type as a string
     */
    public String getType() {
        return this.type;
    }

    /**
     * Converts the image type to its string representation.
     *
     * @return the type as a string
     */
    @Override
    public String toString() {
        return this.type;
    }
}
