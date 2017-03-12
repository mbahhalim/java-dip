import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.opencv.core.Mat;

public class ImageProcessing {
	
	private final static Logger LOGGER =
			Logger.getLogger(ImageProcessing.class.getName());
	
	public static ImageIcon convertMatrix(Mat matrix, int width, int height) {
		
		LOGGER.info("Set image type.");
		int type = BufferedImage.TYPE_BYTE_GRAY;
		
	    if (matrix.channels() > 1) {
	    	
	        type = BufferedImage.TYPE_3BYTE_BGR;
	    }
	    LOGGER.info("Image type value = " + type + ".");
	    
	    LOGGER.info("Convert Mat to BufferedImage.");
	    BufferedImage bufferedImage =
	    		new BufferedImage(matrix.cols(), matrix.rows(), type);
	    
	    matrix.get(
	    		0, 0,
	    		((DataBufferByte) 
	    				bufferedImage.getRaster().getDataBuffer()).getData());
	    LOGGER.info("Matrix = " + matrix.toString() + ".");
		
		return resizeImage(new ImageIcon(bufferedImage), width, height);
	}
	
	private static ImageIcon resizeImage(ImageIcon imageIcon, int width, int height) {
		
		LOGGER.info("Resize image with width/height = " + width + "/" + height + " pixels.");
        return new ImageIcon(
        		imageIcon.getImage().getScaledInstance(
        				width,
        				height,
        				Image.SCALE_SMOOTH));
    }
}
