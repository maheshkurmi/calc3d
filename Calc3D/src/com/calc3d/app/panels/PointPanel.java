package com.calc3d.app.panels;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;

/**
 * Panel used to input a point or vector.
 */
public class PointPanel extends JPanel implements ActionListener, PropertyChangeListener {
	/** The version id */
	private static final long serialVersionUID = 5446710351912720509L;
	
	/** The text field for the x value */
	private JFormattedTextField txtX;
	
	/** The text field for the y value */
	private JFormattedTextField txtY;
	
	/** The text field for the y value */
	private JFormattedTextField txtZ;
	
	/** The button to remove the point */
	protected JButton btnRemove;
	
	/** The button to add the point */
	protected JButton btnAdd;
	
	/**
	 * Default constructor.
	 */
	public PointPanel() {
		this(0.0, 0.0,0.0);
	}
	
	/**
	 * Full constructor.
	 * @param x the initial x value
	 * @param y the initial y value
	 */
	public PointPanel(double x, double y,double z) {
		JLabel lblX = new JLabel(Messages.getString("x"));
		JLabel lblY = new JLabel(Messages.getString("y"));
		JLabel lblZ = new JLabel(Messages.getString("z"));
		
		this.txtX = new JFormattedTextField(new DecimalFormat(Messages.getString("panel.point.format")));
		this.txtY = new JFormattedTextField(new DecimalFormat(Messages.getString("panel.point.format")));
		this.txtZ = new JFormattedTextField(new DecimalFormat(Messages.getString("panel.point.format")));
		
		this.txtX.addFocusListener(new SelectTextFocusListener(this.txtX));
		this.txtY.addFocusListener(new SelectTextFocusListener(this.txtY));
		this.txtZ.addFocusListener(new SelectTextFocusListener(this.txtZ));
			
		this.txtX.setValue(x);
		this.txtY.setValue(y);
		this.txtZ.setValue(z);
		
		this.txtX.setColumns(3);
		this.txtY.setColumns(3);
		this.txtZ.setColumns(3);
		
		this.txtX.addPropertyChangeListener("value", this);
		this.txtY.addPropertyChangeListener("value", this);
		this.txtZ.addPropertyChangeListener("value", this);
		
		this.btnAdd = new JButton();
		this.btnAdd.setIcon(Icons.ADD);
		this.btnAdd.setToolTipText(Messages.getString("panel.point.add.tooltip"));
		this.btnAdd.addActionListener(this);
		this.btnAdd.setActionCommand("add");
		
		this.btnRemove = new JButton();
		this.btnRemove.setIcon(Icons.REMOVE);
		this.btnRemove.setToolTipText(Messages.getString("panel.point.remove.tooltip"));
		this.btnRemove.addActionListener(this);
		this.btnRemove.setActionCommand("remove");
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setHonorsVisibility(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(this.txtX)
				.addComponent(lblX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(this.txtY)
				.addComponent(lblY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(this.txtZ)
				.addComponent(lblZ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								
				.addComponent(this.btnAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(this.btnRemove, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblX)
						.addComponent(this.txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblY)
						.addComponent(this.txtZ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblZ)
						.addComponent(this.btnAdd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.btnRemove, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)));
	}
	
	/**
	 * Adds an action listener to listen for button events.
	 * @param actionListener the action listener to add
	 */
	public void addActionListener(ActionListener actionListener) {
		this.listenerList.add(ActionListener.class, actionListener);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ActionListener[] listeners = this.getListeners(ActionListener.class);
		// set the source to this
		e.setSource(this);
		// forward the event to the listeners on this class
		for (ActionListener listener : listeners) {
			listener.actionPerformed(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		ActionListener[] listeners = this.getListeners(ActionListener.class);
		ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "changed");
		// forward the event to the listeners on this class
		for (ActionListener listener : listeners) {
			listener.actionPerformed(event);
		}
	}
	
	/**
	 * Returns the x value of the point.
	 * @return double
	 */
	public double getValueX() {
		Number number = (Number)this.txtX.getValue();
		return number.doubleValue();
	}
	
	/**
	 * Returns the y value of the point.
	 * @return double
	 */
	public double getValueY() {
		Number number = (Number)this.txtY.getValue();
		return number.doubleValue();
	}
	
	/**
	 * Returns the z value of the point.
	 * @return double
	 */
	public double getValueZ() {
		Number number = (Number)this.txtZ.getValue();
		return number.doubleValue();
	}
}
