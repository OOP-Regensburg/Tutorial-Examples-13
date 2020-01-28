package HeapVisualizer.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import de.ur.mi.oop.colors.Color;

public class AppConfig {

	private static final String DELIMITER_STRING = ":";
	private static final String COLOR_DELIMITER_STRING = ",";
	
	private static final String WIDTH_ATTRIBUTE = "WIDTH";
	private static final String HEIGHT_ATTRIBUTE = "HEIGHT";
	private static final String BACKGROUND_COLOR_ATTRIBUTE = "BACKGROUND_COLOR";
	private static final String MAX_BALLS_ATTRIBUTE = "MAX_BALLS";
	private static final String BALLS_RADIUS_ATTRIBUTE = "BALL_RADIUS";
	private static final String BALLS_MIN_VELOCITY_ATTRIBUTE = "BALL_MIN_VELOCITY";
	private static final String BALLS_MAX_VELOCITY_ATTRIBUTE = "BALL_MAX_VELOCITY";
	private static final String BALL_COLOR_ATTRIBUTE = "BALL_COLOR";

	private int width = -1;
	private int height = -1;
	private Color backgroundColor = null;
	
	private int maxBalls = -1;
	private int ballRadius = -1;
	private int ballMinVelocity = -1;
	private int ballMaxVelocity = -1;
	private Color ballColor = null;
	
	private boolean valid = false;

	public AppConfig(File configFile) {
		readConfigFile(configFile);
		checkConfig();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public int getMaxBalls() {
		return maxBalls;
	}
	
	public int getBallRadius() {
		return ballRadius;
	}
	
	public int getMinBallVelocity() {
		return ballMinVelocity;
	}
	
	public int getMaxBallVelocity() {
		return ballMaxVelocity;
	}
	
	public Color getBallColor() {
		return ballColor;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	private void checkConfig() {
		valid = (width != -1) && (height != -1) && (maxBalls != -1) && (ballRadius != -1) && (ballMinVelocity != -1) && (ballMaxVelocity != -1) && (backgroundColor != null) && (ballColor != null);
	}

	private void readConfigFile(File configFile) {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(
					configFile));
			while (true) {
				String line = fileReader.readLine();
				if (line != null) {
					parseConfigLine(line);
				} else {
					break;
				}
			}
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseConfigLine(String line) throws Exception {
		String[] parts = line.split(DELIMITER_STRING);
		String attribute = parts[0].trim();
		String value = parts[1].trim();
		
		switch (attribute) {
		case WIDTH_ATTRIBUTE:
			width = Integer.parseInt(value);
			break;
		case HEIGHT_ATTRIBUTE:
			height = Integer.parseInt(value);
			break;
		case BACKGROUND_COLOR_ATTRIBUTE:
			backgroundColor = parseColorLine(value);
			break;
		case MAX_BALLS_ATTRIBUTE:
			maxBalls = Integer.parseInt(value);
			break;
		case BALLS_RADIUS_ATTRIBUTE:
			ballRadius = Integer.parseInt(value);
			break;
		case BALLS_MIN_VELOCITY_ATTRIBUTE:
			ballMinVelocity = Integer.parseInt(value);
			break;
		case BALLS_MAX_VELOCITY_ATTRIBUTE:
			ballMaxVelocity = Integer.parseInt(value);
			break;
		case BALL_COLOR_ATTRIBUTE:
			ballColor = parseColorLine(value);
			break;
		}
	}
	
	private Color parseColorLine(String rgb) {
		String[] parts = rgb.split(COLOR_DELIMITER_STRING);
		int red = Integer.parseInt(parts[0].trim());
		int green = Integer.parseInt(parts[1].trim());
		int blue = Integer.parseInt(parts[2].trim());
		return new Color(red,green,blue);
	}

}
