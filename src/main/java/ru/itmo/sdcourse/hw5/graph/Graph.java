package ru.itmo.sdcourse.hw5.graph;

import ru.itmo.sdcourse.hw5.drawing.DrawingApi;
import ru.itmo.sdcourse.hw5.drawing.primitives.Circle;
import ru.itmo.sdcourse.hw5.drawing.primitives.Line;
import ru.itmo.sdcourse.hw5.drawing.primitives.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public abstract class Graph {
    private final DrawingApi drawingApi;
    protected DrawingState drawingState;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public final void drawGraph() {
        drawingState = new RandomDrawingState();
        internalDrawGraph();
    }

    protected abstract void internalDrawGraph();

    @Override
    public abstract boolean equals(Object obj);

     public interface DrawingState {
        void drawVertex(int id);

        void drawEdge(int vertexId1, int vertexId2);
    }

    protected class RandomDrawingState implements DrawingState {
        private static final Random random = new Random();

        private final Map<Integer, Circle> vertices = new HashMap<>();

        private Circle generateCircle() {
            int radius = drawingApi.getCircleRadius();
            Circle circle;
            do {
                int x = random.nextInt(radius, drawingApi.getDrawingAreaWidth() - radius);
                int y = random.nextInt(radius, drawingApi.getDrawingAreaHeight() - radius);
                circle = new Circle(new Point(x, y), radius);
            } while (hasCircleIntersections(circle));
            return circle;
        }

        private int sqrDistance(Point a, Point b) {
            return (a.x() - b.x()) * (a.x() - b.x()) + (a.y() - b.y()) * (a.y() - b.y());
        }

        private boolean isCirclesIntersects(Circle a, Circle b) {
            return sqrDistance(a.center(), b.center()) < (a.radius() + b.radius()) * (a.radius() + b.radius());
        }

        private boolean hasCircleIntersections(Circle circle) {
            for (Circle existCircle : vertices.values()) {
                if (isCirclesIntersects(existCircle, circle))
                    return true;
            }
            return false;
        }

        @Override
        public void drawVertex(int id) {
            if (!vertices.containsKey(id)) {
                Circle circle = generateCircle();
                vertices.put(id, circle);
                drawingApi.drawCircle(circle);
            }
        }

        @Override
        public void drawEdge(int vertexId1, int vertexId2) {
            Point a = vertices.get(vertexId1).center();
            Point aFinal = vertices.get(vertexId1).center();
            Point b = vertices.get(vertexId2).center();
            Point bFinal = vertices.get(vertexId2).center();
            int aR = vertices.get(vertexId1).radius();
            int bR = vertices.get(vertexId2).radius();
            double distance = Math.sqrt(sqrDistance(a, b));

            Function<Integer, Integer> shiftX =
                    (r) -> (int)(Math.round((double) r * Math.abs(bFinal.x() - aFinal.x()) / distance));
            Function<Integer, Integer> shiftY =
                    (r) -> (int)(Math.round((double) r * Math.abs(bFinal.y() - aFinal.y()) / distance));

            if (a.x() == b.x()) {
                if (a.y() < b.y()) {
                    a = new Point(a.x(), a.y() + aR);
                    b = new Point(b.x(), b.y() - bR);
                } else {
                    a = new Point(a.x(), a.y() - aR);
                    b = new Point(b.x(), b.y() + bR);
                }
            } else if (a.y() == b.y()) {
                if (a.x() < b.x()) {
                    a = new Point(a.x() + aR, a.y());
                    b = new Point(b.x() - bR, b.y());
                } else {
                    a = new Point(a.x() - aR, a.y());
                    b = new Point(b.x() + bR, b.y());
                }
            } else if (a.x() < b.x()) {
                if (a.y() < b.y()) {
                    a = new Point(
                            a.x() + shiftX.apply(aR),
                            a.y() + shiftY.apply(aR)
                    );
                    b = new Point(
                            b.x() - shiftX.apply(bR),
                            b.y() - shiftY.apply(bR)
                    );
                } else {
                    a = new Point(
                            a.x() + shiftX.apply(aR),
                            a.y() - shiftY.apply(aR)
                    );
                    b = new Point(
                            b.x() - shiftX.apply(bR),
                            b.y() + shiftY.apply(bR)
                    );
                }
            } else {
                if (a.y() < b.y()) {
                    a = new Point(
                            a.x() - shiftX.apply(aR),
                            a.y() + shiftY.apply(aR)
                    );
                    b = new Point(
                            b.x() + shiftX.apply(bR),
                            b.y() - shiftY.apply(bR)
                    );
                } else {
                    a = new Point(
                            a.x() - shiftX.apply(aR),
                            a.y() - shiftY.apply(aR)
                    );
                    b = new Point(
                            b.x() + shiftX.apply(bR),
                            b.y() + shiftY.apply(bR)
                    );
                }
            }

            drawingApi.drawLine(new Line(a, b));
        }
    }
}
