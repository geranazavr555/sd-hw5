package ru.itmo.sdcourse.hw5.graph.factory;

import ru.itmo.sdcourse.hw5.drawing.DrawingApi;
import ru.itmo.sdcourse.hw5.graph.AdjacencyMatrixGraph;

import java.util.stream.Stream;

public class AdjacencyMatrixGraphFactory extends GraphFactory<AdjacencyMatrixGraph> {
    public AdjacencyMatrixGraphFactory(DrawingApi drawingApi) {
        super(drawingApi);
    }

    private int parseVertexCount(String line) {
        return Integer.parseInt(line);
    }

    private AdjacencyMatrixGraph.Edge[] parseMatrixLine(int vertexCount, String line) {
        String[] tokens = line.split(" ");
        if (tokens.length != vertexCount)
            throw new RuntimeException("Illegal line");

        AdjacencyMatrixGraph.Edge[] edges = new AdjacencyMatrixGraph.Edge[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            if (!"0".equals(tokens[i]) && !"1".equals(tokens[i]))
                throw new RuntimeException("Illegal token");
            if ("1".equals(tokens[i]))
                edges[i] = new AdjacencyMatrixGraph.Edge();
        }
        return edges;
    }

    @Override
    public AdjacencyMatrixGraph readGraph(Stream<String> lines) {
        boolean[] first = {true};
        int[] vertexCount = {0};
        int[] i = {0};
        AdjacencyMatrixGraph.Edge[][][] adjMatrix = new AdjacencyMatrixGraph.Edge[1][][];
        lines.forEach(line -> {
            if (first[0]) {
                vertexCount[0] = parseVertexCount(line);
                adjMatrix[0] = new AdjacencyMatrixGraph.Edge[vertexCount[0]][];
            } else {
                adjMatrix[0][i[0]] = parseMatrixLine(vertexCount[0], line);
                i[0]++;
            }
            first[0] = false;
        });
        return new AdjacencyMatrixGraph(adjMatrix[0], drawingApi);
    }
}
