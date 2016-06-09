import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;


public class SettingsWindow {

	private static JFrame frmSettings;
	private JTextField txtUsername;
	private static String username;
	/**
	 * Launch the application.
	 */
	public static void createWindow(String _username) {
		username = _username;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(frmSettings == null) {
					SettingsWindow window = new SettingsWindow();
					window.frmSettings.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SettingsWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSettings = new JFrame();
		frmSettings.getContentPane().setForeground(SystemColor.inactiveCaption);
		frmSettings.setAlwaysOnTop(true);
		frmSettings.setTitle("Settings");
		frmSettings.setBounds(100, 100, 265, 176);
		frmSettings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmSettings.setLocation(dim.width/2-frmSettings.getSize().width/2, dim.height/2-frmSettings.getSize().height/2);
		
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setDocument(new JTextFieldLimit(20));
		txtUsername.setText(username);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = txtUsername.getText();
//				if(temp.contains(";")) {
//					temp.replaceAll(";", ",");
//				}
				MainWindow.username = temp;
				MainWindow.SaveInfo(temp);
				frmSettings.dispose();
			}
		});
		GroupLayout groupLayout = new GroupLayout(frmSettings.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(51)
							.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(93)
							.addComponent(btnSave)))
					.addContainerGap(54, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(56)
					.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(btnSave)
					.addContainerGap())
		);
		frmSettings.getContentPane().setLayout(groupLayout);
	}
}
