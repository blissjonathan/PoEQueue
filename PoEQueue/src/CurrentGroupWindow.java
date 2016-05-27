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


public class CurrentGroupWindow {

	private static JFrame frmCurrentGroup;
	
	public static Group group;
	
	private JLabel lblTime;
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
		frmCurrentGroup.setResizable(false);
		frmCurrentGroup.setTitle("Current Group");
		frmCurrentGroup.setBounds(100, 100, 220, 410);
		frmCurrentGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCurrentGroup.setUndecorated(true);

		frmCurrentGroup.setLocation(MainWindow.frmPoeQueue.getX()+MainWindow.frmPoeQueue.getWidth(),
									MainWindow.frmPoeQueue.getY());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmCurrentGroup.getContentPane().setLayout(gridBagLayout);
		
		lblTime = new JLabel("Time Spent in Group: ");
		lblTime.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblTimeSpentIn = new GridBagConstraints();
		gbc_lblTimeSpentIn.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeSpentIn.gridx = 0;
		gbc_lblTimeSpentIn.gridy = 0;
		frmCurrentGroup.getContentPane().add(lblTime, gbc_lblTimeSpentIn);
		
//		Timer SimpleTimer = new Timer(1000, new ActionListener(){
//		    @Override
//		    public void actionPerformed(ActionEvent e) {
//		        lblTime.setText(SimpleTime.format(new Date()));
//		    }
//		});
//		SimpleTimer.start();
		
		JLabel lblMembers = new JLabel("Members:");
		lblMembers.setHorizontalAlignment(SwingConstants.LEFT);
		lblMembers.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblMembers = new GridBagConstraints();
		gbc_lblMembers.insets = new Insets(0, 0, 0, 5);
		gbc_lblMembers.gridx = 0;
		gbc_lblMembers.gridy = 2;
		frmCurrentGroup.getContentPane().add(lblMembers, gbc_lblMembers);


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
	
	public static void closeFrame() {
		frmCurrentGroup.dispose();
	}

}
