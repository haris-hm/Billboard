package com.harismehuljic.billboard.rendering;

import com.harismehuljic.billboard.Billboard;

import java.util.HashMap;
import java.util.HashSet;

/**
 * CanvasManager is responsible for managing multiple Canvas instances.
 * It allows adding, rendering, and removing canvases by their UUIDs.
 *
 * @see Canvas
 */
public class CanvasManager {
    private final HashMap<String, Canvas> worldCanvases = new HashMap<>();
    private final HashSet<String> canvasUUIDs = new HashSet<>();

    /**
     * Default constructor for CanvasManager.
     * Initializes the manager with empty collections for canvases and UUIDs.
     */
    public CanvasManager() {}

    /**
     * Adds a new Canvas to the manager.
     *
     * @param uuid   The unique identifier for the canvas.
     * @param canvas The Canvas instance to be added.
     */
    public void addCanvas(String uuid, Canvas canvas) {
        this.worldCanvases.put(uuid, canvas);
        this.canvasUUIDs.add(uuid);
    }

    /**
     * Renders the Canvas associated with the given UUID.
     *
     * @param uuid The unique identifier for the canvas to be rendered.
     */
    public void renderCanvas(String uuid) {
        Canvas canvas = this.worldCanvases.get(uuid);
        if (canvas != null) {
            canvas.render();
        } else {
            System.out.println("Canvas with UUID " + uuid + " not found.");
        }
    }

    /**
     * Removes the Canvas associated with the given UUID.
     *
     * @param uuid The unique identifier for the canvas to be removed.
     * @return true if the canvas was successfully removed, false if it was not found.
     */
    public boolean removeCanvas(String uuid) {
        Canvas canvas = this.worldCanvases.get(uuid);
        if (canvas == null) {
            Billboard.LOGGER.error("Canvas with UUID ({}) not found.", uuid);
            return false;
        }

        canvas.destroy();
        this.worldCanvases.remove(uuid);
        this.canvasUUIDs.remove(uuid);

        return true;
    }

    /**
     * Returns the set of UUIDs for all canvases managed by this CanvasManager.
     * @return A HashSet containing the UUIDs of all canvases.
     *
     * @see HashSet
     */
    public HashSet<String> getCanvasUUIDs() {
        return this.canvasUUIDs;
    }
}
