package ru.itmo.sdcourse.hw5.drawing.javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import ru.itmo.sdcourse.hw5.drawing.GuiProperties;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class JavaFxApplication extends Application {
    private static final AtomicReference<JavaFxApplication> instance = new AtomicReference<>();
    private static Thread applicationThread;

    private final AtomicBoolean ready = new AtomicBoolean(false);
    private final GuiProperties guiProperties;
    private GraphicsContext graphicsContext;

    public JavaFxApplication() {
        guiProperties = GuiProperties.getInstance();
        instance.compareAndSet(null, this);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(guiProperties.title());
        Group root = new Group();
        Canvas canvas = new Canvas(guiProperties.windowWidth(), guiProperties.windowHeight());
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        graphicsContext = canvas.getGraphicsContext2D();
        ready.compareAndSet(false, true);
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public Thread getApplicationThread() {
        return applicationThread;
    };

    public static JavaFxApplication launch() {
        applicationThread = new Thread(Application::launch);
        applicationThread.start();
        //noinspection StatementWithEmptyBody
        while (instance.get() == null) {}
        //noinspection StatementWithEmptyBody
        while (!instance.get().ready.get()) {}
        return instance.get();
    }
}
