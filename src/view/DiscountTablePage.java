package view;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controll.SearchDiscount;


import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 * This class is used to show the discount time table.
 * @author Jerry
 */
public class DiscountTablePage {
	private JFrame frame;
	private DefaultTableModel model;
	private JTable table;

	/**
	 * Launch the application.
	 * @param args No args.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DiscountTablePage window = new DiscountTablePage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Show the frame.
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	/**
	 * Set title.
	 * @param title title
	 */
	public void setTitle(String title) {
		frame.setTitle(title);
	}

	/**
	 * Create the application.
	 */
	public DiscountTablePage() {
		initialize();
	}
	
	/**
	 * @param date Departure date.
	 * @param direction Go north or south.
	 * @param startTime Departure time.
	 * @param endTime Arrival time.
	 * @param startStation Departure station.
	 * @param endStation Destination station.
	 * @throws SQLException SQLException
	 * @throws ParseException ParseException
	 */
	public void showDiscount(int date, int direction, int startTime, int endTime, int startStation, int endStation) throws SQLException, ParseException {
//		System.out.println(startStation);
//		System.out.println(endStation);
		SearchDiscount.searchDis(model, date, direction, startTime, endTime, startStation, endStation);
		if (model.getRowCount() == 0) {
			frame.setVisible(false);
			JOptionPane.showMessageDialog(null, "無優惠車票!", "InfoBox: Failed", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		// create table model
		model = new DefaultTableModel();
		model.addColumn("車次");
		model.addColumn("起站");
		model.addColumn("訖站");
		model.addColumn("大學生折數");
		model.addColumn("早鳥折數");
		model.addColumn("出發時間");
		model.addColumn("抵達時間");
		model.addColumn("行車時間");
		frame.setBounds(100, 100, 750, 432);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
	}
}
