package com.github.sigute.demo.ui.tables;

public class Table {
    private int id;
    private boolean available;

    public Table(int id, boolean available) {
        this.id = id;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public boolean available() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
