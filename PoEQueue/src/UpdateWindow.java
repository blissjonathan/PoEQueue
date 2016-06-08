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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class UpdateWindow {

	private JFrame frmUpdate;
	private JTextField txtUrl;
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
		frmUpdate = new JFrame();
		frmUpdate.getContentPane().setBackground(SystemColor.inactiveCaption);
		frmUpdate.setAlwaysOnTop(true);
		frmUpdate.setType(Type.POPUP);
		frmUpdate.setTitle("Update!");
		frmUpdate.setResizable(false);
		frmUpdate.setBounds(100, 100, 311, 207);
		frmUpdate.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmUpdate.setLocation(dim.width/2-frmUpdate.getSize().width/2, dim.height/2-frmUpdate.getSize().height/2);
		
		JLabel lblANewVersion = new JLabel("A new version of PoE Queue has been released!");
		
		JLabel lblVersion = new JLabel("Version:");
		
		JLabel lblVersionnum = new JLabel(updateVersion);
		
		JLabel lblGetItAt = new JLabel("Get it at:");
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
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
				
				
				frmUpdate.dispose();
				System.exit(0);
			}
		});
		
		txtUrl = new JTextField();
		txtUrl.setText(updateURL);
		txtUrl.setEditable(false);
		txtUrl.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(frmUpdate.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(115)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnOk)
								.addComponent(lblGetItAt)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(88)
							.addComponent(lblVersion)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblVersionnum))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(31)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtUrl)
								.addComponent(lblANewVersion, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap(40, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblANewVersion)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVersion)
						.addComponent(lblVersionnum))
					.addGap(38)
					.addComponent(lblGetItAt)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtUrl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(btnOk)
					.addContainerGap())
		);
		frmUpdate.getContentPane().setLayout(groupLayout);
	}
}
