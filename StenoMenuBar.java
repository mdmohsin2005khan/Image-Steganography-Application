package stegano.image.processing;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;


import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


import javax.swing.ProgressMonitor;
import javax.swing.filechooser.FileFilter;


public class StenoMenuBar extends JMenuBar implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;



	private static final String[] ALLOWED_IMAGE_ENDINGS = { ".jpg", ".jpeg",
			".png", ".gif", ".bmp", ".tif", ".tiff" };

	private final JMenuItem loadMaskImg = new JMenuItem(
			"open covering image...");
	private final JMenuItem loadHideImg = new JMenuItem(
			"open image for hiding...");
	private final JMenuItem loadExtractImg = new JMenuItem(
			"load image to extract...");
	private final JMenuItem saveExtractedImg = new JMenuItem(
			"save extracted image...");
	private final JMenuItem saveResultImg = new JMenuItem(
			"save image with hidden picture...");
	private final JMenuItem home = new JMenuItem("home");
	private final JMenuItem exit = new JMenuItem("exit");
	private final JMenuItem reset = new JMenuItem("reset");
	private final JMenuItem hide = new JMenuItem("hide image");
	private final JMenuItem extract = new JMenuItem("extract image");
	
	private ProgressMonitor progressMonitor;

	private final ImageSteno steno = new ImageSteno();
	private final MainFrame frame;

	public StenoMenuBar(MainFrame frame) {
		this.frame = frame;
		initGUI();
		initListener();
	}

	private void initListener() {
		loadMaskImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage image = loadImage();
				if (image != null) {
					steno.setMaskImage(image);
					frame.setMaskImage(image);
				}
			}
		});

		loadHideImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage image = loadImage();
				if (image != null) {
					steno.setHideImage(image);
					frame.setHideImage(image);
				}
			}
		});

		hide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				progressMonitor = new ProgressMonitor(frame, "Hiding image",
						"", 0, 100);
				progressMonitor.setMillisToDecideToPopup(1);
				progressMonitor.setProgress(0);
				HideWorker task = new HideWorker(steno, frame);
				task.addPropertyChangeListener(StenoMenuBar.this);
				task.execute();
				steno.setQuality(Quality.GOOD);
			}
		});
		
		
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			    frame.dispose();
				new Cli();
			}
		});
		
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
			
				frame.dispose(); 
	    	    Stegno s = new Stegno();
	    	    s.show();
	    	    s.pack();
	    	    s.setTitle("IMA");
			}

			
		});

		extract.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				progressMonitor = new ProgressMonitor(frame, "Extracting image",
						"", 0, 100);
				progressMonitor.setProgress(0);
				ExtractWorker task = new ExtractWorker(steno, null, frame);
				task.addPropertyChangeListener(StenoMenuBar.this);
				task.execute();
			}
		});

		loadExtractImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage image = loadImage();
				if (image != null) {
					steno.setImageToExtract(image);
					frame.setResultImage(image);
				} else {
					System.out.println("image is null!");
				}
			}
		});

		saveExtractedImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				savePicture(frame.getExtractedImg());
			}
		});

		saveResultImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				savePicture(frame.getResultImg());
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});

	
	}

	

	private void savePicture(BufferedImage image) {
		if (image != null) {
			 
			JFileChooser fc = new JFileChooser();
			setSaveFileFilter(fc);
			int state = fc.showSaveDialog(null);

			if (state == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try {
					ImageIO.write(image, "png", file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(frame, "No image to save",
					"No image", JOptionPane.WARNING_MESSAGE);
		}
	}



	private BufferedImage loadImage() {
		 
		JFileChooser fc = new JFileChooser( );

		setOpenFileFilter(fc);

		int state = fc.showOpenDialog(null);

		if (state == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			try {
				return ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void setSaveFileFilter(JFileChooser fc) {
		fc.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return isImageFile(f) || f.isDirectory();
			}

			private boolean isImageFile(File f) {
				for (String ending : ALLOWED_IMAGE_ENDINGS) {
					if (f.getName().toLowerCase().endsWith(ending)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "PNG";
			}
		});
	}

	private void setOpenFileFilter(JFileChooser fc) {
		fc.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				return isImageFile(f) || f.isDirectory();
			}

			private boolean isImageFile(File f) {
				for (String ending : ALLOWED_IMAGE_ENDINGS) {
					if (f.getName().toLowerCase().endsWith(ending)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public String getDescription() {
				return "Image File";
			}
		});
	}

	private void initGUI() {
		JMenu fileMenu = new JMenu("file");
		fileMenu.add(loadMaskImg);
		fileMenu.add(loadHideImg);
		fileMenu.add(loadExtractImg);
		fileMenu.addSeparator();
		fileMenu.add(saveResultImg);
		fileMenu.add(saveExtractedImg);
		fileMenu.addSeparator();
		fileMenu.add(home);
		fileMenu.add(exit);

		JMenu editMenu = new JMenu("Edit");
		editMenu.add(hide);
		editMenu.add(extract);
		editMenu.addSeparator();
		editMenu.add(reset);
		
		add(fileMenu);
		add(editMenu);
	
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressMonitor.setProgress(progress);
			String message = String.format("Completed %d%%.\n", progress);
			progressMonitor.setNote(message);
		}
	}
}
