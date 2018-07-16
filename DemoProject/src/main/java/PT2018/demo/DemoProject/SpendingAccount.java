package PT2018.demo.DemoProject;

public class SpendingAccount extends Account {

	private SpendingAccountType type;

	public SpendingAccount(int id, int holderId, SpendingAccountType type) {
		super(id, holderId);
		this.type = type;
	}

	public boolean withdraw(int amount) {
		return super.withdraw(amount);
	}

	public boolean addFund(int amount) {
		return super.addFund(amount);
	}

	public SpendingAccountType getType() {
		return type;
	}

	public void setType(SpendingAccountType type) {
		this.type = type;
	}

}
