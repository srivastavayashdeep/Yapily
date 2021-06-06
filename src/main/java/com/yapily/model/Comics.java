package com.yapily.model;

import java.util.List;

public class Comics {
    private int available;
    private String collectionURI;
    private List<Item> items;
    private int returned;

    public Comics() {
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(final int available) {
        this.available = available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public void setCollectionURI(final String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(final List<Item> items) {
        this.items = items;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(final int returned) {
        this.returned = returned;
    }
}
