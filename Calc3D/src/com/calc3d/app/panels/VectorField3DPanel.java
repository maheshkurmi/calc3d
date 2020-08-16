package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Window;
import java.text.DecimalFormat;

import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DCurve;
import com.calc3d.app.elements.Element3DSurface;
import com.calc3d.app.elements.Element3DVectorField;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.mathparser.Calculable;
import com.calc3d.mathparser.ExpressionBuilder;

public class VectorField3DPanel extends Object3DCreatePanel  {

	private JLabel lblExpressionX;
	private JLabel lblExpressionY;
	private JLabel lblExpressionZ;
	private JLabel lblSegmentsX;
	private JLabel lblSegmentsY;
	private JLabel lblSegmentsZ;
	
	/** The expression text field */
	private JTextField txtExpressionX;
	private JTextField txtExpressionY;
	private JTextField txtExpressionZ;
	
	
	/** The Spinner for number of sements of curve */
	private JSpinner spinSegmentsX;
	/** The Spinner for number of sements of curve */
	private JSpinner spinSegmentsY;
	/** The Spinner for number of sements of curve */
	private JSpinner spinSegmentsZ;

	private Element3DVectorField curve3D;
	
	public VectorField3DPanel(Element3DVectorField object3D){
		curve3D=object3D;
		lblExpressionX=new JLabel("X(t)", Icons.ABOUT, JLabel.LEFT);
		lblExpressionY=new JLabel("Y(t)", Icons.ABOUT, JLabel.LEFT);
		lblExpressionZ=new JLabel("Z(t)", Icons.ABOUT, JLabel.LEFT);
		lblSegmentsX=new JLabel("X Grids", Icons.ABOUT, JLabel.LEFT);
		lblSegmentsY=new JLabel("Y Grids", Icons.ABOUT, JLabel.LEFT);
		lblSegmentsZ=new JLabel("Z Grids", Icons.ABOUT, JLabel.LEFT);
		
		
		spinSegmentsX=new JSpinner();
		spinSegmentsX.setModel(new javax.swing.SpinnerNumberModel(6, 1, 40, 1));
		spinSegmentsX.setValue(object3D.getNumXSegments());
		spinSegmentsX.setMaximumSize(getSize());
	
		spinSegmentsY=new JSpinner();
		spinSegmentsY.setModel(new javax.swing.SpinnerNumberModel(6, 1, 40, 1));
		spinSegmentsY.setValue(object3D.getNumXSegments());
		spinSegmentsY.setMaximumSize(getSize());
	
		spinSegmentsZ=new JSpinner();
		spinSegmentsZ.setModel(new javax.swing.SpinnerNumberModel(6, 1, 40, 1));
		spinSegmentsZ.setValue(object3D.getNumZSegments());
		spinSegmentsZ.setMaximumSize(getSize());
	
		this.txtExpressionX = new JTextField(curve3D.getExprX());
		this.txtExpressionY = new JTextField(curve3D.getExprY());
		this.txtExpressionZ = new JTextField(curve3D.getExprZ());
	
	
		JPanel objectCreatePanel= new JPanel();
		/*
		TitledBorder border = BorderFactory.createTitledBorder(	BorderFactory.createEtchedBorder(),	Messages.getString("panel.body.createobject"));
		border.setTitlePosition(TitledBorder.TOP);
		objectCreatePanel.setBorder(border);
        */
		GroupLayout layout;
		layout = new GroupLayout(objectCreatePanel);
		objectCreatePanel.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(this.lblExpressionX)
						.addComponent(this.lblExpressionY)
						.addComponent(this.lblExpressionZ)
						.addComponent(this.lblSegmentsX)
						.addComponent(this.lblSegmentsY)
						.addComponent(this.lblSegmentsZ))
					
				.addGroup(layout.createParallelGroup()
						.addComponent(this.txtExpressionX)
						.addComponent(this.txtExpressionY)
						.addComponent(this.txtExpressionZ)
						.addComponent(this.spinSegmentsX)
						.addComponent(this.spinSegmentsY)
						.addComponent(this.spinSegmentsZ)));
					
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblExpressionX)
						.addComponent(this.txtExpressionX))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblExpressionY)
						.addComponent(this.txtExpressionY))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblExpressionZ)
						.addComponent(this.txtExpressionZ))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblSegmentsX)
						.addComponent(this.spinSegmentsX))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblSegmentsY)
						.addComponent(this.spinSegmentsY))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblSegmentsZ)
						.addComponent(this.spinSegmentsZ)));
		
		this.setLayout(new BorderLayout());
		add(objectCreatePanel);
	}
	
	
	@Override
	public boolean isValidInput() {
		Calculable calc_x,calc_y,calc_z;
		double mint,maxt;
		try {
			calc_x = new ExpressionBuilder(txtExpressionX.getText()).withVariableNames("x","y","z")
					.build();
			calc_y = new ExpressionBuilder(txtExpressionY.getText()).withVariableNames("x","y","z")
					.build();
			calc_z = new ExpressionBuilder(txtExpressionZ.getText()).withVariableNames("x","y","z")
					.build();
		} catch (Exception e) {
			errorMsg=e.toString();
			return false;
		}
		return true;
	}

	
	@Override
	public void showInvalidInputMessage(Window owner) {
		JOptionPane.showMessageDialog(owner, errorMsg, Messages.getString("panel.invalid.title"), JOptionPane.ERROR_MESSAGE);
	}

	
	@Override
	public Element3D getObject3D() {
		if (isValidInput()) {
			curve3D=new Element3DVectorField();
			curve3D.setExprX(txtExpressionX.getText());
			curve3D.setExprY(txtExpressionY.getText());
			curve3D.setExprZ(txtExpressionZ.getText());
			curve3D.setNumSegments((Integer) spinSegmentsX.getValue(),(Integer) spinSegmentsY.getValue(),(Integer) spinSegmentsZ.getValue());
			return curve3D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3DVectorField)) return;
		curve3D=(Element3DVectorField) object3d;
		txtExpressionX.setText(curve3D.getExprX());
		txtExpressionY.setText(curve3D.getExprY());
		txtExpressionZ.setText(curve3D.getExprZ());
		spinSegmentsX.setValue(curve3D.getNumXSegments());
		spinSegmentsY.setValue(curve3D.getNumYSegments());
		spinSegmentsZ.setValue(curve3D.getNumZSegments());
	}

}
