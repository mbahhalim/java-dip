import java.util.logging.Logger;

import org.opencv.core.Core;

public class Main {

	private final static Logger LOGGER = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    
		LOGGER.info("Using OpenCV " + Core.VERSION);
		
		ImageFrame.getInstance();
	}

}
