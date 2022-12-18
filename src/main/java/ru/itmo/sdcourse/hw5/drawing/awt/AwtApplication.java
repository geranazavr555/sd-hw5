package ru.itmo.sdcourse.hw5.drawing.awt;

import ru.itmo.sdcourse.hw5.drawing.GuiProperties;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

public class AwtApplication extends Frame {
    private final CountDownLatch working = new CountDownLatch(1);

    public AwtApplication(String title) {
        super(title);
    }

    public CountDownLatch getWorking() {
        return working;
    }

    @Override
    public Graphics2D getGraphics() {
        return (Graphics2D) super.getGraphics();
    }

    public static AwtApplication launch() {
        GuiProperties guiProperties = GuiProperties.getInstance();
        AwtApplication application = new AwtApplication(guiProperties.title());
        application.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                application.working.countDown();
                application.dispose();
            }
        });
        application.setSize(guiProperties.windowWidth(), guiProperties.windowHeight());
        application.setVisible(true);
        return application;
    }
}
