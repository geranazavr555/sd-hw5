package ru.itmo.sdcourse.hw5.drawing;

import ru.itmo.sdcourse.hw5.drawing.primitives.Circle;
import ru.itmo.sdcourse.hw5.drawing.primitives.Line;

public interface DrawingApi {
    int getDrawingAreaWidth();
    int getDrawingAreaHeight();
    int getCircleRadius();
    void drawCircle(Circle circle);
    void drawLine(Line line);
}
