package com.harismehuljic.billboard.rendering;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CanvasBuilder {
    private int width;
    private int height;
    private Vec3d pos;
    private float pixelScale;
    private World world;

    public CanvasBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public CanvasBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public CanvasBuilder setPixelScale(float pixelScale) {
        this.pixelScale = pixelScale;
        return this;
    }

    public CanvasBuilder setPos(Vec3d pos) {
        this.pos = pos;
        return this;
    }

    public CanvasBuilder setWorld(World world) {
        this.world = world;
        return this;
    }

    public Canvas build() {
        return new Canvas(width, height, pos, pixelScale, world);
    }
}
