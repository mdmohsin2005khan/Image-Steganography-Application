package stegano.image.processing;

//import gui.ProgressDisplay;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class ImageSteno {

	public static final int ALPHA = 0;
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;

	private BufferedImage mask;
	private BufferedImage toHide;
	private BufferedImage toExtract;

	private Quality quality = Quality.GOOD;
	private ProgressDisplay progressDisplay;
	private JCECipher cipher;

	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	public BufferedImage extractHiddenImage() throws NoImageException {
		if (toExtract == null) {
			throw new NoImageException();
		}
		return extractHiddenImage(toExtract);
	}

	public BufferedImage extractHiddenImage(BufferedImage img) {
		BufferedImage imgCopy = copyImage(img);
		final int PIXELS = imgCopy.getWidth() * imgCopy.getHeight();

		for (int x = 0; x < imgCopy.getWidth(); x++) {
			for (int y = 0; y < imgCopy.getHeight(); y++) {
				int rgb = imgCopy.getRGB(x, y);
				rgb = extractRGB(rgb);
				imgCopy.setRGB(x, y, rgb);
			}
			progressDisplay.setNewProgress((x * imgCopy.getHeight()) * 100
					/ PIXELS);
		}
		progressDisplay.setNewProgress(100);
		return imgCopy;
	}

	public static BufferedImage copyImage(BufferedImage image) {
		BufferedImage destImage = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = destImage.createGraphics();
		g.drawImage(image, null, 0, 0);
		g.dispose();
		return destImage;
	}

	public BufferedImage hideImage() throws NoImageException {
		if (mask == null || toHide == null) {
			throw new NoImageException();
		}
		BufferedImage maskCopy = getScaledInstance(copyImage(mask),
				toHide.getWidth(), toHide.getHeight(),
				RenderingHints.VALUE_INTERPOLATION_BICUBIC, false);
		// toHide = encrypt(toHide);
		final int PIXELS = toHide.getWidth() * toHide.getHeight();

		for (int x = 0; x < toHide.getWidth(); x++) {
			for (int y = 0; y < toHide.getHeight(); y++) {
				int destRGB = toHide.getRGB(x, y);
				int oldRGB = maskCopy.getRGB(x, y);
				int newRGB = computeNewRGB(oldRGB, destRGB);
				maskCopy.setRGB(x, y, newRGB);
			}
			progressDisplay.setNewProgress((x * toHide.getHeight()) * 100
					/ PIXELS);
		}
		progressDisplay.setNewProgress(100);
		return maskCopy;
	}

	@SuppressWarnings("unused")
	private BufferedImage encrypt(BufferedImage toHide) { // TODO
		StringBuffer buf = new StringBuffer();
		for (int x = 0; x < toHide.getWidth(); x++) {
			for (int y = 0; y < toHide.getHeight(); y++) {
				buf.append(toHide.getRGB(x, y));
			}
		}
		byte[] code = cipher.encrypt(buf.toString()).getBytes();
		System.out.println(code.length / 3);
		return toHide;
	}

	private int extractRGB(int rgb) {
		int[] rgbArr = getRGBArray(rgb);
		for (int i = 1; i <= 3; i++) {
			rgbArr[i] = rgbArr[i] & quality.leastBitsSum;
			rgbArr[i] = rgbArr[i] << quality.shift;
		}
		return getRGBFromArray(rgbArr);
	}

	private int computeNewRGB(int oldRGB, int destRGB) {
		int[] dest = getRGBArray(destRGB);
		int[] old = getRGBArray(oldRGB);
		for (int i = 1; i <= 3; i++) {
			old[i] = old[i] & quality.cuttingMask;
			dest[i] = dest[i] >> quality.shift;
			old[i] += dest[i];
		}
		return getRGBFromArray(old);
	}

	private int getRGBFromArray(int[] rgbArr) {
		return new Color(rgbArr[RED], rgbArr[GREEN], rgbArr[BLUE]).getRGB();
	}

	private int[] getRGBArray(int rgb) {
		return new int[] { (rgb >> 24) & 0xff, (rgb >> 16) & 0xff,
				(rgb >> 8) & 0xff, rgb & 0xff };
	}

	
	public static BufferedImage getScaledInstance(BufferedImage img,
			int targetWidth, int targetHeight, Object hint,
			boolean higherQuality) {
		int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage ret = img;
		int w, h;
		if (higherQuality) {
			
			w = img.getWidth();
			h = img.getHeight();
		} else {
			
			w = targetWidth;
			h = targetHeight;
		}
		do {
			if (higherQuality && w > targetWidth) {
				w /= 2;
				if (w < targetWidth) {
					w = targetWidth;
				}
			}
			if (higherQuality && h > targetHeight) {
				h /= 2;
				if (h < targetHeight) {
					h = targetHeight;
				}
			}
			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);
		return ret;
	}

	public void setMaskImage(BufferedImage image) {
		this.mask = image;
	}

	public void setHideImage(BufferedImage image) {
		this.toHide = image;
	}

	public void setImageToExtract(BufferedImage image) {
		this.toExtract = image;
	}

	public void setProgressDisplay(ProgressDisplay progressDisplay) {
		this.progressDisplay = progressDisplay;
	}

}
