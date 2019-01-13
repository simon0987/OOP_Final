package dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import controll.Convert_Time;
import entity.Station;
import entity.Train;
import parser.MysqlExe;
import parser.MysqlExe.RetVal;

public class Train_DAO implements TrainDAO {
	private RetVal ret = null;
	
	@Override
	public void connect_DB(Vector<Train> arr ,String direction,String date, int start, int end, int t1, int t2, int count, int side, int type, int early) {
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT * FROM %s WHERE date = %s AND %s != -1 AND %s != -1 AND %s >= %d AND %s <= %d",
					direction, date.replace("/", ""), Station.ENG_NAME[start], Station.ENG_NAME[end], Station.ENG_NAME[start], t1, Station.ENG_NAME[end], t2
					));
			while (ret.res.next()) {
				int id = ret.res.getInt("train_id");
				int st1 = ret.res.getInt(Station.ENG_NAME[start]);
				int st2 = ret.res.getInt(Station.ENG_NAME[end]);
				arr.add(new Train(id, Integer.parseInt(date.replace("/", "")), st1, st2, 0, 0));
			}
			ret.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret.conn != null) ret.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getCandidate(JComboBox<String> box,Vector<Train> arr ,Vector<Train> can,int weekday,int start, int end,String date,int count,int side, int type, int early) {
		for (Train o: arr) {
			// check discount
			double Ediscount = 1.0;
			double Sdiscount = 1.0;
			int Ecount = 0;
			int Scount = 0;
			int tot = 0, s1 = 0, s2 = 0;
			try {
				// check sold tickets
				ret = MysqlExe.execQuery(String.format(
						"SELECT * FROM tickets WHERE ticketsType = 4 AND train_id = %d AND date = %s",
						o.train_id, date.replace("/", "")
						));
				while (ret.res.next()) {
					Ecount++;
				}
				ret.conn.close();
				ret = MysqlExe.execQuery(String.format(
						"SELECT * FROM tickets WHERE ticketsType = 1 AND train_id = %d AND date = %s",
						o.train_id, date.replace("/", "")
						));
				while (ret.res.next()) {
					Scount++;
				}
				ret.conn.close();
				// check discount tickets
				ret = MysqlExe.execQuery(String.format(
						"SELECT * FROM earlyDiscount WHERE train_id = %d AND weekday = %d ORDER BY earlyD",
						o.train_id, weekday
						));
				while (ret.res.next()) {
					int cnt = ret.res.getInt("earlyT");
					double dis = ret.res.getDouble("earlyD");
					if (cnt >= Ecount + count) {
						Ediscount = dis;
						break;
					} else {
						Ecount -= cnt;
					}
				}
				ret.conn.close();
				ret = MysqlExe.execQuery(String.format(
						"SELECT * FROM studentDiscount WHERE train_id = %d AND weekday = %d ORDER BY studentD",
						o.train_id, weekday
						));
				while (ret.res.next()) {
					int cnt = ret.res.getInt("studentT");
					double dis = ret.res.getDouble("studentD");
					if (cnt >= Scount + count) {
						Sdiscount = dis;
						break;
					} else {
						Scount -= cnt;
					}
				}
				ret.conn.close();
				// check side
				ret = MysqlExe.execQuery(String.format(
						"SELECT COUNT(*), side FROM allSeat WHERE business=%d GROUP BY side",
						type == 3 ? 1: 0
						));
				while (ret.res.next()) {
					int cnt = ret.res.getInt(1);
					int sd = ret.res.getInt(2);
					tot += cnt;
					if (sd == 1) s1 += cnt;
					if (sd == 2) s2 += cnt;
				}
				ret.conn.close();
				ret = MysqlExe.execQuery(String.format(
						"SELECT COUNT(*), seats FROM `tickets` WHERE ticketsType %s 3 AND date=%s GROUP BY seats",
						type == 3 ? "=": "!=", date.replace("/", "")
						));
				while (ret.res.next()) {
					int cnt = ret.res.getInt(1);
					int sd = ret.res.getInt(2);
					tot -= cnt;
					if (sd == 1) s1 -= cnt;
					if (sd == 2) s2 -= cnt;
				}
				ret.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (ret.conn != null) ret.conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// No student discount
			if (Sdiscount == 1.0 && type == 1) continue;
			// No seat
			if (side == 0 && tot - count < 0) continue;
			if (side == 1 && s1 - count < 0) continue;
			if (side == 2 && s2 - count < 0) continue;
			StringBuffer possible = new StringBuffer();
			possible.append("班次" + o.train_id + ": ");
			if (early == 1 && type == 0 && Ediscount < 1.0) 
				possible.append("早鳥折數 " + Double.toString(Ediscount) + ",");
			if (type == 1 && Sdiscount < 1.0) 
				possible.append("學生折數 " + Double.toString(Sdiscount) + ",");
			possible.append(date);
			possible.append(", 起/訖站 " + Station.CHI_NAME[start] + "->" + Station.CHI_NAME[end]);
			possible.append(", 出發/到達時間 " + o.t1 + "->" + o.t2);
			possible.append(", 行車時間 " + Convert_Time.getDur(o.t1, o.t2));
			can.add(new Train(o.train_id, o.date, o.t1, o.t2, Ediscount, Sdiscount));
			box.addItem(possible.toString());
//			System.out.println(possible);
		}
	}
	
	public void getTimeDown(String date,DefaultTableModel model_down) {
		RetVal ret2 = null;
		try {
			ret2 = MysqlExe.execQuery("SELECT * FROM timeTable_down WHERE date = " + date);
			while (ret2.res.next()) {
				Vector<String> row = new Vector<String>();
				row.add(ret2.res.getString(2));
				for (int i = 0; i < 12; i++) {
					int time = ret2.res.getInt(i + 3);
					String input = String.format("%02d:%02d", time / 100, time % 100);
					if (time == -1) input = "*";
					row.add(input);
				}
				model_down.addRow(row);
			}
			ret2.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret2.conn != null) ret2.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void getTimeUp(String date,DefaultTableModel model_up) {
		RetVal ret3 = null;
		try {
			ret3 = MysqlExe.execQuery("SELECT * FROM timeTable_up WHERE date = " + date);
			while (ret3.res.next()) {
				Vector<String> row = new Vector<String>();
				row.add(ret3.res.getString(2));
				for (int i = 0; i < 12; i++) {
					int time = ret3.res.getInt(i + 3);
					String input = String.format("%02d:%02d", time / 100, time % 100);
					if (time == -1) input = "*";
					row.add(input);
				}
				model_up.addRow(row);
			}
			ret3.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret3.conn != null) ret3.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void searchDis_DAO(DefaultTableModel model,int date, int direction, int startTime, int endTime,int weekday,String start, String end, String startC,String endC) throws SQLException, ParseException {
		MysqlExe.RetVal ret2 = null;
		String dirTable[] = {"timeTable_down", "timeTable_up"};
		try {
			ret2 = MysqlExe.execQuery(String.format(
					"Select * FROM %s WHERE date = %s AND %s != -1 AND %s != -1 AND %s >= %d AND %s <= %d AND train_id IN "
					+ "(SELECT train_id FROM earlyDiscount WHERE weekday = %d UNION SELECT train_id FROM studentDiscount WHERE weekday = %d)"
						, dirTable[direction], date, start, end, start, startTime, end, endTime, weekday, weekday));
			while (ret2.res.next()) {
				String train_id = ret2.res.getString("train_id");
				int st = ret2.res.getInt(start);
				int ed = ret2.res.getInt(end);
				StringBuffer early = new StringBuffer();
				StringBuffer student = new StringBuffer();
				
				// get discount by train id
				MysqlExe.RetVal rsDis = null;
				try {
					rsDis = MysqlExe.execQuery(String.format("SELECT * FROM earlyDiscount WHERE weekday = %d AND train_id = %s", weekday, train_id));
					while (rsDis.res.next()) {
						if (early.length() > 0) early.append(" / ");
						early.append(rsDis.res.getString("earlyD"));
					}
					rsDis.conn.close();
					rsDis = MysqlExe.execQuery(String.format("SELECT * FROM studentDiscount WHERE weekday = %d AND train_id = %s", weekday, train_id));
					while (rsDis.res.next()) {
						if (student.length() > 0) student.append(" / ");
						student.append(rsDis.res.getString("studentD") + "");
					}
					rsDis.conn.close();
				} catch (SQLException e) {
					if (rsDis.conn != null) rsDis.conn.close();
					throw e;
				}
				
				// add new row 
				int duration = (ed / 100 * 60 + ed % 100) - (st / 100 * 60 + st % 100);
				Vector<String> row = new Vector<String>();
				row.add(train_id);
				row.add(startC);
				row.add(endC);
				row.add(student.toString());
				row.add(early.toString());
				row.add(String.format("%02d:%02d", st / 100, st % 100));
				row.add(String.format("%02d:%02d", ed / 100, ed % 100));
				row.add(String.format("%02d:%02d", duration / 60, duration % 60));
				model.addRow(row);
			}
		} catch (SQLException e) {
			if (ret2.conn != null) ret2.conn.close();
			throw e;
		}
	}
}
