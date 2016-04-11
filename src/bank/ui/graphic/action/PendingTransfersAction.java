package bank.ui.graphic.action;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import bank.business.AccountManagementService;
import bank.ui.TextManager;
import bank.ui.graphic.BankGraphicInterface;
import bank.ui.graphic.GUIUtils;

public class PendingTransfersAction extends BankAction {
	
	private JDialog dialog;
	private static final long serialVersionUID = 1L;

	public PendingTransfersAction(BankGraphicInterface bankInterface, 
			TextManager textManager,
			AccountManagementService accountManagementService) {
		super(bankInterface, textManager);

		super.putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		super.putValue(Action.NAME,
				textManager.getText("action.pendingTransfers"));
		
		
	}


	@Override
	public void execute() throws Exception {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// TODO: For each pending transfer, add it to the panel
		
		this.dialog = GUIUtils.INSTANCE.createDialog(bankInterface.getFrame(),
				"action.pendingTransfers", panel);
		this.dialog.setVisible(true);
	}

}
