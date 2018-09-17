package Course;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTable;

public class table extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane jsp = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					table frame = new table();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public table() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 875, 163);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0,0));
		contentPane.setBorder(new TitledBorder("Flow Layout"));
		setContentPane(contentPane);
		
		table = new JTable();
		table.setBounds(10, 10, 839, 430);
		contentPane.add(table);
	}

	public table(String[] columnNames, Object[][] obj,int grade,int class_index) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 875, 163);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder("Grade "+ grade +" Class "+(class_index%4+1)));
		contentPane.setLayout(new BorderLayout(0,0));
		setContentPane(contentPane);
		
		table = new JTable(obj, columnNames);
		table.setBounds(10, 10, 839, 430);
		jsp = new JScrollPane(table);
		contentPane.add(jsp);
		// TODO Auto-generated constructor stub
	}
}
