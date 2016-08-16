package stegano.image.processing;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
 
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class CropImage extends JFrame implements MouseListener, MouseMotionListener {
  
	int drag_status = 0, c1, c2, c3, c4;
     JLabel picLabel = null;
    JFileChooser filechooser;
    File tempfilename,Ofilename,Sfilename,inpfilename;
    String fname,actualfilename,filePath;
    

    String absolutePath;
    
    @SuppressWarnings("deprecation")
	public static void main(String args[]) {
        
    	
        
    	CropImage ci =new CropImage();
        
        
        ci.setTitle("Upload image for cropping");
        ci.pack();
        ci.show();
        
        JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		try {
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName());
			
		} catch (Exception e) {
			System.out.println("Failed loading L&F: ");
			System.out.println(e);
		}
    }
    @SuppressWarnings("deprecation")
	public CropImage() {
    	
    	File filename = null;
    	
    	filechooser=new JFileChooser();
    	
    	int rval = filechooser.showOpenDialog(null);
    	
    	if( rval == JFileChooser.APPROVE_OPTION ) {
            filename = filechooser.getSelectedFile() ;
            
        
            
            absolutePath = filename.getAbsolutePath();
    		
    		actualfilename = filename.getName();
    		
    		fname = actualfilename.substring(0, actualfilename.lastIndexOf("."));
    		    	
    		 filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
        
    	
    		 int ht =1000,wd=1000;

    		 BufferedImage img, img2;
        
    		 try {
    			 img = ImageIO.read(filename);
            
    			 int h = img.getHeight();
    			 int w = img.getWidth();
            
    			 if (h > ht && w > wd){
    				 img2 = scale(img, wd, ht);
    				 picLabel = new JLabel(new ImageIcon(img2));
            	
    			 }
    			 else
    			 {
    				 picLabel = new JLabel(new ImageIcon(img));

    			 }

    		 } catch (IOException e) {

    			 e.printStackTrace();
    		 }
    		 add(picLabel);
    		 setSize(1200, 1200);
    		 setVisible(true);
    		 JOptionPane.showMessageDialog(null, "Drag on the image to crop");
    		 
    		 JMenu fileMenu = new JMenu("Menu");
    		 
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
    	              
    	            }
    	         });
    	      fileMenu.add(exitItem);

    	     JMenuBar menuBar = new JMenuBar();
    	     menuBar.add(fileMenu);
    	     setJMenuBar(menuBar);
    		 addMouseListener(this);
    		 addMouseMotionListener(this);
    		 setDefaultCloseOperation(EXIT_ON_CLOSE);
    	}
    	else
    	{
    		dispose();
    	    Stegno s = new Stegno();
    	    s.show();
    	    s.pack();
    	    s.setTitle("Main Menu - Implementation of Steganography");
    	}
    }
    
	@SuppressWarnings("deprecation")
	public void draggedScreen() throws Exception {
        int w = c1 - c3;
        int h = c2 - c4;
        w = w * -1;
        h = h * -1;
        Robot robot = new Robot();
        BufferedImage img = robot.createScreenCapture(new Rectangle(c1, c2, w, h));
               
   
        filechooser.showSaveDialog(this);
        Sfilename=filechooser.getSelectedFile();
        Ofilename=Sfilename;
        
        ImageIO.write(img, "JPG", Ofilename);
        JOptionPane.showMessageDialog(null, "Image Cropped Successfully");
   
        dispose();
 	    Stegno s = new Stegno();
 	    s.show();
 	    s.pack();
 	    s.setTitle("IMA");
    
    }@
    Override
    public void mouseClicked(MouseEvent arg0) {}

    @
    Override
    public void mouseEntered(MouseEvent arg0) {}

    @
    Override
    public void mouseExited(MouseEvent arg0) {}

    @
    Override
    public void mousePressed(MouseEvent arg0) {
        repaint();
        c1 = arg0.getX();
        c2 = arg0.getY();
    }

    @
    Override
    public void mouseReleased(MouseEvent arg0) {
        repaint();
        if (drag_status == 1) {
            c3 = arg0.getX();
            c4 = arg0.getY();
            try {
                draggedScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @
    Override
    public void mouseDragged(MouseEvent arg0) {
        repaint();
        drag_status = 1;
        c3 = arg0.getX();
        c4 = arg0.getY();
    }

    @
    Override
    public void mouseMoved(MouseEvent arg0) {

    }

    public void paint(Graphics g) {
        super.paint(g);
        int w = c1 - c3;
        int h = c2 - c4;
        w = w * -1;
        h = h * -1;
        if (w < 0)
            w = w * -1;
        g.drawRect(c1, c2, w, h);

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
    
    
}

