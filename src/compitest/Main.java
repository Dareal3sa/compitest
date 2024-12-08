package compitest;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.Color;

public class Main extends JFrame implements ActionListener {

		private static final long serialVersionUID = 1L;
		JPanel contentPane;

		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Main frame = new Main();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		public Main() {
			setBackground(new Color(0, 0, 0));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 1280, 960);
			setResizable(false);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(255, 235, 205));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel appName = new JLabel("Compitest");
			appName.setForeground(new Color(0, 0, 0));
			appName.setFont(new Font("Impact", Font.ITALIC, 98));
			appName.setHorizontalAlignment(SwingConstants.CENTER);
			appName.setBounds(141, 81, 981, 319);
			contentPane.add(appName);
			
			JButton btnProceed = new JButton("Click To Continue");
			btnProceed.setBackground(new Color(0, 0, 0));
			btnProceed.setForeground(new Color(255, 255, 255));
	        btnProceed.setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
	        btnProceed.setBorder(BorderFactory.createEtchedBorder());
			btnProceed.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Dashboard home = new Dashboard();
				      home.setVisible(true);
				      home.setLocationRelativeTo(null);
				      dispose();
				}
			
			});
			btnProceed.setFont(new Font("Arial", Font.BOLD, 15));
			btnProceed.setBounds(366, 434, 531, 82);
			contentPane.add(btnProceed);
			
			JButton btnNewButton = new JButton("Group");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Group info = new Group();
				      info.setVisible(true);
				      info.setLocationRelativeTo(null);
				      dispose();
				}
			});
			btnNewButton.setFont(new Font("Arial", Font.BOLD, 13));
			btnNewButton.setForeground(new Color(255, 255, 255));
			btnNewButton.setBackground(new Color(0, 0, 0));
	        btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
	        btnNewButton.setBorder(BorderFactory.createEtchedBorder());
			btnNewButton.setBounds(366, 576, 531, 46);
			contentPane.add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("Exit");
			btnNewButton_1.setForeground(Color.WHITE);
			btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 13));
			btnNewButton_1.setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
			btnNewButton_1.setBackground(Color.BLACK);
			btnNewButton_1.setBounds(366, 676, 531, 46);
			contentPane.add(btnNewButton_1);
			
		  }

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}