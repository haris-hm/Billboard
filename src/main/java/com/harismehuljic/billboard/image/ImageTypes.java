package com.harismehuljic.billboard.image;

public enum ImageTypes {
    RAW("raw"),
    RLE("rle");

    private final String type;

    ImageTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
