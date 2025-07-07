package com.harismehuljic.billboard.preprocessing.data;

/**
 * Represents connections between pixels in a grid.
 */
public class PixelConnections {
    ImagePixel up, down, left, right;

    /**
     * Default constructor for PixelConnections.
     */
    public PixelConnections() {}

    /**
     * Constructs a PixelConnections object with specified connections.
     *
     * @param up    The pixel connected above.
     * @param down  The pixel connected below.
     * @param left  The pixel connected to the left.
     * @param right The pixel connected to the right.
     */
    public PixelConnections(ImagePixel up, ImagePixel down, ImagePixel left, ImagePixel right) {
        this.setPixel(ConnectionDirection.UP, up);
        this.setPixel(ConnectionDirection.DOWN, down);
        this.setPixel(ConnectionDirection.LEFT, left);
        this.setPixel(ConnectionDirection.RIGHT, right);
    }

    /**
     * Returns the pixel connected in the specified direction.
     *
     * @param direction The direction of the connection (UP, DOWN, LEFT, RIGHT).
     * @return The pixel connected in the specified direction.
     *
     * @see ConnectionDirection
     */
    public ImagePixel getPixel(ConnectionDirection direction) {
        return switch (direction) {
            case UP -> up;
            case DOWN -> down;
            case LEFT -> left;
            case RIGHT -> right;
        };
    }

    /**
     * Sets the pixel connection in the specified direction.
     *
     * @param direction The direction of the connection (UP, DOWN, LEFT, RIGHT).
     * @param pixel The pixel to set in the specified direction.
     *
     * @see ConnectionDirection
     */
    public void setPixel(ConnectionDirection direction, ImagePixel pixel) {
        switch (direction) {
            case UP -> up = pixel;
            case DOWN -> down = pixel;
            case LEFT -> left = pixel;
            case RIGHT -> right = pixel;
        }
    }

    /**
     * Checks if the pixel is connected in any direction.
     *
     * @return true if connected in any direction, false otherwise.
     */
    public boolean isConnected() {
        return up != null || down != null || left != null || right != null;
    }

    /**
     * Checks if the pixel is connected in the specified direction.
     *
     * @param direction The direction to check (UP, DOWN, LEFT, RIGHT).
     * @return true if connected in the specified direction, false otherwise.
     *
     * @see ConnectionDirection
     */
    public boolean isConnected(ConnectionDirection direction) {
        return switch (direction) {
            case UP -> up != null;
            case DOWN -> down != null;
            case LEFT -> left != null;
            case RIGHT -> right != null;
        };
    }

    /**
     * Enum representing the possible directions of pixel connections.
     */
    public enum ConnectionDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
