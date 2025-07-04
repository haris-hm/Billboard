package com.harismehuljic.billboard.rendering;

import com.harismehuljic.billboard.Billboard;

import java.util.HashMap;
import java.util.HashSet;

public class CanvasManager {
    private final HashMap<String, Canvas> worldCanvases = new HashMap<>();
    private final HashSet<String> canvasUUIDs = new HashSet<>();

    public CanvasManager() {}

    public void addCanvas(String uuid, Canvas canvas) {
        this.worldCanvases.put(uuid, canvas);
        this.canvasUUIDs.add(uuid);
    }

    public void renderCanvas(String uuid) {
        Canvas canvas = this.worldCanvases.get(uuid);
        if (canvas != null) {
            canvas.render();
        } else {
            System.out.println("Canvas with UUID " + uuid + " not found.");
        }
    }

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

    public HashSet<String> getCanvasUUIDs() {
        return this.canvasUUIDs;
    }
}
