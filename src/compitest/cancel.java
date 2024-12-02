package compitest;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class cancel extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			cancel dialog = new cancel();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public cancel() {
		setBounds(100, 100, 800, 200);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 235, 205));
		contentPanel.setForeground(new Color(255, 235, 205));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Are you sure you want to Exit?");
			lblNewLabel.setForeground(new Color(160, 82, 45));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
			lblNewLabel.setBounds(158, 46, 467, 37);
			contentPanel.add(lblNewLabel);
		}
		{
			JButton okButton = new JButton("OK");
			okButton.setForeground(new Color(160, 82, 45));
			okButton.setBackground(new Color(240, 248, 255));
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			okButton.setBounds(310, 94, 80, 25);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setForeground(new Color(160, 82, 45));
			cancelButton.setBackground(new Color(240, 248, 255));
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Main startApp = new Main();
				      startApp.setVisible(true);
				      startApp.setLocationRelativeTo(null);
				      dispose();
				}
			});
			cancelButton.setBounds(400, 94, 80, 25);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Confirm ");
			lblNewLabel_2.setForeground(new Color(160, 82, 45));
			lblNewLabel_2.setBackground(new Color(255, 228, 196));
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setFont(new Font("Arial Black", Font.PLAIN, 20));
			lblNewLabel_2.setBounds(158, 11, 467, 37);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(255, 228, 196));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JLabel lblNewLabel_1 = new JLabel("\r\n3-BSCS2 FREE");
				buttonPane.add(lblNewLabel_1);
				lblNewLabel_1.setFont(new Font("Arial", Font.ITALIC, 13));
			}
		}
	}

}
