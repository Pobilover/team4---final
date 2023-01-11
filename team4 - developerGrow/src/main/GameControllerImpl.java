package main;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import database.controllDB.InsertDB;
import database.controllDB.SelectDB;
import database.controllDB.UpdateDB;
import database.dblist.Rank;
import database.dblist.UserInfo;
import main.project.ProjectEventImpl;
import main.suddenQuestion.WorkBookDialog;

public class GameControllerImpl implements GameController {

	private MainFrame mainFrame;
	
	public GameControllerImpl(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	private int day;
	private int minutes = 0;

	@Override
	public void timeController() {
		currentTime = new Timer();
		currentTime.scheduleAtFixedRate(timerTask, 0, 1);
	}

	private TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			if (mainFrame.isTimeGo()) {
				if (minutes == 1440) {
					minutes = 0;
					updateDate();
					updateTime(minutes);
				} else {
					minutes++;
					updateTime(minutes);
				}
				
				if (minutes == randomMin1) {
					showrandomQustion();
				} else if (minutes == randomMin2) {
					showrandomQustion();
				}
	
				if (((minutes % 60) % 10) == 0) {
					saveUserInfoData();
					saveUserProjcet();
					saveRanking();
				}
			}
			try {
				int gameSpeed = mainFrame.getGameSpeed();
				Thread.sleep(50 / gameSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};
	private Timer currentTime;

	private void updateTime(int minutes) {
		mainFrame.getHourlbl().setText(String.format("%02d", minutes / 60));
		mainFrame.getMinutelbl().setText(String.format("%02d", minutes % 60));
	}

	private void updateDate() {

		day = mainFrame.getUserInfo().getDate();
		day++;
		randomMin1 = random.nextInt(1339) + 1;
		randomMin2 = random.nextInt((1339 - randomMin1)) + randomMin1 + 1;

		mainFrame.getDatelbl().setText(String.format("%02d", day) + "일차");
	}

	@Override
	public void readyGame(int userId) {
		mainFrame.setUserId(userId);

		mainFrame.setUserList(SelectDB.selectUser(userId));
		mainFrame.setUserInfoList(SelectDB.selectUserinfo(userId));
		mainFrame.setUserInfo(SelectDB.searchNowGame(mainFrame.getUserInfoList()));
		if (mainFrame.getUserInfo() == null) {
			InsertDB.insertUserInfo(userId);
			mainFrame.setUserInfoList(SelectDB.selectUserinfo(userId));
			mainFrame.setUserInfo(SelectDB.searchNowGame(mainFrame.getUserInfoList()));
		}

		int infoId = mainFrame.getUserInfo().getInfoId();
		mainFrame.setInfoId(infoId);
		mainFrame.setSkillList(SelectDB.selectSkillList());
		mainFrame.setUserSkillList(SelectDB.selectUserSkill(userId, infoId));
		if (mainFrame.getUserSkillList().size() == 0) {
			InsertDB.insertUserSkill(userId, infoId);
			mainFrame.setUserSkillList(SelectDB.selectUserSkill(userId, infoId));
		}

		mainFrame.setProjectList(SelectDB.selectProject());
		mainFrame.setUserProjectList(SelectDB.selectUserProject(userId, infoId));
		if (mainFrame.getUserProjectList().size() == 0) {
			InsertDB.insertUserProject(userId, infoId);
			mainFrame.setUserProjectList(SelectDB.selectUserProject(userId, infoId));
		}

		String userNickName = mainFrame.getUserList().get(0).getUserNickname();
		mainFrame.setUserRankList(SelectDB.selectRank());
		
		System.out.println(SelectDB.searchUserRank(userId));
		
		if (!SelectDB.searchUserRank(userId)) {
			InsertDB.insertUserRank(userId, infoId, scoreCalculator(), userNickName);
		}
		if (mainFrame.getUserRankList().size() == 0) {
			InsertDB.insertUserRank(userId, infoId, scoreCalculator(), userNickName);
			mainFrame.setUserRankList(SelectDB.selectRank());
		}

		mainFrame.setCigaLogList(SelectDB.selectCigaLog(userId, infoId));
	}

	@Override
	public void applyDB() {

		UserInfo userInfo = mainFrame.getUserInfo();

		System.out.println(userInfo);

		try {
			mainFrame.getDatelbl().setText(String.format("%02d", userInfo.getDate()) + "일차");
			minutes = userInfo.getTime();
			updateTime(minutes);
			mainFrame.getLevellbl().setText(String.valueOf(userInfo.getLevel()));
			mainFrame.getExpbar().setValue(userInfo.getExp());
			mainFrame.getHpbar().setValue(userInfo.getHp());
			mainFrame.getHealthbar().setValue(userInfo.getHealth());
			mainFrame.getStressbar().setValue(userInfo.getStress());
			mainFrame.getNumOfcigalbl().setText(String.valueOf(userInfo.getCiga()));
			mainFrame.setCiga(userInfo.getCiga());
			mainFrame.setUsedCiga(userInfo.getUsedCiga());
			mainFrame.setUserId(userInfo.getUserId());
			applyProject();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveUserInfoData() {
		String strDate = mainFrame.getDatelbl().getText();
		int date = Integer.parseInt(strDate.substring(0, 2));
		int time = minutes;
		int level = Integer.parseInt(mainFrame.getLevellbl().getText());
		int exp = mainFrame.getExpbar().getValue();
		int hp = mainFrame.getHpbar().getValue();
		int health = mainFrame.getHealthbar().getValue();
		int stress = mainFrame.getStressbar().getValue();
		int ciga = mainFrame.getCiga();
		int usedCiga = mainFrame.getUsedCiga();
		boolean gameover = mainFrame.getUserInfo().isGameover();

		UserInfo userInfo = new UserInfo(mainFrame.getInfoId(), date, time, level, exp, hp, health, stress, ciga,
				usedCiga, gameover, mainFrame.getUserId());

		UpdateDB.updateUserInfo(userInfo);
	}

	public int getMinutes() {
		return minutes;
	}

	private void applyProject() {
		ProjectEventImpl projectEventImpl = new ProjectEventImpl(mainFrame);
		int searchProject = projectEventImpl.searchNowProject(mainFrame.getUserProjectList());
		if (searchProject != -1) {
			int index = mainFrame.getUserProjectList().get(searchProject).getProjectId() - 1;
			String projectName = mainFrame.getProjectList().get(index).getProjectName();
			int projectHour = mainFrame.getUserProjectList().get(searchProject).getLastHour();
			int projcetMin = mainFrame.getUserProjectList().get(searchProject).getLastMin();
			int time = mainFrame.getProjectList().get(index).getTime();
			mainFrame.getNowProjectlbl().setText(projectName);
			mainFrame.getProjectHour().setText(String.valueOf(projectHour));
			mainFrame.getProjectMinute().setText(String.valueOf(projcetMin));
			projectEventImpl.projectTimeControll(time, index);
		}
	}

	private void saveUserProjcet() {

		int searchProject = mainFrame.getProjectEventImpl().searchNowProject(mainFrame.getUserProjectList());
		if (searchProject != -1) {
			mainFrame.setNowProjectId(searchProject);
			mainFrame.getUserProjectList().get(searchProject)
					.setLastHour(Integer.valueOf(mainFrame.getProjectHour().getText()));
			mainFrame.getUserProjectList().get(searchProject)
					.setLastMin(Integer.valueOf(mainFrame.getProjectMinute().getText()));
		}
		UpdateDB.updateUserProject(mainFrame.getUserProjectList());
	}


	private void saveRanking() {

		Rank userRank = new Rank(mainFrame.getUserList().get(0).getUserNickname(), scoreCalculator(),
				mainFrame.getUserInfo().getInfoId(), mainFrame.getUserInfo().getUserId());
		
		
		
		UpdateDB.updateRanking(userRank);
		System.out.println(userRank);
	}

	private int scoreCalculator() {

		int usedCiga = mainFrame.getUserInfo().getUsedCiga();
		int level = mainFrame.getUserInfo().getLevel();

		int score = (usedCiga * 50) + (level * 200);

		return score;
	}
	

	public Timer getCurrentTime() {
		return currentTime;
	}
	
	private Random random = new Random();
	private int randomMin1 = random.nextInt(1339) + 1;
	private int randomMin2 = random.nextInt((1339 - randomMin1)) + randomMin1 + 1;
	
	public void showrandomQustion() {
		
		int index = random.nextInt(16) + 1;
		WorkBookDialog workBookDialog = new WorkBookDialog(index, mainFrame, mainFrame.getX(), mainFrame.getY());
		workBookDialog.showGUI();
	}
	
	
}
