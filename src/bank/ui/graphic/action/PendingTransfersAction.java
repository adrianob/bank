package bank.ui.graphic.action;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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
	private Vector<Transfer> transactions;
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
	
	public class PendingTransactionTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1550342879460111728L;
		
		public PendingTransactionTableModel(Vector<Transfer> Transfer) {
			transactions = Transfer;
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
				val = t.getAccount().getId().getNumber();
				break;
			case 1:
				val = t.getDestinationAccount().getId().getNumber();
				break;
			case 2:
				val = (Number) t.getAmount();
				break;
			default:
				assert false;
				break;
			}
			return val;
		}
	}


	private void updateList() {
		transactions = accountManagementService.getAllTransactions();
		this.transactionsTable.setModel(new PendingTransactionTableModel(transactions));
	}
	
	private int readRow() {
		int i = transactionsTable.getSelectedRow();
		if (i == -1) {
			// No row was selected
			GUIUtils.INSTANCE.showMessage(bankInterface.getFrame(),
					textManager.getText("message.choose.transfer"), JOptionPane.WARNING_MESSAGE);
		}
		return i;
	}
	
	@Override
	public void execute() throws Exception {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Buttons
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton cancelButton = new JButton(textManager.getText("button.deny"));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = readRow();
				if (i == -1)
					return;
				// Deny the transfer
				//transactions.get(i)
			}
		});
		buttonsPanel.add(cancelButton);
		JButton okButton = new JButton(textManager.getText("button.authorize"));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = readRow();
				if (i == -1)
					return;
				// Authorize the transfer
				//transactions.get(i)
				
				updateList();
			}
		});
		buttonsPanel.add(okButton);
		
		// Build the transaction table
		JPanel transactionsPanel = new JPanel();
		transactionsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.transactionsTable = new JTable();
		updateList();
		JScrollPane scrollPane = new JScrollPane(this.transactionsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		transactionsPanel.add(scrollPane);

		mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		mainPanel.add(transactionsPanel,BorderLayout.NORTH);

		this.dialog = GUIUtils.INSTANCE.createDialog(bankInterface.getFrame(), "action.pendingTransfers", mainPanel);
		this.dialog.setVisible(true);
	}

}
