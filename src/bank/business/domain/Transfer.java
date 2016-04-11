package bank.business.domain;

/**
 * @author Ingrid Nunes
 * 
 */
public class Transfer extends Transaction {

	public static final int MAX_AMOUNT = 5000;
	private CurrentAccount destinationAccount;

	public enum Status {
		Status_Finished, Status_Pending,
	};

	private Status status;

	public Transfer(OperationLocation location, CurrentAccount account, CurrentAccount destinationAccount,
			double amount) {
		super(location, account, amount);
		this.destinationAccount = destinationAccount;
		this.status = this.needsAuthorization() ? Status.Status_Pending : Status.Status_Finished;
	}

	/**
	 * @return the destinationAccount
	 */
	public CurrentAccount getDestinationAccount() {
		return destinationAccount;
	}

	public Status getStatus() {
		return status;
	}
	
	public boolean needsAuthorization() {
		return this.getLocation() instanceof ATM && this.getAmount() >= Transfer.MAX_AMOUNT;
	}

}