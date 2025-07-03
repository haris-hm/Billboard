package com.harismehuljic.billboard.image;

public interface ProcessedImage {
    void processImage();
    ImagePixel[][] getPixelData();
    int getWidth();
    int getHeight();
}
