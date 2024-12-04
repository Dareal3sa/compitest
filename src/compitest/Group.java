package compitest;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class Group extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Group frame = new Group();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Group() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 887, 620);
    	setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 235, 205));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Group");
		lblNewLabel.setForeground(new Color(160, 82, 45));
		lblNewLabel.setBackground(new Color(255, 235, 205));
		lblNewLabel.setFont(new Font("Georgia", Font.BOLD, 30));
		lblNewLabel.setBounds(85, 69, 263, 48);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Manampan, Daryl Trisha");
		lblNewLabel_1.setForeground(new Color(160, 82, 45));
		lblNewLabel_1.setFont(new Font("Georgia", Font.BOLD, 30));
		lblNewLabel_1.setBounds(78, 183, 509, 67);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Macabales, Francine Ella G.");
		lblNewLabel_1_1.setForeground(new Color(160, 82, 45));
		lblNewLabel_1_1.setFont(new Font("Georgia", Font.BOLD, 30));
		lblNewLabel_1_1.setBounds(78, 278, 550, 67);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Solitario, Vina Marie");
		lblNewLabel_1_2.setForeground(new Color(160, 82, 45));
		lblNewLabel_1_2.setFont(new Font("Georgia", Font.BOLD, 30));
		lblNewLabel_1_2.setBounds(78, 371, 539, 67);
		contentPane.add(lblNewLabel_1_2);
	}
}
