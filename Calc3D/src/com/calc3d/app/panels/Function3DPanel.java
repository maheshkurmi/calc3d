package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DSurface;
import com.calc3d.app.elements.Element3Dfunction;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;

public class Function3DPanel extends Object3DCreatePanel  {

	private JLabel lblExpression;
	private JLabel lblGridX;
	private JLabel lblGridY;
	private JLabel lblDrawMode;
	
	/** The expression text field */
	private JTextArea txtExpression;
	
	/** The Spinner for xGrid */
	private JSpinner spinGridX,spinGridY;
	
	private JComboBox comboFillMode;
	
	private Element3Dfunction surface3D;
	
	
	public Function3DPanel(Element3Dfunction object3D){
		surface3D=object3D;
		lblExpression=new JLabel("Function Script", Icons.ABOUT, JLabel.LEFT);
		lblGridX=new JLabel("u steps", Icons.ABOUT, JLabel.LEFT);
		lblGridY=new JLabel("v steps", Icons.ABOUT, JLabel.LEFT);
		lblDrawMode=new JLabel(Messages.getString("panel.body.drawmode"), Icons.ABOUT, JLabel.LEFT);
		
		spinGridX=new JSpinner();
		spinGridX.setModel(new javax.swing.SpinnerNumberModel(15, 1, 100, 1));
		spinGridX.setValue(object3D.getuGrids());
		spinGridX.setMaximumSize(spinGridX.getSize());
		spinGridY=new JSpinner();
		spinGridY.setModel(new javax.swing.SpinnerNumberModel(15, 1, 100, 1));
		spinGridY.setValue(object3D.getvGrids());
		spinGridY.setMaximumSize(spinGridX.getSize());
		comboFillMode = new JComboBox();
		comboFillMode.setModel(new DefaultComboBoxModel(new String[] { "Solid", "WireFrame", "Solid Gradient" }));
		//comboDrawMode.setSelectedIndex(0);
		comboFillMode.setSelectedIndex(surface3D.getFillmode());
		comboFillMode.setMaximumSize(new Dimension(10000,0));
		this.txtExpression = new JTextArea(object3D.getExpression());
		//txtExpression.setPreferredSize(new Dimension(150,100));
		//txtExpression.setMaximumSize(new Dimension(500,200));
		JScrollPane txtPane =new JScrollPane(txtExpression);
		txtPane.setPreferredSize(new Dimension(200,100));
		txtPane.setMinimumSize(new Dimension(200,90));
		
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
						.addComponent(this.lblDrawMode))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtPane)
						.addComponent(this.spinGridX)
						.addComponent(this.spinGridY)
						.addComponent(this.comboFillMode)));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblExpression)
						.addComponent(txtPane))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblGridX)
						.addComponent(this.spinGridX))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblGridY)
						.addComponent(this.spinGridY))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblDrawMode)
						.addComponent(this.comboFillMode)));
		
		//this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.setLayout(new BorderLayout());
		add(objectCreatePanel);
	}
	
	
	@Override
	public boolean isValidInput() {
		Element3Dfunction e=new Element3Dfunction(txtExpression.getText());
		try {
			return e.parse();
		} catch (Exception e1) {
			errorMsg=e1.getMessage();
			e1.printStackTrace();
			return false;
		}
	}

	
	@Override
	public void showInvalidInputMessage(Window owner) {
		JOptionPane.showMessageDialog(owner, errorMsg, Messages.getString("panel.invalid.title"), JOptionPane.ERROR_MESSAGE);
	}

	
	@Override
	public Element3D getObject3D() {
		if (isValidInput()) {
			surface3D=new Element3Dfunction(txtExpression.getText());
			surface3D.setuGrids((Integer)spinGridX.getValue());
			surface3D.setvGrids((Integer)spinGridY.getValue());
			surface3D.setFillmode(comboFillMode.getSelectedIndex());
			return surface3D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3DSurface)) return;
		surface3D=(Element3Dfunction) object3d;
		txtExpression.setText(surface3D.getExpression());
		spinGridX.setValue(surface3D.getuGrids());
		spinGridY.setValue(surface3D.getvGrids());
		comboFillMode.setSelectedIndex(surface3D.getFillmode());
	}

}
