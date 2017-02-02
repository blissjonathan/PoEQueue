import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.SystemColor;


public class SettingsWindow {

	private static JFrame frmSettings;
	private JTextField txtUsername;
	private static String username;
	static int isOpen = 0;
	/**
	 * Launch the application.
	 */
	public static void createWindow(String _username) {
		username = _username;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(isOpen == 0) {
					System.out.println("Opening Settings Window...");
					SettingsWindow window = new SettingsWindow();
					window.frmSettings.setVisible(true);
					} else {
					System.out.println("Fail to open Settings Window");
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
		isOpen = 1;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	public int closeFrame() {
		this.frmSettings = null;
		this.frmSettings.dispose();
		return 1;
	}
	private void initialize() {
		frmSettings = new JFrame();
		frmSettings.getContentPane().setForeground(SystemColor.inactiveCaption);
		frmSettings.setAlwaysOnTop(true);
		frmSettings.setTitle("Settings");
		frmSettings.setBounds(100, 100, 265, 176);
		
		frmSettings.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                	isOpen=0;
                    frmSettings.dispose();
                }
            });
		//frmSettings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmSettings.setLocation(dim.width/2-frmSettings.getSize().width/2, dim.height/2-frmSettings.getSize().height/2);
		
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setDocument(new JTextFieldLimit(20));
		txtUsername.setText(username);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(MainWindow.currentGroup == false) {
				String temp = txtUsername.getText();
				if(temp.contains(";")) {
					temp.replaceAll(";", ",");
				}
				MainWindow.username = temp;
				MainWindow.SaveInfo(temp);
				frmSettings.dispose();
			} else {
				JOptionPane.showMessageDialog(frmSettings, "You must leave the group before changing your username.");
				frmSettings.dispose();
			}
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
