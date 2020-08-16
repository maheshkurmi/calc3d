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
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.mathparser.Calculable;
import com.calc3d.mathparser.ExpressionBuilder;

public class Curve3DPanel extends Object3DCreatePanel  {

	private JLabel lblExpressionX;
	private JLabel lblExpressionY;
	private JLabel lblExpressionZ;
	private JLabel lblMin_t;
	private JLabel lblMax_t;
	private JLabel lblSegments;
	
	/** The expression text field */
	private JTextField txtExpressionX;
	private JTextField txtExpressionY;
	private JTextField txtExpressionZ;
	
	private JFormattedTextField txtMin_t;
	private JFormattedTextField txtMax_t;
	
	/** The Spinner for number of sements of curve */
	private JSpinner spinSegments;
	
	private Element3DCurve curve3D;
	
	public Curve3DPanel(Element3DCurve object3D){
		curve3D=object3D;
		lblExpressionX=new JLabel("X(t)", Icons.ABOUT, JLabel.LEFT);
		lblExpressionY=new JLabel("Y(t)", Icons.ABOUT, JLabel.LEFT);
		lblExpressionZ=new JLabel("Z(t)", Icons.ABOUT, JLabel.LEFT);
		lblMin_t=new JLabel("Min. value of  t", Icons.ABOUT, JLabel.LEFT);
		lblMax_t=new JLabel("Max. value of  t", Icons.ABOUT, JLabel.LEFT);
		lblSegments=new JLabel("Segments", Icons.ABOUT, JLabel.LEFT);
		
		
		spinSegments=new JSpinner();
		spinSegments.setModel(new javax.swing.SpinnerNumberModel(40, 1, 200, 1));
		spinSegments.setValue(object3D.getNumSegments());
		spinSegments.setMaximumSize(getSize());
	
		this.txtExpressionX = new JTextField(curve3D.getExprX());
		this.txtExpressionY = new JTextField(curve3D.getExprY());
		this.txtExpressionZ = new JTextField(curve3D.getExprZ());
	
		this.txtMin_t = new JFormattedTextField(new DecimalFormat());
		this.txtMin_t.setValue(curve3D.getMin_t());
		this.txtMin_t.setColumns(7);
		this.txtMax_t = new JFormattedTextField(new DecimalFormat());
		this.txtMax_t.setValue(curve3D.getMax_t());
		this.txtMax_t.setColumns(7);
	
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
						.addComponent(this.lblSegments)
						.addComponent(this.lblMin_t)
						.addComponent(this.lblMax_t))
					
				.addGroup(layout.createParallelGroup()
						.addComponent(this.txtExpressionX)
						.addComponent(this.txtExpressionY)
						.addComponent(this.txtExpressionZ)
						.addComponent(this.spinSegments)
						.addComponent(this.txtMin_t)
						.addComponent(this.txtMax_t)));
					
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
						.addComponent(this.lblSegments)
						.addComponent(this.spinSegments))
	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblMin_t)
						.addComponent(this.txtMin_t))
	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblMax_t)
						.addComponent(this.txtMax_t)));
		
		this.setLayout(new BorderLayout());
		add(objectCreatePanel);
	}
	
	
	@Override
	public boolean isValidInput() {
		Calculable calc_x,calc_y,calc_z;
		double mint,maxt;
		try {
			calc_x = new ExpressionBuilder(txtExpressionX.getText()).withVariableNames("t")
					.build();
			calc_y = new ExpressionBuilder(txtExpressionY.getText()).withVariableNames("t")
					.build();
			calc_z = new ExpressionBuilder(txtExpressionZ.getText()).withVariableNames("t")
					.build();
			mint=((Number)txtMin_t.getValue()).doubleValue();
			maxt=((Number)txtMax_t.getValue()).doubleValue();
			if (mint>=maxt) {
				errorMsg="Maximum value of t must be greater than its minimum value";
				return false;
			}
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
			curve3D=new Element3DCurve();
			curve3D.setExprX(txtExpressionX.getText());
			curve3D.setExprY(txtExpressionY.getText());
			curve3D.setExprZ(txtExpressionZ.getText());
			curve3D.setMin_t(((Number)txtMin_t.getValue()).doubleValue());
			curve3D.setMax_t(((Number)txtMax_t.getValue()).doubleValue());
			curve3D.setNumSegments((Integer) spinSegments.getValue());
			return curve3D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3DCurve)) return;
		curve3D=(Element3DCurve) object3d;
		txtExpressionX.setText(curve3D.getExprX());
		txtExpressionY.setText(curve3D.getExprY());
		txtExpressionZ.setText(curve3D.getExprZ());
		txtMin_t.setValue(curve3D.getMin_t());
		txtMax_t.setValue(curve3D.getMax_t());
		spinSegments.setValue(curve3D.getNumSegments());
	}

}
