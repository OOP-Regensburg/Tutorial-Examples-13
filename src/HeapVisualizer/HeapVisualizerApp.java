package HeapVisualizer;

import HeapVisualizer.config.AppConfig;
import HeapVisualizer.logger.Logger;
import HeapVisualizer.ui.Ball;
import HeapVisualizer.ui.HeapVisualizer;
import de.ur.mi.oop.app.GraphicsApp;
import de.ur.mi.oop.graphics.Rectangle;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class HeapVisualizerApp extends GraphicsApp  {

    private static final String CONFIG_FILE_PATH = "data/visualizer.config";
    private static final String LOG_FILE_PATH = "data/visualizer.log";
    private static final int MAX_FRAMES_TO_LOG = 100;

    // private RandomGenerator random = RandomGenerator.getInstance();

    private AppConfig config;
    private Logger log;
    private Random random;
    private Rectangle boundingBox;
    private HeapVisualizer heapVisualizer;
    private ArrayList<Ball> balls;

    private int collisionsInCurrentFrame = 0;

    @Override
    public void initialize() {
        random = new Random();
        initConfig();
        initBalls();
        initHeapVisualizer();
        initLogger();
        setCanvasSize(config.getWidth(), config.getHeight());
    }

    private void initConfig() {
        File configFile = new File(CONFIG_FILE_PATH);
        config = new AppConfig(configFile);
        if(!config.isValid()) {
            System.out.println("Invalid config file!");
            System.exit(1);
        }
    }

    private void initBalls() {
        balls = new ArrayList<Ball>();
        boundingBox = new Rectangle(0,0,config.getWidth(),config.getHeight(), null);
        fillBallList();
    }

    private void initHeapVisualizer() {
        heapVisualizer = new HeapVisualizer(config.getWidth(), config.getHeight());
    }

    private void initLogger() {
        File logFile = new File(LOG_FILE_PATH);
        log = new Logger(logFile, MAX_FRAMES_TO_LOG);
    }

    private void fillBallList() {
        for(int i = balls.size(); i < config.getMaxBalls(); i++) {
            addNewBall();
        }
    }

    private void addNewBall() {
        boolean addBall = random.nextBoolean();
        if(!addBall) {
            return;
        }
        int x = random.nextInt(config.getWidth());
        int y = random.nextInt(config.getHeight());
        int dx = 0;
        int dy = 0;
        while(dx == 0 || dy == 0) {
            dx = config.getMinBallVelocity() + random.nextInt(config.getMaxBallVelocity() - config.getMinBallVelocity());
            dy = config.getMinBallVelocity() + random.nextInt(config.getMaxBallVelocity() - config.getMinBallVelocity());
        }
        balls.add(new Ball(x,y, config.getBallRadius(), dx, dy, config.getBallColor(), boundingBox));
    }

    @Override
    public void draw() {
        drawBackground(config.getBackgroundColor());
        heapVisualizer.draw();

        updateBalls();
        drawBalls();

        log.logFrame(balls.size(), collisionsInCurrentFrame);

        fillBallList();
    }

    private void updateBalls() {
        collisionsInCurrentFrame = 0;
        for(Ball ball: balls) {
            ball.update();
            handleBallCollision(ball);
        }
    }
    private void drawBalls() {
        for (Iterator<Ball> it = balls.iterator(); it.hasNext(); ) {
            Ball ball = it.next();
            if(ball.isInBoundingBox()) {
                ball.draw();
            } else {
                it.remove();
            }
        }
    }

    private void handleBallCollision(Ball ball) {
        for(Ball checkBall: balls) {
            if(ball.equals(checkBall)) {
                continue;
            } else {
                if(ball.collidesWith(checkBall)) {
                    int[] newDirecton = ball.reverseDirection();
                    checkBall.setDirection(-newDirecton[0], -newDirecton[1]);
                    collisionsInCurrentFrame++;
                    break;
                }
            }
        }
    }
}
