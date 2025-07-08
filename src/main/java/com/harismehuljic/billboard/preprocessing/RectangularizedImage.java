package com.harismehuljic.billboard.preprocessing;

import com.harismehuljic.billboard.rendering.Canvas;
import net.minecraft.entity.decoration.DisplayEntity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

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
        HashSet<int[]> visitedPixels = new HashSet<>();

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {

            }
        }
    }

    private void floodFillRectangle(BufferedImage image, int x, int y, int color, HashSet<int[]> visitedPixels) {
        for (int i = x; i < this.width; i++) {

        }
    }

//    private void floodFillRectangle(BufferedImage image, int x, int y, int color) {
//        ArrayList<ArrayList<Boolean>> visitedPixels = new ArrayList<>();
//        floodFillRectangleHelper(image, x, y, color, visitedPixels);
//    }
//
//    private void floodFillRectangleHelper(BufferedImage image, int x, int y, int color, ArrayList<ArrayList<Boolean>> visitedPixels) {
//        int[] pixelCoord = new int[]{x, y};
//
//        if (x < 0 || x >= this.width || y < 0 || y >= this.height || image.getRGB(x, y) != color) {
//            visitedPixels.get(y).add(false);
//            return;
//        }
//
//        if (visitedPixels.size() <= y) {
//            visitedPixels.add(new ArrayList<>());
//        }
//
//        visitedPixels.get(y).add(true);
//
//        floodFillRectangleHelper(image, x + 1, y, color, visitedPixels);
//        floodFillRectangleHelper(image, x, y - 1, color, visitedPixels);
//    }
}
