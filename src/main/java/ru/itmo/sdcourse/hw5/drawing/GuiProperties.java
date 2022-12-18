package ru.itmo.sdcourse.hw5.drawing;

public record GuiProperties(String title, int windowWidth, int windowHeight, int circleRadius) {
    private static GuiProperties instance;

    public static void init(String title, int windowWidth, int windowHeight, int circleRadius) {
        instance = new GuiProperties(title, windowWidth, windowHeight, circleRadius);
    }

    public static GuiProperties getInstance() {
        return instance;
    }
}
