package com.harismehuljic.billboard.preprocessing.filter;

import com.harismehuljic.billboard.Billboard;
import com.harismehuljic.billboard.preprocessing.Image;
import com.harismehuljic.billboard.preprocessing.data.ImagePixel;

import java.util.Arrays;
import java.util.Collections;

/**
 * Represents a color quantized image.
 *
 * @implNote Utilizes the median cut algorithm for color quantization.
 */
public class MedianCutColorQuantizedFilter extends Filter {
    private final int colors;

    public MedianCutColorQuantizedFilter(Image image, int colors) {
        super(image);
        this.colors = colors;
    }

    @Override
    public Image apply() {
        ImagePixel[] pixelData = this.image.getFlattenedPixelData();

        int[] colorPalette = getQuantizedColorPalette(pixelData, this.colors);
        Billboard.LOGGER.info("Color palette generated with {} colors: {}", colorPalette.length, Arrays.toString(colorPalette));
        return this.image;
    }

    private ColorChannel getGreatestRangeChannel(ImagePixel[] pixelData) {
        int rMin, gMin, bMin;
        int rMax, gMax, bMax;
        rMin = gMin = bMin = Integer.MAX_VALUE;
        rMax = gMax = bMax = Integer.MIN_VALUE;

        for (ImagePixel pixel : pixelData) {
            rMin = Math.min(rMin, pixel.getRedChannel());
            gMin = Math.min(gMin, pixel.getGreenChannel());
            bMin = Math.min(bMin, pixel.getBlueChannel());

            rMax = Math.max(rMax, pixel.getRedChannel());
            gMax = Math.max(gMax, pixel.getGreenChannel());
            bMax = Math.max(bMax, pixel.getBlueChannel());
        }

        int rRange = rMax - rMin;
        int gRange = gMax - gMin;
        int bRange = bMax - bMin;

        int maxRange = Math.max(rRange, Math.max(gRange, bRange));

        if (maxRange == rRange) {
            return ColorChannel.RED;
        } else if (maxRange == gRange) {
            return ColorChannel.GREEN;
        } else {
            return ColorChannel.BLUE;
        }
    }

    private int[] getQuantizedColorPalette(ImagePixel[] pixelData, int maxDepth) {
        return quantizationHelper(pixelData, 0, maxDepth);
    }

    private int[] quantizationHelper(ImagePixel[] pixelData, int depth, int maxDepth) {
        if (depth == maxDepth) {
            return new int[]{getAverageColor(pixelData)};
        }

        ColorChannel channel = getGreatestRangeChannel(pixelData);
        Arrays.sort(pixelData,
            (ImagePixel a, ImagePixel b) -> switch (channel) {
                case RED -> Integer.compare(a.getRedChannel(), b.getRedChannel());
                case GREEN -> Integer.compare(a.getGreenChannel(), b.getGreenChannel());
                case BLUE -> Integer.compare(a.getBlueChannel(), b.getBlueChannel());
            }
        );

        int mid = pixelData.length / 2;
        int[] left = quantizationHelper(Arrays.copyOfRange(pixelData, 0, mid), depth + 1, maxDepth);
        int[] right = quantizationHelper(Arrays.copyOfRange(pixelData, mid, pixelData.length), depth + 1, maxDepth);
        return mergeArrays(left, right);
    }

    private int[] mergeArrays(int[] left, int[] right) {
        int[] merged = new int[left.length + right.length];
        System.arraycopy(left, 0, merged, 0, left.length);
        System.arraycopy(right, 0, merged, left.length, right.length);
        return merged;
    }

    private int getAverageColor(ImagePixel[] pixelData) {
        int rSum = 0, gSum = 0, bSum = 0;

        for (ImagePixel pixel : pixelData) {
            rSum += pixel.getRedChannel();
            gSum += pixel.getGreenChannel();
            bSum += pixel.getBlueChannel();
        }

        int count = pixelData.length;
        return (rSum / count << 16) | (gSum / count << 8) | (bSum / count);
    }

    private int calculateColorDifference(int color1, int color2) {
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
    }

    private enum ColorChannel {
        RED,
        GREEN,
        BLUE
    }
}
