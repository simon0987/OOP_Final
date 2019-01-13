package controll;

import javax.swing.table.DefaultTableModel;

import dao.DAOFactory;
import dao.Train_DAO;

public final class SearchTrain {
	
	public static void setTimeDown(String date,DefaultTableModel model_down) {
		Train_DAO traindao = new Train_DAO();
		traindao.getTimeDown(date, model_down);
	}
	public static void setTimeUp(String date,DefaultTableModel model_up) {
		Train_DAO traindao;
		traindao = DAOFactory.getTrain_DAO();
		traindao.getTimeUp(date, model_up);
	}
}
