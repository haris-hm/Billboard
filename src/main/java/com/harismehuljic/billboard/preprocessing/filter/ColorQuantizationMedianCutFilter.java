package com.harismehuljic.billboard.preprocessing.filter;

import com.harismehuljic.billboard.Billboard;
import com.harismehuljic.billboard.preprocessing.Image;
import com.harismehuljic.billboard.preprocessing.data.Color;
import com.harismehuljic.billboard.preprocessing.data.ColorChannel;
import com.harismehuljic.billboard.preprocessing.data.ImagePixel;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Represents a color quantized image.
 *
 * @implNote Utilizes the median cut algorithm for color quantization.
 */
public class ColorQuantizationMedianCutFilter extends Filter {
    private final int colors;

    public ColorQuantizationMedianCutFilter(Image image, int colors) {
        super(image);
        this.colors = colors;
    }

    @Override
    public Image apply() {
        ImagePixel[] pixelData = this.image.getFlattenedPixelData();
        Color[] colorPalette = getQuantizedColorPalette(pixelData, this.colors);

        return this.image;
    }

    private Color[] getQuantizedColorPalette(ImagePixel[] pixelData, int maxDepth) {
        return quantizationHelper(pixelData, 0, maxDepth);
    }

    private Color[] quantizationHelper(ImagePixel[] pixelData, int depth, int maxDepth) {
        if (depth == maxDepth) {
            return new Color[]{getAverageColor(pixelData)};
        }

        ColorChannel channel = getGreatestRangeChannel(pixelData);
        Arrays.sort(pixelData, Comparator.comparingInt((ImagePixel a) -> a.getColor().getChannelValue(channel)));

        int mid = pixelData.length / 2;
        Color[] left = quantizationHelper(Arrays.copyOfRange(pixelData, 0, mid), depth + 1, maxDepth);
        Color[] right = quantizationHelper(Arrays.copyOfRange(pixelData, mid, pixelData.length), depth + 1, maxDepth);
        return mergeArrays(left, right);
    }

    private Color[] mergeArrays(Color[] left, Color[] right) {
        Color[] merged = new Color[left.length + right.length];
        System.arraycopy(left, 0, merged, 0, left.length);
        System.arraycopy(right, 0, merged, left.length, right.length);
        return merged;
    }

    private Color getAverageColor(ImagePixel[] pixelData) {
        int rSum = 0, gSum = 0, bSum = 0;

        for (ImagePixel pixel : pixelData) {
            Color color = pixel.getColor();
            rSum += color.getRedChannel();
            gSum += color.getGreenChannel();
            bSum += color.getBlueChannel();
        }

        int count = pixelData.length;
        return new Color(
            rSum / count,
            gSum / count,
            bSum / count
        );
    }

    private ColorChannel getGreatestRangeChannel(ImagePixel[] pixelData) {
        int rMin, gMin, bMin;
        int rMax, gMax, bMax;
        rMin = gMin = bMin = Integer.MAX_VALUE;
        rMax = gMax = bMax = Integer.MIN_VALUE;

        for (ImagePixel pixel : pixelData) {
            Color color = pixel.getColor();
            rMin = Math.min(rMin, color.getRedChannel());
            gMin = Math.min(gMin, color.getGreenChannel());
            bMin = Math.min(bMin, color.getBlueChannel());

            rMax = Math.max(rMax, color.getRedChannel());
            gMax = Math.max(gMax, color.getGreenChannel());
            bMax = Math.max(bMax, color.getBlueChannel());
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
}
