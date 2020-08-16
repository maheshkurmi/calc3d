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
import com.calc3d.app.elements.Element3DPoint;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.math.Vector3D;

public class Point3DPanel extends Object3DCreatePanel  {

	private JLabel lblXcoord;
	private JLabel lblYcoord;
	private JLabel lblZcoord;
	private JLabel lblRadius;
	private JLabel lblText;
	
	
	/** The expression text field */
	private JFormattedTextField txtXcoord;
	private JFormattedTextField txtYcoord;
	private JFormattedTextField txtZcoord;
	private JFormattedTextField txtRadius;
	private JFormattedTextField txtText;
	
		
	private Element3DPoint point3D;
	private Vector3D point;
	
	
	public Point3DPanel(Element3DPoint object3D){
		point3D=object3D;
		point=point3D.getPoint();
		lblXcoord=new JLabel("x coordinate", Icons.ABOUT, JLabel.LEFT);
		lblYcoord=new JLabel("y coordinate", Icons.ABOUT, JLabel.LEFT);
		lblZcoord=new JLabel("z coordinate", Icons.ABOUT, JLabel.LEFT);
		lblRadius=new JLabel("DrawRadius", Icons.ABOUT, JLabel.LEFT);
		lblText=new JLabel("Display Text", Icons.ABOUT, JLabel.LEFT);
		
			
		this.txtXcoord = new JFormattedTextField(new DecimalFormat());
		this.txtXcoord.setValue(point.getX());
		this.txtXcoord.setColumns(3);
		//this.txtCoeffX.setMaximumSize(this.txtCoeffX.getPreferredSize());
		this.txtXcoord.setEditable(true);
		
		
		this.txtYcoord = new JFormattedTextField(new DecimalFormat());
		this.txtYcoord.setValue(point.getY());
		this.txtYcoord.setColumns(3);
		//this.txtCoeffY.setMaximumSize(this.txtCoeffY.getPreferredSize());
		this.txtYcoord.setEditable(true);
		
		this.txtZcoord = new JFormattedTextField(new DecimalFormat());
		this.txtZcoord.setValue(point.getZ());
		this.txtZcoord.setColumns(3);
		this.txtZcoord.setEditable(true);
		
		this.txtRadius = new JFormattedTextField(new DecimalFormat("0"));
		this.txtRadius.setValue(point3D.getRadius());
		this.txtRadius.setColumns(3);
		this.txtRadius.setEditable(true);
		
		this.txtText = new JFormattedTextField();
		this.txtText.setValue(point3D.getText());
		this.txtText.setColumns(1);
		this.txtText.setEditable(true);
		
	
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
						.addComponent(this.lblXcoord)
						.addComponent(this.lblYcoord)
						.addComponent(this.lblZcoord)
						.addComponent(this.lblRadius)
						.addComponent(this.lblText)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(this.txtXcoord)
						.addComponent(this.txtYcoord)
						.addComponent(this.txtZcoord)
						.addComponent(this.txtRadius)
						.addComponent(this.txtText)
						
						));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblXcoord)
						.addComponent(this.txtXcoord))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblYcoord)
						.addComponent(this.txtYcoord))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblZcoord)
						.addComponent(this.txtZcoord))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblRadius)
						.addComponent(this.txtRadius))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblText)
						.addComponent(this.txtText))
						);
		
		this.setLayout(new BorderLayout());
		add(objectCreatePanel);
	}
	
	int radius =1;
	@Override
	public boolean isValidInput() {
		try {
			double a, b, c ,d;
			a=((Number)txtXcoord.getValue()).doubleValue();//Double.parseDouble(txtXcoord.getText());
			b=((Number)txtYcoord.getValue()).doubleValue();
			c=((Number)txtZcoord.getValue()).doubleValue();
			d=((Number)txtRadius.getValue()).doubleValue();//Double.parseDouble(txtRadius.getText());
			point.set(a,b,c);
			radius=(int) Math.max(1, d);
		} catch (Exception e) {
			errorMsg=e.toString();
			return false;
		}
		return true;
	}

	
	@Override
	public void showInvalidInputMessage(Window owner) {
		JOptionPane.showMessageDialog(owner,errorMsg, Messages.getString("panel.invalid.title"), JOptionPane.ERROR_MESSAGE);
	}

	
	@Override
	public Element3D getObject3D() {
		if (isValidInput()) {
			point3D=new Element3DPoint(point);
			point3D.setRadius(radius);
			point3D.setText(txtText.getText());
			return point3D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3DPoint)) return;
		point3D=(Element3DPoint) object3d;
		point=point3D.getPoint();
		this.txtXcoord.setValue(point.getX());
		this.txtYcoord.setValue(point.getY());
		this.txtZcoord.setValue(point.getZ());
		this.txtRadius.setValue(point3D.getRadius());
		this.txtText.setText(point3D.getText());
	}

}
