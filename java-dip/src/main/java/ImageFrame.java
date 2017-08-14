import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER =
			Logger.getLogger(ImageFrame.class.getName());

	private static final short FRAME_WIDTH = 1000;
	private static final short FRAME_HEIGHT = 700;
	
	private static final short IMAGE_X = 20;
	private static final short IMAGE_Y = 20;
	
	private static final short IMAGE_WIDTH = 400;
	private static final short IMAGE_HEIGHT = 400;

	private static ImageFrame instance = new ImageFrame();
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openItem, closeItem;
	
	private JLabel labelImage, labelImageProcessed;
	private JLabel kernelSizeText;
	
//	private JButton erodeButton, dilateButton, openingButton, closingButton;
//	private JButton blurButton, medianBlurButton, gaussianButton, bilateralButton;
//	private JButton boxButton;
	private JButton medianBlurButton, grayscaleButton, otsuButton, closingButton;
	private JButton edgeDetectButton;
	private JButton resetButton;
	
	private JTextField kernelSize;
	
	private Mat imageMatrix;
	private Mat imageMatrixProcessed;
	
	public static ImageFrame getInstance() {
		
		return instance;
	}
	
	public ImageFrame() {
		
		initialiseComponent();
		
		LOGGER.info("Adding action for open sub-menu.");
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				LOGGER.info("Create a file chooser and adding filter for images.");
				JFileChooser file = new JFileChooser();
				
				LOGGER.info("Adding filter for images.");
				FileNameExtensionFilter filter =
						new FileNameExtensionFilter(
								"Image file",
								"jpg",
								"gif",
								"png");
				
				file.addChoosableFileFilter(filter);
				
				LOGGER.info("Show open dialog.");
				int result = file.showOpenDialog(null);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					
					LOGGER.info("Get image path.");
					File selectedFile = file.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					LOGGER.info("Image path = " + path + ".");
					
					LOGGER.info("Get matrix from image file.");
					imageMatrix = Imgcodecs.imread(path);
				    LOGGER.info("Matrix = " + imageMatrix.toString() + ".");
					
					imageMatrixProcessed = new Mat();
					imageMatrix.copyTo(imageMatrixProcessed);
					labelImage.setIcon(
							ImageProcessing.convertMatrix(
									imageMatrixProcessed,
									labelImage.getWidth(),
									labelImage.getHeight()));
					
					LOGGER.info("Set components visiblity.");
					
					kernelSizeText.setVisible(true);
					kernelSize.setVisible(true);
					
//					erodeButton.setVisible(true);
//					dilateButton.setVisible(true);
//					openingButton.setVisible(true);
					closingButton.setVisible(true);
					
//					blurButton.setVisible(true);
					medianBlurButton.setVisible(true);
//					gaussianButton.setVisible(true);
//					bilateralButton.setVisible(true);
					
//					boxButton.setVisible(true);
					grayscaleButton.setVisible(true);
					otsuButton.setVisible(true);
					edgeDetectButton.setVisible(true);
					
					resetButton.setVisible(true);
				}
			}
		});
		
		LOGGER.info("Adding action for close sub-menu.");
		closeItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				LOGGER.info("Closing application.");
				System.exit(EXIT_ON_CLOSE);
			}
		});
		
//		LOGGER.info("Adding action for erode button.");
//		erodeButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				imageMatrixProcessed = ImageProcessing.erode(
//						imageMatrixProcessed,
//						Integer.parseInt(kernelSize.getText()));
//				
//				labelImageProcessed.setIcon(
//						ImageProcessing.convertMatrix(
//								imageMatrixProcessed,
//								labelImage.getWidth(),
//								labelImage.getHeight()));
//			}
//		});
//		
//		LOGGER.info("Adding action for dilate button.");
//		dilateButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				imageMatrixProcessed = ImageProcessing.dilate(
//						imageMatrixProcessed,
//						Integer.parseInt(kernelSize.getText()));
//				
//				labelImageProcessed.setIcon(
//						ImageProcessing.convertMatrix(
//								imageMatrixProcessed,
//								labelImage.getWidth(),
//								labelImage.getHeight()));
//			}
//		});
//		
//		LOGGER.info("Adding action for opening button.");
//		openingButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				imageMatrixProcessed = ImageProcessing.open(
//						imageMatrixProcessed,
//						Integer.parseInt(kernelSize.getText()));
//				
//				labelImageProcessed.setIcon(
//						ImageProcessing.convertMatrix(
//								imageMatrixProcessed,
//								labelImage.getWidth(),
//								labelImage.getHeight()));
//			}
//		});
		
		LOGGER.info("Adding action for closing button.");
		closingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				imageMatrixProcessed = ImageProcessing.close(
						imageMatrixProcessed,
						Integer.parseInt(kernelSize.getText()));
				
				labelImageProcessed.setIcon(
						ImageProcessing.convertMatrix(
								imageMatrixProcessed,
								labelImage.getWidth(),
								labelImage.getHeight()));
			}
		});
		
//		LOGGER.info("Adding action for blur button.");
//		blurButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				imageMatrixProcessed = ImageProcessing.blur(
//						imageMatrixProcessed,
//						Integer.parseInt(kernelSize.getText()));
//				
//				labelImageProcessed.setIcon(
//						ImageProcessing.convertMatrix(
//								imageMatrixProcessed,
//								labelImage.getWidth(),
//								labelImage.getHeight()));
//			}
//		});
		
		LOGGER.info("Adding action for median blur button.");
		medianBlurButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				imageMatrixProcessed = ImageProcessing.medianBlur(
						imageMatrixProcessed,
						Integer.parseInt(kernelSize.getText()));
				
				labelImageProcessed.setIcon(
						ImageProcessing.convertMatrix(
								imageMatrixProcessed,
								labelImage.getWidth(),
								labelImage.getHeight()));
			}
		});
		
//		LOGGER.info("Adding action for gaussian blur button.");
//		gaussianButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				imageMatrixProcessed = ImageProcessing.gaussianBlur(
//						imageMatrixProcessed,
//						Integer.parseInt(kernelSize.getText()));
//				
//				labelImageProcessed.setIcon(
//						ImageProcessing.convertMatrix(
//								imageMatrixProcessed,
//								labelImage.getWidth(),
//								labelImage.getHeight()));
//			}
//		});
//		
//		LOGGER.info("Adding action for bilateral filtering button.");
//		bilateralButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				imageMatrixProcessed = ImageProcessing.bilateralFilter(
//						imageMatrixProcessed,
//						Integer.parseInt(kernelSize.getText()));
//				
//				labelImageProcessed.setIcon(
//						ImageProcessing.convertMatrix(
//								imageMatrixProcessed,
//								labelImage.getWidth(),
//								labelImage.getHeight()));
//			}
//		});
//		
//		LOGGER.info("Adding action for box button.");
//		boxButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				imageMatrixProcessed = ImageProcessing.boxFilter(
//						imageMatrixProcessed,
//						Integer.parseInt(kernelSize.getText()));
//				
//				LOGGER.info("Matrix = " + imageMatrix.toString() + ".");
//				labelImageProcessed.setIcon(
//						ImageProcessing.convertMatrix(
//								imageMatrixProcessed,
//								labelImage.getWidth(),
//								labelImage.getHeight()));
//			}
//		});
		
		LOGGER.info("Adding action for grayscale button.");
		grayscaleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				imageMatrixProcessed = ImageProcessing.grayscaling(
						imageMatrixProcessed);
				
				LOGGER.info("Matrix = " + imageMatrix.toString() + ".");
				labelImageProcessed.setIcon(
						ImageProcessing.convertMatrix(
								imageMatrixProcessed,
								labelImage.getWidth(),
								labelImage.getHeight()));
			}
		});
		
		LOGGER.info("Adding action for otsu threshold button.");
		otsuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				imageMatrixProcessed = ImageProcessing.otsuTreshold(
						imageMatrixProcessed);
				
				LOGGER.info("Matrix = " + imageMatrix.toString() + ".");
				labelImageProcessed.setIcon(
						ImageProcessing.convertMatrix(
								imageMatrixProcessed,
								labelImage.getWidth(),
								labelImage.getHeight()));
			}
		});
		
		LOGGER.info("Adding action for edge detection button.");
		edgeDetectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				imageMatrixProcessed = ImageProcessing.edgeDetection(
						imageMatrixProcessed,
						Integer.parseInt(kernelSize.getText()));
				
				LOGGER.info("Matrix = " + imageMatrix.toString() + ".");
				labelImageProcessed.setIcon(
						ImageProcessing.convertMatrix(
								imageMatrixProcessed,
								labelImage.getWidth(),
								labelImage.getHeight()));
			}
		});
		
		LOGGER.info("Adding action for reset button.");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				LOGGER.info(
						"Processed matrix = "
						+ imageMatrixProcessed.toString() + ".");
				
				imageMatrix.copyTo(imageMatrixProcessed);
				LOGGER.info("Matrix = " + imageMatrix.toString() + ".");
				labelImageProcessed.setIcon(
						ImageProcessing.convertMatrix(
								imageMatrixProcessed,
								labelImage.getWidth(),
								labelImage.getHeight()));
			}
		});
	}

	private void initialiseComponent() {
		
		LOGGER.info("Initialise frame.");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LOGGER.info("Instantiate and initialise menu bar.");
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		LOGGER.info("Instantiate and initialise menus.");
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		LOGGER.info("Instantiate and initialise menu items.");
		openItem = new JMenuItem("Open");
		closeItem = new JMenuItem("Close");
		
		fileMenu.add(openItem);
		fileMenu.add(closeItem);
		
		LOGGER.info("Instantiate and initialise labels.");
		labelImage = new JLabel();
		labelImageProcessed = new JLabel();
		kernelSizeText = new JLabel();
		
		labelImage.setBounds(IMAGE_X, IMAGE_Y, IMAGE_WIDTH, IMAGE_HEIGHT);
		labelImageProcessed.setBounds(
				IMAGE_X + IMAGE_WIDTH + 20,
				IMAGE_Y,
				IMAGE_WIDTH,
				IMAGE_HEIGHT);
		
		kernelSizeText.setBounds(IMAGE_X, IMAGE_Y + IMAGE_HEIGHT + 20, 100, 20);
		kernelSizeText.setText("Kernel size: ");
		kernelSizeText.setVisible(false);

		add(labelImage);
		add(labelImageProcessed);
		add(kernelSizeText);
		
		LOGGER.info("Instantiate and initialise text fields.");
		kernelSize = new JTextField();
		kernelSize.setBounds(
				IMAGE_X + 100,
				IMAGE_Y + IMAGE_HEIGHT + 20,
				IMAGE_WIDTH - 100,
				20);
		
		kernelSize.setText("3");
		kernelSize.setVisible(false);
		add(kernelSize);
		
		LOGGER.info("Instantiate and initialise buttons.");
//		erodeButton = new JButton("Erode");
//		dilateButton = new JButton("Dilate");
//		openingButton = new JButton("Opening");
		closingButton = new JButton("Closing");
		
//		blurButton = new JButton("Blur");
		medianBlurButton = new JButton("Median Blur");
//		gaussianButton = new JButton("Gaussian");
//		bilateralButton = new JButton("Biateral");
//		
//		boxButton = new JButton("Box Filter");
		
		grayscaleButton = new JButton("Grayscale");		
		otsuButton = new JButton("Otsu Threshold");
		edgeDetectButton = new JButton("Edge Detection");
		
		resetButton = new JButton("Reset");
		
//		erodeButton.setBounds(
//				IMAGE_X,
//				IMAGE_Y + IMAGE_HEIGHT + 60,
//				IMAGE_WIDTH/2 - 5,
//				20);
//		
//		dilateButton.setBounds(
//				IMAGE_X + IMAGE_WIDTH/2 + 5,
//				IMAGE_Y + IMAGE_HEIGHT + 60,
//				IMAGE_WIDTH/2 - 5,
//				20);
//		
//		openingButton.setBounds(
//				IMAGE_X,
//				IMAGE_Y + IMAGE_HEIGHT + 90,
//				IMAGE_WIDTH/2 - 5,
//				20);
		
		closingButton.setBounds(
				IMAGE_X,
				IMAGE_Y + IMAGE_HEIGHT + 90,
				IMAGE_WIDTH,
				20);
		
//		blurButton.setBounds(
//				IMAGE_X,
//				IMAGE_Y + IMAGE_HEIGHT + 120,
//				IMAGE_WIDTH/2 - 5,
//				20);
		
		medianBlurButton.setBounds(
				IMAGE_X,
				IMAGE_Y + IMAGE_HEIGHT + 120,
				IMAGE_WIDTH - 5,
				20);
		
//		gaussianButton.setBounds(
//				IMAGE_X,
//				IMAGE_Y + IMAGE_HEIGHT + 150,
//				IMAGE_WIDTH/2 - 5,
//				20);
//		
//		bilateralButton.setBounds(
//				IMAGE_X + IMAGE_WIDTH/2 + 5,
//				IMAGE_Y + IMAGE_HEIGHT + 150,
//				IMAGE_WIDTH/2 - 5,
//				20);
//		
//		boxButton.setBounds(IMAGE_X, IMAGE_Y + IMAGE_HEIGHT + 180, IMAGE_WIDTH, 20);
		
		grayscaleButton.setBounds(
				IMAGE_X,
				IMAGE_Y + IMAGE_HEIGHT + 150,
				IMAGE_WIDTH/2 - 5,
				20);
		
		otsuButton.setBounds(
				IMAGE_X + IMAGE_WIDTH/2 + 5,
				IMAGE_Y + IMAGE_HEIGHT + 150,
				IMAGE_WIDTH/2 - 5,
				20);
		
		edgeDetectButton.setBounds(
				IMAGE_X,
				IMAGE_Y + IMAGE_HEIGHT + 180,
				IMAGE_WIDTH, 20);
		
		resetButton.setBounds(IMAGE_X, IMAGE_Y + IMAGE_HEIGHT + 210, IMAGE_WIDTH, 20);
		
//		erodeButton.setVisible(false);
//		dilateButton.setVisible(false);
//		openingButton.setVisible(false);
		closingButton.setVisible(false);
		
//		blurButton.setVisible(false);
		medianBlurButton.setVisible(false);
//		gaussianButton.setVisible(false);
//		bilateralButton.setVisible(false);
//		
//		boxButton.setVisible(false);
		
		grayscaleButton.setVisible(false);
		otsuButton.setVisible(false);
		edgeDetectButton.setVisible(false);
		
		resetButton.setVisible(false);
		
//		add(erodeButton);
//		add(dilateButton);
//		add(openingButton);
		add(closingButton);
		
//		add(blurButton);
		add(medianBlurButton);
//		add(gaussianButton);
//		add(bilateralButton);
//
//		add(boxButton);
		
		add(grayscaleButton);
		add(otsuButton);
		add(edgeDetectButton);
		
		add(resetButton);
	}
}
