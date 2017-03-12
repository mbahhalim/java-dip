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
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER =
			Logger.getLogger(ImageFrame.class.getName());

	private static final short FRAME_WIDTH = 1000;
	private static final short FRAME_HEIGHT = 640;
	
	private static final short IMAGE_X = 50;
	private static final short IMAGE_Y = 50;
	
	private static final short IMAGE_WIDTH = 400;
	private static final short IMAGE_HEIGHT = 400;

	private static ImageFrame instance = new ImageFrame();
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openItem, closeItem;
	private JLabel labelImage, labelImageProcessed;
	private JButton erodeButton, dilateButton;
	private JButton openingButton, closingButton;
	private JButton resetButton;
	
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
					
					LOGGER.info("Set buttons visiblity.");
					erodeButton.setVisible(true);
					dilateButton.setVisible(true);
					openingButton.setVisible(true);
					closingButton.setVisible(true);
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
		
		LOGGER.info("Adding action for erode button.");
		erodeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int erosionSize = 5;
				
				LOGGER.info(
						"Create structuring element with erosion size = "
						+ erosionSize + ".");
				
				Mat element =
						Imgproc.getStructuringElement(
								Imgproc.MORPH_RECT,
								new Size(2*erosionSize + 1, 2*erosionSize + 1));
				
				LOGGER.info("Structuring element = " + element.toString() + ".");
				Imgproc.erode(imageMatrixProcessed, imageMatrixProcessed, element);
				LOGGER.info(
						"Processed matrix = "
						+ imageMatrixProcessed.toString() + ".");
				
				labelImageProcessed.setIcon(
						ImageProcessing.convertMatrix(
								imageMatrixProcessed,
								labelImage.getWidth(),
								labelImage.getHeight()));
			}
		});
		
		LOGGER.info("Adding action for dilate button.");
		dilateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int dilateSize = 5;
				
				LOGGER.info(
						"Create structuring element with dilation size = "
						+ dilateSize + ".");
				
				Mat element =
						Imgproc.getStructuringElement(
								Imgproc.MORPH_RECT,
								new Size(2*dilateSize + 1, 2*dilateSize + 1));
				
				LOGGER.info("Structuring element = " + element.toString() + ".");
				Imgproc.dilate(imageMatrixProcessed, imageMatrixProcessed, element);
				LOGGER.info(
						"Processed matrix = "
						+ imageMatrixProcessed.toString() + ".");
				
				labelImageProcessed.setIcon(
						ImageProcessing.convertMatrix(
								imageMatrixProcessed,
								labelImage.getWidth(),
								labelImage.getHeight()));
			}
		});
		
		LOGGER.info("Adding action for opening button.");
		openingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				erodeButton.doClick();
				dilateButton.doClick();
			}
		});
		
		LOGGER.info("Adding action for closing button.");
		openingButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				dilateButton.doClick();
				erodeButton.doClick();
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
		labelImage.setBounds(IMAGE_X, IMAGE_Y, IMAGE_WIDTH, IMAGE_HEIGHT);
		add(labelImage);
		
		labelImageProcessed = new JLabel();
		labelImageProcessed.setBounds(
				IMAGE_X + IMAGE_WIDTH + 50,
				IMAGE_Y,
				IMAGE_WIDTH,
				IMAGE_HEIGHT);
		
		add(labelImageProcessed);
		
		LOGGER.info("Instantiate and initialise buttons.");
		erodeButton = new JButton("Erode");
		dilateButton = new JButton("Dilate");
		openingButton = new JButton("Opening");
		closingButton = new JButton("Closing");
		resetButton = new JButton("Reset");
		
		erodeButton.setBounds(
				IMAGE_X,
				IMAGE_Y + IMAGE_HEIGHT + 20,
				IMAGE_WIDTH/2 - 10,
				20);
		
		dilateButton.setBounds(
				IMAGE_X + IMAGE_WIDTH/2 + 10,
				IMAGE_Y + IMAGE_HEIGHT + 20,
				IMAGE_WIDTH/2 - 10,
				20);
		
		openingButton.setBounds(
				IMAGE_X,
				IMAGE_Y + IMAGE_HEIGHT + 60,
				IMAGE_WIDTH/2 - 10,
				20);
		
		closingButton.setBounds(
				IMAGE_X + IMAGE_WIDTH/2 + 10,
				IMAGE_Y + IMAGE_HEIGHT + 60,
				IMAGE_WIDTH/2 - 10,
				20);
		
		resetButton.setBounds(
				IMAGE_X,
				IMAGE_Y + IMAGE_HEIGHT + 100,
				IMAGE_WIDTH,
				20);

		erodeButton.setVisible(false);
		dilateButton.setVisible(false);
		openingButton.setVisible(false);
		closingButton.setVisible(false);
		resetButton.setVisible(false);
		
		add(erodeButton);
		add(dilateButton);
		add(openingButton);
		add(closingButton);
		add(resetButton);
	}
}
