package stegano.image.processing;

 
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.awt.image.ShortLookupTable;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageProcessingTest
{
   public static void main(String[] args)
   {
	   
	    new ImageProcessingTest();

	    JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		try {
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName());
			
		} catch (Exception e) {
			System.out.println("Failed loading L&F: ");
			System.out.println(e);
		}
      
   }
   
   public ImageProcessingTest() {
	   
	   EventQueue.invokeLater(new Runnable()
       {
          public void run()
          {
             JFrame frame = new ImageProcessingFrame();
             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             frame.setVisible(true);
          }
       });
   	
   }
}

/**
 * This frame has a menu to load an image and to specify various transformations, and a component to
 * show the resulting image.
 */
@SuppressWarnings("serial")
class ImageProcessingFrame extends JFrame
{
	private BufferedImage image;
	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 400;
	BufferedImage img1 = null;
	File fname=null;
   public ImageProcessingFrame()
   {
      setTitle("ImageProcessingTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      add(new JComponent()
         {
            public void paintComponent(Graphics g)
            {
               if (image != null) g.drawImage(image, 0, 0, null);
            }
         });

      JMenu fileMenu = new JMenu("File");
      JMenuItem openItem = new JMenuItem( "Open");
      openItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               int reset =0;
               openFile(reset);
            }
         });
      fileMenu.add(openItem);

      JMenuItem exitItem = new JMenuItem("Home");
      exitItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
                dispose();
                Stegno s = new Stegno();
                s.show();
                s.pack();
                s.setTitle("IMA");
               //System.exit(0);
            }
         });
      fileMenu.add(exitItem);
      
      JMenuItem saveItem = new JMenuItem("Save");
      saveItem.addActionListener(new ActionListener()
         {
            private File Ofilename;

			public void actionPerformed(ActionEvent event)
            {
            	
                
                try {
                	JFileChooser filechooser = new JFileChooser();
                	
					filechooser.showSaveDialog(getParent());
                    File Sfilename = filechooser.getSelectedFile();
                    Ofilename=Sfilename;
					ImageIO.write(image, "JPG", Ofilename);
				} catch (IOException e) {
					e.printStackTrace();
				}
                JOptionPane.showMessageDialog(null, "Enhanced image saved successfully");
           
            }
         });
      fileMenu.add(saveItem);

      JMenu editMenu = new JMenu("Edit");
      JMenuItem blurItem = new JMenuItem("Blur");
      blurItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               float weight = 1.0f / 9.0f;
               float[] elements = new float[9];
               for (int i = 0; i < 9; i++)
                  elements[i] = weight;
               convolve(elements);
            }
         });
      editMenu.add(blurItem);

      JMenuItem sharpenItem = new JMenuItem("Sharpen");
      sharpenItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               float[] elements = { 0.0f, -1.0f, 0.0f, -1.0f, 5.f, -1.0f, 0.0f, -1.0f, 0.0f };
               convolve(elements);
            }
         });
      editMenu.add(sharpenItem);

      JMenuItem brightenItem = new JMenuItem("Brighten");
      brightenItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               float a = 1.1f;
               // float b = 20.0f;
               float b = 0;
               RescaleOp op = new RescaleOp(a, b, null);
               filter(op);
            }
         });
      editMenu.add(brightenItem);

      JMenuItem edgeDetectItem = new JMenuItem("Edge detect");
      edgeDetectItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               float[] elements = { 0.0f, -1.0f, 0.0f, -1.0f, 4.f, -1.0f, 0.0f, -1.0f, 0.0f };
               convolve(elements);
            }
         });
      editMenu.add(edgeDetectItem);

      JMenuItem negativeItem = new JMenuItem("Negative");
      negativeItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               short[] negative = new short[256 * 1];
               for (int i = 0; i < 256; i++)
                  negative[i] = (short) (255 - i);
               ShortLookupTable table = new ShortLookupTable(0, negative);
               LookupOp op = new LookupOp(table, null);
               filter(op);
            }
         });
      editMenu.add(negativeItem);

      JMenuItem rotateRightItem = new JMenuItem("Rotate right");
      rotateRightItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               if (image == null) return;
               AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians(90),
                     image.getWidth() / 2, image.getHeight() / 2);
               AffineTransformOp op = new AffineTransformOp(transform,
            		   AffineTransformOp.TYPE_BICUBIC);
               filter(op);
               
               
            }
         });
      editMenu.add(rotateRightItem);
      
      JMenuItem rotateLeftItem = new JMenuItem("Rotate left");
      rotateLeftItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               if (image == null) return;
               AffineTransform transform =   
            		   AffineTransform.getRotateInstance(Math.toRadians(-90),
                     image.getWidth()/2 , image.getHeight()/2 );
               AffineTransformOp op = new AffineTransformOp(transform,
                     AffineTransformOp.TYPE_BICUBIC);
               filter(op);
            }
         });
      editMenu.add(rotateLeftItem);
      
      JMenuItem resetItem = new JMenuItem("Reset");
      resetItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
            	int reset =1;
                openFile(reset);   
            }
         });
      editMenu.add(resetItem);

      JMenuBar menuBar = new JMenuBar();
      menuBar.add(fileMenu);
      menuBar.add(editMenu);
      setJMenuBar(menuBar);
   }

   /**
    * Open a file and load the image.
    */
   public void openFile(int reset)
   {
	  
	  if(reset==0){ 
		   
		  JFileChooser chooser = new JFileChooser();
		  chooser.setCurrentDirectory(new File("."));
		  String[] extensions = ImageIO.getReaderFileSuffixes();
		  chooser.setFileFilter(new FileNameExtensionFilter("Image files", extensions));
		  int r = chooser.showOpenDialog(this);
		  if (r != JFileChooser.APPROVE_OPTION) return;
        
		  try
		  {
			  fname = chooser.getSelectedFile();
			  System.out.println("filename is"+fname);      
			  img1 = ImageIO.read(fname);
			  
         
			  displayImage(img1);
         
		  }
		  catch (IOException e)
		  {
			  JOptionPane.showMessageDialog(this, e);
		  }
	  }
	  else{
		   
		  displayImage(img1);
	  }
      repaint();
   }
   
   public void displayImage(BufferedImage img1)
   {
	   int ht =1000,wd=1000;
	   int h = img1.getHeight();
       int w = img1.getWidth();
       BufferedImage img2;
       if (h > ht && w > wd){
        	img2 = scale(img1, wd, ht);
        	image = new BufferedImage(img2.getWidth(null), img2.getHeight(null),
                   BufferedImage.TYPE_INT_RGB);
        
             image.getGraphics().drawImage(img2, 0,0, null);

        	
        }
        else
        {
       	 
       	
            image = new BufferedImage(img1.getWidth(null), img1.getHeight(null),
                  BufferedImage.TYPE_INT_RGB);
            image.getGraphics().drawImage(img1, 0, 0, null);

        }
   }

   public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

       int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
       BufferedImage ret = img;
       BufferedImage scratchImage = null;
       Graphics2D g2 = null;

       int w = img.getWidth();
       int h = img.getHeight();

       int prevW = w;
       int prevH = h;

       do {
           if (w > targetWidth) {
               w /= 2;
               w = (w < targetWidth) ? targetWidth : w;

           }

           if (h > targetHeight) {
               h /= 2;
               h = (h < targetHeight) ? targetHeight : h;
           }

           if (scratchImage == null) {
               scratchImage = new BufferedImage(w, h, type);
               g2 = scratchImage.createGraphics();
           }

           g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
               RenderingHints.VALUE_INTERPOLATION_BILINEAR);
           g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

           prevW = w;
           prevH = h;
           ret = scratchImage;
       } while (w != targetWidth || h != targetHeight);

       if (g2 != null) {
           g2.dispose();
       }

       if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
           scratchImage = new BufferedImage(targetWidth, targetHeight, type);
           g2 = scratchImage.createGraphics();
           g2.drawImage(ret, 0, 0, null);
           g2.dispose();
           ret = scratchImage;
       }

       return ret;

   }
 
   private void filter(BufferedImageOp op)
   {
      if (image == null) return;
      image = op.filter(image, null);
      repaint();
   }

  
   private void convolve(float[] elements)
   {
      Kernel kernel = new Kernel(3, 3, elements);
      ConvolveOp op = new ConvolveOp(kernel);
      filter(op);
   }

   
}
