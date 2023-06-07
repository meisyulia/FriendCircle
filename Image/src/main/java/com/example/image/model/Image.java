package com.example.image.model;

import java.io.Serializable;

/**
 * @author liwanlian
 * @date 2023/5/29 23:11
 */
public class Image implements Serializable {
    private String path;
    private String name;

    public Image(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public Image() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
