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


public class CurrentGroupWindow {

	private static JFrame frmCurrentGroup;
	
	public static Group group;
	
	private JLabel lblTime;
	
	private String selectedMember = null;
	
	private JList memlist;
	
	
	/**
	 * Launch the application.
	 */
	public static void createWindow(Group _group) {
		group = _group;
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
		frmCurrentGroup.setBounds(100, 100, 220, 291);
		frmCurrentGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCurrentGroup.setUndecorated(true);

		frmCurrentGroup.setLocation(MainWindow.frmPoeQueue.getX()+MainWindow.frmPoeQueue.getWidth(),
									MainWindow.frmPoeQueue.getY());
		
		lblTime = new JLabel("Time Spent in Group: ");
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblMembers = new JLabel("Members");
		
		JLabel lblGroupId = new JLabel("Group ID");
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		
		JButton btnPmMember = new JButton("PM Member");
		btnPmMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CopyWindow.createWindow("@" + "membernamehere" + " hey heres the message");
			}
		});
		
		JButton btnPmLeader = new JButton("PM Leader");
		btnPmLeader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CopyWindow.createWindow("@" + "leaderhere" + " hey heres the message");
			}
		});
		
		JButton btnInviteMember = new JButton("Invite Member");
		btnInviteMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CopyWindow.createWindow("/invite" + " " + "membernamehere");
			}
		});
		
		JLabel lblLeader = new JLabel("Leader: ");
		
		JButton btnLeave = new JButton("Leave");
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.LeaveGroup();
			}
		});
		
		JButton btnKickMember = new JButton("Kick Member");
		GroupLayout groupLayout = new GroupLayout(frmCurrentGroup.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTime)
						.addComponent(lblGroupId)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblMembers))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
						.addComponent(lblLeader)
						.addComponent(btnLeave)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(btnPmMember)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnPmLeader, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnInviteMember)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnKickMember)))
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
					.addGap(43)
					.addComponent(lblMembers)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnPmLeader)
						.addComponent(btnPmMember))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnInviteMember)
						.addComponent(btnKickMember))
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(btnLeave))
		);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{100, 1, 0};
		gbl_panel.rowHeights = new int[]{1, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		memlist = new JList();
		drawMemList();
		
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 0;
		panel.add(memlist, gbc_list);
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
