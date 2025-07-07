package com.harismehuljic.billboard.rendering;

import com.harismehuljic.billboard.preprocessing.*;
import com.harismehuljic.billboard.preprocessing.data.ImageTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.image.BufferedImage;

public class CanvasBuilder {
    private int width;
    private int height;
    private Vec3d pos;
    private float pixelScale;
    private World world;
    private Image image;

    /**
     * Creates a new CanvasBuilder instance with default values.
     * The default width and height are set to 128 pixels, the position is set to (0, 0, 0),
     * the pixel scale is set to 1.0f.
     *
     * @apiNote At least the world and image need to be set before building the canvas. All other properties can use the
     * default assigned values.
     */
    public CanvasBuilder() {
        this.width = 128;
        this.height = 128;
        this.pos = new Vec3d(0, 0, 0);
        this.pixelScale = 1.0f;
        this.world = null;
        this.image = null;
    }

    /**
     * Sets the width of the canvas.
     * @param width The width of the canvas in terms of pixels.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException If the width is less than or equal to 0.
     */
    public CanvasBuilder setWidth(int width) throws IllegalArgumentException {
        if (width <= 0) {
            throw new IllegalArgumentException("Canvas width must be greater than 0.");
        }

        this.width = width;
        return this;
    }

    /**
     * Sets the height of the canvas.
     * @param height The height of the canvas in terms of pixels.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException If the height is less than or equal to 0.
     */
    public CanvasBuilder setHeight(int height) throws IllegalArgumentException {
        if (height <= 0) {
            throw new IllegalArgumentException("Canvas height must be greater than 0.");
        }

        this.height = height;
        return this;
    }

    /**
     * Sets the pixel scale for the canvas.
     * @param pixelScale The scale of each pixel in the canvas, determining how large each pixel appears in the world.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException If the pixel scale is less than or equal to 0.
     *
     * @see CanvasPixel
     */
    public CanvasBuilder setPixelScale(float pixelScale) throws IllegalArgumentException {
        if (pixelScale <= 0) {
            throw new IllegalArgumentException("Pixel scale must be greater than 0.");
        }

        this.pixelScale = pixelScale;
        return this;
    }

    /**
     * Sets the position of the canvas in the world.
     * @param pos The position of the top-left corner of the canvas in the world, represented as a Vec3d.
     * @return This builder instance for method chaining.
     */
    public CanvasBuilder setPos(Vec3d pos) {
        this.pos = pos;
        return this;
    }

    /**
     * Sets the world in which the canvas will be rendered.
     * @param world The Minecraft world where the canvas will be rendered.
     * @return This builder instance for method chaining.
     */
    public CanvasBuilder setWorld(World world) {
        this.world = world;
        return this;
    }

    /**
     * Sets the image to be rendered on the canvas.
     * @param image The image to render on the canvas, provided as an {@link Image}.
     * @return This builder instance for method chaining.
     * @throws IllegalStateException If the height and width have not been properly set before defining the image to render.
     * @throws IllegalArgumentException If the dimensions of the provided image do not match the defined dimensions of the canvas.
     *
     * @implNote The image must match the dimensions of the canvas.
     *
     * @see Image
     */
    public CanvasBuilder setImage(Image image) throws IllegalStateException {
        if (this.height <= 0 || this.width <= 0) {
            throw new IllegalStateException("Canvas width or height must be defined and greater than 0 before setting the image.");
        }
        else if (image.getWidth() != this.width || image.getHeight() != this.height) {
            throw new IllegalArgumentException(String.format("Image dimensions do not match canvas dimensions. Expected: %dx%d, but got: %dx%d",
                    this.width, this.height, this.image.getWidth(), this.image.getHeight()));
        }

        this.image = image;

        return this;
    }

    /**
     * Defines the image to be rendered on the canvas.
     * @param image The image to render on the canvas, provided as an {@link Image}.
     * @param type The type of image processing to apply. This should be an instance of {@link ImageTypes} enum.
     * @return This builder instance for method chaining.
     * @throws IllegalStateException If the height and width have not been properly set before defining the image to render.
     *
     * @implNote If the width and height of the canvas are different from the dimensions of the provided image, then
     * the image will be resized to fit the canvas dimensions.
     *
     * @see ImageTypes
     * @see Image
     */
    public CanvasBuilder setImage(BufferedImage image, ImageTypes type) throws IllegalStateException {
        if (this.height <= 0 || this.width <= 0) {
            throw new IllegalStateException("Canvas width or height must be defined and greater than 0 before setting the image.");
        }

        Image processedImage = switch (type) {
            case RLE -> new RunLengthEncodedImage(image, width, height);
            case RAW -> new RawImage(image, width, height);
        };

        return setImage(processedImage);
    }

    /**
     * Sets the image to be rendered on the canvas, resizing it based on the provided resize factor.
     * @param image The image to render on the canvas, provided as a BufferedImage.
     * @param type The type of image processing to apply. This should be an instance of {@link ImageTypes} enum.
     * @param resizeFactor The factor by which to reduce the resolution of the image.
     * @return This builder instance for method chaining.
     * @throws IllegalStateException If the height and width have not been properly set before defining the image to render.
     *
     * @apiNote A value of 1.0 means the image will be rendered at its original size, while a value of 0.5 will reduce the resolution by half.
     */
    public CanvasBuilder setImage(BufferedImage image, ImageTypes type, float resizeFactor) throws IllegalStateException {
        if (this.height <= 0 || this.width <= 0) {
            throw new IllegalStateException("Canvas width or height must be defined and greater than 0 before setting the image.");
        }
        else if (resizeFactor <= 0 || resizeFactor > 1) {
            throw new IllegalArgumentException("Resize factor must be greater than 0 and less than or equal to 1.");
        }

        this.width = (int) Math.floor(this.width * resizeFactor);
        this.height = (int) Math.floor(this.height * resizeFactor);

        return setImage(image, type);
    }

    /**
     * Generates an image full of random noise, used primarily for testing purposes.
     * @return This builder instance for method chaining.
     * @throws IllegalStateException If the height and width have not been properly set before defining the image to render.
     */
    public CanvasBuilder setImage() throws IllegalStateException {
        if (this.height <= 0 || this.width <= 0) {
            throw new IllegalStateException("Canvas width or height must be defined and greater than 0 before setting the image.");
        }

        BufferedImage randomImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

        int maxColors = 0x1000000; // 16,777,216
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                randomImage.setRGB(x, y, (int) Math.floor(maxColors * Math.random()));
            }
        }

        this.image = new RawImage(randomImage);
        return this;
    }

    /**
     * Builds the canvas with the defined properties.
     * @return A new instance of the Canvas class with the defined properties.
     * @throws IllegalStateException If the world or image have not been set before building the canvas.
     *
     * @implNote The canvas will not be rendered (spawned in the world) until the `render()` method is called on the
     * returned Canvas instance.
     *
     * @see Canvas
     */
    public Canvas build() throws IllegalStateException {
        if (this.world == null) {
            throw new IllegalStateException("World must be set before building the canvas.");
        }

        if (this.image == null) {
            throw new IllegalStateException("The image must be set before building the canvas.");
        }

        return new Canvas(
            this.width,
            this.height,
            this.pos,
            this.pixelScale,
            this.world,
            this.image
        );
    }
}
