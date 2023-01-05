package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import custom_panel.ProjectPanel;

public class ProjectDialog extends JDialog {

	private JPanel contentPane;

	public ProjectDialog(int x, int y) {
		
		setUndecorated(true);
		setModal(true);
		setBounds(x + 7, y + 30, 1185, 762);
		setBackground(new Color(0, 0, 0, 100));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(false);
		contentPane.setBackground(new Color(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel rankTextPanel = new JLabel("프로젝트");
		rankTextPanel.setBackground(Color.WHITE);
		rankTextPanel.setHorizontalAlignment(SwingConstants.CENTER);
		rankTextPanel.setFont(new Font("HY목각파임B", Font.BOLD, 40));
		rankTextPanel.setBounds(12, 10, 1161, 50);
		contentPane.add(rankTextPanel);

		JPanel ProjectsPanel = new JPanel();
		ProjectsPanel.setOpaque(false);
		ProjectsPanel.setBackground(new Color(0, true));
		ProjectsPanel.setLayout(new BoxLayout(ProjectsPanel, BoxLayout.Y_AXIS));

		ProjectPanel[] pj = new ProjectPanel[17];
		List<String> projectList = new ArrayList<>();
		projectList.add("자바 기초 및 프로그래밍 기초");
		projectList.add("선택, 반복, 배열");
		projectList.add("객체 지향 소개 및 클래스, 객체, 메소드");
		projectList.add("클래스와 메소드 심층 연구");
		projectList.add("상속, 그래픽 사용자 인터페이스");
		projectList.add("추상 클래스, 인터페이스, 패키지");
		projectList.add("자바 API 패키지, 예외처리, 모듈");
		projectList.add("중간 평가");
		projectList.add("자바 GUI 기초, 이벤트 처리");
		projectList.add("스윙 컴포넌트, 자바 그래픽");
		projectList.add("제네릭과 컬렉션");
		projectList.add("함수형 프로그래밍, 스트림, 람다식");
		projectList.add("파일 입출력");
		projectList.add("멀티 스레딩");
		projectList.add("네트워크 프로그래밍");
		projectList.add("데이터베이스 프로그래밍");
		projectList.add("기말 평가");
		
		for (int i = 0; i < pj.length; i++) {
			ProjectPanel project = new ProjectPanel();
			project.getRankLabel().setText(String.valueOf((i + 1) + "주차"));
			project.getProjectName().setText(projectList.get(i));
			pj[i] = project;
			ProjectsPanel.add(pj[i]);
			ProjectsPanel.add(Box.createVerticalStrut(25));
		}

		JScrollPane scrollPane = new JScrollPane(ProjectsPanel);
		scrollPane.setOpaque(false);
		scrollPane.setBackground(new Color(0, true));
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds(312, 72, 560, 619);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane);

		ClassLoader classLoader = getClass().getClassLoader();

		URL URLCloseButtonImage = classLoader.getResource("btn_image/closeBtnImage.png");
		ImageIcon CloseButtonIcon = new ImageIcon(URLCloseButtonImage);

		URL URLCloseButtonPushImage = classLoader.getResource("btn_image/closeBtnPushImage.png");
		ImageIcon CloseButtonPushIcon = new ImageIcon(URLCloseButtonPushImage);

		JButton closeButton = new JButton();
		closeButton.setBounds(488, 701, 200, 50);
		closeButton.setIcon(CloseButtonIcon);
		closeButton.setPressedIcon(CloseButtonPushIcon);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(closeButton);

	}

	public void showGUI() {
		setVisible(true);
	}
}
