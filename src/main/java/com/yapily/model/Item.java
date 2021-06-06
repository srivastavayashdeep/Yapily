package com.yapily.model;

public class Item {
    private String resourceURI;
    private String name;
    private String type;

    public Item() {
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(final String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
