package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DImplicit;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.mathparser.Calculable;
import com.calc3d.mathparser.ExpressionBuilder;

public class Implicit3DPanel extends Object3DCreatePanel  {

	private JLabel lblExpression;
	private JLabel lblGridX;
	private JLabel lblGridY;
	private JLabel lblGridZ;
	private JLabel lblDrawMode;
	
	/** The expression text field */
	private JTextField txtExpression;
	
	/** The Spinner for xGrid */
	private JSpinner spinGridX,spinGridY,spinGridZ;
	
	private JComboBox comboFillMode;
	
	private Element3DImplicit surface3D;
	
	
	public Implicit3DPanel(Element3DImplicit object3D){
		surface3D=object3D;
		lblExpression=new JLabel("Expression", Icons.ABOUT, JLabel.LEFT);
		lblGridX=new JLabel("xCubes", Icons.ABOUT, JLabel.LEFT);
		lblGridY=new JLabel("ycubes", Icons.ABOUT, JLabel.LEFT);
		lblGridZ=new JLabel("zcubes", Icons.ABOUT, JLabel.LEFT);
		
		lblDrawMode=new JLabel(Messages.getString("panel.body.drawmode"), Icons.ABOUT, JLabel.LEFT);
		
		spinGridX=new JSpinner();
		spinGridX.setModel(new javax.swing.SpinnerNumberModel(15, 1, 100, 1));
		spinGridX.setValue(object3D.getxGrids());
		spinGridX.setMaximumSize(spinGridX.getSize());
		spinGridY=new JSpinner();
		spinGridY.setModel(new javax.swing.SpinnerNumberModel(15, 1, 100, 1));
		spinGridY.setValue(object3D.getyGrids());
		spinGridY.setMaximumSize(spinGridX.getSize());
		spinGridZ=new JSpinner();
		spinGridZ.setModel(new javax.swing.SpinnerNumberModel(15, 1, 100, 1));
		spinGridZ.setValue(object3D.getzGrids());
		spinGridZ.setMaximumSize(spinGridX.getSize());
		
		
		comboFillMode = new JComboBox();
		comboFillMode.setModel(new DefaultComboBoxModel(new String[] { "Solid", "WireFrame", "Solid Gradient" }));
		//comboDrawMode.setSelectedIndex(0);
		comboFillMode.setSelectedIndex(surface3D.getFillmode());
		comboFillMode.setMaximumSize(new Dimension(10000,0));
		
		this.txtExpression = new JTextField(object3D.getExpression());
		
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
						.addComponent(this.lblExpression)
						.addComponent(this.lblGridX)
						.addComponent(this.lblGridY)
						.addComponent(this.lblGridZ)
						.addComponent(this.lblDrawMode))
				.addGroup(layout.createParallelGroup()
						.addComponent(this.txtExpression)
						.addComponent(this.spinGridX)
						.addComponent(this.spinGridY)
						.addComponent(this.spinGridZ)
						.addComponent(this.comboFillMode)));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblExpression)
						.addComponent(this.txtExpression))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblGridX)
						.addComponent(this.spinGridX))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblGridY)
						.addComponent(this.spinGridY))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblGridZ)
						.addComponent(this.spinGridZ))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblDrawMode)
						.addComponent(this.comboFillMode)));
		this.setLayout(new BorderLayout());
		add(objectCreatePanel);
	}
	
	
	@Override
	public boolean isValidInput() {
		Calculable calc;
		try {
			String[] temp;
			String exprNew;
			temp=txtExpression.getText().split("=");
			switch (temp.length)
			{
			case 1:
				exprNew=txtExpression.getText();
				break;
			case 2:
				exprNew=temp[0]+"-("+temp[1]+")";
				break;
			default:
				throw new Exception("Syntax Error");
			}
			calc = new ExpressionBuilder(exprNew).withVariableNames("x", "y","z")
					.build();
		} catch (Exception e) {
			errorMsg=e.getMessage();
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
			surface3D=new Element3DImplicit(txtExpression.getText());
			surface3D.setxGrids((Integer)spinGridX.getValue());
			surface3D.setyGrids((Integer)spinGridY.getValue());
			surface3D.setzGrids((Integer)spinGridZ.getValue());
			
			surface3D.setFillmode(comboFillMode.getSelectedIndex());
			return surface3D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3DImplicit)) return;
		surface3D=(Element3DImplicit) object3d;
		txtExpression.setText(surface3D.getExpression());
		spinGridX.setValue(surface3D.getyGrids());
		spinGridY.setValue(surface3D.getyGrids());
		comboFillMode.setSelectedIndex(surface3D.getFillmode());
	}

}
