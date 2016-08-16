package stegano.image.processing;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;


public class Cli implements ProgressDisplay {

	private String progressBar = "";
	private String toExtract = null;
	private String toHide = null;
	private String mask = null;
	private String outputFile = null;
	private String quality = null;

	public static void main(String[] args) {
		   new Cli();
	}

	public Cli() {
		
		startGUI();
		startSteno();
	}


	private void startSteno() {
		ImageSteno steno = new ImageSteno();
		steno.setProgressDisplay(this);

		try {
			setQuality(quality, steno);
			if (toExtract != null && outputFile != null) {
				extract(toExtract, outputFile, steno);
			} else if (toHide != null && mask != null && outputFile != null) {
				hide(toHide, mask, outputFile, steno);
			} else {
				System.out.println("Check the conditions");
			}
		} catch (IOException e) {
			System.out.println("file not found or no image");
		} catch (NoImageException e) {
			System.out.println("no image set");
		} catch (NumberFormatException e) {
			System.out.println("wrong format for -b");
		}
	}

	private void hide(String toHide, String mask, String outputFile,
			ImageSteno steno) throws IOException, NoImageException {
		BufferedImage hideImage = ImageIO.read(new File(toHide));
		steno.setHideImage(hideImage);
		BufferedImage maskImage = ImageIO.read(new File(mask));
		steno.setMaskImage(maskImage);
		BufferedImage toSave = steno.hideImage();
		saveImage(outputFile, toSave);
	}

	private void saveImage(String outputFile, BufferedImage toSave)
			throws IOException {
		System.out.println();
		System.out.println("saving ...");
		ImageIO.write(toSave, "png", new File(outputFile));
	}

	private void extract(String toExtract, String outputFile, ImageSteno steno)
			throws IOException, NoImageException {
		BufferedImage image = ImageIO.read(new File(toExtract));
		steno.setImageToExtract(image);
		BufferedImage toSave = steno.extractHiddenImage();
		saveImage(outputFile, toSave);
	}

	private void setQuality(String quality, ImageSteno steno) {
		if (quality != null) {
				steno.setQuality(Quality.GOOD);
		}
	}

	private static void startGUI() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame();
			}
		});
	}

	@Override
	public void setNewProgress(int progress) {
		String oldProgressBar = progressBar;
		buildProgressBar(progress / 10);
		if (!oldProgressBar.equals(progressBar)) {
			System.out.print("\r" + "creating image " + progressBar);
		}
	}

	private void buildProgressBar(int n) {
		StringBuilder b = new StringBuilder();
		b.append("[");
		for (int i = 0; i < n; i++) {
			b.append("#");
		}
		for (int i = n; i < 10; i++) {
			b.append("-");
		}
		b.append("]");
		progressBar = b.toString();
	}


}
