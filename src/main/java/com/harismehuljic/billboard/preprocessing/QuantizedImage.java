package com.harismehuljic.billboard.preprocessing;

import java.awt.image.BufferedImage;

/**
 * Represents a color quantized image.
 *
 * @implNote Utilizes the median cut algorithm for color quantization.
 */
public class QuantizedImage extends Image {
    public QuantizedImage(BufferedImage image, int colors) {
        super(image);
    }

    @Override
    public void processImage(BufferedImage image) {
        
    }
}
