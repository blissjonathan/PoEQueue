import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;







import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.StringTokenizer;

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


public class CurrentGroupWindow {

	private static JFrame frmCurrentGroup;
	
	public static String group;
	
	private JLabel lblTime;
	
	private String selectedMember = null;
	private JTextField textField;
	
	private static String count;
	private static String id;
	private static String leader;
	private static String title;
	private static String league;
	private static String type;
	private static String date;
	private static String session;
	/**
	 * Launch the application.
	 */
	public static void createWindow(String _group) {
		group = _group;
		
		StringTokenizer st = new StringTokenizer(group, ",");
		type = st.nextToken();
		title = st.nextToken();
		date = st.nextToken();
		count = st.nextToken();
		StringTokenizer leaderST = new StringTokenizer(st.nextToken(), ":");
		session = leaderST.nextToken();
		if(leaderST.hasMoreTokens()) {
		leader = leaderST.nextToken();
		}
		
		
		
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
		frmCurrentGroup.setAutoRequestFocus(false);
		frmCurrentGroup.setResizable(false);
		frmCurrentGroup.setTitle("Current Group");
		frmCurrentGroup.setBounds(100, 100, 220, 325);
		frmCurrentGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCurrentGroup.setUndecorated(true);

		frmCurrentGroup.setLocation(MainWindow.frmPoeQueue.getX()+MainWindow.frmPoeQueue.getWidth(),
									MainWindow.frmPoeQueue.getY());
		
		lblTime = new JLabel("Time Spent in Group: ");
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblMembers = new JLabel("Members: " + count);
		
		JLabel lblGroupId = new JLabel("Group ID: " + id);
		
		JButton btnPmLeader = new JButton("PM Leader");
		btnPmLeader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CopyWindow.createWindow("@" + leader + " Hello, I have joined your group on PoE queue. I'm ready for my invite.");
			}
		});
		
		JLabel lblLeader = new JLabel("Leader: " + leader);
		
		JButton btnLeave = new JButton("Leave");
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.LeaveGroup();
			}
		});
		
		JButton btnKickMember = new JButton("Remove Member");
		
		JButton btnAddMember = new JButton("Add Member");
		btnAddMember.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		JSeparator separator = new JSeparator();
		
		JLabel lblLeaderControls = new JLabel("Leader Controls");
		lblLeaderControls.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblDescription = new JLabel("Description:");
		
		textField = new JTextField();
		textField.setText(title);
		textField.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		
		if(MainWindow.isLeader == false) {
			btnAddMember.setEnabled(false);
			btnKickMember.setEnabled(false);
			textField.setEditable(false);
			btnUpdate.setEnabled(false);
		}
		
		JSeparator separator_1 = new JSeparator();
		GroupLayout groupLayout = new GroupLayout(frmCurrentGroup.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblLeaderControls)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDescription)
							.addPreferredGap(ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
							.addComponent(btnUpdate)))
					.addGap(29))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnLeave)
					.addContainerGap(171, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblTime, Alignment.LEADING)
						.addComponent(lblGroupId, Alignment.LEADING)
						.addComponent(lblLeader, Alignment.LEADING)
						.addComponent(lblMembers, Alignment.LEADING)
						.addComponent(btnPmLeader, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(btnAddMember, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(btnKickMember)))
					.addGap(26))
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblTime)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblGroupId)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLeader)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMembers)
					.addGap(2)
					.addComponent(btnPmLeader)
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
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddMember)
						.addComponent(btnKickMember, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(16)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnLeave)
					.addContainerGap())
		);
		drawMemList();
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
		
	}
	
	public void drawMemList() {
		
	}
	
	public static void closeFrame() {
		frmCurrentGroup.dispose();
	}
}
