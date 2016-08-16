package stegano.image.processing;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class JoinImage extends JFrame implements ActionListener{
	JLabel label1,label2,label3,label4,label5,label6;
    JTextField textfield1,textfield2,textfield3,textfield4,textfield5;
    JButton button1,button2,button3,button4;
    JFileChooser filechooser;
    File f,tempfilename1,tempfilename2,Ofilename1,Ofilename2,Sfilename,inpfilename;
    InputStream ins;
    OutputStream outs;
    InetAddress ipaddress;
    String address,name;
    int Copened1,Copened2; 
    Thread t;
    public JoinImage() {
    	JoinImageLayout customLayout = new JoinImageLayout();

        getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 12));
        getContentPane().setLayout(customLayout);

        label1 = new JLabel("Note :: Select only image files like .jpg, .bmp, .gif");
        getContentPane().add(label1);
        label1.setVisible(false);
        label1.setFont(new Font("Century",Font.BOLD,18));

        label2 = new JLabel("IMAGE 1");
        getContentPane().add(label2);
        label2.setFont(new Font("Garamond",Font.BOLD,18));
                       
        textfield1 = new JTextField();
        getContentPane().add(textfield1);
        textfield1.setFont(new Font("Century",Font.BOLD,15));
        textfield1.setEditable(false);
        textfield1.setBackground(Color.LIGHT_GRAY);
        textfield1.setToolTipText("Choose the image file for merging");
        
        button1 = new JButton("BROWSE");
        button1.setActionCommand("BROWSE1");
        getContentPane().add(button1);
        button1.setFocusable(true);
        button1.addActionListener(this);
        button1.setRolloverEnabled(true);
        button1.setContentAreaFilled(true);
		button1.setBorderPainted(true);
        button1.setVerifyInputWhenFocusTarget(true);
        button1.setFont(new Font("Garamond", Font.BOLD, 16));
        
        label3 = new JLabel("IMAGE 2");
        getContentPane().add(label3);
        label3.setFont(new Font("Garamond",Font.BOLD,18));
        
        textfield2 = new JTextField();
        getContentPane().add(textfield2);
        textfield2.setFont(new Font("Century",Font.BOLD,15));
        textfield2.setEditable(false);
        textfield2.setBackground(Color.LIGHT_GRAY);
        textfield2.setToolTipText("Choose the image file for merging");
        
        button3 = new JButton("BROWSE");
        button3.setActionCommand("BROWSE2");
        getContentPane().add(button3);
        button3.setFocusable(true);
        button3.addActionListener(this);
        button3.setRolloverEnabled(true);
        button3.setContentAreaFilled(true);
		button3.setBorderPainted(true);
        button3.setVerifyInputWhenFocusTarget(true);
        button3.setFont(new Font("Garamond", Font.BOLD, 16));

        button2 = new JButton("CANCEL");
        getContentPane().add(button2);
        button2.setFocusable(true);
        button2.addActionListener(this);
        button2.setRolloverEnabled(true);
        button2.setContentAreaFilled(true);
		button2.setBorderPainted(true);
		button2.setToolTipText("Cancels the current operation and returns to the main menu");
        button2.setVerifyInputWhenFocusTarget(true);
        button2.setFont(new Font("Garamond", Font.BOLD, 16));

        button4 = new JButton("MERGE & SAVE");
        button4.setActionCommand("MERGE");
        getContentPane().add(button4);
        button4.setFocusable(true);
        button4.addActionListener(this);
        button4.setRolloverEnabled(true);
        button4.setContentAreaFilled(true);
		button4.setBorderPainted(true);
        button4.setVerifyInputWhenFocusTarget(true);
        button3.setFont(new Font("Garamond", Font.BOLD, 16));
              
               
        
     //   t=new Thread(this);
     //   t.start();
        
        filechooser=new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        setSize(getPreferredSize());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void Imagejoin(File filename1,File filename2)throws java.io.IOException{
    	
    	
        try {
            
            BufferedImage img1 = ImageIO.read(filename1);
            BufferedImage img2=ImageIO.read(filename2);
            BufferedImage joinedImg = joinBufferedImage(img1,img2);
            
        	
			filechooser.showSaveDialog(this);
			filename1=filechooser.getSelectedFile();
            
            boolean success = ImageIO.write(joinedImg, "JPG", filename1);
            
            JOptionPane.showMessageDialog(null, "Image Merged Successfully");
		    
            while(success == true){
            	
            	break;
            	
            }
            	
            
            
            	
            
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    	
    }
    
    public static void main(String args[])
    {   
    	JoinImage ji = new JoinImage();
		
		ji.setTitle("Upload image for joining");
        ji.pack();
        ji.show();
        
        JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info: javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
			}

		}
		catch (Exception ex)
		{
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
		}
    }
    
    public void actionPerformed(ActionEvent e){
        try{
        
        String cmd;
        cmd = e.getActionCommand();
 	       
 	        
        if(cmd.equals("CANCEL")){  
     	   dispose();
     	   Stegno s = new Stegno();
     	   s.show();
     	   s.pack();
     	   s.setTitle("IMA");
        }
        if(cmd.equals("BROWSE1")){
        	
    
        	
        	int r=filechooser.showOpenDialog(this);
        	tempfilename1=filechooser.getSelectedFile(); //File type
      
     		if(r==JFileChooser.CANCEL_OPTION)
     			JOptionPane.showMessageDialog(this,"FileNotSelected","Error",JOptionPane.ERROR_MESSAGE);
     		else{
     			String name=tempfilename1.getName();

     			if((!name.endsWith(".JPG") && !name.endsWith(".jpg")) && 
     			   (!name.endsWith(".GIF") && !name.endsWith(".gif")) &&
     			   (!name.endsWith(".PNG") && !name.endsWith(".png")) &&
     			   (!name.endsWith(".BMP") && !name.endsWith(".bmp")) && 
     			   (!name.endsWith(".JPEG")&& !name.endsWith(".jpeg")))  

     				JOptionPane.showMessageDialog(this,"Select Only Image file","Error",JOptionPane.ERROR_MESSAGE);

     			else{
     				Copened1=1;
     				Ofilename1=tempfilename1;
     			 	textfield1.setText(name);
     				textfield1.setText(tempfilename1.getPath());
     				textfield1.setFont(new Font("Century",Font.PLAIN,15));
     				textfield1.setBackground(Color.LIGHT_GRAY);
     				textfield1.setEditable(false); 
     			}
     		}
        }
        
        if(cmd.equals("BROWSE2")){
         
        	int r=filechooser.showOpenDialog(this);
        	tempfilename2=filechooser.getSelectedFile(); //File type
      
     		if(r==JFileChooser.CANCEL_OPTION)
     			JOptionPane.showMessageDialog(this,"FileNotSelected","Error",JOptionPane.ERROR_MESSAGE);
     		else{
     			String name=tempfilename2.getName();


     			if((!name.endsWith(".JPG") && !name.endsWith(".jpg")) && 
     			   (!name.endsWith(".GIF") && !name.endsWith(".gif")) && 
     			   (!name.endsWith(".PNG") && !name.endsWith(".png")) &&
     			   (!name.endsWith(".BMP") && !name.endsWith(".bmp")) && 
     			   (!name.endsWith(".JPEG")&& !name.endsWith(".jpeg")))  

     				JOptionPane.showMessageDialog(this,"Select Only Image file","Error",JOptionPane.ERROR_MESSAGE);

     			else{
     				Copened2=1;
     				Ofilename2=tempfilename2;
     			 	textfield2.setText(name);
     				textfield2.setText(tempfilename2.getPath());
     				textfield2.setFont(new Font("Century",Font.PLAIN,15));
     				textfield2.setBackground(Color.LIGHT_GRAY);
     				textfield2.setEditable(false); 
     			}
     		}
        }

        if(cmd.equals("MERGE")){
        	
      
        	if(Copened1==1 && Copened2==1){
        		Imagejoin(Ofilename1,Ofilename2);
        		
        		
        	}else{
        		JOptionPane.showMessageDialog(this,"File NotOpened","Error",JOptionPane.ERROR_MESSAGE);
        		
        	}
        		
        	
     	   
          }
       } // end try
     
      catch(Exception xe)
      {
      	//xe.printStackTrace();
     	 JOptionPane.showMessageDialog(this,xe,"Error",JOptionPane.ERROR_MESSAGE);
      }
    }//End of action performed
    
    /**
     * join two BufferedImage
     */

    public static BufferedImage joinBufferedImage(BufferedImage img1,BufferedImage img2) {

        //do some calculate first
        int offset  = 0;
        int wid = img1.getWidth()+img2.getWidth()+offset;
        int height = Math.max(img1.getHeight(),img2.getHeight())+offset;
        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(wid,height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = newImage.createGraphics();
        Color oldColor = g2.getColor();
        //fill background
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, wid, height);
        //draw image
       // g2.setColor(oldColor);
        g2.drawImage(img1, null, 0, 0);
        g2.drawImage(img2, null, img1.getWidth()+offset, 0);
        g2.dispose();
         
        return newImage;
    }
}

class JoinImageLayout implements LayoutManager {

    public JoinImageLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 700 + insets.left + insets.right;
        dim.height = 400 + insets.top + insets.bottom;

        return dim;
    }

    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {c.setBounds(insets.left+70,insets.top+16,550,34);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+46,insets.top+64,96,32);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+140,insets.top+64,330,32);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+500,insets.top+64,120,32);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+46,insets.top+120,124,38);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+140,insets.top+120,330,32);}
        c = parent.getComponent(6);
       if (c.isVisible()) {c.setBounds(insets.left+500,insets.top+120,120,32);}
        c = parent.getComponent(7);
       if (c.isVisible()) {c.setBounds(insets.left+100,insets.top+300,124,32);}
        c = parent.getComponent(8);
       if (c.isVisible()) {c.setBounds(insets.left+400,insets.top+300,124,32);}
   
        
    }
}


