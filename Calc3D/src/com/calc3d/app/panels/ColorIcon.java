package com.calc3d.app.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.calc3d.app.dialogs.ColorDialog;


public class ColorIcon extends JPanel implements MouseListener{
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	/** The original border of the currently hovered over panel */
	private Border originalBorder;
	private ActionListener listener;
	public ColorIcon(Color color){
		super();
		this.addMouseListener(this);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setPreferredSize(new Dimension(13, 13));
		this.setMaximumSize(new Dimension(13, 13));
		this.setMinimumSize(new Dimension(13, 13));
		this.setBackground(color);
		this.setToolTipText(com.calc3d.app.resources.Messages.getString("panel.preferences.color.tooltip"));
	}
	
	public void setActionListener(ActionListener listener){
		this.listener=listener;
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		// save the border
		this.originalBorder = panel.getBorder();
		// create a new border to show highlight
		Border hoverBorder = BorderFactory.createLineBorder(Color.WHITE);
		// set the border
		panel.setBorder(hoverBorder);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		// set the border back
		panel.setBorder(this.originalBorder);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		JPanel panel = (JPanel)e.getSource();
		Color color = ColorDialog.show(this, panel.getBackground(), false);
		if (color != null) {
			panel.setBackground(color);
			panel.invalidate();
			if (null!=listener)listener.actionPerformed(new ActionEvent(this,0,""));
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
