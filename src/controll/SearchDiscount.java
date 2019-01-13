package controll;

import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.table.DefaultTableModel;

import dao.DAOFactory;
import dao.Train_DAO;
import entity.Station;

public class SearchDiscount {
	public static void searchDis(DefaultTableModel model,int date, int direction, int startTime, int endTime, int startStation, int endStation) throws SQLException, ParseException {
		int weekday = Convert_Time.getWeekDay(date);
		String start = Station.ENG_NAME[startStation];
		String end = Station.ENG_NAME[endStation];
		String startC = Station.CHI_NAME[startStation];
		String endC = Station.CHI_NAME[endStation];
		if (direction == 1) {
			startStation = Station.ENG_NAME.length - 1 - startStation;
			endStation = Station.ENG_NAME.length - 1 - endStation;
		}
		model.setRowCount(0);
		Train_DAO traindao;
		traindao = DAOFactory.getTrain_DAO();
		traindao.searchDis_DAO(model, date, direction, startTime, endTime,weekday,start,end,startC,endC);
	}
}
