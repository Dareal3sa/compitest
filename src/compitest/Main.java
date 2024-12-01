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
			setBounds(100, 100, 850, 500);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(0, 102, 102));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel appName = new JLabel("Compiler");
			appName.setForeground(new Color(192, 192, 192));
			appName.setFont(new Font("Impact", Font.ITALIC, 50));
			appName.setHorizontalAlignment(SwingConstants.CENTER);
			appName.setBounds(152, 102, 530, 110);
			contentPane.add(appName);
			
			JButton btnProceed = new JButton("Click To Continue");
			btnProceed.setBackground(new Color(0, 0, 0));
			btnProceed.setForeground(new Color(255, 255, 255));
	        btnProceed.setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
	        btnProceed.setBorder(BorderFactory.createEtchedBorder());
			btnProceed.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MainDashboard home = new MainDashboard();
				      home.setVisible(true);
				      home.setLocationRelativeTo(null);
				      dispose();
				}
			
			});
			btnProceed.setFont(new Font("Arial", Font.BOLD, 15));
			btnProceed.setBounds(308, 247, 218, 50);
			contentPane.add(btnProceed);
			
			JButton btnNewButton = new JButton("Exit");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cancel exApp = new cancel();
				      exApp.setVisible(true);
				      exApp.setLocationRelativeTo(null);
				      dispose();
				}
			});
			btnNewButton.setFont(new Font("Arial", Font.BOLD, 13));
			btnNewButton.setForeground(new Color(255, 255, 255));
			btnNewButton.setBackground(new Color(0, 0, 0));
	        btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
	        btnNewButton.setBorder(BorderFactory.createEtchedBorder());
			btnNewButton.setBounds(308, 320, 218, 23);
			contentPane.add(btnNewButton);
			
		  }

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}