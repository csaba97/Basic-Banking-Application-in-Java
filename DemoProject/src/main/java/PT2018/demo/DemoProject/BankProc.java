package PT2018.demo.DemoProject;

public interface BankProc {

	/**
	 * Remove the Person person from the list
	 * 
	 * @pre getSize() > 0
	 * @pre person != null
	 * @post getSize() == getSize()@pre -1
	 */
	public void removePerson(Person person);

	/**
	 * Adds a new person to the list
	 * 
	 * @pre person != null
	 * @post nextPersonId == nextPersonId@pre + 1
	 * @post getSize() == getSize()@pre + 1
	 */
	public void addPerson(Person person);

	/**
	 * Adds a new account to the list
	 * 
	 * @pre person != null
	 * @pre account != null
	 * @post getSize() == getSize()@pre + 1
	 * @post nextAccountId == nextAccountId@pre + 1
	 */
	public void addAccount(Person person, Account account);

	/**
	 * Remove the Account account from the list
	 * 
	 * @pre getSize() > 0
	 * @pre person != null
	 * @pre account != null
	 * @post @result = getElement(account)@pre
	 * @post getSize() == getSize()@pre -1
	 */
	public void removeAccount(Person person, Account account);

}
