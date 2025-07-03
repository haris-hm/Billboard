package com.harismehuljic.billboard.image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RunLengthEncodedImage extends Image implements ProcessedImage {
    private ArrayList<ArrayList<ImagePixel>> pixelData;

    public RunLengthEncodedImage(BufferedImage image) {
        super(image);
        this.processImage();
    }

    @Override
    public void processImage() {
        this.pixelData = new ArrayList<>();

        for (int y = 0; y < this.image.getHeight(); y++) {
            ArrayList<ImagePixel> rowPixels = new ArrayList<>();
            ImagePixel currentPixel = new ImagePixel(this.image.getRGB(0, y));

            for (int x = 1; x < this.image.getWidth(); x++) {
                int pixelColor = this.image.getRGB(x, y);

                if (pixelColor == currentPixel.getRGB()) {
                    currentPixel.incrementLength();
                } else {
                    rowPixels.add(currentPixel.copy());
                    currentPixel = new ImagePixel(pixelColor);
                }
            }

            rowPixels.add(currentPixel.copy());
            this.pixelData.add(rowPixels);
        }
    }

    @Override
    public ImagePixel[][] getPixelData() {
        ImagePixel[][] pixelArray = new ImagePixel[this.pixelData.size()][];
        for (int i = 0; i < this.pixelData.size(); i++) {
            ArrayList<ImagePixel> row = this.pixelData.get(i);
            pixelArray[i] = row.toArray(new ImagePixel[0]);
        }
        return pixelArray;
    }
}
