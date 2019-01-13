package dao;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import entity.Train;

public interface TrainDAO {
	
	public void connect_DB(Vector<Train> arr ,String direction,String date, int start, int end, int t1, int t2, int count, int side, int type, int early);
	
	public void getCandidate(JComboBox<String> box,Vector<Train> arr ,Vector<Train> can,int weekday,int start, int end,String date,int count,int side, int type, int early);
	
	public void getTimeDown(String date,DefaultTableModel model_down);
	
	public void getTimeUp(String date,DefaultTableModel model_down);
	
	public void searchDis_DAO(DefaultTableModel model,int date, int direction, int startTime, int endTime,int weekday,String start, String end, String startC,String endC) throws SQLException, ParseException;
}