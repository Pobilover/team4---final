package main.suddenQuestion;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import guiDesign.Methods;
import main.MainFrame;
import java.awt.Font;
import javax.swing.SwingConstants;

public class WorkBookDialog extends JDialog {
	
	private MainFrame mainFrame;
	
	public WorkBookDialog (int num, MainFrame mainFrame, int x, int y) {
		
		this.mainFrame = mainFrame;
		
		getContentPane().setLayout(null);
		setUndecorated(true);
		setModal(true);
		setBounds(x + 7, y + 30, 1185, 762);
		setBackground(new Color(0, 0, 0, 100));
		
		JPanel pnlU = new JPanel();
		pnlU.setBounds(217, 231, 750, 300);
		pnlU.setBackground(new Color(0, 0, 0, 0));
		getContentPane().add(pnlU);
		
		JLabel lbl = new JLabel();
		lbl.setIcon(Methods.convertToIcon(getClass(), "images/workBook/q" + String.valueOf(num) + ".png"));
		pnlU.add(lbl);
		
		JPanel pnlD = new JPanel();
		pnlD.setLayout(null);
		pnlD.setBounds(217, 531, 750, 100);
		pnlD.setBackground(new Color(0, 0, 0, 0));
		getContentPane().add(pnlD);
		
		JTextField inputTxt = new JTextField();
		inputTxt.setFont(new Font("휴먼편지체", Font.BOLD, 22));
		inputTxt.setHorizontalAlignment(SwingConstants.CENTER);
		inputTxt.setBounds(339, 51, 70, 40);
		pnlD.add(inputTxt);
		
		JButton btn = new JButton("확인");
		btn.setBounds(421, 50, 70, 40);
		pnlD.add(btn);
		
		JLabel lbl_ = new JLabel("답을 입력하세요. ");
		lbl_.setForeground(Color.WHITE);
		lbl_.setFont(new Font("휴먼편지체", Font.BOLD, 22));
		lbl_.setBounds(503, 50, 235, 40);
		pnlD.add(lbl_);

		int[] list = {1, 5, 4, 3, 3, 3, 4, 3, 2, 2, 1, 4, 4, 3, 3, 3};
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (list[num - 1] == Integer.valueOf(inputTxt.getText())) {
						lbl_.setText("정답입니다. ");
					} else {
						lbl_.setText("오답입니다. ");
					}
					
					revalidate();
					repaint();
					btn.setEnabled(false);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btn_ = new JButton("닫기");
		btn_.setBounds(668, 50, 70, 40);
		pnlD.add(btn_);
		
		btn_.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setTimeGo(true);
				dispose();
			}
		});
	}
	public void showGUI() {
		mainFrame.setTimeGo(false);
		setVisible(true);
	}
}
