package com.calc3d.app.panels;

import java.awt.Window;

public interface InputPanel {
	/**
	 * Returns true if the input on the panel is valid.
	 * @return boolean
	 */
	public abstract boolean isValidInput();
	
	/**
	 * Shows a JOptionPane displaying to the user the invalid input.
	 * @param owner the owner of the message
	 */
	public abstract void showInvalidInputMessage(Window owner);
	

}
