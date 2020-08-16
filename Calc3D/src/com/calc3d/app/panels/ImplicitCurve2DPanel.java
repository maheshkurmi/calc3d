package com.calc3d.app.panels;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DCurve;
import com.calc3d.app.elements.Element3DSurface;
import com.calc3d.app.elements.Element3Dcartesian2D;
import com.calc3d.app.elements.Element3Dimplicit2D;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.mathparser.Calculable;
import com.calc3d.mathparser.ExpressionBuilder;

public class ImplicitCurve2DPanel extends Object3DCreatePanel implements ItemListener {
	private JLabel lblFuncType;
	private JLabel lblExpression;
	private JLabel lblGridX;
	private JLabel lblGridY;
	/** The expression text field */
	private JTextField txtExpression;
		

	/** The Spinner for number of sements of curve */
	/** The Spinner for xGrid */
	private JSpinner spinGridX,spinGridY;
	
	private Element3Dimplicit2D curve2D;
	/**Combo box to select function modes Polr/cartesian*/
	private JComboBox<String> comboFunctype;
	private int funcType=0;

	public ImplicitCurve2DPanel(Element3Dimplicit2D object3D){
		curve2D=object3D;
		lblFuncType=new JLabel("Type", Icons.ABOUT, JLabel.LEFT);
		lblExpression=new JLabel(Messages.getString("panel.body.expression"), Icons.ABOUT, JLabel.LEFT);
		lblGridX=new JLabel(Messages.getString("panel.body.gridx"), Icons.ABOUT, JLabel.LEFT);
		lblGridY=new JLabel(Messages.getString("panel.body.gridx"), Icons.ABOUT, JLabel.LEFT);
	
		spinGridX=new JSpinner();
		spinGridX.setModel(new javax.swing.SpinnerNumberModel(15, 1, 100, 1));
		spinGridX.setValue(object3D.getxGrids());
		spinGridX.setMaximumSize(getSize());
		spinGridY=new JSpinner();
		spinGridY.setModel(new javax.swing.SpinnerNumberModel(15, 1, 100, 1));
		spinGridY.setValue(object3D.getyGrids());
		spinGridY.setMaximumSize(getSize());
	
		this.txtExpression = new JTextField(curve2D.getExpression());
		comboFunctype = new JComboBox<String>();
		comboFunctype.setModel(new DefaultComboBoxModel<String>(new String[] { "Cartesian Curve", "Polar Curve"}));
		comboFunctype.addItemListener(this);
		funcType=curve2D.getFuncType();
		comboFunctype.setSelectedIndex(funcType);
		this.txtExpression = new JTextField(curve2D.getExpression());
		
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
						.addComponent(this.lblFuncType)
						.addComponent(this.lblExpression)
						.addComponent(this.lblGridX)
						.addComponent(this.lblGridY))
				.addGroup(layout.createParallelGroup()
						.addComponent(this.comboFunctype)
						.addComponent(this.txtExpression)
						.addComponent(this.spinGridX)
						.addComponent(this.spinGridY)));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblFuncType)
						.addComponent(this.comboFunctype))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblExpression)
						.addComponent(this.txtExpression))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblGridX)
						.addComponent(this.spinGridX))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblGridY)
						.addComponent(this.spinGridY)));
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
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
			calc = new ExpressionBuilder(exprNew).withVariableNames((funcType==0)?"x":"r", (funcType==0)?"y":"t")
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
			curve2D=new Element3Dimplicit2D(txtExpression.getText());
			curve2D.setxGrids((Integer)spinGridX.getValue());
			curve2D.setyGrids((Integer)spinGridY.getValue());
			curve2D.setFuncType(funcType);
			return curve2D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3Dimplicit2D)) return;
		curve2D=(Element3Dimplicit2D) object3d;
		txtExpression.setText(curve2D.getExpression());
		spinGridX.setValue(curve2D.getyGrids());
		spinGridY.setValue(curve2D.getyGrids());
		funcType=curve2D.getFuncType();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		 if (e.getStateChange() == ItemEvent.SELECTED) {
	            JComboBox combo = (JComboBox) e.getSource();
	            funcType  = combo.getSelectedIndex();
	            switch(funcType){
	            case 0:{
	            	lblExpression.setText("Expression f(x,y)=0");
	            	txtExpression.setText("");
	            	break;
	            }
	            case 1:{
	            	lblExpression.setText("Expression f(r,t)=0");
	            	txtExpression.setText("");
	               	break;
	            }
	            	
	            }
	     }
	}
	
}
