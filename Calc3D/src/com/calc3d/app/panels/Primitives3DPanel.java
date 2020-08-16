package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DObject;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;

public class Primitives3DPanel extends Object3DCreatePanel implements ItemListener  {

	private JLabel lblExpression;
	private JLabel lblparam_a;
	private JLabel lblparam_b;
	private JLabel lblparam_c;
	
	private JLabel lblDrawMode;
	
	/**The Parameters textField*/
	private JFormattedTextField txtParam_a;//fist parameter
	private JFormattedTextField txtParam_b;//second parameter
	private JFormattedTextField txtParam_c;//third parameter
	
	//Combo to selct Object type
	private JComboBox comboPrimitiveType;
	
	private JComboBox comboFillMode;
	
	private Element3DObject surface3D;
	
	/**parametrs a,b,c*/
	private double a, b, c;
	
	/**ObjectCode*/
	private int objectCode;
	
	public Primitives3DPanel(Element3DObject object3D){
		surface3D=object3D;
		objectCode=surface3D.getObjectCode();
		lblExpression=new JLabel("PremitiveType", Icons.ABOUT, JLabel.LEFT);
		lblparam_a=new JLabel(object3D.getParamNames()[0], Icons.ABOUT, JLabel.LEFT);
		lblparam_b=new JLabel(object3D.getParamNames()[1], Icons.ABOUT, JLabel.LEFT);
		lblparam_c=new JLabel(object3D.getParamNames()[2], Icons.ABOUT, JLabel.LEFT);
		
		lblDrawMode=new JLabel(Messages.getString("panel.body.drawmode"), Icons.ABOUT, JLabel.LEFT);

		comboPrimitiveType = new JComboBox();
		comboPrimitiveType.setModel(new DefaultComboBoxModel(new String[] { "Regular Polygon", "Right Regular Pyramid", 
				 "Right Regular Prism","Ellipsoid","Simple Torus","Pie" }));
		
		comboPrimitiveType.setSelectedIndex(objectCode);
		comboPrimitiveType.addItemListener(this);
		txtParam_a= new JFormattedTextField(new DecimalFormat());
		txtParam_b= new JFormattedTextField(new DecimalFormat());
		txtParam_c= new JFormattedTextField(new DecimalFormat());
		
		txtParam_a.setValue(object3D.getParameters()[0]);
		txtParam_b.setValue(object3D.getParameters()[1]);
		txtParam_c.setValue(object3D.getParameters()[2]);
				
		comboFillMode = new JComboBox();
		comboFillMode.setModel(new DefaultComboBoxModel(new String[] { "Solid", "WireFrame", "Solid Gradient" }));
		//comboDrawMode.setSelectedIndex(0);
		comboFillMode.setSelectedIndex(surface3D.getFillmode());
		//this.txtExpression = new JTextField(object3D.getExpression());
		comboFillMode.setMaximumSize(new Dimension(10000,0));
		
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
						.addComponent(this.lblparam_a)
						.addComponent(this.lblparam_b)
						.addComponent(this.lblparam_c)
						.addComponent(this.lblDrawMode))
				.addGroup(layout.createParallelGroup()
						.addComponent(this.comboPrimitiveType)
						.addComponent(this.txtParam_a)
						.addComponent(this.txtParam_b)
						.addComponent(this.txtParam_c)
						.addComponent(this.comboFillMode)));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblExpression)
						.addComponent(this.comboPrimitiveType))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblparam_a)
						.addComponent(this.txtParam_a))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblparam_b)
						.addComponent(this.txtParam_b))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblparam_c)
						.addComponent(this.txtParam_c))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblDrawMode)
						.addComponent(this.comboFillMode)));
		this.setLayout(new BorderLayout());
		add(objectCreatePanel);
	}
	
	
	@Override
	public boolean isValidInput() {
		try {
			a=Double.parseDouble(txtParam_a.getText());
			b=Double.parseDouble(txtParam_b.getText());
			c=Double.parseDouble(txtParam_c.getText());
			//if((a==0)||(b==0)||(c==0)) return false;
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
			surface3D=new Element3DObject(objectCode,a,b,c);
			surface3D.setFillmode(comboFillMode.getSelectedIndex());
			return surface3D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3DObject)) return;
		surface3D=(Element3DObject) object3d;
		comboPrimitiveType.setSelectedIndex(surface3D.getObjectCode());
		lblparam_a.setText(Element3DObject.getParameterNames(objectCode)[0]);
        lblparam_b.setText(Element3DObject.getParameterNames(objectCode)[1]);
        lblparam_c.setText(Element3DObject.getParameterNames(objectCode)[2]);
		txtParam_a.setValue(surface3D.getParameters()[0]);
		txtParam_b.setValue(surface3D.getParameters()[1]);
		txtParam_c.setValue(surface3D.getParameters()[2]);
		comboFillMode.setSelectedIndex(surface3D.getFillmode());
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		 if (e.getStateChange() == ItemEvent.SELECTED) {
	            JComboBox combo = (JComboBox) e.getSource();
	            objectCode = combo.getSelectedIndex();
	            surface3D=new Element3DObject(objectCode);
	            setObject3D(surface3D);
	            surface3D.setName(surface3D.getObjectName());
	     }
	}

}
