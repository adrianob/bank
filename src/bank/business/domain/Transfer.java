package bank.business.domain;

/**
 * @author Ingrid Nunes
 * 
 */
public class Transfer extends Transaction {

	private CurrentAccount destinationAccount;
	public enum Status {
		Status_Finished,
		Status_Pending,
	};
	private Status status;
	public Transfer(OperationLocation location, CurrentAccount account,
			CurrentAccount destinationAccount, double amount) {
		super(location, account, amount);
		this.destinationAccount = destinationAccount;
		this.status = Status.Status_Finished; // temp;
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



}
