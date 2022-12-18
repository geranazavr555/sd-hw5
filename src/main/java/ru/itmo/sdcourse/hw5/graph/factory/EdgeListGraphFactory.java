package ru.itmo.sdcourse.hw5.graph.factory;

import ru.itmo.sdcourse.hw5.drawing.DrawingApi;
import ru.itmo.sdcourse.hw5.graph.EdgeListGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EdgeListGraphFactory extends GraphFactory<EdgeListGraph> {
    public EdgeListGraphFactory(DrawingApi drawingApi) {
        super(drawingApi);
    }

    private int parseVertexCount(String line) {
        return Integer.parseInt(line);
    }

    private EdgeListGraph.Edge parseEdge(int vertexCount, String line) {
        String[] tokens = line.split(" ");
        if (tokens.length != 2)
            throw new RuntimeException("Expected exactly 2 integers per a line");

        int a = Integer.parseInt(tokens[0]);
        int b = Integer.parseInt(tokens[1]);
        if (a < 1 || a > vertexCount || b < 1 || b > vertexCount)
            throw new RuntimeException("Illegal vertex index");

        return new EdgeListGraph.Edge(a, b);
    }

    @Override
    public EdgeListGraph readGraph(Stream<String> lines) {
        boolean[] first = {true};
        int[] vertexCount = {0};
        List<EdgeListGraph.Edge> edges = new ArrayList<>();

        lines.forEach(line -> {
            if (first[0])
                vertexCount[0] = parseVertexCount(line);
            else
                edges.add(parseEdge(vertexCount[0], line));
            first[0] = false;
        });

        return new EdgeListGraph(vertexCount[0], edges, drawingApi);
    }
}
