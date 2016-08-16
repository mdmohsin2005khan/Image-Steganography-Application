package stegano.image.processing;

import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

 

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JLabel obviousImg = new JLabel();
	private final JLabel imgToHide = new JLabel();
	private final JLabel resultImg = new JLabel();
	private final JLabel extractedImg = new JLabel();

	private BufferedImage extracted;

	private BufferedImage result;

	public MainFrame() {
		initGUI();
	}

	private void initGUI() {
		setTitle("Image Hiding");
		setJMenuBar(new StenoMenuBar(this));
		setVisible(true);
		setResizable(false);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setLayout(new GridLayout(2, 2));

		obviousImg.setBorder(BorderFactory.createTitledBorder("Cover image"));
		imgToHide.setBorder(BorderFactory.createTitledBorder("Image to hide"));
		resultImg.setBorder(BorderFactory.createTitledBorder("Image with hidden picture"));
		extractedImg.setBorder(BorderFactory.createTitledBorder("Extracted image"));
		
		add(obviousImg);
		add(imgToHide);
		add(resultImg);
		add(extractedImg);
	}

	public void setMaskImage(BufferedImage image) {
		image = scaleImage(image);
		this.obviousImg.setIcon(new ImageIcon(image));
		repaint();
	}

	public void setHideImage(BufferedImage image) {
		image = scaleImage(image);
		this.imgToHide.setIcon(new ImageIcon(image));
		repaint();
	}

	public void setResultImage(BufferedImage image) {
		this.result = ImageSteno.copyImage(image);
		image = scaleImage(image);
		this.resultImg.setIcon(new ImageIcon(image));
		repaint();
	}

	public void setExtractedImage(BufferedImage image) {
		this.extracted = ImageSteno.copyImage(image);
		image = scaleImage(image);
		this.extractedImg.setIcon(new ImageIcon(image));
		repaint();
	}

	private BufferedImage scaleImage(BufferedImage image) {
		image = ImageSteno.getScaledInstance(image, getScaledWidth(image),
				getScaledHeight(image),
				RenderingHints.VALUE_INTERPOLATION_BICUBIC, true);
		return image;
	}

	private int getScaledHeight(BufferedImage image) {
		int maxWidth = this.getWidth() / 2;
		int maxHeight = this.getHeight() / 2;
		if (image.getHeight() > image.getWidth()) {
			return maxHeight;
		}
		return maxWidth * image.getHeight() / image.getWidth();
	}

	private int getScaledWidth(BufferedImage image) {
		int maxWidth = this.getWidth() / 2;
		int maxHeight = this.getHeight() / 2;
		if (image.getWidth() > image.getHeight()) {
			return maxWidth;
		}
		return maxHeight * image.getWidth() / image.getHeight();
	}

	public BufferedImage getExtractedImg() {
		return extracted;
	}

	public BufferedImage getResultImg() {
		return result;
	}

}
