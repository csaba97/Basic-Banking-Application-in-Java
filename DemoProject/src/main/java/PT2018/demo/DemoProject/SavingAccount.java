package PT2018.demo.DemoProject;

public class SavingAccount extends Account {

	private int depositPeriod = -1;// number of days
	public static final float INTEREST_RATE = 0.03f;// percentage
	private Capitalizat capitalizat = Capitalizat.CAPITALIZAT;

	public SavingAccount(int id, int holderId, Capitalizat capitalizat) {
		super(id, holderId);
		depositPeriod = -1;
		this.capitalizat = capitalizat;
	}

	public boolean withdraw() {
		// depositPeriod is over
		super.addFund(super.getAmount() * INTEREST_RATE * depositPeriod);
		if (super.withdraw(super.getAmount())) {
			depositPeriod = -1;
			return true;
		}
		return false;
	}

	public boolean addFund(int amount, int depositPeriod) {
		// can only add fund if the deposit period is over
		if (this.depositPeriod == -1) {
			if (super.addFund(amount)) {
				this.depositPeriod = depositPeriod;
				return true;
			}
			return false;
		}
		return false;
	}

	public int getDepositPeriod() {
		return depositPeriod;
	}

	public Capitalizat getCapitalizat() {
		return capitalizat;
	}

	public void setCapitalizat(Capitalizat capitalizat) {
		this.capitalizat = capitalizat;
	}

}
