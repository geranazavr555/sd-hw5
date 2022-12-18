package ru.itmo.sdcourse.hw5.drawing.javafx;

import javafx.scene.canvas.GraphicsContext;
import ru.itmo.sdcourse.hw5.drawing.DrawingApi;
import ru.itmo.sdcourse.hw5.drawing.GuiProperties;
import ru.itmo.sdcourse.hw5.drawing.primitives.Circle;
import ru.itmo.sdcourse.hw5.drawing.primitives.Line;

public class JavaFxDrawingApi implements DrawingApi {
    private final GraphicsContext graphicsContext;
    private final GuiProperties guiProperties;

    public JavaFxDrawingApi(GraphicsContext graphicsContext, GuiProperties guiProperties) {
        this.graphicsContext = graphicsContext;
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
        graphicsContext.strokeOval(
                circle.center().x() - circle.radius(),
                circle.center().y() - circle.radius(),
                circle.radius() * 2,
                circle.radius() * 2
        );
    }

    @Override
    public void drawLine(Line line) {
        graphicsContext.moveTo(line.a().x(), line.a().y());
        graphicsContext.lineTo(line.b().x(), line.b().y());
        graphicsContext.stroke();
    }
}
