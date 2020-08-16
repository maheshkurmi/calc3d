package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Window;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DVector;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.math.Vector3D;

public class Vector3DPanel extends Object3DCreatePanel  {

	private JLabel lblXcoord1;
	private JLabel lblYcoord1;
	private JLabel lblZcoord1;
	private JLabel lblXcoord2;
	private JLabel lblYcoord2;
	private JLabel lblZcoord2;
	/** The expression text field */
	private JFormattedTextField txtXcoord1;
	private JFormattedTextField txtYcoord1;
	private JFormattedTextField txtZcoord1;
	private JFormattedTextField txtXcoord2;
	private JFormattedTextField txtYcoord2;
	private JFormattedTextField txtZcoord2;
	
		
	private Element3DVector vector3D;
	private Vector3D point1;
	private Vector3D point2;
	
	
	public Vector3DPanel(Element3DVector object3D){
		vector3D=object3D;
		point1=vector3D.getPoint1();
		point2=vector3D.getPoint2();
		
		lblXcoord1=new JLabel("x coordinate (p1)", Icons.ABOUT, JLabel.LEFT);
		lblYcoord1=new JLabel("y coordinate (p1)", Icons.ABOUT, JLabel.LEFT);
		lblZcoord1=new JLabel("z coordinate (p1)", Icons.ABOUT, JLabel.LEFT);
		lblXcoord2=new JLabel("x coordinate (p2)", Icons.ABOUT, JLabel.LEFT);
		lblYcoord2=new JLabel("y coordinate (p2)", Icons.ABOUT, JLabel.LEFT);
		lblZcoord2=new JLabel("z coordinate (p2)", Icons.ABOUT, JLabel.LEFT);
		
				
		this.txtXcoord1 = new JFormattedTextField(new DecimalFormat());
		this.txtXcoord1.setValue(point1.getX());
		this.txtXcoord1.setColumns(7);
		//this.txtCoeffX.setMaximumSize(this.txtCoeffX.getPreferredSize());
		this.txtXcoord1.setEditable(true);
		
		
		this.txtYcoord1 = new JFormattedTextField(new DecimalFormat());
		this.txtYcoord1.setValue(point1.getY());
		this.txtYcoord1.setColumns(7);
		//this.txtCoeffY.setMaximumSize(this.txtCoeffY.getPreferredSize());
		this.txtYcoord1.setEditable(true);
		
		this.txtZcoord1 = new JFormattedTextField(new DecimalFormat());
		this.txtZcoord1.setValue(point1.getZ());
		this.txtZcoord1.setColumns(7);
		//this.txtCoeffZ.setMaximumSize(this.txtCoeffZ.getPreferredSize());
		this.txtZcoord1.setEditable(true);
		
		this.txtXcoord2 = new JFormattedTextField(new DecimalFormat());
		this.txtXcoord2.setValue(point2.getX());
		this.txtXcoord2.setColumns(7);
		//this.txtCoeffX.setMaximumSize(this.txtCoeffX.getPreferredSize());
		this.txtXcoord2.setEditable(true);
		
		
		this.txtYcoord2 = new JFormattedTextField(new DecimalFormat());
		this.txtYcoord2.setValue(point2.getY());
		this.txtYcoord2.setColumns(7);
		//this.txtCoeffY.setMaximumSize(this.txtCoeffY.getPreferredSize());
		this.txtYcoord2.setEditable(true);
		
		this.txtZcoord2 = new JFormattedTextField(new DecimalFormat());
		this.txtZcoord2.setValue(point2.getZ());
		this.txtZcoord2.setColumns(7);
		//this.txtCoeffZ.setMaximumSize(this.txtCoeffZ.getPreferredSize());
		this.txtZcoord2.setEditable(true);
		
				
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
		
		//.addComponent(this.lblInfo)
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(this.lblXcoord1)
						.addComponent(this.lblYcoord1)
						.addComponent(this.lblZcoord1)
						.addComponent(this.lblXcoord2)
						.addComponent(this.lblYcoord2)
						.addComponent(this.lblZcoord2))
				.addGroup(layout.createParallelGroup()
						.addComponent(this.txtXcoord1)
						.addComponent(this.txtYcoord1)
						.addComponent(this.txtZcoord1)
						.addComponent(this.txtXcoord2)
						.addComponent(this.txtYcoord2)
						.addComponent(this.txtZcoord2)));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblXcoord1)
						.addComponent(this.txtXcoord1))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblYcoord1)
						.addComponent(this.txtYcoord1))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblZcoord1)
						.addComponent(this.txtZcoord1))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblXcoord2)
						.addComponent(this.txtXcoord2))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblYcoord2)
						.addComponent(this.txtYcoord2))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblZcoord2)
						.addComponent(this.txtZcoord2)));
		
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.setLayout(new BorderLayout());
		add(objectCreatePanel);
	}
	
	int radius =1;
	@Override
	public boolean isValidInput() {
		try {
			double a, b, c;
			//a=Double.parseDouble(txtXcoord1.getText());
			a=((Number)txtXcoord1.getValue()).doubleValue();
			b=((Number)txtYcoord1.getValue()).doubleValue();
			c=((Number)txtZcoord1.getValue()).doubleValue();
			point1.set(a,b,c);
			a=((Number)txtXcoord2.getValue()).doubleValue();
			b=((Number)txtYcoord2.getValue()).doubleValue();
			c=((Number)txtZcoord2.getValue()).doubleValue();
			point2.set(a,b,c);
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
			vector3D=new Element3DVector(point1,point2);
			return vector3D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3DVector)) return;
		vector3D=(Element3DVector) object3d;
		point1=vector3D.getPoint1();
		point2=vector3D.getPoint2();
		this.txtXcoord1.setValue(point1.getX());
		this.txtYcoord1.setValue(point1.getY());
		this.txtZcoord1.setValue(point1.getZ());
		this.txtXcoord2.setValue(point2.getX());
		this.txtYcoord2.setValue(point2.getY());
		this.txtZcoord2.setValue(point2.getZ());
	
	}

}
