import java.awt.EventQueue;

import javax.swing.JFrame;


public class NewGroupWindow {

	private JFrame frmNewGroup;

	/**
	 * Launch the application.
	 */
	public static void createWindow() {
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
		frmNewGroup.setBounds(100, 100, 560, 423);
		frmNewGroup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

}
