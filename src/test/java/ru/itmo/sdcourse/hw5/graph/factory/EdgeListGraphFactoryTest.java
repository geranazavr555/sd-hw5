package ru.itmo.sdcourse.hw5.graph.factory;

import org.junit.Assert;
import org.junit.Test;
import ru.itmo.sdcourse.hw5.graph.EdgeListGraph;

import java.util.Collections;
import java.util.List;

public class EdgeListGraphFactoryTest {
    @Test
    public void empty() {
        EdgeListGraphFactory edgeListGraphFactory = new EdgeListGraphFactory(null);
        EdgeListGraph graph = edgeListGraphFactory.readGraph(new String[]{
                "0"
        });
        Assert.assertEquals(new EdgeListGraph(0, Collections.emptyList(), null), graph);
    }

    @Test
    public void simple() {
        EdgeListGraphFactory edgeListGraphFactory = new EdgeListGraphFactory(null);
        EdgeListGraph graph = edgeListGraphFactory.readGraph(new String[]{
                "5",
                "1 2",
                "2 4",
                "4 3"
        });
        Assert.assertEquals(new EdgeListGraph(5, List.of(
                new EdgeListGraph.Edge(1, 2),
                new EdgeListGraph.Edge(2, 4),
                new EdgeListGraph.Edge(4, 3)
        ), null), graph);
    }

    @Test(expected = Exception.class)
    public void error() {
        EdgeListGraphFactory edgeListGraphFactory = new EdgeListGraphFactory(null);
        edgeListGraphFactory.readGraph(new String[]{
                "5",
                "1 2",
                "2 6",
                "4 3"
        });
    }
}
