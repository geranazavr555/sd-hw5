package ru.itmo.sdcourse.hw5.drawing.awt;

import ru.itmo.sdcourse.hw5.drawing.DrawingApi;
import ru.itmo.sdcourse.hw5.drawing.GuiProperties;
import ru.itmo.sdcourse.hw5.drawing.primitives.Circle;
import ru.itmo.sdcourse.hw5.drawing.primitives.Line;

import java.awt.*;

public class AwtDrawingApi implements DrawingApi {
    private final Graphics2D graphics;
    private final GuiProperties guiProperties;

    public AwtDrawingApi(Graphics2D graphics, GuiProperties guiProperties) {
        this.graphics = graphics;
        this.guiProperties = guiProperties;
    }


    @Override
    public int getDrawingAreaWidth() {
        return guiProperties.windowWidth();
    }

    @Override
    public int getDrawingAreaHeight() {
        return guiProperties.windowHeight();
    }

    @Override
    public int getCircleRadius() {
        return guiProperties.circleRadius();
    }

    @Override
    public void drawCircle(Circle circle) {
        graphics.drawOval(
                circle.center().x() - circle.radius(),
                circle.center().y() - circle.radius(),
                circle.radius() * 2,
                circle.radius() * 2
        );
    }

    @Override
    public void drawLine(Line line) {
        graphics.drawLine(line.a().x(), line.a().y(), line.b().x(), line.b().y());
    }
}
