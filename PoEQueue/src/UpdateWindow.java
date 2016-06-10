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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


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
		frmUpdate.setBounds(100, 100, 269, 199);
		frmUpdate.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmUpdate.setLocation(dim.width/2-frmUpdate.getSize().width/2, dim.height/2-frmUpdate.getSize().height/2);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{31, 47, 39, 25, 91, 0};
		gridBagLayout.rowHeights = new int[]{14, 14, 38, 14, 20, 23, 14, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmUpdate.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblANewVersion = new JLabel("A new version of PoE Queue has been released!");
		GridBagConstraints gbc_lblANewVersion = new GridBagConstraints();
		gbc_lblANewVersion.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblANewVersion.insets = new Insets(0, 0, 5, 0);
		gbc_lblANewVersion.gridwidth = 4;
		gbc_lblANewVersion.gridx = 1;
		gbc_lblANewVersion.gridy = 0;
		frmUpdate.getContentPane().add(lblANewVersion, gbc_lblANewVersion);
		
		JLabel lblVersion = new JLabel("Version:");
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblVersion.insets = new Insets(0, 0, 5, 5);
		gbc_lblVersion.gridx = 2;
		gbc_lblVersion.gridy = 1;
		frmUpdate.getContentPane().add(lblVersion, gbc_lblVersion);
		
		JLabel lblVersionnum = new JLabel(updateVersion);
		GridBagConstraints gbc_lblVersionnum = new GridBagConstraints();
		gbc_lblVersionnum.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblVersionnum.insets = new Insets(0, 0, 5, 5);
		gbc_lblVersionnum.gridx = 3;
		gbc_lblVersionnum.gridy = 1;
		frmUpdate.getContentPane().add(lblVersionnum, gbc_lblVersionnum);
		
		JButton btnOk = new JButton("Update Now");
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
		
		JButton btnOk_1 = new JButton("OK");
		btnOk_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmUpdate.dispose();
				System.exit(0);
			}
		});
		
		JLabel lblGetItAt = new JLabel("Get it at:");
		GridBagConstraints gbc_lblGetItAt = new GridBagConstraints();
		gbc_lblGetItAt.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblGetItAt.insets = new Insets(0, 0, 5, 5);
		gbc_lblGetItAt.gridwidth = 2;
		gbc_lblGetItAt.gridx = 2;
		gbc_lblGetItAt.gridy = 3;
		frmUpdate.getContentPane().add(lblGetItAt, gbc_lblGetItAt);
		
		txtUrl = new JTextField();
		txtUrl.setText(updateURL);
		txtUrl.setEditable(false);
		txtUrl.setColumns(10);
		GridBagConstraints gbc_txtUrl = new GridBagConstraints();
		gbc_txtUrl.anchor = GridBagConstraints.NORTH;
		gbc_txtUrl.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUrl.insets = new Insets(0, 0, 5, 0);
		gbc_txtUrl.gridwidth = 5;
		gbc_txtUrl.gridx = 0;
		gbc_txtUrl.gridy = 4;
		frmUpdate.getContentPane().add(txtUrl, gbc_txtUrl);
		GridBagConstraints gbc_btnOk_1 = new GridBagConstraints();
		gbc_btnOk_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnOk_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnOk_1.gridx = 1;
		gbc_btnOk_1.gridy = 5;
		frmUpdate.getContentPane().add(btnOk_1, gbc_btnOk_1);
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnOk.insets = new Insets(0, 0, 5, 0);
		gbc_btnOk.gridx = 4;
		gbc_btnOk.gridy = 5;
		frmUpdate.getContentPane().add(btnOk, gbc_btnOk);
		
		JLabel lblwindowsOnly = new JLabel("(Windows Only)");
		GridBagConstraints gbc_lblwindowsOnly = new GridBagConstraints();
		gbc_lblwindowsOnly.anchor = GridBagConstraints.NORTH;
		gbc_lblwindowsOnly.gridx = 4;
		gbc_lblwindowsOnly.gridy = 6;
		frmUpdate.getContentPane().add(lblwindowsOnly, gbc_lblwindowsOnly);
	}
}
