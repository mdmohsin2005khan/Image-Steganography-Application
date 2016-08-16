package stegano.image.processing;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

import java.awt.event.ActionListener;
 
import java.awt.event.ActionEvent;
 
import java.awt.Component;
 


import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.Serializable;
import javax.crypto.*;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.spec.*;
import java.net.*;

public class Encryption extends JFrame implements ActionListener,Serializable{
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JButton button1;
    JButton button2;
    JTextField textfield1;
    JTextArea textarea2;
    JScrollPane sp_textarea2;
    JPasswordField passwordfield3;
    JButton button3;
    JButton button4;
    JButton button5;
    JButton button6;
    JMenuItem exitItem = new JMenuItem("Home");
    JMenuBar menuBar = new JMenuBar();
    
    JFileChooser filechooser;
    File f,tempfilename,Ofilename,Sfilename;
    int Copened,Cencrypt,Csave;
    InetAddress ipaddress;
    String name,Ekey,address;
    String chosenFile;
    InputStream ins;
    OutputStream outs;
    Thread t;
    
    
    public Encryption() 
    {
        EncryptionLayout customLayout = new EncryptionLayout();

        getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 12));
        getContentPane().setLayout(customLayout);
        //getContentPane().setBackground(Color.LIGHT_GRAY);

        label1 = new JLabel("SOURCE");
        getContentPane().add(label1);
        label1.setFont(new Font("Garamond", Font.BOLD, 16));

        label2 = new JLabel("MESSAGE");
        getContentPane().add(label2);
        label2.setFont(new Font("Garamond", Font.BOLD, 16));

        label3 = new JLabel("KEY");
        label3.setVisible(false);
        getContentPane().add(label3);
        label3.setFont(new Font("Garamond", Font.BOLD, 16));
        
        JMenu home=new JMenu("Menu");
	    home.setMnemonic('h');
	    exitItem.addActionListener(this);
	    home.add(exitItem);
	    menuBar.add(home);
	    setJMenuBar(menuBar);
       
        button2 = new JButton("ENCRYPT");
        getContentPane().add(button2);
        button2.setFocusable(true);
        button2.setContentAreaFilled(true);
		button2.setBorderPainted(true);
        button2.setRolloverEnabled(true);
        button2.setVerifyInputWhenFocusTarget(true);
        button2.addActionListener(this);
        button2.setFont(new Font("Garamond", Font.BOLD, 16));

        textfield1 = new JTextField("");
        getContentPane().add(textfield1);
        textfield1.setEditable(false);
        textfield1.setBackground(Color.LIGHT_GRAY);
        textfield1.setToolTipText("Choose the image file to be encrypted");
        
        
        textarea2 = new JTextArea("");
        getContentPane().add(textarea2);
        textarea2.setFocusable(true);
        GraphicsEnvironment.getLocalGraphicsEnvironment();
        textarea2.setAutoscrolls(true);
        textarea2.setWrapStyleWord(true);
        textarea2.setBackground(Color.LIGHT_GRAY);
        textarea2.setToolTipText("Enter the message to be embedded with image");
        
       sp_textarea2 = new JScrollPane(textarea2);
       getContentPane().add(sp_textarea2);
        
        passwordfield3 = new JPasswordField("");
        getContentPane().add(passwordfield3);
        passwordfield3.setVisible(false);
        passwordfield3.setToolTipText("Enter a 6 digit KEY");

        button3 = new JButton("BROWSE");
        getContentPane().add(button3);
        button3.addActionListener(this);
        button3.setFocusable(true);
        button3.setContentAreaFilled(true);
		button3.setBorderPainted(true);
        button3.setRolloverEnabled(true);
        button3.setVerifyInputWhenFocusTarget(true);
        button3.setFont(new Font("Garamond", Font.BOLD, 16));

        button4 = new JButton("CLEAR");
        getContentPane().add(button4);
        button4.setFocusable(true);
        button4.setContentAreaFilled(true);
		button4.setBorderPainted(true);
        button4.setToolTipText("Clears the Textarea");
        button4.setRolloverEnabled(true);
        button4.setVerifyInputWhenFocusTarget(true);
        button4.addActionListener(this);
        button4.setFont(new Font("Garamond", Font.BOLD, 16));
        
        button5 = new JButton("SAVE");
        getContentPane().add(button5);
        button5.setFocusable(true);
        button5.setContentAreaFilled(true);
		button5.setBorderPainted(true);
        button5.setToolTipText("Saves the Encrypted image file");
        button5.setRolloverEnabled(true);
        button5.setVerifyInputWhenFocusTarget(true);
        button5.addActionListener(this);
        button5.setFont(new Font("Garamond", Font.BOLD, 16));
        
        button6 = new JButton("SEND");
        getContentPane().add(button6);
        button6.setFocusable(true);
        button6.setContentAreaFilled(true);
		button6.setBorderPainted(true);
        button6.setToolTipText("Sends the Encrypted image file to the remote machine");
        button6.setRolloverEnabled(true);
        button6.setVerifyInputWhenFocusTarget(true);
        button6.addActionListener(this);
        button6.setFont(new Font("Garamond", Font.BOLD, 16));
        button6.setVisible(false);
        
        Copened=0;
        Cencrypt=0;
        Csave=0;
        
        filechooser=new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
        setSize(getPreferredSize());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
   
   public static void main(String args[])  {
        Encryption en = new Encryption();

        en.setTitle("Encryption");
        en.show();
        en.pack();
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
    public void Imageencrypt(String message,File file,String key) throws java.io.IOException 
    {
    
      DataInputStream ins=new DataInputStream(new FileInputStream(file));
      
      try {
          FileInputStream file1 = new FileInputStream(file);
          DataOutputStream outStream=new DataOutputStream(new FileOutputStream(new File("1.JPG")));
          byte k2[] = key.getBytes();
          SecretKeySpec key1 = new SecretKeySpec(k2, "AES");
          Cipher enc = Cipher.getInstance("AES");
          enc.init(Cipher.ENCRYPT_MODE, key1);
          CipherOutputStream cos = new CipherOutputStream(outStream, enc);
          byte[] buf = new byte[63000000];
          int read;
          while ((read = file1.read(buf)) != -1) {
        	  cos.write(buf, 0, read);
          }
          
          DataInputStream file2 = new DataInputStream(new FileInputStream(new File("1.JPG")));
          DataOutputStream outStream1=new DataOutputStream(new FileOutputStream(new File("2.JPG")));
          SecretKeySpec key2 = new SecretKeySpec(k2, "AES");
          Cipher dec = Cipher.getInstance("AES");
          dec.init(Cipher.DECRYPT_MODE, key2);
          CipherOutputStream cos1 = new CipherOutputStream(outStream1, dec);
          byte[] buf1 = new byte[63000000];
          int read1;
          while ((read1 = file2.read(buf1)) != -1) {
              cos1.write(buf1, 0, read1);
          }
          
          
          file1.close();
          file2.close();
          outStream.flush();
          outStream1.flush();
          cos.close();
          cos1.close();
    
          
      } catch (Exception e) {
    	   
           JOptionPane.showMessageDialog(null, e);
      }
    	
    ins.close();
  
   }
    
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
    
    
    
    if(cmd.equals("CLEAR"))
    {
    textarea2.setText("");	 
    }
    if(cmd.equals("SAVE"))
    {
    	
        if(Copened==1 && Cencrypt==1)
       {
        	
        String absolutePath,filePath;
        int r=filechooser.showSaveDialog(this);
        Sfilename=filechooser.getSelectedFile();
        FileInputStream in=new FileInputStream("2.jpg");
        FileOutputStream out=new FileOutputStream(Sfilename);
        Ofilename=Sfilename;
        textfield1.setEditable(true);
        textfield1.setText(Sfilename.getPath());
        
        
        absolutePath = Sfilename.getAbsolutePath();
        filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
        String actualfilename = Sfilename.getName();
        String fname = actualfilename.substring(0, actualfilename.lastIndexOf("."));                                                                                                                                                                                                                   File save_path = new File(filePath+"/"+fname+"_encr.txt");File save_path1 = new File(filePath+"/"+fname+"_Key.txt");
        
        save_path.delete();
        save_path.createNewFile();
       
        save_path1.delete();
        save_path1.createNewFile();

		FileWriter fw = new FileWriter(save_path.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(textarea2.getText());

		
		
		FileWriter fw1 = new FileWriter(save_path1.getAbsoluteFile());
		BufferedWriter bw1 = new BufferedWriter(fw1);
		bw1.write(Ekey); 
		
	 	try{
				
			Runtime.getRuntime().exec("attrib +a +s +h \""+save_path.getAbsolutePath().toString()+"\"");
			Runtime.getRuntime().exec("attrib +a +s +h \""+save_path1.getAbsolutePath().toString()+"\"");
			}catch(Exception ex){
				
				System.out.println(
		                "Error writing file '" 
		                + save_path +" or "+save_path1+ "'");                  
		            // Or we could just do this: 
		             ex.printStackTrace();
			}
		
		bw.close();
		bw1.close();
        
        
        textfield1.setEditable(false);
        
       while(true)
         {
          int i=in.read();
          
          if(i==-1) break;
          out.write(i);
          
          
         }
       
       in.close();
       out.close();
       JOptionPane.showMessageDialog(null,"\nYour image file has been encrypted and saved successfully\n","message",JOptionPane.INFORMATION_MESSAGE);
       
       }
      else
       {
        String m;
        if(Copened==0)
         m="File not Opened";
        else if(Cencrypt==0)
         m="Not Encrypted";
        else
         m="Not Decrypted";
         
        System.out.println("At Encrypt");
        JOptionPane.showMessageDialog(this,m,"Error",JOptionPane.ERROR_MESSAGE);
       }
     } 
   	
    if(cmd.equals("ENCRYPT"))
     {
     	  
     if(Copened==1)
       {
        
        Ekey=JOptionPane.showInputDialog("Enter 16 byte Key For Encryption");
  	 
//String type
        if(Ekey==null)
        {
        JOptionPane.showMessageDialog(this,"Enter only 16 Byte key","Error",JOptionPane.ERROR_MESSAGE);	
        } 	
        if(Ekey.trim().length()<16)
        {
         JOptionPane.showMessageDialog(this,"Enter valid 16 Byte key","Error",JOptionPane.ERROR_MESSAGE);		
        }	
        if(Ekey.trim().length()>16)
         JOptionPane.showMessageDialog(this,"Enter only 16 Byte Key","Error",JOptionPane.ERROR_MESSAGE);
        
        else
         {
          // encrypt the message
           Imageencrypt(textarea2.getText(),Ofilename,Ekey);
           Cencrypt=1;
          
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
  

          JOptionPane.showMessageDialog(this,"Select Only Image file","Error",JOptionPane.ERROR_MESSAGE);

        else
          {
        
            Copened=1;
            Ofilename=tempfilename;
            textfield1.setText(name);
            textfield1.setText(tempfilename.getPath());
            textfield1.setFont(new Font("Century",Font.PLAIN,15));
            textfield1.setBackground(Color.LIGHT_GRAY);
            textfield1.setEditable(false);
        
           }
          }
        }

    } // end try
    
     catch(Exception xe)
     {
    	 System.out.println("HEre at the end");
     	//xe.printStackTrace();
    	 JOptionPane.showMessageDialog(this,xe,"Error",JOptionPane.ERROR_MESSAGE);
     }

   } // end of actionperformed
} // End of class
    
class EncryptionLayout implements LayoutManager {

    public EncryptionLayout() {
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
        if (c.isVisible()) {c.setBounds(insets.left+75,insets.top+74,112,32);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+75,insets.top+170,112,32);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+88,insets.top+160,112,32);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+200,insets.top+294,124,38);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+158,insets.top+74,390,32);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+158,insets.top+120,390,150);}
        c = parent.getComponent(6);
        if (c.isVisible()) {c.setBounds(insets.left+200,insets.top+160,284,52);}
        c = parent.getComponent(7);
        if (c.isVisible()) {c.setBounds(insets.left+558,insets.top+74,114,35);}
        c = parent.getComponent(8);
        if (c.isVisible()) {c.setBounds(insets.left+558,insets.top+173,114,35);}
        c = parent.getComponent(9);
        if (c.isVisible()) {c.setBounds(insets.left+400,insets.top+294,124,38);}
        c = parent.getComponent(10);
        if (c.isVisible()) {c.setBounds(insets.left+559,insets.top+204,114,35);}
       
      
    }
}
