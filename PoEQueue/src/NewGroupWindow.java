import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
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
import java.awt.GridBagLayout;

import javax.swing.JList;

import java.awt.GridBagConstraints;

import javax.swing.JButton;

import com.mysql.jdbc.PreparedStatement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class NewGroupWindow {

	private JFrame frmNewGroup;
	private JTextField txtDescription;
	private JPanel panel;
	
	private DefaultListModel listModel = new DefaultListModel();
	private JList list;
	
	private String text = "";
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private Date date = new Date();
	private String dateData = dateFormat.format(date);
	
	 static Random r = new Random(10);
	 static String Lid = Long.toString(Math.abs(r.nextLong()), 36);
	 public static String leader = MainWindow.username + "-" + Lid;
	 
	 private int count = 0;
	 
	 private ArrayList<String> members = new ArrayList<String>();
	 
	 private String type = "Any";
	 
	 private String league = "Standard";
	 
	 public boolean isLeader = false;

	/**
	 * Launch the application.
	 */
	public static void createWindow() {
		System.out.println(leader);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewGroupWindow window = new NewGroupWindow();
					window.frmNewGroup.setVisible(true);
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNewGroup = new JFrame();
		frmNewGroup.setTitle("New Group");
		frmNewGroup.setResizable(false);
		frmNewGroup.setBounds(100, 100, 502, 272);
		frmNewGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		txtDescription = new JTextField();
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				type = comboBox.getSelectedItem().toString();
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Any", "Maps", "PvP", "Labyrinth", "Leveling"}));
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				league = comboBox_1.getSelectedItem().toString();
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Standard", "Hardcore", "Standard Challenge", "Hardcore Challenge"}));
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)), "Members", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		
		JButton btnAddMember = new JButton("Add Member");
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(null, "Member ID",
				        "Add Member", JOptionPane.OK_CANCEL_OPTION);
				if(name != null && !(name.isEmpty()) && count <5) {
					count++;
					members.add(name);
					listModel.addElement(name);
					list.repaint();
					list.revalidate();
				}
			}
		});
		
		JButton btnRemoveMember = new JButton("Remove Member");
		btnRemoveMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list.getSelectedValue() != null) {
				listModel.remove(list.getSelectedIndex());
				list.repaint();
				list.revalidate();
				
				if(members.contains(list.getSelectedValue())) {
					members.remove(list.getSelectedValue());
				}
				
				String query = "";
				try {
					PreparedStatement st = (PreparedStatement) MainWindow.conn.prepareStatement(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				count--;
				}
			}
		});
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "insert into groups (id, type, title, date, count, league, leader,"
						+ "member1, member2, member3, member4, member5)"
				        + " values (?, ?, ?, ?, ?, ?, ?, ?)";
				try {
					PreparedStatement st = (PreparedStatement) MainWindow.conn.prepareStatement(query);
					st.setInt(1, 0);
					st.setString(2,type);
					st.setString(3, text);
					st.setString(4, dateData);
					st.setInt(5, count);
					st.setString(6, league);
					st.setString(7, leader);
					st.setString(8, members.get(0));
					st.setString(9, members.get(1));
					st.setString(10, members.get(2));
					st.setString(11, members.get(3));
					st.setString(12, members.get(4));
					st.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				MainWindow.Update(MainWindow.rs);
				MainWindow.isLeader = true;
				frmNewGroup.dispose();
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frmNewGroup.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(btnAddMember)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnRemoveMember)))
						.addComponent(btnCreate))
					.addContainerGap(75, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtDescription, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddMember)
						.addComponent(btnRemoveMember))
					.addGap(28)
					.addComponent(btnCreate)
					.addContainerGap(160, Short.MAX_VALUE))
		);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		list = new JList(listModel);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		panel.add(list, gbc_list);
		frmNewGroup.getContentPane().setLayout(groupLayout);
	}
}
