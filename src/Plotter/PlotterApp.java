package Plotter;

import Plotter.config.AppConfig;
import de.ur.mi.oop.app.GraphicsApp;

public class PlotterApp extends GraphicsApp implements AppConfig {

    @Override
    public void initialize() {
        setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    @Override
    public void draw() {
        drawBackground(BACKGROUND_COLOR);
    }
}
