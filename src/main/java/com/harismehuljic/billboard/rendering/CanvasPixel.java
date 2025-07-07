package com.harismehuljic.billboard.rendering;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.AffineTransformation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class CanvasPixel {
    private final DisplayEntity.TextDisplayEntity pixelEntity;
    private final Vec3d pos;
    private final World world;
    private final float scale;

    private int length = 1;

    /**
     * Creates a new Pixel instance. A pixelText is a {@link DisplayEntity.TextDisplayEntity}
     * that represents a single pixelText of a larger {@link Canvas} in the Minecraft world.
     * @param pos The position of the pixelText in the world. This usually relates to the positions of all the other pixels
     *            in the canvas and is determined by the canvas's position and pixelText scale.
     * @param world The Minecraft world where the pixelText will be rendered. This is typically the same world as the canvas it belongs to.
     * @param scale The scale of the pixelText, which determines how large the pixelText appears in the world.
     * @param color The color of the pixelText, represented as an RGB integer.
     *
     * @apiNote A scale of 1.0 is equivalent to 0.2 blocks. A scale of 5 is equivalent to 1 block.
     *
     * @see DisplayEntity.TextDisplayEntity
     * @see Canvas
     * @see CanvasBuilder
     */
    public CanvasPixel(Vec3d pos, World world, float scale, int color) {
        this.pos = pos;
        this.world = world;
        this.scale = scale;

        this.pixelEntity = new DisplayEntity.TextDisplayEntity(EntityType.TEXT_DISPLAY, world);
        this.pixelEntity.setPosition(pos);

        this.pixelEntity.setTransformation(
            new AffineTransformation(
                new Vector3f(0.0F, 0.0F, 0.0F),
                null,
                new Vector3f(scale, scale, scale),
                null
            )
        );

        this.updateColor(color);
    }

    /**
     * Updates the color of the pixel
     * @param color The color of the pixelText, represented as an RGB integer. This defines the color of the text
     *              and the background of the pixelText in the Minecraft world.
     */
    private void updateColor(int color) {
        int rgb = Math.abs(color);
        MutableText pixelText = Text.literal("█");
        pixelText.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)));

        this.pixelEntity.setBackground(rgb);
        this.pixelEntity.setText(pixelText);
    }

    public void setLength(int length) {
        this.length = length;
        float stretchFactor = this.scale * length;
        float offset = CanvasPixel.getOffsetConstant(length, scale);

        this.pixelEntity.setTransformation(
            new AffineTransformation(
                new Vector3f(offset, 0.0F, 0.0F),
                null,
                new Vector3f(stretchFactor, scale, scale),
                null
            )
        );
    }

    public int getLength() {
        return this.length;
    }

    /**
     * Spawns the {@link DisplayEntity.TextDisplayEntity} linked to this pixel into the Minecraft world.
     */
    public void render() {
        this.world.spawnEntity(this.pixelEntity);
    }

    /**
     * Destroys the {@link DisplayEntity.TextDisplayEntity} linked to this pixel, removing it from the Minecraft world.
     */
    public void destroy() {
        this.pixelEntity.remove(Entity.RemovalReason.DISCARDED);
    }

    /**
     * Calculates how many blocks a pixel will take up in the world based on its scale.
     * @param scale The scale of the pixel {@link DisplayEntity.TextDisplayEntity}, which determines how
     *              large the pixelText appears in the world.
     * @return The size of the pixel entity in terms of blocks.
     * @apiNote A scale of 1.0 is equivalent to 0.2 blocks. A scale of 5 is equivalent to 1 block.
     */
    public static float getPixelBlocks(float scale) {
        return 0.2F * scale;
    }

    /**
     * Returns the offset amount for a pixelText based on its length.
     * @return The amount of offset for the pixelText, which is calculated as 0.0875F multiplied by the length.
     */
    public static float getOffsetConstant(int length, float scale) {
        return 0.0875F * (length - 1) * scale;
    }

    /**
     * Returns the position of the pixelText.
     * @return A {@link Vec3d} containing the (X, Y, Z) coordinates of the pixelText
     */
    public Vec3d getPos() {
        return this.pos;
    }

    /**
     * Returns the UUID of the pixelText entity.
     * @return The UUID of the pixelText entity as a String.
     */
    public String getUUID() {
        return this.pixelEntity.getUuidAsString();
    }
}
