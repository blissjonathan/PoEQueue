import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import java.awt.SystemColor;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class UpdateWindow {

	private JFrame frmUpdate;
	private static String updateURL;
	private static String updateVersion;

	/**
	 * Launch the application.
	 */
	public static void createWindow(String _updateURL, String _updateVersion) {
		updateURL = _updateURL;
		updateVersion = _updateVersion;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateWindow window = new UpdateWindow();
					window.frmUpdate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UpdateWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String os = System.getProperty("os.name");
		System.out.println("Using operating System: " + os);
		MainWindow.frmPoeQueue.hide();
		frmUpdate = new JFrame();
		frmUpdate.setResizable(false);
		frmUpdate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUpdate.getContentPane().setBackground(SystemColor.inactiveCaption);
		frmUpdate.setAlwaysOnTop(true);
		frmUpdate.setType(Type.POPUP);
		frmUpdate.setTitle("Update!");
//		frmUpdate.setBounds(100, 100, 269, 199);
//		frmUpdate.setPreferredSize(new Dimension(266, 103));
		frmUpdate.setSize(350,130);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmUpdate.setLocation(dim.width/2-frmUpdate.getSize().width/2, dim.height/2-frmUpdate.getSize().height/2);
		
		JLabel lblANewVersion = new JLabel("A new version of PoE Queue has been released!");
		
		JLabel lblVersion = new JLabel("Version:");
		
		JLabel lblVersionnum = new JLabel("VNUM");
		lblVersionnum.setText(updateVersion);
		JButton btnOk = new JButton("Update");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(os.startsWith("Windows")) {
					File file=new File("./Update.bat");
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(file);
						DataOutputStream dos = new DataOutputStream(fos);
						String newLine = System.getProperty("line.separator");
						dos.writeBytes("timeout 3");
						dos.writeBytes(newLine);
						dos.writeBytes("bitsadmin.exe /transfer \"Update\" " + updateURL + " %~dp0\\PoEQueue.jar");
						dos.writeBytes(newLine);
						dos.writeBytes("start javaw -jar PoEQueue.jar"); 
						dos.writeBytes(newLine);
						dos.writeBytes("del \"%~f0\"&exit");
						
						String cmd="cmd /c start Update.bat";
						Runtime r = Runtime.getRuntime();
						Process pr = r.exec(cmd); 
					} catch (IOException e) {
						e.printStackTrace();
					}			
				} else {
					
					File file=new File("./update.sh");
					FileOutputStream fos;
					try {
						fos = new FileOutputStream(file);
						DataOutputStream dos = new DataOutputStream(fos);
						String newLine = System.getProperty("line.separator");
						dos.writeBytes("#!/bin/sh");
						dos.writeBytes(newLine);
						dos.writeBytes("sleep 3");
						dos.writeBytes(newLine);
						dos.writeBytes("cd \"$(dirname \"$0\")\"");
						dos.writeBytes(newLine);
						dos.writeBytes("curl -O " + updateURL); 
						dos.writeBytes(newLine);
						dos.writeBytes("java -jar ./PoEQueue.jar");
						dos.writeBytes(newLine);
						dos.writeBytes("rm -- \"$0\" && exit 0");

						Runtime.getRuntime().exec("chmod u+x ./update.sh"); 
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				frmUpdate.dispose();
				System.exit(0);
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmUpdate.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(87)
					.addComponent(lblVersion)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblVersionnum)
					.addGap(43))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addComponent(lblANewVersion, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(32))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(106)
					.addComponent(btnOk)
					.addContainerGap(121, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblANewVersion, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOk)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVersionnum)
						.addComponent(lblVersion))
					.addGap(99))
		);
		frmUpdate.getContentPane().setLayout(groupLayout);
	}
}
