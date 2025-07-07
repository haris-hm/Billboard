package com.harismehuljic.billboard.preprocessing;

import com.harismehuljic.billboard.rendering.Canvas;
import net.minecraft.entity.decoration.DisplayEntity;

import java.awt.image.BufferedImage;

/**
 * Represents and image where connected pixels of the same color are efficiently grouped into rectangles.
 *
 * @implNote This, just like {@link RunLengthEncodedImage}, is a helpful way of rendering the {@link Canvas} more
 * efficiently, as it reduces the number of {@link DisplayEntity.TextDisplayEntity}s spawned into the world
 */
public class RectangularizedImage extends Image {
    public RectangularizedImage(BufferedImage image) {
        super(image);
    }

    public RectangularizedImage(BufferedImage image, int width, int height) {
        super(image, width, height);
    }

    @Override
    public void processImage(BufferedImage image) {

    }

    private void rectangularizeRegion(BufferedImage image, int startX, int startY) {

    }
}
