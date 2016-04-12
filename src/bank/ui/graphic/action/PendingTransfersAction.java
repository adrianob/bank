package bank.ui.graphic.action;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;

import bank.business.AccountManagementService;
import bank.business.domain.Transfer;
import bank.ui.TextManager;
import bank.ui.graphic.BankGraphicInterface;
import bank.ui.graphic.GUIUtils;


public class PendingTransfersAction extends BankAction {

	private static final long serialVersionUID = 558966388633948681L;
	private JDialog dialog;
	private JTable transactionsTable;
	AccountManagementService accountManagementService;
	public PendingTransfersAction(BankGraphicInterface bankInterface, 
			TextManager textManager,
			AccountManagementService accountManagementService) {
		super(bankInterface, textManager);

		super.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		super.putValue(Action.NAME, textManager.getText("action.pendingTransfers"));
		this.accountManagementService = accountManagementService;

	}
	

	
	public void close() {
		dialog.dispose();
		dialog = null;
	}
	
	@SuppressWarnings("unused")
	public class PendingTransactionTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1550342879460111728L;
		
		private List<Transfer> transactions;
		
		public PendingTransactionTableModel(List<Transfer> transactions) {
			this.transactions = new ArrayList<>(transactions);
		}
		
		@Override
		public String getColumnName(int column) {
			String key = null;
			switch (column) {
			case 0:
				key = "account.number";
				break;
			case 1:
				key = "destination.account.number";
				break;
			case 2:
				key = "amount";
				break;
			default:
				assert false;
				break;
			}
			return textManager.getText(key);
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public int getRowCount() {
			return transactions.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Transfer t = transactions.get(rowIndex);
			Object val = null;
			switch (columnIndex) {
			case 0:
				val = t.getAccount().toString();
				break;
			case 1:
				val = t.getDestinationAccount().toString();
				break;
			case 2:
				val = t.getAmount();
				break;
			default:
				assert false;
				break;
			}
			return val;
		}
	}


	@Override
	public void execute() throws Exception {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Confirmation Buttons
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton cancelButton = new JButton(textManager.getText("button.deny"));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		buttonsPanel.add(cancelButton);
		JButton okButton = new JButton(textManager.getText("button.authorize"));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Autoriza transação
				// Atualiza lista
			}
		});
		buttonsPanel.add(okButton);

		// TODO: Move to function that loads the transactions
		JPanel transactionsPanel = new JPanel();
		transactionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.transactionsTable = new JTable();
		
		/*
		List<Transfer> transfers = new Vector<Transfer>();
		List<Transaction> allTransactions = accountManagementService.getAllTransactions();
		for (Transaction transaction : allTransactions){
			if (transaction instanceof Transaction ){
				transfers.add((Transfer) transaction);
			}
		}
		this.transactionsTable.setModel(new PendingTransactionTableModel(transfers));
		*/
		//this.transactionsTable.setModel(new PendingTransactionTableModel(null));
		
		JScrollPane scrollPane = new JScrollPane(this.transactionsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		transactionsPanel.add(scrollPane);

		
		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		mainPanel.add(transactionsPanel,BorderLayout.NORTH);

		this.dialog = GUIUtils.INSTANCE.createDialog(bankInterface.getFrame(), "action.pendingTransfers", mainPanel);
		this.dialog.setVisible(true);
	}

}
