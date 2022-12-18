package ru.itmo.sdcourse.hw5.graph.factory;

import org.junit.Assert;
import org.junit.Test;
import ru.itmo.sdcourse.hw5.graph.AdjacencyMatrixGraph;

public class AdjacencyMatrixGraphFactoryTest {
    @Test
    public void empty() {
        AdjacencyMatrixGraphFactory adjacencyMatrixGraphFactory = new AdjacencyMatrixGraphFactory(null);
        AdjacencyMatrixGraph graph = adjacencyMatrixGraphFactory.readGraph(new String[]{
                "0"
        });
        Assert.assertEquals(
                new AdjacencyMatrixGraph(new AdjacencyMatrixGraph.Edge[][]{}, null),
                graph
        );
    }

    @Test
    public void simple() {
        AdjacencyMatrixGraphFactory adjacencyMatrixGraphFactory = new AdjacencyMatrixGraphFactory(null);
        AdjacencyMatrixGraph graph = adjacencyMatrixGraphFactory.readGraph(new String[]{
                "3",
                "0 1 1",
                "1 0 0",
                "1 1 0"
        });
        Assert.assertEquals(
                new AdjacencyMatrixGraph(new AdjacencyMatrixGraph.Edge[][]{
                        {null, edge(), edge()},
                        {edge(), null, null},
                        {edge(), edge(), null}
                }, null),
                graph
        );
    }

    @Test(expected = Exception.class)
    public void error() {
        AdjacencyMatrixGraphFactory adjacencyMatrixGraphFactory = new AdjacencyMatrixGraphFactory(null);
        adjacencyMatrixGraphFactory.readGraph(new String[]{
                "3",
                "0 1 1",
                "1 0 0 4",
                "1 1 0"
        });
    }

    private AdjacencyMatrixGraph.Edge edge() {
        return new AdjacencyMatrixGraph.Edge();
    }
}
