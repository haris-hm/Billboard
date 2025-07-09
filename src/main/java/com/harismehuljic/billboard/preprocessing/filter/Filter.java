package com.harismehuljic.billboard.preprocessing.filter;

import com.harismehuljic.billboard.preprocessing.Image;

public abstract class Filter {
    public final Image image;

    protected Filter(Image image) {
        this.image = image;
    }

    public abstract Image apply();
}
