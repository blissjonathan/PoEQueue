import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.MatteBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JList;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;


public class NewGroupWindow {

	private static JFrame frmNewGroup;
	private JTextField txtDescription;
	
	private DefaultListModel listModel = new DefaultListModel();
	private JList list;
	
	private String text = "";
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private Date date = new Date();
	private String dateData = dateFormat.format(date);
	
	 
	 private int count = 0;
	 
	 private ArrayList<String> members = new ArrayList<String>();
	 
	 private String type = "Any";
	 
	 private String league = "Standard";
	 
	 public boolean isLeader = false;
	 public static int isOpen = 0;
	 JSpinner spinner;

	/**
	 * Launch the application.
	 */
	public static void createWindow() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(isOpen == 0) {
					NewGroupWindow window = new NewGroupWindow();
					window.frmNewGroup.setVisible(true);
					} else {
						System.out.println("Failed to open NewGroupWindow");
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
	public NewGroupWindow() {
		initialize();
		isOpen = 1;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNewGroup = new JFrame();
		frmNewGroup.getContentPane().setBackground(new Color(0, 153, 255));
		frmNewGroup.setTitle("New Group");
		frmNewGroup.setResizable(false);
		frmNewGroup.setBounds(100, 100, 370, 147);
		
		frmNewGroup.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	isOpen = 0;
                frmNewGroup.dispose();
            }
        });
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmNewGroup.setLocation(dim.width/2-frmNewGroup.getSize().width/2, dim.height/2-frmNewGroup.getSize().height/2);
		
		txtDescription = new JTextField(15);
		txtDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				  if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
					  text = txtDescription.getText();
				    }
			}
		});
		txtDescription.setText("Description");
		txtDescription.setColumns(10);
		txtDescription.setDocument(new JTextFieldLimit(40));
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				type = comboBox.getSelectedItem().toString();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Any", "Maps", "PvP", "Labyrinth", "Leveling", "Misc"}));
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				league = comboBox_1.getSelectedItem().toString();
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Standard", "Hardcore", "Standard Challenge", "Hardcore Challenge"}));
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(MainWindow.username != null) {
				String query = "insert into groups (id, type, title, date, count, league, leader, members)"
				        + " values (?, ?, ?, ?, ?, ?, ?, ?)";
				
				String titleText = txtDescription.getText();
				if(titleText.contains("|")) {
					titleText = titleText.replaceAll("|", ",");
				}
				
				try {
					PreparedStatement st = (PreparedStatement) MainWindow.conn.prepareStatement(query);
					st.setInt(1, 0);
					st.setString(2, comboBox.getSelectedItem().toString());
					st.setString(3, titleText);
					st.setString(4, dateData);
					st.setString(5, spinner.getValue().toString());
					st.setString(6, comboBox_1.getSelectedItem().toString());
					st.setString(7, MainWindow.sessionID + ":" + MainWindow.username);
					st.setString(8, "");
					st.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				String group = type + "| " + txtDescription.getText() + "| " + dateData + "| " + spinner.getValue().toString() + "| " +
								comboBox_1.getSelectedItem().toString() + "| " + MainWindow.sessionID + ":" + MainWindow.username;
				
				System.out.println("Group " + group + " created.");
				
				CurrentGroupWindow.createWindow(group);
				
				Statement stmt;
				try {
					stmt = (Statement) MainWindow.conn.createStatement();
					ResultSet _rs = stmt.executeQuery("SELECT type, title, date, count, league, leader FROM groups");
					MainWindow.Update(_rs);
					MainWindow.isLeader = true;
				} catch (SQLException e) {
					e.printStackTrace();
				}

				MainWindow.currentGroup = true;
				frmNewGroup.dispose();
			} else if(MainWindow.username == null) {
				JOptionPane.showMessageDialog(frmNewGroup, "Must create username in settings to form new group.");
				frmNewGroup.dispose();
			}
			}
		});
		
		JLabel lblNoOfMembers = new JLabel("No. of members in group");
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		
		
		GroupLayout groupLayout = new GroupLayout(frmNewGroup.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addComponent(lblNoOfMembers)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spinner))
								.addComponent(txtDescription, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(comboBox_1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(comboBox, 0, 156, Short.MAX_VALUE)))
						.addComponent(btnCreate))
					.addContainerGap(283, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNoOfMembers)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCreate)
					.addContainerGap(157, Short.MAX_VALUE))
		);
		
		frmNewGroup.getContentPane().setLayout(groupLayout);
	}
}
