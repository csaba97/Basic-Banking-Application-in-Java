package presentation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import PT2018.demo.DemoProject.Account;
import PT2018.demo.DemoProject.Bank;
import PT2018.demo.DemoProject.Capitalizat;
import PT2018.demo.DemoProject.Person;
import PT2018.demo.DemoProject.SavingAccount;
import PT2018.demo.DemoProject.SpendingAccount;
import PT2018.demo.DemoProject.SpendingAccountType;

public class Model {

	private Bank bank;

	public Model() {
		 //makePersons();
		bank = loadBank();
		addObservers();
		// printAccounts(bank.getAccounts());
	}

	public Bank getBank() {
		return bank;
	}

	public void makePersons() {
		bank = new Bank();
		Person person1 = new Person(1, "John Big", 22, "Bulevardul 3");
		Person person2 = new Person(2, "John Tom", 22, "Observator 23");
		Person person3 = new Person(3, "Tom Jerry", 22, "Dorobantilor 7");
		Person person4 = new Person(4, "Matthew Luise", 22, "Bulevardul 32");

		bank.addPerson(person1);
		bank.addPerson(person2);
		bank.addPerson(person3);
		bank.addPerson(person4);

		SpendingAccount acc1 = new SpendingAccount(1, 1, SpendingAccountType.STUDENT);
		bank.addAccount(person1, acc1);
		acc1.addFund(200);
		acc1.addFund(100);
		acc1.withdraw(50);

		SpendingAccount acc2 = new SpendingAccount(2, 2, SpendingAccountType.SENIOR);
		bank.addAccount(person1, acc2);
		acc2.addFund(200);
		acc2.addFund(100);
		acc2.withdraw(100);

		SavingAccount acc3 = new SavingAccount(3, 2, Capitalizat.NU_E_CAPITALIZAT);
		bank.addAccount(person1, acc3);
		acc3.addFund(200, 5);
		acc3.withdraw();

		// acc2.change();
		// acc2.nothing();
		saveBank();

		Map<Person, List<Account>> map = bank.getAccounts();
		printAccounts(map);
	}

	public void saveBank() {
		try {
			FileOutputStream fileOut = new FileOutputStream("./tmp/bank.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this.bank);
			out.close();
			fileOut.close();
			// System.out.println("Serialized data is saved in /tmp/bank.ser");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public Bank loadBank() {
		Bank bank = null;
		try {
			FileInputStream fileIn = new FileInputStream("./tmp/bank.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			bank = (Bank) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return null;
		}
		return bank;
	}

	public void addObservers() {
		for (Entry<Person, List<Account>> entry : bank.getAccounts().entrySet()) {
			Person person = entry.getKey();
			List<Account> accs = entry.getValue();
			for (Account acc : accs) {
				acc.addObservers(person);
			}
		}

	}

	public void printAccounts(Map<Person, List<Account>> map) {
		for (Entry<Person, List<Account>> entry : map.entrySet()) {
			Person key = entry.getKey();
			List<Account> value = entry.getValue();
			for (Account aString : value) {
				System.out.println("key : " + key.toString() + " value : " + aString.toString());
			}
		}
		System.out.println("**********************************************************");

	}

}
