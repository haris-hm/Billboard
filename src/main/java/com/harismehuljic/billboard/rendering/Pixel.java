package com.harismehuljic.billboard.rendering;

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

public class Pixel {
    private DisplayEntity.TextDisplayEntity pixelEntity;
    private Vec3d pos;
    private float scale;
    private World world;

    /**
     * Creates a new Pixel instance. A pixelText is a {@link DisplayEntity.TextDisplayEntity}
     * that represents a single pixelText of a larger {@link Canvas} in the Minecraft world.
     * @param pos The position of the pixelText in the world. This usually relates to the positions of all the other pixels
     *            in the canvas and is determined by the canvas's position and pixelText scale.
     * @param scale The scale of the pixelText, which determines how large the pixelText appears in the world.
     * @param color The color of the pixelText, represented as an RGB integer.
     * @param world The Minecraft world where the pixelText will be rendered. This is typically the same world as the canvas it belongs to.
     *
     * @apiNote A scale of 1.0 is equivalent to 0.2 blocks. A scale of 5 is equivalent to 1 block.
     *
     * @see DisplayEntity.TextDisplayEntity
     * @see Canvas
     * @see CanvasBuilder
     */
    public Pixel(Vec3d pos, float scale, int color, World world) {
        this.pixelEntity = new DisplayEntity.TextDisplayEntity(EntityType.TEXT_DISPLAY, world);
        this.pixelEntity.setPosition(pos);
        this.pixelEntity.setTransformation(new AffineTransformation(null, null, new Vector3f(scale, scale, scale), null));
        this.updateColor(color);

        this.pos = pos;
        this.scale = scale;
        this.world = world;
    }

    /**
     * Updates the color of the pixel
     * @param color The color of the pixelText, represented as an RGB integer. This defines the color of the text
     *              and the background of the pixelText in the Minecraft world.
     */
    private void updateColor(int color) {
        MutableText pixelText = Text.literal("█");
        pixelText.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)));

        this.pixelEntity.setBackground(color);
        this.pixelEntity.setText(pixelText);
    }

    /**
     * Renders the pixelText in the Minecraft world.
     */
    public void render() {
        this.world.spawnEntity(this.pixelEntity);
    }

    /**
     * Returns the size of a scale 1.0 pixelText in terms of Minecraft blocks.
     * @return The size of a scale 1.0 pixelText in blocks, which is 0.2F.
     * @apiNote A scale of 1.0 is equivalent to 0.2 blocks. A scale of 5 is equivalent to 1 block.
     */
    public static float getBlockConstant() {
        return 0.2F;
    }

    /**
     * Returns the position of the pixelText.
     * @return A {@link Vec3d} containing the (X, Y, Z) coordinates of the pixelText
     */
    public Vec3d getPos() {
        return this.pos;
    }
}
