package stegano.image.processing;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import JavaMail.SwingEmailSender;

import java.util.*;
import java.text.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Stegno extends JFrame implements ActionListener,Serializable{
    JLabel label1,logo;
    ButtonGroup cbg;
    JRadioButton radio1,radio2,radio3,radio4,radio5,radio6,radio7;
    JButton button1;
    JTextField timeField;
    JTextField dateLongField;
   
	


    public Stegno(){
        StegnoLayout customLayout = new StegnoLayout();

        getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 12));
        getContentPane().setLayout(customLayout);
        //getContentPane().setBackground(Color.LIGHT_GRAY);

       label1 = new JLabel("Image Manipulation Application");
        getContentPane().add(label1);
        label1.setFont(new Font("RockWell", Font.BOLD, 27));
        

        cbg = new ButtonGroup();
        radio1 = new JRadioButton("ENCRYPTION", false);
        cbg.add(radio1);
        getContentPane().add(radio1);
        radio1.setFocusable(true);
        radio1.setRolloverEnabled(true);
        radio1.setVerifyInputWhenFocusTarget(true);
        radio1.addActionListener(this);
        radio1.setFont(new Font("Engravers MT", Font.BOLD, 20));
        
        JMenu fileMenu = new JMenu("Menu");
		 
		 JMenuItem exitItem = new JMenuItem("Exit");
	      exitItem.addActionListener(new ActionListener()
	         {
	            public void actionPerformed(ActionEvent event)
	            {
	               
	               System.exit(0);
	            }
	         });
	      fileMenu.add(exitItem);
	      JMenuBar menuBar = new JMenuBar();
 	     menuBar.add(fileMenu);
 	     setJMenuBar(menuBar);


        radio2 = new JRadioButton("DECRYPTION", false);
        cbg.add(radio2);
        getContentPane().add(radio2);
        radio2.addActionListener(this);  
        radio2.setFocusable(true);
        radio2.setRolloverEnabled(true);
        radio2.setVerifyInputWhenFocusTarget(true);
        radio2.setFont(new Font("Engravers MT", Font.BOLD, 20));


        timeField = new JTextField(5);
		showSysTime(timeField);
		timeField.setFont(new Font("TimesRoman",Font.BOLD,12));
	//	timeField.setForeground(Color.GRAY);
		timeField.setEditable(false);
		getContentPane().add(timeField);
		
		dateLongField = new JTextField(18);
		dateLongField.setText(showTodayDate());
		getContentPane().add(dateLongField);
		dateLongField.setEditable(false);
		//dateLongField.setForeground(Color.GRAY);
		dateLongField.setFont(new Font("TimesRoman",Font.BOLD,12));
		
       
	    radio3 = new JRadioButton("CROP IMAGE", false);
        cbg.add(radio3);
        getContentPane().add(radio3);
        radio3.setFocusable(true);
        radio3.setRolloverEnabled(true);
        radio3.setVerifyInputWhenFocusTarget(true);
        radio3.addActionListener(this);
        radio3.setFont(new Font("Engravers MT", Font.BOLD, 20));
        
        radio4 = new JRadioButton("MERGE IMAGES", false);
        cbg.add(radio4);
        getContentPane().add(radio4);
        radio4.setFocusable(true);
        radio4.setRolloverEnabled(true);
        radio4.setVerifyInputWhenFocusTarget(true);
        radio4.addActionListener(this);
        radio4.setFont(new Font("Engravers MT", Font.BOLD, 20));
        
        radio5 = new JRadioButton("IMAGE ENHANCEMENTS", false);
        cbg.add(radio5);
        getContentPane().add(radio5);
        radio5.setFocusable(true);
        radio5.setRolloverEnabled(true);
        radio5.setVerifyInputWhenFocusTarget(true);
        radio5.addActionListener(this);
        radio5.setFont(new Font("Engravers MT", Font.BOLD, 20));


        radio6 = new JRadioButton("Email Images", false);
        cbg.add(radio6);
        getContentPane().add(radio6);
        radio6.setFocusable(true);
        radio6.setRolloverEnabled(true);
        radio6.setVerifyInputWhenFocusTarget(true);
        radio6.addActionListener(this);
        radio6.setFont(new Font("Engravers MT", Font.BOLD, 20));
        
        radio7 = new JRadioButton("Image Hiding in Image", false);
        cbg.add(radio7);
        getContentPane().add(radio7);
        radio7.setFocusable(true);
        radio7.setRolloverEnabled(true);
        radio7.setVerifyInputWhenFocusTarget(true);
        radio7.addActionListener(this);
        radio7.setFont(new Font("Engravers MT", Font.BOLD, 20));
        
        logo = new JLabel(new ImageIcon("ima3.PNG"));
        getContentPane().add(logo);
        logo.setFont(new Font("RockWell", Font.BOLD, 27));
        logo.setVisible(true);
        
       
        setSize(getPreferredSize());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    	public void showSysTime(final JTextField tf)
	{
	 	final SimpleDateFormat timef = new SimpleDateFormat("HH:mm:ss");
		javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			String s = timef.format(new Date(System.currentTimeMillis()));
			tf.setText(s);
		  }
		});
		timer.start();
	}
	public String showTodayDate()
	{
		Date dt = new Date();
		System.out.println(dt.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
		return sdf.format(dt).toString();
	}

    
    @SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e)
    {
    		 String cmd;
	         cmd = e.getActionCommand();
	       
    	if(e.getSource() == radio1)
         {
         	dispose();
            Encryption en = new Encryption();
            en.setTitle("ENCRYPTION");
         	en.pack();
         	en.show();
          }  
          
           if(e.getSource() == radio2)
         {
         	dispose();
            Decryption dn = new Decryption();
         	dn.setTitle("DECRYPTION");
         	dn.pack();
         	dn.show();
          }  
          
          if(e.getSource()==radio3)
          {
          	dispose();
          	CropImage si=new CropImage();
          	si.setTitle("Crops the image");
          	si.show();
          	si.pack();
          }
          
          if(e.getSource()==radio4)
          {
          	dispose();
          	JoinImage si=new JoinImage();
          	si.setTitle("Merge images");
          	si.show();
          	si.pack();
          }
          
          if(e.getSource()==radio5)
          {
          	dispose();
          	new ImageProcessingTest();   
          }
         
          if(e.getSource()==radio6)
          {
          	dispose();
          	SwingEmailSender ses = new SwingEmailSender();   
          	ses.setTitle("Merge images");
          	ses.show();
          	ses.pack();
          	          	
          }
          
          if(e.getSource()==radio7)
          {
          	dispose();
          	new Cli();
          	dispose();
          
          	          	
          }
          
      if(cmd.equals("EXIT"))
  
          	{
    	  int res;
    	  res = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?");
    	  switch(res) 
    	  {
    	  case JOptionPane.OK_OPTION:
    		  System.exit(0);
    		  break;
    	  case JOptionPane.NO_OPTION:
    		  break;
			
    	  }
      }      
      
}
      @SuppressWarnings("deprecation")
	public static void main(String args[]){
        Stegno s = new Stegno();

        s.setTitle("IMA");
        s.pack();
        s.show();
        
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
}

class StegnoLayout implements LayoutManager {

    public StegnoLayout() {
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
        Dimension dim = new Dimension(800, 800);
        return dim;
    }

    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {c.setBounds(insets.left+150,insets.top+20,648,42);}//ImageManipulationApplicaiton
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+235,insets.top+60,240,48);}//Encryption
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+235,insets.top+100,240,48);}//Decryption
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+536,insets.top+378,58,22);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+593,insets.top+378,105,22);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+235,insets.top+180,240,48);}//CropImage
        c = parent.getComponent(6);
        if (c.isVisible()) {c.setBounds(insets.left+235,insets.top+220,240,48);}//MergeImage
        c = parent.getComponent(7);
        if (c.isVisible()) {c.setBounds(insets.left+235,insets.top+260,400,48);}//Image Enhancement
        c = parent.getComponent(8);
        if (c.isVisible()) {c.setBounds(insets.left+235,insets.top+300,400,48);}//Email Images
        c = parent.getComponent(9);
        if (c.isVisible()) {c.setBounds(insets.left+235,insets.top+140,400,48);}//ImageHidingImage
        c = parent.getComponent(10);
        if (c.isVisible()) {c.setBounds(insets.left+20,insets.top+20,130,130);}//Logo
    }
}
