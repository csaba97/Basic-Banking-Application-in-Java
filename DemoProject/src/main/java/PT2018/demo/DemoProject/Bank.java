package PT2018.demo.DemoProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @invariant isWellFormed(): every client and every account needs to have a unique ID
 */
public class Bank implements BankProc, Serializable {
	private Map<Person, List<Account>> accounts = new HashMap<Person, List<Account>>();
	private int nextPersonId = 1;
	private int nextAccountId = 1;

	/**
	 * @invariant isWellFormed()
	 *
	 */
	protected boolean isWellFormed() {
		// check if clients are valid
		for (Entry<Person, List<Account>> entry : this.accounts.entrySet()) {
			Person key = entry.getKey();
			for (Entry<Person, List<Account>> entry2 : this.accounts.entrySet()) {
				Person key2 = entry2.getKey();
				if (key.getId() == key2.getId() && !key.equals(key2))
					return false;
			}
		}
		// check if accounts are valid
		List<Account> accs = new ArrayList<Account>();
		for (Entry<Person, List<Account>> entry : this.accounts.entrySet()) {
			List<Account> value = entry.getValue();
			for (Account acc1 : value) {
				for (Entry<Person, List<Account>> entry2 : this.accounts.entrySet()) {
					List<Account> value2 = entry2.getValue();
					for (Account acc2 : value2) {
						if (acc1.getId() == acc2.getId() && !acc1.equals(acc2))
							return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Remove the Person person from the list
	 * 
	 * @pre getSize() > 0
	 * @pre person != null
	 * @post getSize() == getSize()@pre -1
	 */
	public void removePerson(Person person) {
		assert accounts.size() > 0;
		assert person != null;
		assert isWellFormed();
		int sizePre = accounts.size();

		accounts.remove(person);

		int sizePost = accounts.size();
		assert sizePost == sizePre - 1;
		assert isWellFormed();
	}

	/**
	 * Adds a new person to the list
	 * 
	 * @pre person != null
	 * @post getSize() == getSize()@pre + 1
	 * @post nextPersonId == nextPersonId@pre + 1
	 */
	public void addPerson(Person person) {
		assert person != null;
		assert isWellFormed();
		int sizePre = accounts.size();
		int idPre = nextPersonId;

		accounts.put(person, new ArrayList<Account>());
		nextPersonId++;

		int idPost = nextPersonId;
		int sizePost = accounts.size();
		assert idPost == idPre + 1;
		assert sizePost == sizePre + 1;
		assert isWellFormed();
	}

	/**
	 * Adds a new account to the list
	 * 
	 * @pre account != null
	 * @pre person != null
	 * @post getSize() == getSize()@pre + 1
	 * @post nextAccountId == nextAccountId@pre + 1
	 */
	public void addAccount(Person person, Account account) {
		assert account != null;
		assert person != null;
		assert isWellFormed();
		int sizePre = accounts.get(person).size();
		int preId = nextAccountId;

		// add observer
		account.setHolderId(person.getId());
		account.addObservers(person);
		List<Account> list = accounts.get(person);
		list.add(account);
		accounts.put(person, list);
		nextAccountId++;

		int postId = nextAccountId;
		int sizePost = accounts.get(person).size();
		assert postId == preId + 1;
		assert sizePost == sizePre + 1;
		assert isWellFormed();
	}

	/**
	 * Remove the Account account from the list
	 * 
	 * @pre getSize() > 0
	 * @pre person != null
	 * @pre account != null
	 * @post getSize() == getSize()@pre -1
	 */
	public void removeAccount(Person person, Account account) {
		assert person != null;
		assert accounts.get(person).size() > 0;
		assert account != null;
		assert isWellFormed();
		int sizePre = accounts.get(person).size();
		
		account.deleteObserver(person);
		List<Account> list = accounts.get(person);
		list.remove(account);
		accounts.put(person, list);

		int sizePost = accounts.get(person).size();
		assert sizePost == sizePre - 1;
		assert isWellFormed();
	}

	public Map<Person, List<Account>> getAccounts() {
		return accounts;
	}

	public List<Account> getSavingAccounts() {
		List<Account> accs = new ArrayList<Account>();
		for (Entry<Person, List<Account>> entry : this.accounts.entrySet()) {
			List<Account> value = entry.getValue();
			for (Account acc : value) {
				if (acc.getClass().equals(SavingAccount.class))
					accs.add(acc);
			}
		}
		return accs;
	}

	public List<Account> getSpendingAccounts() {
		List<Account> accs = new ArrayList<Account>();
		for (Entry<Person, List<Account>> entry : this.accounts.entrySet()) {
			List<Account> value = entry.getValue();
			for (Account acc : value) {
				if (acc.getClass().equals(SpendingAccount.class))
					accs.add(acc);
			}
		}
		return accs;
	}

	public List<Person> getClients() {
		List<Person> clients = new ArrayList<Person>();
		for (Entry<Person, List<Account>> entry : this.accounts.entrySet()) {
			Person key = entry.getKey();
			clients.add(key);
		}
		return clients;
	}

	public void setAccounts(Map<Person, List<Account>> accounts) {
		this.accounts = accounts;
	}

	public int getNextPersonId() {
		return nextPersonId;
	}

	public int getNextAccountId() {
		return nextAccountId;
	}

}
