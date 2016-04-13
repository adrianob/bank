package bank.business.domain;

import bank.business.BusinessException;

/**
 * @author Ingrid Nunes
 * 
 */
public class Transfer extends Transaction {

	public static final int MAX_AMOUNT = 5000;
	private CurrentAccount destinationAccount;

	public enum Status {
		Status_Finished, Status_Pending, Status_Canceled
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
	
	public void authorize() {
		if (this.getStatus() == Status.Status_Pending) {
			try {
				this.getDestinationAccount().deposit(this.getLocation(), 0, this.getAmount());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.status = Status.Status_Finished;
		}
	}
	
	public void deny() {
		if (this.getStatus() == Status.Status_Pending) {
			try {
				this.getAccount().deposit(this.getLocation(), 0, this.getAmount());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.status = Status.Status_Canceled;
		}
	}

	public Status getStatus() {
		return status;
	}
	
	public boolean needsAuthorization() {
		return this.getLocation() instanceof ATM && this.getAmount() >= Transfer.MAX_AMOUNT;
	}

}