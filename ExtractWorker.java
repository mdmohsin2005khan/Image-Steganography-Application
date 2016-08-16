package stegano.image.processing;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

//import control.ImageSteno;
//import control.NoImageException;

public class ExtractWorker extends SwingWorker<BufferedImage, Void> implements
		ProgressDisplay {

	private final ImageSteno steno;
	private final BufferedImage image;
	private final MainFrame frame;

	public ExtractWorker(ImageSteno steno, BufferedImage image, MainFrame frame) {
		this.steno = steno;
		this.image = image;
		this.frame = frame;
	}

	@Override
	protected BufferedImage doInBackground() throws Exception {
		BufferedImage result = null;
		steno.setProgressDisplay(this);
		try {
			if (image == null) {
				result = steno.extractHiddenImage();
			} else {
				result = steno.extractHiddenImage(image);
			}
			frame.setExtractedImage(result);
		} catch (NoImageException e) {
			JOptionPane.showMessageDialog(frame,
					"Please load a mask image\nand an image to hide",
					"No image", JOptionPane.WARNING_MESSAGE);
		}
		return result;
	}

	@Override
	public void setNewProgress(int progress) {
		this.setProgress(progress);
	}

}
