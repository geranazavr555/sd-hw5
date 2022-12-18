package ru.itmo.sdcourse.hw5.graph;

import ru.itmo.sdcourse.hw5.drawing.DrawingApi;

import java.util.HashSet;
import java.util.List;

public class EdgeListGraph extends Graph {
    private final int vertexCount;
    private final List<Edge> edges;

    public EdgeListGraph(int vertexCount, List<Edge> edges, DrawingApi drawingApi) {
        super(drawingApi);
        this.vertexCount = vertexCount;
        this.edges = edges;
    }

    @Override
    protected void internalDrawGraph() {
        for (int i = 0; i < vertexCount; i++) {
            drawingState.drawVertex(i);
        }

        for (Edge edge : edges) {
            drawingState.drawEdge(edge.a() - 1, edge.b() - 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof EdgeListGraph that))
            return false;
        if (vertexCount != that.vertexCount)
            return false;
        return new HashSet<>(edges).containsAll(that.edges) && new HashSet<>(that.edges).containsAll(edges);
    }

    public record Edge(int a, int b) {
    }
}
