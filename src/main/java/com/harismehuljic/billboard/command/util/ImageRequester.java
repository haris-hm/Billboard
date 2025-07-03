package com.harismehuljic.billboard.command.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;

public class ImageRequester {
    public static CompletableFuture<BufferedImage> getImage(String input) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (validURL(input)) {
                    URL url = new URL(input);
                    URLConnection connection = url.openConnection();
                    connection.setRequestProperty("User-Agent", "Map");
                    connection.connect();
                    return ImageIO.read(connection.getInputStream());
                }

                return null;
            }
            catch (Exception e) {
                return null;
            }
        });
    }

    private static boolean validURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
