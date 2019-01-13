package controll;



import javax.swing.JComboBox;

import entity.Candidate;


/**
 * This class is used to search the train that fit the user's constrain.
 * All candidates will be added to the comboBox, the user will need to
 * choose the train tickets they want later.
 * @author Simon
 */
public class SearchCandidate {
	Candidate candidates = new Candidate();
	/**
	 * Search the train that fit the user's constrain.
	 * @param box The comboBox in the mainUI.
	 * @param date The date of the ticket.
	 * @param start Departure station.
	 * @param end Destination station.
	 * @param t1 Depart time.
	 * @param t2 Arrive time.
	 * @param count Ticket count.
	 * @param side The side of the seat.
	 * @param type The type of the ticket.
	 * @param early A boolean that decide if the ticket has the early discount.
	 */
	public void search(JComboBox<String> box, String date, int start, int end, int t1, int t2, int count, int side, int type, int early) {
		candidates.listTrain(box, date, start, end, t1, t2, count, side, type, early);
	}
}
