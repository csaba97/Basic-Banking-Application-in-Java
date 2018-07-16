package presentation;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * The Class View. Contains a table and several buttons for changing the table
 * content and buttons for different operations like: insert/delete data etc.
 */
public class View extends JFrame implements TableModelListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The content of the frame. */
	private Container content;

	/** The 1st panel containing the table. */
	private JPanel panel;

	/** The 2nd panel containing buttons for changing the table. */
	private JPanel panel2;

	/**
	 * The 3rd panel containing the buttons for the operations: insert, delete etc.
	 */
	private JPanel panel3;

	/** The table model containing a list with the corresponding model class. */
	private DefaultTableModel tableModel;

	/** The table itself. */
	private JTable t_table = new JTable();

	/** The btn for update. */
	private JButton btn_update = new JButton("Update");

	/** The btn for insert. */
	private JButton btn_insert = new JButton("Insert");

	/** The btn for delete. */
	private JButton btn_delete = new JButton("Delete");
	private JButton btn_addFund = new JButton("addFund");
	private JButton btn_withDraw = new JButton("withDraw");

	private JButton btn_client = new JButton("Client");
	private JButton btn_saving_account = new JButton("Saving Account");
	private JButton btn_spending_account = new JButton("Spending Account");
	private TableContent tableContent;

	/** The rows modified. */
	private List<Integer> rowsModified = new ArrayList<Integer>();

	/**
	 * Instantiates a new view. The Client table will show up first.
	 */
	public View() {
		content = getContentPane();
		content.setLayout((new BorderLayout()));
		panel = new JPanel();

		panel2 = new JPanel();

		panel2.add(btn_client);
		panel2.add(btn_saving_account);
		panel2.add(btn_spending_account);

		panel3 = new JPanel(new GridLayout(20, 1));

		content.add(panel2, BorderLayout.NORTH);

		panel3.add(btn_update);
		panel3.add(btn_insert);
		panel3.add(btn_delete);
		panel3.add(btn_addFund);
		panel3.add(btn_withDraw);
		content.add(panel3, BorderLayout.WEST);
	}

	/**
	 * Adds the JTable to 'panel' then 'panel' to the current frame
	 */
	public void addFrame() {
		panel.removeAll();
		JScrollPane scrollPane = new JScrollPane(t_table);
		panel.add(scrollPane);
		content.add(panel, BorderLayout.CENTER);
		this.setSize(400, 500);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setName("ORDER MANAGEMENET SYSTEM");
		this.setVisible(true);
	}

	/**
	 * Ask message with Yes/NO option.
	 *
	 * @param message
	 *            message to be asked
	 * 
	 * @return true, if successful
	 */
	public static boolean askMessage(String message) {
		int reply = JOptionPane.showConfirmDialog(null, message, "Question", JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}

	/**
	 * Shows msgTrue if value is true, show msgFalse else.
	 *
	 * @param value
	 *            value to check
	 * 
	 * @param msgTrue
	 *            show this message if true
	 * 
	 * @param msgFalse
	 *            show this message if false
	 * 
	 */
	public static void showMessageTrue(int value, String msgTrue, String msgFalse) {
		if (value > 0) {
			JOptionPane.showMessageDialog(null, msgTrue);
		} else {
			JOptionPane.showMessageDialog(null, msgFalse);
		}
	}

	/**
	 * Show message.
	 *
	 * @param msg
	 *            the msg to show
	 * @param warningMessage
	 *            the type of the message, type is JOptionPane.type
	 */
	public static void showMessage(String msg, int warningMessage) {
		switch (warningMessage) {
		case JOptionPane.WARNING_MESSAGE:
			JOptionPane.showMessageDialog(null, msg, "Message", JOptionPane.WARNING_MESSAGE);
			break;
		case JOptionPane.ERROR_MESSAGE:
			JOptionPane.showMessageDialog(null, msg, "Message", JOptionPane.ERROR_MESSAGE);
			break;
		case JOptionPane.INFORMATION_MESSAGE:
			JOptionPane.showMessageDialog(null, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
			break;
		default:
			JOptionPane.showMessageDialog(null, msg, "Message", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/**
	 * Listener for the table, if one row is modified, it will be added to the list
	 * containing all the modified rows
	 */
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		addRowModified(row);
	}

	/**
	 * Adds an index to the list of the rows modified.
	 *
	 * @param row
	 *            index
	 */
	public void addRowModified(int row) {
		rowsModified.add(row);
	}

	/**
	 * Removes the all indexes from the list of the rows modified.
	 */
	public void removeAllRowsModified() {
		rowsModified.clear();
	}

	/**
	 * Sets a new TableModel to the table. Add listener to this new TableModel.
	 *
	 * @param t_table
	 *            the model to set
	 * 
	 */
	public void setTable(DefaultTableModel t_table) {
		this.t_table = new JTable(t_table);
		this.t_table.setPreferredScrollableViewportSize(new Dimension(800, 500));
		tableModel = t_table;
		tableModel.addTableModelListener(this);
	}

	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public JTable getTable() {
		return t_table;
	}

	/**
	 * Gets the table model.
	 *
	 * @return the table model
	 */
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	/**
	 * Sets the table model.
	 *
	 * @param tableModel
	 *            the new table model
	 */
	public void setTableModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}

	/**
	 * Adds the update button listener.
	 *
	 * @param mal
	 *            the listener class to add
	 */
	public void addUpdateButtonListener(ActionListener mal) {
		btn_update.addActionListener(mal);
	}

	/**
	 * Adds the insert button listener.
	 *
	 * @param mal
	 *            the listener class to add
	 */
	public void addInsertButtonListener(ActionListener mal) {
		btn_insert.addActionListener(mal);
	}

	/**
	 * Adds the delete button listener.
	 *
	 * @param mal
	 *            the listener class to add
	 */
	public void addDeleteButtonListener(ActionListener mal) {
		btn_delete.addActionListener(mal);
	}

	public void addFundButtonListener(ActionListener mal) {
		btn_addFund.addActionListener(mal);
	}

	public void addWithdrawButtonListener(ActionListener mal) {
		btn_withDraw.addActionListener(mal);
	}

	public void addClientButtonListener(ActionListener mal) {
		btn_client.addActionListener(mal);
	}

	public void addSavingAccountButtonListener(ActionListener mal) {
		btn_saving_account.addActionListener(mal);
	}

	public void addSpendingAccountButtonListener(ActionListener mal) {
		btn_spending_account.addActionListener(mal);
	}

	/**
	 * Gets the list with the rows modified.
	 *
	 * @return the rows modified
	 */
	public List<Integer> getRowsModified() {
		return rowsModified;
	}

	public TableContent getTableContent() {
		return tableContent;
	}

	public void setTableContent(TableContent tableContent) {
		this.tableContent = tableContent;
	}

}
