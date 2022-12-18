package ru.itmo.sdcourse.hw5.drawing.primitives;

public record Circle(Point center, int radius, String text) {
    public Circle(Point center, int radius) {
        this(center, radius, "");
    }
}
