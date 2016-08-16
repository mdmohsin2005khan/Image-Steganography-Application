package stegano.image.processing;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

//import control.ImageSteno;
//import control.NoImageException;

public class HideWorker extends SwingWorker<BufferedImage, Void> implements
		ProgressDisplay {

	private final ImageSteno steno;
	private final MainFrame frame;

	public HideWorker(ImageSteno steno, MainFrame frame) {
		this.steno = steno;
		this.frame = frame;
	}

	@Override
	protected BufferedImage doInBackground() throws Exception {
		BufferedImage result = null;
		steno.setProgressDisplay(this);
		try {
			result = steno.hideImage();
			BufferedImage extraction = steno.extractHiddenImage(result);
			frame.setResultImage(result);
			//frame.setExtractedImage(extraction);
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
