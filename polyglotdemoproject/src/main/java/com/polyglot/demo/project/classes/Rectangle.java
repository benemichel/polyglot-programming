package com.polyglot.demo.project.classes;

public class Rectangle {
    private float x;
    private float y;

    public Rectangle(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getArea() {
        return x * y;
    }

}
