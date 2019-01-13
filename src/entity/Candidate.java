package entity;

import java.util.Vector;
import javax.swing.JComboBox;

import controll.Convert_Time;
import dao.DAOFactory;
import dao.Train_DAO;
import entity.Train;

public class Candidate {
	public Vector<Train> can;
	public final String direction[] = {"timeTable_down", "timeTable_up"};
	
	/**
	 * Initialize the vector of the possible train.
	 */
	public Candidate() {
		can = new Vector<Train>();
	}
	
	/**
	 * List trains that meet the users' need. It shows the message directly to the GUI.
	 */
	public void listTrain(JComboBox<String> box, String date, int start, int end, int t1, int t2, int count, int side, int type, int early) {
		can.clear();
		box.removeAllItems();
		int weekday = Convert_Time.getWeekDay(date);
		String direction = null;
		if (start < end) direction = this.direction[0];
		else {
			direction = this.direction[1];
		}
		
		Vector<Train> arr = new Vector<Train>();
		Train_DAO trainDAO;
		trainDAO = DAOFactory.getTrain_DAO();
		trainDAO.connect_DB(arr,direction,date,start,end,t1,t2,count,side,type,early);
		trainDAO.getCandidate(box, arr,can, weekday, start, end, date, count, side, type, early);
	}
	
	public String isAvailable(String uid, int startStationIndex, int endStationIndex, boolean ifGoBack, int count,
			int goDateFlag, int backDateFlag, int goStartTime, int goEndTime, int backStartTime, int backEndTime) {
		if (uid.equals("")) {
			return "�ϥΪ�ID���i����";
		}
		if (startStationIndex == endStationIndex) {
			return "�_���M�W�����i�H�@��!";
		}
		if (ifGoBack && count > 5) {
			return "�Ӧ^���̦h�R5�i!";
		}

		if (ifGoBack && backDateFlag < goDateFlag) {
			return "�^�{������i��X�o�ɶ���";
		}
		if (goStartTime >= goEndTime) {
			return "[�h�{] �X�o�ɶ����i�����F�ɶ�!";
		}
		if (ifGoBack && backStartTime >= backEndTime) {
			return "[�^�{] �X�o�ɶ����i�����F�ɶ�!";
		}
		return "ok";
	}
	public int getSize(){
		return can.size();
	}
	public Train getTrain(int index) {
		return can.elementAt(index);
	}
}