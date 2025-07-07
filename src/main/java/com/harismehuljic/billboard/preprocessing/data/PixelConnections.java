package com.harismehuljic.billboard.preprocessing.data;

public class PixelConnections {
    ImagePixel up, down, left, right;

    public PixelConnections() {}

    public ImagePixel getPixel(ConnectionDirection direction) {
        return switch (direction) {
            case UP -> up;
            case DOWN -> down;
            case LEFT -> left;
            case RIGHT -> right;
        };
    }

    public void setPixel(ConnectionDirection direction, ImagePixel pixel) {
        switch (direction) {
            case UP -> up = pixel;
            case DOWN -> down = pixel;
            case LEFT -> left = pixel;
            case RIGHT -> right = pixel;
        }
    }

    public boolean isConnected() {
        return up != null || down != null || left != null || right != null;
    }

    public boolean isConnected(ConnectionDirection direction) {
        return switch (direction) {
            case UP -> up != null;
            case DOWN -> down != null;
            case LEFT -> left != null;
            case RIGHT -> right != null;
        };
    }

    public enum ConnectionDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
