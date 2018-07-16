package PT2018.demo.DemoProject;

import java.io.Serializable;
import java.util.Observable;

public class Account extends Observable implements Serializable {
	private int id;
	private int holderId = -1;
	private float amount;
	public static final int MAX_AMOUNT = 9999;

	public Account(int id, int holderId) {
		this.id = id;
		this.amount = 0;
		this.holderId = holderId;
	}

	protected boolean withdraw(float amount) {
		if (this.amount >= amount) {
			this.amount -= amount;
			setChanged();
			notifyObservers("Fund withdrawn from account id=" + id + " amount=" + amount);
			return true;
		}
		return false;
	}

	protected boolean addFund(float amount) {
		if (this.amount + amount < MAX_AMOUNT) {
			this.amount += amount;
			setChanged();
			notifyObservers("Fund added to account id=" + id + " amount=" + amount);
			return true;
		}
		return false;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public float getAmount() {
		return amount;
	}

	public void addObservers(Person person) {
		this.addObserver(person);
	}

	public int getHolderId() {
		return holderId;
	}

	public void setHolderId(int holderId) {
		this.holderId = holderId;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", amount=" + amount + "]";
	}

}
