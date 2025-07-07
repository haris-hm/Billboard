package com.harismehuljic.billboard.preprocessing.data;

public class ImagePixel {
    private final int rgb;
    private PixelConnections pixelConnections = new PixelConnections();

    public ImagePixel(int rgb) {
        this.rgb = rgb;
    }

    private ImagePixel(int rgb, PixelConnections pixelConnections) {
        this.rgb = rgb;
        this.pixelConnections = pixelConnections;
    }

    public int getRGB() {
        return rgb;
    }

    public PixelConnections getPixelConnections() {
        return pixelConnections;
    }

    public ImagePixel copy() {
        return new ImagePixel(this.rgb, this.pixelConnections);
    }
}
