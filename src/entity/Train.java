package entity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used to record some details of the train tickets.
 * @author Jerry
 */
public class Train {
	public int train_id;
	public int date;
	public int t1, t2;
	public double early, student;
	
	public static final String[] TICKET_TYPE = {
			"標準", "學生", "愛心/孩童/敬老", "商務" //, "早鳥"
		};
	public static final String[] TICKET_TYPE_2 = {
			"標準", "學生", "愛心/孩童/敬老", "商務", "早鳥"
		};
	public static final String[] SEAT_TYPE = {
			"隨機", "靠窗", "靠走道"
		};
			
		
	public static final int BUSS_CABIN = 5;	// 0 base
	public static final int TOTAL_CABIN = 9;
	public static final int TOTAL_SEATS = 733; 
	public static final int CABIN_SEATS[] = {63, 96, 88, 96, 83, 66, 57, 96 ,88};
	public static final String[][] VALID_SEATS;
	
	public static final int WINDOW = 1;	
	public static final int SIDE = 2;
	
	/**
	 * @param train_id Train ID.
	 * @param date Departure date.
	 * @param t1 Departure time.
	 * @param t2 Arrival Time.
	 * @param early Early discount.
	 * @param student Student discount.
	 */
	public Train(int train_id, int date, int t1, int t2, double early, double student) {
		this.train_id = train_id;
		this.date = date;
		this.t1 = t1;
		this.t2 = t2;
		this.early = early;
		this.student = student;
	}
		
	static {
		// initialize valid seats
		VALID_SEATS = new String[TOTAL_CABIN][];
		ArrayList<String> tmp = new ArrayList<String>();
		// cabin 1, 3, 5, 9
		tmp.clear();
		tmp.addAll(Arrays.asList("01A", "01B", "01C"));
		for (int i = 2; i <= 13; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[0] = tmp.toArray(new String[tmp.size()]);
		for (int i = 14; i <= 17; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[4] = tmp.toArray(new String[tmp.size()]);
		for (int i = 18; i <= 18; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[2] = tmp.toArray(new String[tmp.size()]);
		VALID_SEATS[8] = tmp.toArray(new String[tmp.size()]);
		// cabin 2, 4, 8
		tmp.clear();
		for (int i = 1; i <= 20; i++) {
			for (int j = 0; j < 3; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		for (int i = 2; i <= 19; i++) {
			for (int j = 3; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[1] = tmp.toArray(new String[tmp.size()]);
		VALID_SEATS[3] = tmp.toArray(new String[tmp.size()]);
		VALID_SEATS[7] = tmp.toArray(new String[tmp.size()]);
		// cabin 6
		tmp.clear();
		tmp.addAll(Arrays.asList("01D", "01E"));
		for (int i = 2; i <= 17; i++) {
			for (int j = 0; j < 5; j++) {
				if (j == 1) continue;
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		VALID_SEATS[5] = tmp.toArray(new String[tmp.size()]);
		// cabin 7
		tmp.clear();
		for (int i = 1; i <= 11; i++) {
			for (int j = 0; j < 5; j++) {
				tmp.add(String.format("%02d%c", i, 'A' + j));
			}
		}
		tmp.addAll(Arrays.asList("12D", "12E"));
		//tmp.addAll(Arrays.asList("NO1", "NO2", "NO3", "NO4"));
		VALID_SEATS[6] = tmp.toArray(new String[tmp.size()]);
	}
}
