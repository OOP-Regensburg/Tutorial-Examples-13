package HeapVisualizer.ui;

import de.ur.mi.oop.colors.Color;
import de.ur.mi.oop.graphics.Circle;
import de.ur.mi.oop.graphics.Rectangle;


public class Ball {
	
	private int dx;
	private int dy;
	
	private Circle ball;
	private Rectangle boundingBox;
	
	public Ball(int x, int y, int radius, int xVelocity, int yVelocity, Color color, Rectangle boundingBox) {
		this.ball = new Circle(x, y, radius, color);
		this.dx = xVelocity;
		this.dy = yVelocity;
		this.boundingBox = boundingBox;
	}
	
	public float getX() {
		return ball.getXPos();
	}
	
	public float getY() {
		return ball.getYPos();
	}
	
	public void update() {
		ball.move(dx, dy);
	}
	
	public int[] reverseDirection() {
		dx = -dx;
		dy = -dy;
		return new int[] {dx,dy};
	}
	
	public int[] setDirection(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
		return new int[] {dx,dy};
	}
	
	public boolean isInBoundingBox() {
		return boundingBox.hitTest(ball.getXPos(), ball.getYPos());
	}
	
	public boolean collidesWith(Ball ball) {
		return this.ball.hitTest(ball.getX(), ball.getY());
	}
	
	public void draw() {
		ball.draw();
	}
}
