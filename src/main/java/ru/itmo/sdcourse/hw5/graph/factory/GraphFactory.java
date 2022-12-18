package ru.itmo.sdcourse.hw5.graph.factory;

import ru.itmo.sdcourse.hw5.drawing.DrawingApi;
import ru.itmo.sdcourse.hw5.graph.Graph;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public abstract class GraphFactory<T extends Graph> {
    protected final DrawingApi drawingApi;

    protected GraphFactory(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract T readGraph(Stream<String> lines);

    public T readGraph(List<String> lines) {
        return readGraph(lines.stream());
    }

    public T readGraph(Reader reader) {
        BufferedReader bufferedReader = new BufferedReader(reader);
        return readGraph(bufferedReader.lines());
    }

    public T readGraph(InputStream inputStream) {
        return readGraph(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public T readGraph(Path path) {
        try {
            return readGraph(Files.lines(path));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public T readGraph(File file) {
        return readGraph(file.toPath());
    }

    public T readGraph(String[] lines) {
        return readGraph(List.of(lines));
    }
}
