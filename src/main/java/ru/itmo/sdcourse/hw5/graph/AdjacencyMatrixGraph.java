package ru.itmo.sdcourse.hw5.graph;

import ru.itmo.sdcourse.hw5.drawing.DrawingApi;

import java.util.Arrays;

public class AdjacencyMatrixGraph extends Graph {
    private final Edge[][] adjMatrix;

    public AdjacencyMatrixGraph(Edge[][] adjMatrix, DrawingApi drawingApi) {
        super(drawingApi);
        this.adjMatrix = adjMatrix;
    }

    @Override
    protected void internalDrawGraph() {
        for (int i = 0; i < adjMatrix.length; i++) {
            drawingState.drawVertex(i);
        }

        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = i + 1; j < adjMatrix[i].length; j++) {
                if (adjMatrix[i][j] != null) {
                    drawingState.drawEdge(i, j);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AdjacencyMatrixGraph that))
            return false;
        return Arrays.deepEquals(adjMatrix, that.adjMatrix);
    }

    public record Edge() {}
}
