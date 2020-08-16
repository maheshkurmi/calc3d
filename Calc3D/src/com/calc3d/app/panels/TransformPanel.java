
package com.calc3d.app.panels;

import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.MessageFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.math.AffineTransform3D;
import com.calc3d.math.Angles3D;
import com.calc3d.math.Vector3D;


/**
 * Panel used to capture translation and rotation.
 * @author William Bittle
 * @version 1.0.4
 * @since 1.0.0
 */
public class TransformPanel extends JPanel implements InputPanel {
	/** The version id */
	private static final long serialVersionUID = -5389987241883097903L;

	/** The translation */
	private Vector3D translation;
	
	/** The rotation (in degrees) */
	private Angles3D rotation;
	
	/** The translation label */
	private JLabel lblT;
	
	/** The Rotation label */
	private JLabel lblR;
	
	/** The label for the translation along the x-axis */
	private JLabel lblX;
	
	/** The label for the translation along the y-axis */
	private JLabel lblY;
	
	/** The label for the translation along the z-axis */
	private JLabel lblZ;
	
	
	/** The label for the rotation about X axis*/
	private JLabel lblRX;
	
	/** The label for the rotation about Y axis*/
	private JLabel lblRY;
	
	/** The label for the rotation about Z axis*/
	private JLabel lblRZ;
	
	
	/** The text box for the translation along the x-axis */
	private JFormattedTextField txtX;
	
	/** The text box for the translation along the y-axis */
	private JFormattedTextField txtY;
	
	/** The text box for the translation along the Z-axis */
	private JFormattedTextField txtZ;
	
	
	/** The text box for the rotation about x axis */
	private JFormattedTextField txtRX;
	
	/** The text box for the rotation about y axis */
	private JFormattedTextField txtRY;
	
	/** The text box for the rotation about z axis */
	private JFormattedTextField txtRZ;
	
		
	public TransformPanel() {
		this(new Vector3D(),new Angles3D());
	}
	/**
	 * Full constructor.
	 * @param tx the initial translation
	 * @param rot the initial rotation
	 */
	public TransformPanel(Vector3D tr, Angles3D rot) {
		this.translation = tr.clone();
		
		this.rotation = rot;
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
			
		this.lblT = new JLabel(Messages.getString("panel.transform.translation"), Icons.ABOUT, JLabel.LEFT);
		this.lblT.setToolTipText(MessageFormat.format(Messages.getString("panel.transform.translation.tooltip"), Messages.getString("unit.length")));
		this.lblX = new JLabel(Messages.getString("x"));
		this.lblY = new JLabel(Messages.getString("y"));
		this.lblZ = new JLabel("z");
		
	
		this.lblR = new JLabel(Messages.getString("panel.transform.rotation"), Icons.ABOUT, JLabel.LEFT);
		this.lblR.setToolTipText(MessageFormat.format(Messages.getString("panel.transform.rotation.tooltip") +"(about Coordinate axis)", Messages.getString("unit.rotation")));
		
		this.lblRX = new JLabel(Messages.getString("x"));
		this.lblRX.setToolTipText(MessageFormat.format(Messages.getString("panel.transform.rotation.tooltip") +"(about X axis)", Messages.getString("unit.rotation")));
		
		this.lblRY = new JLabel(Messages.getString("y"));
		this.lblRY.setToolTipText(MessageFormat.format(Messages.getString("panel.transform.rotation.tooltip") +"(about Y axis)", Messages.getString("unit.rotation")));
	
		this.lblRZ = new JLabel("z");
		this.lblRZ.setToolTipText(MessageFormat.format(Messages.getString("panel.transform.rotation.tooltip") +"(about Z axis)", Messages.getString("unit.rotation")));
		
		this.txtX = new JFormattedTextField(new DecimalFormat(Messages.getString("panel.transform.translation.format")));
		this.txtY = new JFormattedTextField(new DecimalFormat(Messages.getString("panel.transform.translation.format")));
		this.txtZ = new JFormattedTextField(new DecimalFormat(Messages.getString("panel.transform.translation.format")));
		
		this.txtRX = new JFormattedTextField(new DecimalFormat(Messages.getString("panel.transform.rotation.format")));
		this.txtRY = new JFormattedTextField(new DecimalFormat(Messages.getString("panel.transform.rotation.format")));
		this.txtRZ = new JFormattedTextField(new DecimalFormat(Messages.getString("panel.transform.rotation.format")));
		
		
		this.txtX.setColumns(7);
		this.txtY.setColumns(7);
		this.txtZ.setColumns(7);
		
		this.txtRX.setColumns(7);
		this.txtRY.setColumns(7);
		this.txtRZ.setColumns(7);
		
		this.txtX.setValue(this.translation.getX());
		this.txtY.setValue(this.translation.getY());
		this.txtZ.setValue(this.translation.getZ());
		
		this.txtRX.setValue(this.rotation.iAngleX);
		this.txtRY.setValue(this.rotation.iAngleY);
		this.txtRZ.setValue(this.rotation.iAngleZ);
		
		
		this.txtX.addFocusListener(new SelectTextFocusListener(this.txtX));
		this.txtX.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				Number number = (Number)event.getNewValue();
				translation.setX(number.doubleValue());
			}
		});
		
		this.txtY.addFocusListener(new SelectTextFocusListener(this.txtY));
		this.txtY.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				Number number = (Number)event.getNewValue();
				translation.setY( number.doubleValue());
			}
		});
		
		this.txtZ.addFocusListener(new SelectTextFocusListener(this.txtZ));
		this.txtZ.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				Number number = (Number)event.getNewValue();
				translation.setZ( number.doubleValue());
			}
		});
		
		
		this.txtRX.addFocusListener(new SelectTextFocusListener(this.txtRX));
		this.txtRX.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				Number number = (Number)event.getNewValue();
				rotation.iAngleX =  number.floatValue();
			}
		});
		
		this.txtRY.addFocusListener(new SelectTextFocusListener(this.txtRY));
		this.txtRY.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				Number number = (Number)event.getNewValue();
				rotation.iAngleY =  number.floatValue();
			}
		});
		
		this.txtRZ.addFocusListener(new SelectTextFocusListener(this.txtRZ));
		this.txtRZ.addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				Number number = (Number)event.getNewValue();
				rotation.iAngleZ =  number.floatValue();
			}
		});
		
		
		JLabel lblFiller = new JLabel();
			
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		ParallelGroup pg = layout.createParallelGroup();
		
		layout.setHorizontalGroup(pg
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(this.lblT)
								.addComponent(lblFiller)
								.addComponent(this.lblR))
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addComponent(this.txtX)
										.addComponent(this.lblX))
								.addGroup(layout.createSequentialGroup()
										.addComponent(this.txtY)
										.addComponent(this.lblY))
								.addGroup(layout.createSequentialGroup()
										.addComponent(this.txtZ)
										.addComponent(this.lblZ))	
										
								.addGroup(layout.createSequentialGroup()
										.addComponent(this.txtRX)
										.addComponent(this.lblRX))		
										
								.addGroup(layout.createSequentialGroup()
										.addComponent(this.txtRY)
										.addComponent(this.lblRY))		
										
								.addGroup(layout.createSequentialGroup()
										.addComponent(this.txtRZ)
										.addComponent(this.lblRZ)))))		;
								
		
		SequentialGroup sg = layout.createSequentialGroup();
	
		layout.setVerticalGroup(sg
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.lblT)
						.addComponent(this.txtX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.lblX))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(lblFiller)
						.addComponent(this.txtY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.lblY))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(lblFiller)
						.addComponent(this.txtZ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.lblZ))		
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.lblR )
						.addComponent(this.txtRX , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
		                .addComponent(this.lblRX))
	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
	            		.addComponent(lblFiller)
						.addComponent(this.txtRY , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				        .addComponent(this.lblRY))	
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
			    		.addComponent(lblFiller)
						.addComponent(this.txtRZ , GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				        .addComponent(this.lblRZ)));        
	
	}
	
	/**
	 * Returns the selected translation.
	 * @return Vector2
	 */
	public Vector3D getTranslation() {
		return this.translation.clone();
	}
	
	/**
	 * Returns the selected rotation (in degrees).
	 * @return double
	 */
	public Angles3D getRotation() {
		return this.rotation;
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.sandbox.panels.InputPanel#isValidInput()
	 */
	@Override
	public boolean isValidInput() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.dyn4j.sandbox.panels.InputPanel#showInvalidInputMessage(java.awt.Window)
	 */
	@Override
	public void showInvalidInputMessage(Window owner) {}
}
