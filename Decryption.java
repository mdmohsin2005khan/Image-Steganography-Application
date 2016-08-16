package stegano.image.processing;
import java.awt.*;
import java.awt.event.*;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Serializable;



@SuppressWarnings("serial")
class Decryption extends JFrame implements ActionListener,Serializable {
    JLabel label1;
   JLabel label2;
    JLabel label3;
    JButton button1;
    JButton button2;
    JTextField textfield1;
    JTextField textfield2;
    JTextArea textarea3;
    JScrollPane sp_textarea3;
    JButton button3;
    JButton button4;
    JMenuItem exitItem = new JMenuItem("Home");
    JMenuBar menuBar = new JMenuBar();
    JFileChooser filechooser;
    File f,tempfilename,Ofilename,Sfilename;
    int Copened,Cdecrypt;
    String name,Dkey;
    String chosenFile;
    
    

    public Decryption() 
    {
        decryptionLayout customLayout = new decryptionLayout();

        getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 12));
        getContentPane().setLayout(customLayout);
        //getContentPane().setBackground(Color.LIGHT_GRAY);

        label1 = new JLabel("SOURCE");
        getContentPane().add(label1);
        label1.setFont(new Font("Garamond", Font.BOLD, 16));

        label2 = new JLabel("IMAGE");
        getContentPane().add(label2);
        label2.setVisible(false);
        label2.setFont(new Font("Garamond", Font.BOLD, 16));

        label3 = new JLabel("MESSAGE");
        getContentPane().add(label3);
        label3.setToolTipText("Encrypted message is:");
        label3.setFont(new Font("Garamond", Font.BOLD, 16));


        button2 = new JButton("DECRYPT");
        getContentPane().add(button2);
        button2.setFocusable(true);
        button2.setRolloverEnabled(true);
        button2.setVerifyInputWhenFocusTarget(true);
        button2.addActionListener(this);
        button2.setFont(new Font("Garamond", Font.BOLD, 18));

        JMenu home=new JMenu("Menu");
	    home.setMnemonic('h');
	    exitItem.addActionListener(this);
	    home.add(exitItem);
	    menuBar.add(home);
	    setJMenuBar(menuBar);
        
        textfield1 = new JTextField("");
        getContentPane().add(textfield1);
        textfield1.setEditable(false);
        textfield1.setBackground(Color.LIGHT_GRAY);
        textfield1.setToolTipText("Choose the file to decrypt for message");

        textfield2 = new JTextField("");
        getContentPane().add(textfield2);
        textfield2.setVisible(false);
        textfield2.setToolTipText("Choose the image file to be encrypted");

        textarea3 = new JTextArea("");
        getContentPane().add(textarea3);
        textarea3.setBackground(Color.LIGHT_GRAY);
        textarea3.setToolTipText("The Encrypted Message is ::");
        textarea3.setEditable(false);
        sp_textarea3 = new JScrollPane(textarea3);
        sp_textarea3.setWheelScrollingEnabled(true);
        textarea3.setFocusable(true);
       
        
        getContentPane().add(sp_textarea3);

        button3 = new JButton("BROWSE");
        getContentPane().add(button3);
        button3.setFocusable(true);
        button3.setRolloverEnabled(true);
        button3.setVerifyInputWhenFocusTarget(true);
        button3.addActionListener(this);
        button3.setToolTipText("Select the Encrypted image file");
        button3.setFont(new Font("Garamond", Font.BOLD, 16));

        button4 = new JButton("BROWSE");
        getContentPane().add(button4);
        button4.addActionListener(this);
        button4.setVisible(false);
        button4.setFont(new Font("Garamond", Font.BOLD, 16));
        
        Copened=0;
        Cdecrypt=0;
       
        
    filechooser=new JFileChooser();
    filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        setSize(getPreferredSize());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    public void Imagedecrypt(File filename,String key)throws java.io.IOException
 {
  String absolutePath,filePath,line3=null,line2=null;
  
  
  FileInputStream ins=new FileInputStream(filename);
 
 
  absolutePath = filename.getAbsolutePath();
  filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
  String actualfilename = filename.getName();
  String fname = actualfilename.substring(0, actualfilename.lastIndexOf("."));
  
  
  String filePath1 = filePath+"\\"+fname+"_key.txt";
  String filePath2 = filePath+"\\"+fname+"_encr.txt";
  
  
 
  try {
      // FileReader reads text files in the default encoding.
      FileReader fileReader = 
          new FileReader(filePath1);

      FileReader fileReader1 = 
              new FileReader(filePath2);

      // Always wrap FileReader in BufferedReader.
      BufferedReader bufferedReader = 
          new BufferedReader(fileReader);

      BufferedReader bufferedReader1 = 
              new BufferedReader(fileReader1);
      
      String line=null;
	while((line = bufferedReader.readLine())!= null) {
		   
		    line2=line;
		    
	      
      }   

	 String line1 = null; 
	while((line1 = bufferedReader1.readLine()) != null) {
			
		   line3=line1;
		   textarea3.append(line3+"\n");
			
	      }
      // Always close files.
      bufferedReader.close();
      bufferedReader1.close();
  }
  catch(FileNotFoundException ex) {
      System.out.println("Unable to open file '" + filePath1 + "'");                
  }

  
 if (key.equals(line2)){

	 try {
      
      FileOutputStream outStream = new FileOutputStream("E:/Mohi/Decrypt.JPG");
      byte k[] = key.getBytes();
      SecretKeySpec key1 = new SecretKeySpec(k, "AES");
      Cipher enc = Cipher.getInstance("AES");
      enc.init(Cipher.DECRYPT_MODE, key1);
      CipherOutputStream cos = new CipherOutputStream(outStream, enc);
      byte[] buf = new byte[63000000];
      int read;
      while ((read = ins.read(buf)) != -1) {
    	  
          cos.write(buf, 0, read);
      }
      //ins.close();
      outStream.flush();
      cos.close();
      
      
	 } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
	 }
	 
	 
  
 }
 else{
	 Cdecrypt=0;
	 JOptionPane.showMessageDialog(this,"Key didn't match the encrypted key","Error",JOptionPane.ERROR_MESSAGE);
 }

ins.close();

 }

    @SuppressWarnings("deprecation")
	public static void main(String args[]) throws Exception
    {
        Decryption dn = new Decryption();

        dn.setTitle("Decryption");
        dn.show();
        dn.pack();
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


@SuppressWarnings("deprecation")
public void actionPerformed(ActionEvent e)
  {
  	try
    {

    String cmd;
    cmd=e.getActionCommand();
    
    if(e.getSource()==exitItem)
    {
    	dispose();
        Stegno s = new Stegno();
        s.show();
        s.pack();
        s.setTitle("IMA");
    } 
    

    if(cmd.equals("DECRYPT"))
     {
      if(Copened==1)
       {
        Dkey=JOptionPane.showInputDialog("Enter 16 byte Key For Decryption");
//String type
        if(Dkey.trim().equals(""))
         JOptionPane.showMessageDialog(this,"Invalid Input","Error",JOptionPane.ERROR_MESSAGE);
        else if(Dkey.trim().length()<16)
         JOptionPane.showMessageDialog(this,"Enter Valid 16 byte key","Error",JOptionPane.ERROR_MESSAGE);	
        else if(Dkey.trim().length()>32)
            JOptionPane.showMessageDialog(this,"Maximum 16 byte key is allowed","Error",JOptionPane.ERROR_MESSAGE);		
        else
         {
          // decrypt the message
      
          Cdecrypt=1;
          Imagedecrypt(Ofilename,Dkey);
          
          if(Cdecrypt==1){
              
        	  JOptionPane.showMessageDialog(null, "The image was decrypted successfully");
          }
         }
       }
      else
       {
        JOptionPane.showMessageDialog(this,"File NotOpened","Error",JOptionPane.ERROR_MESSAGE);
       }
     } 
    if(cmd.equals("BROWSE"))
    {
    	int r=filechooser.showOpenDialog(this);
      tempfilename=filechooser.getSelectedFile(); //File type
      if(r==JFileChooser.CANCEL_OPTION)
       JOptionPane.showMessageDialog(this,"FileNotSelected","Error",JOptionPane.ERROR_MESSAGE);
      else
       {
       String name=tempfilename.getName();

       if((!name.endsWith(".JPG") && !name.endsWith(".jpg")) && 
         (!name.endsWith(".GIF") && !name.endsWith(".gif")) && 
         (!name.endsWith(".PNG") && !name.endsWith(".png")) &&
         (!name.endsWith(".BMP") && !name.endsWith(".bmp")) && 
         (!name.endsWith(".JPEG")&& !name.endsWith(".jpeg")))  
      

          JOptionPane.showMessageDialog(this,"Select Only Encrypted Image file","Error",JOptionPane.ERROR_MESSAGE);

        else
          {
            Copened=1;
            Ofilename=tempfilename;
            textfield1.setEditable(true);
            textfield1.setText(tempfilename.getPath());
            textfield1.setFont(new Font("Century",Font.PLAIN,15));
            textfield1.setBackground(Color.LIGHT_GRAY);
            //textfield1.setText(name);
            textfield1.setEditable(false);
           }

         }
            }
            } // end try
    catch(Exception ae)
     {
     //
JOptionPane.showMessageDialog(this,e,"Error",JOptionPane.ERROR_MESSAGE);
     }      
}

class decryptionLayout implements LayoutManager {

    public decryptionLayout() {
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
        if (c.isVisible()) {c.setBounds(insets.left+75,insets.top+64,112,32);}
       c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+88,insets.top+112,112,32);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+75,insets.top+165,112,32);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+270,insets.top+294,134,42);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+165,insets.top+64,390,32);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+200,insets.top+112,264,70);}
        c = parent.getComponent(6);
        if (c.isVisible()) {c.setBounds(insets.left+165,insets.top+110,390,150);}
        c = parent.getComponent(7);
        if (c.isVisible()) {c.setBounds(insets.left+570,insets.top+64,104,32);}
        c = parent.getComponent(8);
        if (c.isVisible()) {c.setBounds(insets.left+480,insets.top+112,104,32);}
     
        
        
    }
}
}
