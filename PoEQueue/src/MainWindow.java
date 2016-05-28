import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.mysql.jdbc.Connection;
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

import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;


public class MainWindow {

	public static JFrame frmPoeQueue;
	public ArrayList<Group> groups = new ArrayList<Group>();
	public JList qList;
	public static Group currentGroup = null;
	
	private String setType;
	private String searchText;
	
	public JMenu menuUpdate;
	public static JMenu menuJoin;
	public static JMenu menuLeave;
	
	public Group selectedGroup = new Group();
	
	public static String username = "test";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					String url = "jdbc:mysql://localhost:3306/qlist?autoReconnect=true&useSSL=false";
					String user = "root";
					String password = "foobar";
					Connection conn = (Connection) DriverManager.getConnection(url, user, password);
					
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
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.gridheight = 11;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frmPoeQueue.getContentPane().add(scrollPane, gbc_scrollPane);
		
		qList = new JList();
		scrollPane.setViewportView(qList);
		qList.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), new Color(0, 0, 0), Color.LIGHT_GRAY, Color.GRAY));
		
		JPanel InfoPane = new JPanel();
		InfoPane.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 3, true), "Group Information", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		GridBagConstraints gbc_InfoPane = new GridBagConstraints();
		gbc_InfoPane.gridwidth = 2;
		gbc_InfoPane.gridheight = 11;
		gbc_InfoPane.insets = new Insets(0, 0, 5, 0);
		gbc_InfoPane.fill = GridBagConstraints.BOTH;
		gbc_InfoPane.gridx = 8;
		gbc_InfoPane.gridy = 0;
		frmPoeQueue.getContentPane().add(InfoPane, gbc_InfoPane);
		GridBagLayout gbl_InfoPane = new GridBagLayout();
		gbl_InfoPane.columnWidths = new int[]{0, 0};
		gbl_InfoPane.rowHeights = new int[]{0, 0, 0};
		gbl_InfoPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_InfoPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		InfoPane.setLayout(gbl_InfoPane);
		
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
				Update();
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
				JoinGroup(selectedGroup);
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
				if(currentGroup != null) {
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
		
		JMenuItem mntmType = new JMenuItem("Type");
		mnSortBy.add(mntmType);
		
		JMenuItem mntmSearch = new JMenuItem("Search");
		mnSortBy.add(mntmSearch);
	}

	public void Update() {
		Thread refresh = new Thread(new Runnable() {
			public void run() {
				
			}
		});
		refresh.start();
	}
	
	public void SearchList(String info) {
		
	}
	
	public void JoinGroup(Group selectedGroup) {
		if(currentGroup == null && username != null) {
			ImageFilter filter = new GrayFilter(true, 50);  
			ImageProducer producer = new FilteredImageSource(((ImageIcon) menuJoin.getIcon()).getImage().getSource(), filter);  
			Image grayIcon = Toolkit.getDefaultToolkit().createImage(producer); 
			menuJoin.setIcon(new ImageIcon(grayIcon));
			menuJoin.repaint();
			
			menuLeave.setIcon(new ImageIcon("./resources/leave.png"));
			
			CurrentGroupWindow.createWindow(selectedGroup);
			
			currentGroup = selectedGroup;
		}
	}
	
	public static void LeaveGroup() {
		if(currentGroup != null) {
			currentGroup = null;
			
			CurrentGroupWindow.closeFrame();
			
			menuJoin.setIcon(new ImageIcon("./resources/join.png"));
			menuJoin.repaint();
			
			ImageFilter filter = new GrayFilter(true, 50);  
			ImageProducer producer = new FilteredImageSource(((ImageIcon) menuLeave.getIcon()).getImage().getSource(), filter);  
			Image grayIcon = Toolkit.getDefaultToolkit().createImage(producer); 
			menuLeave.setIcon(new ImageIcon(grayIcon));
			menuLeave.repaint();
		}
	}
	
	public static void SaveInfo(String username) {
		
	}
	
}
