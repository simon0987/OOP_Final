package dao;

public class DAOFactory {
	private static Train_DAO myTrain_DAO;
	
	public static Train_DAO getTrain_DAO() {
		myTrain_DAO = new Train_DAO();
		return myTrain_DAO;
	}
}
