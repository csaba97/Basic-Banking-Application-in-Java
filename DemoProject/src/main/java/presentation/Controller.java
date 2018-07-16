package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import PT2018.demo.DemoProject.Account;
import PT2018.demo.DemoProject.Capitalizat;
import PT2018.demo.DemoProject.Person;
import PT2018.demo.DemoProject.SavingAccount;
import PT2018.demo.DemoProject.SpendingAccount;
import PT2018.demo.DemoProject.SpendingAccountType;

/**
 * The Class Controller. It adds the functionality of the Model class to the
 * View.
 */
public class Controller {

	protected static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
	private View view;
	private Model model;

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
		this.view.addUpdateButtonListener(new UpdateListener());
		this.view.addInsertButtonListener(new InsertListener());
		this.view.addDeleteButtonListener(new DeleteListener());
		this.view.addClientButtonListener(new ClientListener());
		this.view.addSavingAccountButtonListener(new SavingAccountListener());
		this.view.addSpendingAccountButtonListener(new SpendingAccountListener());
		this.view.addFundButtonListener(new AddFundListener());
		this.view.addWithdrawButtonListener(new WithDrawListener());

		this.view.addFrame();
	}

	public DefaultTableModel createTable(List<?> objects) {
		if (objects.size() > 0) {
			List<Field> fields = new ArrayList<Field>(Arrays.asList(objects.get(0).getClass().getDeclaredFields()));
			fields.addAll(
					new ArrayList<Field>(Arrays.asList(objects.get(0).getClass().getSuperclass().getDeclaredFields())));

			Object rowData[][] = new String[objects.size()][fields.size()];
			Object columnNames[] = new String[fields.size()];

			for (int i = 0; i < objects.size(); i++) {
				int j = 0;
				Object object = objects.get(i);
				for (Field field : fields) {
					field.setAccessible(true);
					Object value;
					try {
						value = field.get(object);
						rowData[i][j] = value.toString();
						if (i == 0)
							columnNames[j] = field.getName();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					j++;
				}
			}
			return new DefaultTableModel(rowData, columnNames);
		}
		return new DefaultTableModel();
	}

	public void update() {
		int succesfull = 1;
		DefaultTableModel m = view.getTableModel();
		DefaultTableModel table;
		Integer holderId = -1;
		Account account = null;
		Person person = null;
		for (Integer row : view.getRowsModified()) {
			switch (view.getTableContent()) {
			case CLIENT:
				String name = m.getValueAt(row, 1).toString();
				Integer age = Integer.parseInt(m.getValueAt(row, 2).toString());
				String address = m.getValueAt(row, 3).toString();
				Person client = this.model.getBank().getClients().get(row);
				client.setName(name);
				client.setAge(age);
				client.setAddress(address);
				this.model.saveBank();
				table = createTable(model.getBank().getClients());
				view.setTable(table);
				view.addFrame();
				break;
			case SAVING_ACCOUNT:
				String cap = m.getValueAt(row, 2).toString();
				Capitalizat capitalizat;
				switch (cap) {
				case "CAPITALIZAT":
					capitalizat = Capitalizat.CAPITALIZAT;
					break;
				case "NU_E_CAPITALIZAT":
					capitalizat = Capitalizat.NU_E_CAPITALIZAT;
					break;
				default:
					capitalizat = Capitalizat.NU_E_CAPITALIZAT;
				}
				holderId = Integer.parseInt(m.getValueAt(row, 4).toString());
				account = this.model.getBank().getSavingAccounts().get(row);
				SavingAccount acc2 = (SavingAccount) account;
				acc2.setCapitalizat(capitalizat);

				for (Person pers : model.getBank().getClients()) {
					if (pers.getId() == account.getHolderId())
						person = pers;
				}
				if (person != null)
					model.getBank().removeAccount(person, account);

				account.setHolderId(holderId);

				for (Person pers : model.getBank().getClients()) {
					if (pers.getId() == account.getHolderId())
						person = pers;
				}
				if (person != null)
					model.getBank().addAccount(person, account);

				this.model.saveBank();
				table = createTable(model.getBank().getSavingAccounts());
				view.setTable(table);
				view.addFrame();
				break;
			case SPENDING_ACCOUNT:
				SpendingAccountType type = null;
				switch (m.getValueAt(row, 0).toString()) {
				case "STUDENT":
					type = SpendingAccountType.STUDENT;
					break;
				case "ADULT":
					type = SpendingAccountType.ADULT;
					break;
				case "COMPANY":
					type = SpendingAccountType.COMPANY;
					break;
				case "SENIOR":
					type = SpendingAccountType.SENIOR;
					break;
				default:
					type = SpendingAccountType.SENIOR;
				}
				holderId = Integer.parseInt(m.getValueAt(row, 2).toString());
				account = this.model.getBank().getSpendingAccounts().get(row);
				SpendingAccount acc1 = (SpendingAccount) account;
				acc1.setType(type);
				// remove account from old person
				for (Person pers : model.getBank().getClients()) {
					if (pers.getId() == account.getHolderId())
						person = pers;
				}
				if (person != null)
					model.getBank().removeAccount(person, account);
				account.setHolderId(holderId);
				// then add it to the new person
				for (Person pers : model.getBank().getClients()) {
					if (pers.getId() == account.getHolderId())
						person = pers;
				}
				if (person != null)
					model.getBank().addAccount(person, account);
				this.model.saveBank();
				table = createTable(model.getBank().getSpendingAccounts());
				view.setTable(table);
				view.addFrame();
				break;
			}
		}
		view.removeAllRowsModified();
	}

	public Person constructPersonFromDialogPane() {
		Object[] a = new Object[7];
		a[0] = "id=" + this.model.getBank().getNextPersonId();
		a[1] = "name";
		a[2] = new JTextField();
		a[3] = "age";
		a[4] = new JTextField();
		a[5] = "address";
		a[6] = new JTextField();
		if (JOptionPane.showConfirmDialog(null, a, "INSERT", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			Person newObject = new Person();
			JTextField f1 = (JTextField) a[2];
			JTextField f2 = (JTextField) a[4];
			JTextField f3 = (JTextField) a[6];
			newObject.setId(this.model.getBank().getNextPersonId());
			newObject.setName(f1.getText());
			newObject.setAge(Integer.parseInt(f2.getText()));
			newObject.setAddress(f3.getText());
			return newObject;
		}
		return null;
	}

	public SavingAccount constructSavingFromDialogPane() {
		Object[] a = new Object[8];
		a[0] = "id=" + model.getBank().getNextAccountId();
		a[1] = "holderId=";
		String clients[] = new String[model.getBank().getClients().size()];
		int i = 0;
		for (Person person : model.getBank().getClients()) {
			clients[i] = person.toString();
			i++;
		}
		a[2] = new JComboBox(clients);
		a[3] = "amount=0";
		a[4] = "max amount=" + Account.MAX_AMOUNT;
		a[5] = "interest rate=" + SavingAccount.INTEREST_RATE;
		a[6] = "capitalizat or not";
		Object cap_options[] = { Capitalizat.CAPITALIZAT, Capitalizat.NU_E_CAPITALIZAT };
		a[7] = new JComboBox(cap_options);

		if (JOptionPane.showConfirmDialog(null, a, "INSERT", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

			JComboBox f1 = (JComboBox) a[2];
			JComboBox f2 = (JComboBox) a[7];
			int id = model.getBank().getNextAccountId();
			int holderId = model.getBank().getClients().get(f1.getSelectedIndex()).getId();
			Capitalizat capitalizat = (Capitalizat) f2.getSelectedItem();
			SavingAccount newObject = new SavingAccount(id, holderId, capitalizat);
			return newObject;
		}
		return null;
	}

	public SpendingAccount constructSpendingFromDialogPane() {
		Object[] a = new Object[9];
		a[0] = "id=" + model.getBank().getNextAccountId();
		a[1] = "holderId=";
		String clients[] = new String[model.getBank().getClients().size()];
		int i = 0;
		for (Person person : model.getBank().getClients()) {
			clients[i] = person.toString();
			i++;
		}
		a[2] = new JComboBox(clients);
		a[3] = "amount=0";
		a[4] = "max amount=" + Account.MAX_AMOUNT;
		a[7] = "type=";
		Object types[] = { SpendingAccountType.ADULT, SpendingAccountType.COMPANY, SpendingAccountType.SENIOR,
				SpendingAccountType.STUDENT };
		a[8] = new JComboBox(types);

		if (JOptionPane.showConfirmDialog(null, a, "INSERT", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

			JComboBox f1 = (JComboBox) a[2];
			JComboBox f2 = (JComboBox) a[8];
			int id = model.getBank().getNextAccountId();
			int holderId = model.getBank().getClients().get(f1.getSelectedIndex()).getId();
			SpendingAccountType type = (SpendingAccountType) f2.getSelectedItem();
			SpendingAccount newObject = new SpendingAccount(id, holderId, type);
			return newObject;
		}
		return null;
	}

	class UpdateListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			update();
		}
	}

	class InsertListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel table = null;
			Person person = null;
			switch (view.getTableContent()) {
			case CLIENT:
				Person client = constructPersonFromDialogPane();
				if (client != null) {
					model.getBank().addPerson(client);
					model.saveBank();
					table = createTable(model.getBank().getClients());
				}
				break;
			case SAVING_ACCOUNT:
				SavingAccount acc1 = constructSavingFromDialogPane();
				if (acc1 != null) {
					for (Person p : model.getBank().getClients()) {
						if (p.getId() == acc1.getHolderId())
							person = p;
					}
					model.getBank().addAccount(person, acc1);
					model.saveBank();
					table = createTable(model.getBank().getSavingAccounts());
				}
				break;
			case SPENDING_ACCOUNT:
				SpendingAccount acc2 = constructSpendingFromDialogPane();
				if (acc2 != null) {
					for (Person p : model.getBank().getClients()) {
						if (p.getId() == acc2.getHolderId())
							person = p;
					}
					model.getBank().addAccount(person, acc2);
					model.saveBank();
					table = createTable(model.getBank().getSpendingAccounts());
				}
				break;
			}
			if (table != null)
				view.setTable(table);
			view.addFrame();
		}
	}

	class DeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JTable m = view.getTable();
			DefaultTableModel table = null;
			int row = m.getSelectedRow();
			switch (view.getTableContent()) {
			case CLIENT:
				Person client = model.getBank().getClients().get(row);
				model.getBank().removePerson(client);
				model.saveBank();
				table = createTable(model.getBank().getClients());
				break;
			case SAVING_ACCOUNT:
				Account acc1 = model.getBank().getSavingAccounts().get(row);
				if (acc1 != null) {
					Person pers = null;
					for (Person person : model.getBank().getClients()) {
						if (person.getId() == acc1.getHolderId()) {
							pers = person;
						}
					}
					model.getBank().removeAccount(pers, acc1);
					model.saveBank();
					table = createTable(model.getBank().getSavingAccounts());
				}
				break;
			case SPENDING_ACCOUNT:
				Account acc2 = model.getBank().getSpendingAccounts().get(row);
				if (acc2 != null) {
					Person pers2 = null;
					for (Person person : model.getBank().getClients()) {
						if (person.getId() == acc2.getHolderId()) {
							pers2 = person;
						}
					}
					System.out.println(pers2 + " " + acc2);
					model.getBank().removeAccount(pers2, acc2);
					model.saveBank();
					table = createTable(model.getBank().getSpendingAccounts());
				}
				break;
			}
			if (table != null)
				view.setTable(table);
			view.addFrame();
		}
	}

	class AddFundListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel table = null;
			Object[] a;
			switch (view.getTableContent()) {
			case SPENDING_ACCOUNT:
				a = new Object[4];
				a[0] = "id";
				a[1] = new JTextField();
				a[2] = "amount";
				a[3] = new JTextField();
				if (JOptionPane.showConfirmDialog(null, a, "", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

					JTextField f1 = (JTextField) a[1];
					JTextField f2 = (JTextField) a[3];
					int id = Integer.parseInt(f1.getText());
					int amount = Integer.parseInt(f2.getText());
					for (Account acc1 : model.getBank().getSpendingAccounts()) {
						if (acc1.getId() == id) {
							SpendingAccount acc11 = (SpendingAccount) acc1;
							acc11.addFund(amount);
						}
					}
				}
				table = createTable(model.getBank().getSpendingAccounts());
				break;
			case SAVING_ACCOUNT:
				a = new Object[6];
				a[0] = "id";
				a[1] = new JTextField();
				a[2] = "amount";
				a[3] = new JTextField();
				a[4] = "deposit period";
				a[5] = new JTextField();
				if (JOptionPane.showConfirmDialog(null, a, "", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

					JTextField f1 = (JTextField) a[1];
					JTextField f2 = (JTextField) a[3];
					JTextField f3 = (JTextField) a[5];
					int id = Integer.parseInt(f1.getText());
					int amount = Integer.parseInt(f2.getText());
					int depositPeriod = Integer.parseInt(f3.getText());
					for (Account acc1 : model.getBank().getSavingAccounts()) {
						if (acc1.getId() == id) {
							SavingAccount acc11 = (SavingAccount) acc1;
							acc11.addFund(amount, depositPeriod);
						}
					}
				}
				table = createTable(model.getBank().getSavingAccounts());
				break;
			default:
				break;
			}
			model.saveBank();
			if (table != null)
				view.setTable(table);
			view.addFrame();
		}
	}

	class WithDrawListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel table = null;
			Object[] a;
			switch (view.getTableContent()) {
			case SPENDING_ACCOUNT:
				a = new Object[4];
				a[0] = "id";
				a[1] = new JTextField();
				a[2] = "amount to withdraw";
				a[3] = new JTextField();
				if (JOptionPane.showConfirmDialog(null, a, "", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

					JTextField f1 = (JTextField) a[1];
					JTextField f2 = (JTextField) a[3];
					int id = Integer.parseInt(f1.getText());
					int amount = Integer.parseInt(f2.getText());
					for (Account acc1 : model.getBank().getSpendingAccounts()) {
						if (acc1.getId() == id) {
							SpendingAccount acc11 = (SpendingAccount) acc1;
							acc11.withdraw(amount);
						}
					}
				}
				table = createTable(model.getBank().getSpendingAccounts());
				break;
			case SAVING_ACCOUNT:
				a = new Object[4];
				a[0] = "id";
				a[1] = new JTextField();
				a[2] = "withdraw(all)";
				if (JOptionPane.showConfirmDialog(null, a, "", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

					JTextField f1 = (JTextField) a[1];
					int id = Integer.parseInt(f1.getText());
					for (Account acc1 : model.getBank().getSavingAccounts()) {
						if (acc1.getId() == id) {
							SavingAccount acc11 = (SavingAccount) acc1;
							float am = acc11.getAmount()
									+ acc11.getAmount() * acc11.INTEREST_RATE * acc11.getDepositPeriod();
							acc11.withdraw();
							View.showMessage("Total Fund withdrawn=" + am, JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				table = createTable(model.getBank().getSavingAccounts());
				break;
			default:
				break;
			}
			model.saveBank();
			if (table != null)
				view.setTable(table);
			view.addFrame();
		}
	}

	class ClientListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel table = createTable(model.getBank().getClients());
			view.setTable(table);
			view.addFrame();
			view.setTableContent(TableContent.CLIENT);
		}
	}

	class SavingAccountListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel table = createTable(model.getBank().getSavingAccounts());
			view.setTable(table);
			view.addFrame();
			view.setTableContent(TableContent.SAVING_ACCOUNT);
		}
	}

	class SpendingAccountListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			DefaultTableModel table = createTable(model.getBank().getSpendingAccounts());
			view.setTable(table);
			view.addFrame();
			view.setTableContent(TableContent.SPENDING_ACCOUNT);
		}
	}

}
