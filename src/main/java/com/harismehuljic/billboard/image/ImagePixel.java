package com.harismehuljic.billboard.image;

public class ImagePixel {
    private final int rgb;
    private int length;

    public ImagePixel(int rgb) {
        this.rgb = rgb;
        this.length = 1;
    }

    private ImagePixel(int rgb, int length) {
        this.rgb = rgb;
        this.length = length;
    }

    public int getRGB() {
        return rgb;
    }

    public int getLength() {
        return length;
    }

    public void incrementLength() {
        this.length++;
    }

    public ImagePixel copy() {
        return new ImagePixel(this.rgb, this.length);
    }
}
