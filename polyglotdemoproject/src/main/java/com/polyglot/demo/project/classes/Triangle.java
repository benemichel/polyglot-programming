package com.polyglot.demo.project.classes;

public class Triangle {
    public double g;
    public double h;

    public Triangle(double g, double h) {
        this.g = g;
        this.h = h;
    }

    public double getArea() {
        return 0.5 * g * h;
    }
}
