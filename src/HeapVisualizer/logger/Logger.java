package HeapVisualizer.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Logger {
	private static final String DELIMITER = ",";
	
	private File logFile;
	private ArrayList<String> log;
	private boolean fileWritten = false;
	
	private int framesToLog = 0;
	private int loggedFrames = 0;

	public Logger(File logFile, int framesToLog) {
		this.logFile = logFile;
		this.framesToLog = framesToLog;
		initLog();
	}

	private void initLog() {
		log = new ArrayList<String>();
	}
	
	public void logFrame(int ballCount, int collisions) {
		if(loggedFrames >= framesToLog) {
			if(!fileWritten) {
				writeLog();
			}
			return;
		}
		String logString = loggedFrames + DELIMITER + ballCount + DELIMITER + collisions;
		log.add(logString);
		loggedFrames++;
	}
	
	private void writeLog() {
		try {
			PrintWriter fileWriter = new PrintWriter(new FileWriter(logFile));
			for(String line: log) {
				fileWriter.println(line);
			}
			fileWriter.close();
			fileWritten = true;
			System.out.println("Log file written!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
