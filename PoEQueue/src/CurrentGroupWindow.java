import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

















import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimerTask;

import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import java.awt.Color;

import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JSeparator;

import com.mysql.jdbc.PreparedStatement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CurrentGroupWindow {

	public static JFrame frmCurrentGroup;
	
	public static String group;
	
	private JLabel lblTime;
	
	private String selectedMember = null;

	private static JList memlist;
	private static DefaultListModel memModel = new DefaultListModel();
	private static JTextField textField;

	private static JLabel lblMembers;
	
	private static String count;
	private static String id;
	private static String leader;
	private static String title;
	private static String league;
	private static String type;
	private static String date;
	private static String session;
	public static String leaderinfo;

	public static Timer timer;
	/**
	 * Launch the application.
	 */
	public static void createWindow(String _group) {
		group = _group;
		
		StringTokenizer st = new StringTokenizer(group, "|");
		type = st.nextToken();
		title = st.nextToken();
		date = st.nextToken();
		count = st.nextToken();
		league = st.nextToken();
		leaderinfo = st.nextToken();
		leaderinfo = leaderinfo.substring(1); //removes white space
		StringTokenizer leaderST = new StringTokenizer(leaderinfo, ":");
		session = leaderST.nextToken();
		if(leaderST.hasMoreTokens()) {
		leader = leaderST.nextToken();
		}
		
		timer = new Timer(10000, null);
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Update();
				
			}
			
		};
		timer.addActionListener(listener);
		timer.start();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CurrentGroupWindow window = new CurrentGroupWindow();
					window.frmCurrentGroup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CurrentGroupWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCurrentGroup = new JFrame();
		frmCurrentGroup.getContentPane().setBackground(Color.DARK_GRAY);
		frmCurrentGroup.setAutoRequestFocus(false);
		frmCurrentGroup.setResizable(false);
		frmCurrentGroup.setContentPane(new JLabel(new ImageIcon(getClass().getResource("/images/black.png"))));
		frmCurrentGroup.setTitle("Current Group");
		frmCurrentGroup.setBounds(100, 100, 258, 467);
		frmCurrentGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCurrentGroup.setUndecorated(true);

		frmCurrentGroup.setLocation(MainWindow.frmPoeQueue.getX()+MainWindow.frmPoeQueue.getWidth(),
									MainWindow.frmPoeQueue.getY());
		
		lblTime = new JLabel("Time Spent in Group: ");
		lblTime.setForeground(Color.WHITE);
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		
		lblMembers = new JLabel("Members: " + count);
		lblMembers.setForeground(Color.WHITE);
		
		JLabel lblGroupId = new JLabel("Group ID: " + session.substring(24));
		lblGroupId.setForeground(Color.WHITE);
		
		JButton btnPmLeader = new JButton("PM Leader");
		btnPmLeader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CopyWindow.createWindow("@" + leader + " Hello, I have joined your group on PoE queue. I'm ready for my invite.");
			}
		});
		
		JLabel lblLeader = new JLabel("Leader: " + leader);
		lblLeader.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblLeader.setForeground(Color.WHITE);
		
		JButton btnLeave = new JButton("Leave");
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.LeaveGroup();
			}
		});
		
		JButton btnKickMember = new JButton("Remove Member");
		btnKickMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(MainWindow.isLeader==true) {
					String query = "UPDATE groups SET count = count - 1 WHERE leader = '" + leaderinfo + "' AND count > 0";
					String query2 = "UPDATE groups SET members = REPLACE(members, '" + memlist.getSelectedValue().toString() + ";', '') "
									+ "WHERE leader = '" + leaderinfo + "'";
					
					try {
						PreparedStatement st = (PreparedStatement) MainWindow.conn.prepareStatement(query);
						st.execute();
						PreparedStatement st2 = (PreparedStatement) MainWindow.conn.prepareStatement(query2);
						st2.execute();
						Update();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		JButton btnAddMember = new JButton("Add Member");
		btnAddMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "UPDATE groups SET count = count + 1 WHERE leader = '" + leaderinfo + "' AND count < 6";
				//query2 add member somehow
				try {
					PreparedStatement st = (PreparedStatement) MainWindow.conn.prepareStatement(query);
					st.execute();
					Update();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAddMember.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		JSeparator separator = new JSeparator();
		
		JLabel lblLeaderControls = new JLabel("Leader Controls");
		lblLeaderControls.setForeground(Color.WHITE);
		lblLeaderControls.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setForeground(Color.WHITE);
		
		textField = new JTextField(15);
		textField.setText(title);
		textField.setColumns(10);
		textField.setDocument(new JTextFieldLimit(40));
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(MainWindow.isLeader==true) {
				String query = "UPDATE groups SET title = '" + textField.getText() + "' WHERE leader = '" + leaderinfo + "'" ;
				
				try {
					PreparedStatement st = (PreparedStatement) MainWindow.conn.prepareStatement(query);
					st.execute();
					Update();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		});
		
		if(MainWindow.isLeader == false) {
			btnAddMember.setEnabled(false);
			btnKickMember.setEnabled(false);
			textField.setEditable(false);
			btnUpdate.setEnabled(false);
		}
		
		JSeparator separator_1 = new JSeparator();
		
		JLabel lblLeague = new JLabel("League: " + league);
		lblLeague.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblLeague.setForeground(Color.WHITE);
		
		JLabel lblType = new JLabel("Type: " + type);
		lblType.setForeground(Color.WHITE);
		
		JButton buttonRefresh = new JButton("");
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update();
			}
		});
		
		buttonRefresh.setPreferredSize(new Dimension(16,16));
		buttonRefresh.setOpaque(false);
		buttonRefresh.setContentAreaFilled(false);
		buttonRefresh.setBorderPainted(false);
		buttonRefresh.setIcon(new ImageIcon(getClass().getResource("/images/refresh.png")));
		
		memlist = new JList();
		memlist.setToolTipText("Right click for invite message.");
		memlist.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3) {
					if(memlist.getSelectedValue()!=null && MainWindow.isLeader == true) {
					CopyWindow.createWindow("/invite " + memlist.getSelectedValue().toString());
					}
				}
			}
		});
		memlist.setModel(memModel);
		
		JLabel lblMembers_1 = new JLabel("Members:");
		lblMembers_1.setForeground(Color.WHITE);
		
		GroupLayout groupLayout = new GroupLayout(frmCurrentGroup.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnPmLeader, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLeader)
						.addComponent(lblMembers))
					.addGap(22)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLeague)
						.addComponent(lblType))
					.addGap(26))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblLeaderControls)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTime)
						.addComponent(lblGroupId))
					.addPreferredGap(ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
					.addComponent(buttonRefresh, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(32))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnLeave)
							.addPreferredGap(ComponentPlacement.RELATED, 187, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnAddMember, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(btnKickMember)
					.addContainerGap(38, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(lblDescription)
							.addPreferredGap(ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
							.addComponent(btnUpdate)))
					.addGap(29))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(memlist, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
					.addGap(29))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMembers_1, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(191, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTime)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblGroupId)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblLeader)
								.addComponent(lblType))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMembers)
								.addComponent(lblLeague))
							.addGap(2)
							.addComponent(btnPmLeader))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(buttonRefresh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLeaderControls)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDescription)
						.addComponent(btnUpdate))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(22)
					.addComponent(lblMembers_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(memlist, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddMember)
						.addComponent(btnKickMember, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLeave)
					.addGap(69))
		);
		frmCurrentGroup.getContentPane().setLayout(groupLayout);


		Thread timer = new Thread(new Runnable() {
			public void run() {
				long start = System.currentTimeMillis();
		        while (true)
		        {
		            long time = System.currentTimeMillis() - start;
		            int seconds = (int) (time / 1000);
		            SwingUtilities.invokeLater(new Runnable() {
		                 public void run()
		                 {
		                       lblTime.setText("Time Passed: " + seconds);
		                 }
		            });
		            try { Thread.sleep(100); } catch(Exception e) {}
		        }
			}
		});
		timer.start();
		
		Update();
		
	}
	
	private static void Update() {
		  Thread update = new Thread(){
			    public void run(){
			    System.out.println("Refreshing Group");
				java.sql.ResultSetMetaData rsmd;
			    String group = "";
			    String query = "SELECT title, count, members FROM groups WHERE leader = '" + leaderinfo + "'";
			    	
			    	try {
			    		ResultSet rs = null;
						PreparedStatement st = (PreparedStatement) MainWindow.conn.prepareStatement(query);
						rs = st.executeQuery();
						if(rs.next()) {
						 rs = st.executeQuery();
						 rsmd = rs.getMetaData();
						 int columnsNumber = rsmd.getColumnCount();
						    while (rs.next()) {
						    	String tempRow = "";
						        for (int i = 1; i <= columnsNumber; i++) {
						            String columnValue = rs.getString(i);
						           
						            tempRow += (columnValue + "|");
						            
						        }
						        tempRow = tempRow.substring(0,tempRow.length()-1);
						        group = tempRow;
						        System.out.println("Updating info: " + group);
						    }
						    
						    memlist.clearSelection();
							memModel.clear();
						    
						    StringTokenizer groupST = new StringTokenizer(group,"|");
						    String description = groupST.nextToken();
						    String memCount = groupST.nextToken();
						    String members = "";
						    if(groupST.hasMoreTokens()) {
						    members = groupST.nextToken();
						    members = members.substring(0, members.length()-1);
						    StringTokenizer memST = new StringTokenizer(members,";");
						    while(memST.hasMoreTokens()) {
						    	memModel.addElement(memST.nextToken());
						    }
						    memlist.repaint();
					    	memlist.revalidate();
						    }
						    
						    textField.setText(description);
						    lblMembers.setText("Members: " + memCount);
					    	
						    if(!(members.contains(MainWindow.username)) && MainWindow.isLeader == false) {
						    	JOptionPane.showMessageDialog(frmCurrentGroup, "You have been removed from the group.");
								MainWindow.LeaveGroup();
						    }
						    
						} else {
							JOptionPane.showMessageDialog(frmCurrentGroup, "Leader has left or your group cannot be found.");
							MainWindow.LeaveGroup();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}	
			    }
			  };

			  update.start();
	}
	
	
	public static void closeFrame() {
		timer.stop();
		frmCurrentGroup.dispose();
	}
}
