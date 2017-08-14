import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

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

	public static Mat erode(Mat matrix, int kernelSize) {
		
		Mat dst = new Mat();
		
		Mat element =
				Imgproc.getStructuringElement(
						Imgproc.MORPH_RECT,
						new Size(2*kernelSize + 1, 2*kernelSize + 1));
		
		LOGGER.info("Matrix = " + matrix.toString() + ".");
		Imgproc.erode(matrix, dst, element);
		LOGGER.info("Processed matrix = " + dst.toString() + ".");
		
		return dst;
	}

	public static Mat dilate(Mat matrix, int kernelSize) {
		
		Mat dst = new Mat();
		
		Mat element =
				Imgproc.getStructuringElement(
						Imgproc.MORPH_RECT,
						new Size(2*kernelSize + 1, 2*kernelSize + 1));
		
		LOGGER.info("Matrix = " + matrix.toString() + ".");
		Imgproc.dilate(matrix, dst, element);
		LOGGER.info("Processed matrix = " + dst.toString() + ".");
		
		return dst;
	}

	public static Mat open(Mat matrix, int kernelSize) {
		
		Mat dst = new Mat();
		
		Mat element =
				Imgproc.getStructuringElement(
						Imgproc.MORPH_RECT,
						new Size(2*kernelSize + 1, 2*kernelSize + 1));
		
		LOGGER.info("Matrix = " + matrix.toString() + ".");
		Imgproc.erode(matrix, dst, element);
		
		dst.copyTo(matrix);
		dst = new Mat();
		
		Imgproc.dilate(matrix, dst, element);
		LOGGER.info("Processed matrix = " + dst.toString() + ".");
		
		return dst;
	}

	public static Mat close(Mat matrix, int kernelSize) {
		
		Mat dst = new Mat();
		
		Mat element =
				Imgproc.getStructuringElement(
						Imgproc.MORPH_RECT,
						new Size(2*kernelSize + 1, 2*kernelSize + 1));
		
		LOGGER.info("Matrix = " + matrix.toString() + ".");
		Imgproc.dilate(matrix, dst, element);
		
		dst.copyTo(matrix);
		dst = new Mat();

		Imgproc.erode(matrix, dst, element);
		LOGGER.info("Processed matrix = " + dst.toString() + ".");
		
		return dst;
	}

	public static Mat blur(Mat matrix, int kernelSize) {
		
		Mat dst = new Mat();
		
		LOGGER.info("Matrix = " + matrix.toString() + ".");
		Imgproc.blur(matrix, dst, new Size(2*kernelSize + 1, 2*kernelSize + 1));
		LOGGER.info("Processed matrix = " + dst.toString() + ".");
		
		return dst;
	}

	public static Mat medianBlur(Mat matrix, int kernelSize) {
		
		Mat dst = new Mat();
		
		LOGGER.info("Matrix = " + matrix.toString() + ".");
		Imgproc.medianBlur(matrix, dst, 2*kernelSize + 1);
		LOGGER.info("Processed matrix = " + dst.toString() + ".");
		
		return dst;
	}

	public static Mat gaussianBlur(Mat matrix, int kernelSize) {
		
		Mat dst = new Mat();
		
		LOGGER.info("Matrix = " + matrix.toString() + ".");
		Imgproc.GaussianBlur(
				matrix,
				dst,
				new Size(2*kernelSize + 1, 2*kernelSize + 1), 0);
		
		LOGGER.info("Processed matrix = " + dst.toString() + ".");
		
		return dst;
	}
	
	public static Mat bilateralFilter(Mat matrix, int kernelSize) {
		
		Mat dst = new Mat();
		
		LOGGER.info("Matrix = " + matrix.toString() + ".");
		Imgproc.bilateralFilter(matrix, dst, 10, 150, 150);
		LOGGER.info("Processed matrix = " + dst.toString() + ".");
		
		return dst;
	}
	
	public static Mat boxFilter(Mat matrix, int kernelSize) {
		
		Mat dst = new Mat();
		
		Imgproc.boxFilter(
				matrix,
				dst,
				-1,
				new Size(2*kernelSize + 1, 2*kernelSize + 1));
		
		return dst;
	}
	
	public static Mat grayscaling(Mat matrix) {
		
		Mat dst = new Mat(matrix.rows(), matrix.cols(), matrix.type());
		
		LOGGER.info("Matrix = " + matrix.toString() + ".");
		try {

			Imgproc.cvtColor(matrix, dst, Imgproc.COLOR_RGB2GRAY);
		} catch (Exception e) {
			
		} finally {

			LOGGER.info("Processed matrix = " + dst.toString() + ".");	
		}
		
		return dst;
	}
	
	public static Mat otsuTreshold(Mat matrix) {
		
		Mat dst = new Mat(matrix.rows(), matrix.cols(), matrix.type());
		
		LOGGER.info("Matrix = " + matrix.toString() + ".");
		Imgproc.threshold(matrix, dst, 126, 255, Imgproc.THRESH_OTSU);
		LOGGER.info("Processed matrix = " + dst.toString() + ".");	
		
		return dst;
	}
	
	public static Mat edgeDetection(Mat matrix, int kernelSize) {
		
		Mat dst = new Mat(matrix.rows(), matrix.cols(), matrix.type());
		
		try {
			
			Imgproc.cvtColor(matrix, matrix, Imgproc.COLOR_RGB2GRAY);
		} catch (Exception e) {
			
		} finally {
			
			matrix = otsuTreshold(matrix);
			Imgproc.Sobel(matrix, dst, matrix.depth(), 1, 1);
		}
		
		return dst;
	}
}
