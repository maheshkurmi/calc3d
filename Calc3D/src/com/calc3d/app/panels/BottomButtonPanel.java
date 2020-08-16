package com.calc3d.app.panels;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.calc3d.utils.ColorUtils;

/**
 * Represents a JPanel that houses buttons at the bottom of a dialog.
 */
public class BottomButtonPanel extends JPanel {
	/** The version id */
	private static final long serialVersionUID = -7759531973168244255L;
	
	/**
	 * Default constructor.
	 * <p>
	 * Creates a JPanel that has a top border and a slightly darker background.
	 */
	public BottomButtonPanel() {
		Color bg = this.getBackground();
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, bg.darker()));
		this.setBackground(ColorUtils.getColor(bg, 0.98f));
	}
}
