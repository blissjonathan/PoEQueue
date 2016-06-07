

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class AboutWindow {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void createWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutWindow window = new AboutWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AboutWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.setResizable(false);
		frame.setBounds(100, 100, 387, 187);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{122, 125, 0};
		gridBagLayout.rowHeights = new int[]{27, 14, 20, 23, 14, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblVersion = new JLabel("Version: " + MainWindow.version);
		lblVersion.setFont(new Font("Tahoma", Font.BOLD, 22));
		GridBagConstraints gbc_lblVersion = new GridBagConstraints();
		gbc_lblVersion.anchor = GridBagConstraints.NORTH;
		gbc_lblVersion.insets = new Insets(0, 0, 5, 0);
		gbc_lblVersion.gridx = 1;
		gbc_lblVersion.gridy = 0;
		frame.getContentPane().add(lblVersion, gbc_lblVersion);
		
		JLabel lblSoftwareGuideAt = new JLabel("Software guide at:");
		GridBagConstraints gbc_lblSoftwareGuideAt = new GridBagConstraints();
		gbc_lblSoftwareGuideAt.anchor = GridBagConstraints.NORTH;
		gbc_lblSoftwareGuideAt.insets = new Insets(0, 0, 5, 0);
		gbc_lblSoftwareGuideAt.gridx = 1;
		gbc_lblSoftwareGuideAt.gridy = 1;
		frame.getContentPane().add(lblSoftwareGuideAt, gbc_lblSoftwareGuideAt);
		
		JButton btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection stringSelection = new StringSelection (textField.getText());
				Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
				clpbrd.setContents (stringSelection, null);
			}
		});
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setText("https://github.com/blissjonathan/PoEQueue.git");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.NORTH;
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		frame.getContentPane().add(textField, gbc_textField);
		GridBagConstraints gbc_btnCopy = new GridBagConstraints();
		gbc_btnCopy.anchor = GridBagConstraints.NORTH;
		gbc_btnCopy.insets = new Insets(0, 0, 5, 0);
		gbc_btnCopy.gridx = 1;
		gbc_btnCopy.gridy = 3;
		frame.getContentPane().add(btnCopy, gbc_btnCopy);
		
		JLabel lblCreatedByJonathan = new JLabel("Created By Jonathan Bliss");
		GridBagConstraints gbc_lblCreatedByJonathan = new GridBagConstraints();
		gbc_lblCreatedByJonathan.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblCreatedByJonathan.gridx = 1;
		gbc_lblCreatedByJonathan.gridy = 4;
		frame.getContentPane().add(lblCreatedByJonathan, gbc_lblCreatedByJonathan);
	}
}
