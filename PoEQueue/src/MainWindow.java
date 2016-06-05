import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.swing.JFrame;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.awt.GridBagLayout;

import javax.swing.JList;

import java.awt.GridBagConstraints;

import javax.swing.border.BevelBorder;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class MainWindow {

	public static JFrame frmPoeQueue;
	public ArrayList<Group> groups = new ArrayList<Group>();
	
	private static DefaultListModel qModel = new DefaultListModel();
	public static JList qList;
	
	public static boolean currentGroup = false;
	
	private String setType;
	private String searchText;
	
	public JMenu menuUpdate;
	public static JMenu menuJoin;
	public static JMenu menuLeave;
	
	public static String selectedGroup = "";
	
	public static String username = "test";
	
	public static ResultSet rs;
	static Connection conn;
	
	public static UUID sessionID = UUID.randomUUID();
	public static String leaderID = "";
	
	public static boolean isLeader = false;
	
	private JTextField searchField;
	
	public static ArrayList<String> groupList = new ArrayList<String>();
	private JTextField textField;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					try {
					    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
					        if ("Nimbus".equals(info.getName())) {
					            UIManager.setLookAndFeel(info.getClassName());
					            break;
					        }
					    }
					} catch (UnsupportedLookAndFeelException e) {
					    // handle exception
					} catch (ClassNotFoundException e) {
					    // handle exception
					} catch (InstantiationException e) {
					    // handle exception
					} catch (IllegalAccessException e) {
					    // handle exception
					}
					
					conn = (Connection) DriverManager.getConnection(LoginInfo.url, LoginInfo.username, LoginInfo.password);
					Statement stmt = (Statement) conn.createStatement();
					rs = stmt.executeQuery("SELECT type, title, date, count, league, leader FROM groups");
					

					String content = new String(Files.readAllBytes(Paths.get("./resources/data.txt")));
					username = content;
					
					Runtime.getRuntime().addShutdownHook(new Thread()
					{
					    @Override
					    public void run()
					    {
					    	System.out.println("Quitting Application");
					        LeaveGroup();
					    }
					});
					
					System.out.println("Session ID: " + sessionID);
					MainWindow window = new MainWindow();
					window.frmPoeQueue.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPoeQueue = new JFrame();
		frmPoeQueue.setResizable(false);
		frmPoeQueue.setTitle("PoE Queue");
		frmPoeQueue.setBounds(100, 100, 674, 485);
		frmPoeQueue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmPoeQueue.setLocation(dim.width/2-frmPoeQueue.getSize().width/2, dim.height/2-frmPoeQueue.getSize().height/2);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		frmPoeQueue.getContentPane().setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		JLabel lblTypeDescriptionDate = new JLabel("Type, Description, Date, # of members, League, ID:Leader", JLabel.LEFT);
		lblTypeDescriptionDate.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		scrollPane.setColumnHeaderView(lblTypeDescriptionDate);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 9;
		gbc_scrollPane.gridheight = 11;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frmPoeQueue.getContentPane().add(scrollPane, gbc_scrollPane);
		
		qList = new JList();
		qList.setModel(qModel);
		
		JLabel lblLeader = new JLabel("Leader: ");
		
		JLabel lblType = new JLabel("Type: ");
		
		JLabel lblLeague = new JLabel("League: ");
		
		JLabel lblMembers = new JLabel("Members: ");
		
		JLabel lblDescription = new JLabel("Description: ");
		
		qList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(qList.getSelectedValue() != null) {
					selectedGroup = qList.getSelectedValue().toString();
					
					StringTokenizer token = new StringTokenizer(selectedGroup, ",");
					String type = token.nextToken();
					String title = token.nextToken();
					String date = token.nextToken();
					String count = token.nextToken();
					String league = token.nextToken();
					String leaderinfo = token.nextToken();
					String leader = null;
					StringTokenizer leaderST = new StringTokenizer(leaderinfo, ":");
					leaderID = leaderST.nextToken();
					if(leaderST.hasMoreTokens()) {
					leader = leaderST.nextToken();
					}
					
					lblLeader.setText("Leader: " + leader);
					lblType.setText("Type: " + type);
					lblLeague.setText("League: " + league);
					lblMembers.setText("Members: " + count);
					textField.setText(title);
					

				}
			}
		});
		
		Update(rs);
		scrollPane.setViewportView(qList);
		qList.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), new Color(0, 0, 0), Color.LIGHT_GRAY, Color.GRAY));
		
		JPanel InfoPane = new JPanel();
		InfoPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "Group Information", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		GridBagConstraints gbc_InfoPane = new GridBagConstraints();
		InfoPane.setPreferredSize(new Dimension(25,100));
		gbc_InfoPane.gridheight = 6;
		gbc_InfoPane.gridwidth = 3;
		gbc_InfoPane.insets = new Insets(0, 0, 5, 0);
		gbc_InfoPane.fill = GridBagConstraints.BOTH;
		gbc_InfoPane.gridx = 9;
		gbc_InfoPane.gridy = 0;
		frmPoeQueue.getContentPane().add(InfoPane, gbc_InfoPane);
	
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		
		JButton btnJoin = new JButton("Join");
		GroupLayout gl_InfoPane = new GroupLayout(InfoPane);
		gl_InfoPane.setHorizontalGroup(
			gl_InfoPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_InfoPane.createSequentialGroup()
					.addGroup(gl_InfoPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLeader)
						.addComponent(lblType)
						.addComponent(lblLeague)
						.addComponent(lblMembers)
						.addComponent(lblDescription)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
						.addComponent(btnJoin))
					.addContainerGap())
		);
		gl_InfoPane.setVerticalGroup(
			gl_InfoPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_InfoPane.createSequentialGroup()
					.addComponent(lblLeader)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblType)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLeague)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMembers)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDescription)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
					.addComponent(btnJoin)
					.addContainerGap())
		);
		InfoPane.setLayout(gl_InfoPane);
		
		JMenuBar menuBar = new JMenuBar();
		frmPoeQueue.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmGroupinfo = new JMenuItem("GroupInfo");
		mnFile.add(mntmGroupinfo);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);

		menuUpdate = new JMenu("");
		menuUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "SELECT * FROM groups";
				
				try {
					PreparedStatement st = (PreparedStatement) conn.prepareStatement(query);
					ResultSet _rs = st.executeQuery();
					Update(_rs);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		menuUpdate.setIcon(new ImageIcon("./resources/refresh.png"));
		menuUpdate.setToolTipText("Refresh list");
		menuBar.add(menuUpdate);
		
		menuJoin = new JMenu("");
		menuJoin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(selectedGroup != null) {
				JoinGroup();
				}
			}
		});
		menuJoin.setIcon(new ImageIcon("./resources/join.png"));
		menuJoin.setToolTipText("Join Group");
		menuBar.add(menuJoin);
		
		menuLeave = new JMenu("");
		menuLeave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(currentGroup == true) {
				LeaveGroup();
				}
			}
		});
		menuLeave.setIcon(new ImageIcon("./resources/leave.png"));
		ImageFilter filter = new GrayFilter(true, 50);  
		ImageProducer producer = new FilteredImageSource(((ImageIcon) menuLeave.getIcon()).getImage().getSource(), filter);  
		Image grayIcon = Toolkit.getDefaultToolkit().createImage(producer); 
		menuLeave.setIcon(new ImageIcon(grayIcon));
		menuLeave.setToolTipText("Leave Group");
		menuBar.add(menuLeave);
		
		JMenu menuNewGroup = new JMenu("");
		menuNewGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				NewGroupWindow.createWindow();
			}
		});
		menuNewGroup.setIcon(new ImageIcon("./resources/new.png"));
		menuNewGroup.setToolTipText("Create new group");
		menuBar.add(menuNewGroup);
		
		JMenu menuSettings = new JMenu("");
		menuSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(username == null) {
					SettingsWindow.createWindow("Username");
				}
				if(username != null) {
					SettingsWindow.createWindow(username);
				}
			}
		});
		menuSettings.setIcon(new ImageIcon("./resources/settings.png"));
		menuBar.add(menuSettings);
		
		JMenu mnSortBy = new JMenu("Sort By...");
		menuBar.add(mnSortBy);
		
		JMenu mnType = new JMenu("Type");
		mnSortBy.add(mnType);
		
		JMenuItem mntmAny = new JMenuItem("Any");
		mntmAny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sorting by " + mntmAny.getText());
				SortByType(mntmAny.getText());
				javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath();
			}
		});
		mnType.add(mntmAny);
		
		JMenuItem mntmPvp = new JMenuItem("PvP");
		mntmPvp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sorting by " + mntmPvp.getText());
				SortByType(mntmPvp.getText());
				javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath();
			}
		});
		mnType.add(mntmPvp);
		
		JMenuItem mntmMaps = new JMenuItem("Maps");
		mntmMaps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sorting by " + mntmMaps.getText());
				SortByType(mntmMaps.getText());
				javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath();
			}
		});
		mnType.add(mntmMaps);
		
		JMenuItem mntmLeveling = new JMenuItem("Leveling");
		mntmLeveling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sorting by " + mntmLeveling.getText());
				SortByType(mntmLeveling.getText());
				javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath();
			}
		});
		mnType.add(mntmLeveling);
		
		JMenuItem mntmLab = new JMenuItem("Lab");
		mntmLab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sorting by " + mntmLab.getText());
				SortByType(mntmLab.getText());
				javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath();
			}
		});
		mnType.add(mntmLab);
		
		JMenuItem mntmMisc = new JMenuItem("Misc");
		mntmMisc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sorting by " + mntmMisc.getText());
				SortByType(mntmMisc.getText());
				javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath();
			}
		});
		mnType.add(mntmMisc);
		
		JMenu mnSearch = new JMenu("Search");
		mnSortBy.add(mnSearch);
		
		searchField = new JTextField();
		searchField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				searchField.selectAll();
			}
		});
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				  if (arg0.getKeyCode()==KeyEvent.VK_ENTER){
					  System.out.println("Sorting by input " + searchField.getText());
					  SortByText(searchField.getText());
					  javax.swing.MenuSelectionManager.defaultManager().clearSelectedPath();
				    }
			}
		});
		searchField.setText("Text");
		mnSearch.add(searchField);
		searchField.setColumns(10);
		
		JMenu mnLeague = new JMenu("League");
		mnSortBy.add(mnLeague);
		
		JMenuItem mntmStandard = new JMenuItem("Standard");
		mntmStandard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Sorting by " + mntmStandard.getText());
				SortByLeague(mntmStandard.getText());
			}
		});
		mnLeague.add(mntmStandard);
		
		JMenuItem mntmHardcore = new JMenuItem("Hardcore");
		mntmHardcore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Sorting by " + mntmHardcore.getText());
				SortByLeague(mntmHardcore.getText());
			}
		});
		mnLeague.add(mntmHardcore);
		
		JMenuItem mntmChallengeStandard = new JMenuItem("Challenge Standard");
		mntmChallengeStandard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sorting by " + mntmChallengeStandard.getText());
				SortByLeague(mntmChallengeStandard.getText());
			}
		});
		mnLeague.add(mntmChallengeStandard);
		
		JMenuItem mntmChallengeHardcore = new JMenuItem("Challenge Hardcore");
		mntmChallengeHardcore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Sorting by " + mntmChallengeHardcore.getText());
				SortByLeague(mntmChallengeHardcore.getText());
			}
		});
		mnLeague.add(mntmChallengeHardcore);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		mnHelp.add(mntmAbout);
	}

	public static void Update(ResultSet _rs) {
		Thread refresh = new Thread(new Runnable() {
			public void run() {	
				java.sql.ResultSetMetaData rsmd;
				groupList.clear();
				qList.clearSelection();
				qModel.clear();
				try {
					rsmd = _rs.getMetaData();
					 int columnsNumber = rsmd.getColumnCount();
					    while (_rs.next()) {
					    	String tempRow = "";
					        for (int i = 1; i <= columnsNumber; i++) {
					            String columnValue = _rs.getString(i);
					           
					            tempRow += (columnValue + ", ");
					            
					        }
					        tempRow = tempRow.substring(0,tempRow.length()-2);
					        groupList.add(tempRow);
					    }
						
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				for(int i = 0; i < groupList.size(); i++) {
					qModel.addElement(groupList.get(i));
				}
				qList.repaint();
				qList.revalidate();
			}
		});
		refresh.start();
	}
	
	public void SearchList(String info) {
		
	}
	
	public static void JoinGroup() {
		if(currentGroup == false && username != null) {
			selectedGroup = qList.getSelectedValue().toString();
			currentGroup = true;
			ImageFilter filter = new GrayFilter(true, 50);  
			ImageProducer producer = new FilteredImageSource(((ImageIcon) menuJoin.getIcon()).getImage().getSource(), filter);  
			Image grayIcon = Toolkit.getDefaultToolkit().createImage(producer); 
			menuJoin.setIcon(new ImageIcon(grayIcon));
			menuJoin.repaint();
			menuLeave.setIcon(new ImageIcon("./resources/leave.png"));
			
			CurrentGroupWindow.createWindow(selectedGroup);
			
			StringTokenizer token = new StringTokenizer(selectedGroup, ",");
			String type = token.nextToken();
			String title = token.nextToken();
			String date = token.nextToken();
			String count = token.nextToken();
			String league = token.nextToken();
			String leaderinfo = token.nextToken();
			StringTokenizer leaderST = new StringTokenizer(leaderinfo, ":");
			leaderID = leaderST.nextToken();
			if(leaderST.hasMoreTokens()) {
			String leader = leaderST.nextToken();
			}
			
			String query = "UPDATE groups SET count = count + 1 WHERE leader = '" + leaderinfo + "' AND count < 6";
			
			try {
				PreparedStatement st = (PreparedStatement) conn.prepareStatement(query);
				st.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		} else if(username == null) {
			JOptionPane.showMessageDialog(frmPoeQueue, "Please enter username in settings.");
		}
	}
	
	public static void LeaveGroup() {
		if(currentGroup == true) {

			CurrentGroupWindow.closeFrame();
			
			menuJoin.setIcon(new ImageIcon("./resources/join.png"));
			menuJoin.repaint();
			
			ImageFilter filter = new GrayFilter(true, 50);  
			ImageProducer producer = new FilteredImageSource(((ImageIcon) menuLeave.getIcon()).getImage().getSource(), filter);  
			Image grayIcon = Toolkit.getDefaultToolkit().createImage(producer); 
			menuLeave.setIcon(new ImageIcon(grayIcon));
			menuLeave.repaint();
			
			
			if(isLeader == true) {
				
			String query = "DELETE FROM groups WHERE leader = '"+ sessionID + ":" + username + "'";
			try {
				PreparedStatement st = (PreparedStatement) conn.prepareStatement(query);
				st.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			isLeader = false;
			
			} else if(isLeader == false){
				
			String query = "UPDATE groups SET count = count - 1 WHERE leader = '" + CurrentGroupWindow.leaderinfo + "' AND count > 0";
			
			try {
				PreparedStatement st = (PreparedStatement) conn.prepareStatement(query);
				st.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			}
			
		}
		currentGroup = false;
	}
	
	public static void SaveInfo(String _username) {
		try {
			PrintWriter out = new PrintWriter("./resources/data.txt");
			out.println(_username);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void SortByText(String input) {
		Thread sort = new Thread(){
		    public void run(){
		    String query = "SELECT type, title, date, count, league, leader FROM groups WHERE title LIKE '%" + input + "%'";
				
				try {
					PreparedStatement st = (PreparedStatement) conn.prepareStatement(query);
					ResultSet result = st.executeQuery();
					Update(result);
				} catch (SQLException e) {
					e.printStackTrace();
				}		    			    	
		    }
		  };
		  sort.start();
	}
	
	public void SortByType(String input) {
		Thread sort = new Thread(){
		    public void run(){
		    String query = "";
		    if(input.equals("Any")) {
			query = "SELECT type, title, date, count, league, leader FROM groups";
		    } else if(!(input.equals("Any"))) {
		    query = "SELECT type, title, date, count, league, leader FROM groups WHERE type = '"+input+"'";
		    }
				try {
					PreparedStatement st = (PreparedStatement) conn.prepareStatement(query);
					ResultSet result = st.executeQuery();
					Update(result);
				} catch (SQLException e) {
					e.printStackTrace();
				}		    			    	
		    }
		  };
		  sort.start();
	}
	
	public void SortByLeague(String input) {
		Thread sort = new Thread(){
		    public void run(){

		    String query = "SELECT type, title, date, count, league, leader FROM groups WHERE league = '"+input+"'";
		    
				try {
					PreparedStatement st = (PreparedStatement) conn.prepareStatement(query);
					ResultSet result = st.executeQuery();
					Update(result);
				} catch (SQLException e) {
					e.printStackTrace();
				}		    			    	
		    }
		  };
		  sort.start();
	}
}
