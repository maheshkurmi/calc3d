package com.calc3d.app.dialogs;

import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;


import com.calc3d.app.elements.Element3DSurface;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;

/**
 * Dialog to create a new Surface3D.
 */
public class AddSurfaceDialog extends JDialog implements ActionListener{
	/** The dialog canceled flag */
	private boolean canceled = true;
 
	private static Element3DSurface surface;
		private AddSurfaceDialog(Window owner) {
			super(owner, Messages.getString("dialog.bounds.set.title"), ModalityType.APPLICATION_MODAL);
			this.setIconImage(Icons.ADDSURFACE.getImage());
			JButton btnCancel = new JButton(Messages.getString("button.cancel"));
			JButton btnCreate = new JButton(Messages.getString("button.set"));
			btnCancel.setActionCommand("cancel");
			btnCreate.setActionCommand("set");
			btnCreate.addActionListener(this);
			btnCancel.addActionListener(this);
		}
			
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			// check the action command
			if ("cancel".equals(event.getActionCommand())) {
				// if its canceled then set the canceled flag and
				// close the dialog
				this.setVisible(false);
				this.canceled = true;
			} else {
				surface=new Element3DSurface("");
				this.setVisible(false);
			}
		}
		
		/**
		 * Shows a add Surface dialog and returns a new Element3DSurfaceObject if the user clicks the ok button.
		 * @param owner the dialog owner
		 * @return Element3DSurfaceObject or null if user clicks cancel
		 */
		public static final Element3DSurface show(Window owner) {
			AddSurfaceDialog dialog = new AddSurfaceDialog(owner);
			dialog.setLocationRelativeTo(owner);
			dialog.setVisible(true);
			// control returns to this method when the dialog is closed
			
			// check the canceled flag
			if (!dialog.canceled) {
				// get the shape
				return surface;
			}
			
			// if it was canceled then return null
			return null;
		}
}
