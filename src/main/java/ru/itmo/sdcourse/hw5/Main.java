package ru.itmo.sdcourse.hw5;

import javafx.scene.canvas.GraphicsContext;
import org.apache.commons.cli.*;
import ru.itmo.sdcourse.hw5.drawing.GuiProperties;
import ru.itmo.sdcourse.hw5.drawing.awt.AwtApplication;
import ru.itmo.sdcourse.hw5.drawing.awt.AwtDrawingApi;
import ru.itmo.sdcourse.hw5.drawing.javafx.JavaFxApplication;
import ru.itmo.sdcourse.hw5.drawing.javafx.JavaFxDrawingApi;
import ru.itmo.sdcourse.hw5.graph.Graph;
import ru.itmo.sdcourse.hw5.graph.factory.AdjacencyMatrixGraphFactory;
import ru.itmo.sdcourse.hw5.graph.factory.EdgeListGraphFactory;

import java.awt.*;
import java.nio.file.Path;

public class Main {
    private static final Options options = new Options();

    private Runnable syncRunnable;

    private JavaFxDrawingApi prepareJavaFx() {
        JavaFxApplication javaFxApplication = JavaFxApplication.launch();
        GraphicsContext graphicsContext = javaFxApplication.getGraphicsContext();

        syncRunnable = () -> {
            try {
                javaFxApplication.getApplicationThread().join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        return new JavaFxDrawingApi(graphicsContext, GuiProperties.getInstance());
    }

    private AwtDrawingApi prepareAwt() {
        AwtApplication awtApplication = AwtApplication.launch();
        Graphics2D graphics = awtApplication.getGraphics();

        syncRunnable = () -> {
            try {
                awtApplication.getWorking().await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        return new AwtDrawingApi(graphics, GuiProperties.getInstance());
    }

    private void process(String drawingApiStr, String graphType, String inputFile) {
        var drawingApi = switch (drawingApiStr) {
            case "jfx" -> prepareJavaFx();
            case "awt" -> prepareAwt();
            default -> throw new IllegalArgumentException("Illegal drawing api");
        };

        var graphFactory = switch (graphType) {
            case "adj" -> new AdjacencyMatrixGraphFactory(drawingApi);
            case "edges" -> new EdgeListGraphFactory(drawingApi);
            default -> throw new IllegalArgumentException("Illegal graph type");
        };

        Graph graph = graphFactory.readGraph(Path.of(inputFile));
        graph.drawGraph();
        syncRunnable.run();
    }

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            printHelpAndFail(e.getMessage());
            return;
        }

        if (cmd.getArgs().length != 1) {
            printHelpAndFail("Expected file as an argument");
            return;
        }

        GuiProperties.init(
                "title",
                800,
                600,
                40
        );

        new Main().process(cmd.getOptionValue("api"), cmd.getOptionValue("gt"), cmd.getArgs()[0]);
    }

    private static void printHelpAndFail(String message) {
        System.err.println(message);
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("java -jar hw5.jar <options> <input file>", options);
        System.exit(1);
    }

    static {
        options.addRequiredOption("api", "draw-api", true, "Drawing api. \"jfx\" or \"awt\". Required.");
        options.addRequiredOption("gt", "graph-type", true, "Graph type. \"adj\" or \"edges\" Required.");
    }
}
