package com.harismehuljic.billboard.preprocessing.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;

/**
 * Utility class for fetching images from a URL asynchronously.
 * It checks if the input is a valid URL and retrieves the image.
 */
public abstract class ImageRequester {
    /**
     * Asynchronously fetches an image from the given URL.
     *
     * @param input The URL of the image as a String.
     * @return A CompletableFuture that resolves to a BufferedImage or null if the URL is invalid or an error occurs.
     */
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

    /**
     * Validates if the provided string is a well-formed URL.
     *
     * @param url The URL string to validate.
     * @return true if the URL is valid, false otherwise.
     */
    private static boolean validURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
