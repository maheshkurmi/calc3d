package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Window;
import java.text.DecimalFormat;

import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DPlane;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.geometry3d.Plane3D;

public class Plane3DPanel extends Object3DCreatePanel  {

	private JLabel lblInfo;
	private JLabel lblCoeffX;
	private JLabel lblCoeffY;
	private JLabel lblCoeffZ;
	private JLabel lblConstant;
	
	/** The expression text field */
	private JFormattedTextField txtCoeffX;
	private JFormattedTextField txtCoeffY;
	private JFormattedTextField txtCoeffZ;
	private JFormattedTextField txtConstant;
	
		
	private Element3DPlane plane3D;
	private Plane3D plane;
	
	public Plane3DPanel(Element3DPlane object3D){
		plane3D=object3D;
		lblInfo=new JLabel(Messages.getString("panel.body.expression"), Icons.ABOUT, JLabel.LEFT);
		lblCoeffX=new JLabel("Coefficent of x", Icons.ABOUT, JLabel.LEFT);
		lblCoeffY=new JLabel("Coefficent of y", Icons.ABOUT, JLabel.LEFT);
		lblCoeffZ=new JLabel("Coefficent of z", Icons.ABOUT, JLabel.LEFT);
		
		lblConstant=new JLabel("Constant term", Icons.ABOUT, JLabel.LEFT);
		
		plane=plane3D.getPlane3D();
		
		this.txtCoeffX = new JFormattedTextField(new DecimalFormat());
		this.txtCoeffX.setValue(plane.get_a());
		this.txtCoeffX.setColumns(7);
		//this.txtCoeffX.setMaximumSize(this.txtCoeffX.getPreferredSize());
		this.txtCoeffX.setEditable(true);
		
		
		this.txtCoeffY = new JFormattedTextField(new DecimalFormat());
		this.txtCoeffY.setValue(plane.get_b());
		this.txtCoeffY.setColumns(7);
		//this.txtCoeffY.setMaximumSize(this.txtCoeffY.getPreferredSize());
		this.txtCoeffY.setEditable(true);
		
		this.txtCoeffZ = new JFormattedTextField(new DecimalFormat());
		this.txtCoeffZ.setValue(plane.get_c());
		this.txtCoeffZ.setColumns(7);
		//this.txtCoeffZ.setMaximumSize(this.txtCoeffZ.getPreferredSize());
		this.txtCoeffZ.setEditable(true);
		
	
		this.txtConstant = new JFormattedTextField(new DecimalFormat());
		this.txtConstant.setValue(plane.get_d());
		this.txtConstant.setColumns(7);
		//this.txtConstant.setMaximumSize(this.txtConstant.getPreferredSize());
		this.txtConstant.setEditable(true);
		
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
						.addComponent(this.lblCoeffX)
						.addComponent(this.lblCoeffY)
						.addComponent(this.lblCoeffZ)
						.addComponent(this.lblConstant))
				.addGroup(layout.createParallelGroup()
						.addComponent(this.txtCoeffX)
						.addComponent(this.txtCoeffY)
						.addComponent(this.txtCoeffZ)
						.addComponent(this.txtConstant)));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblCoeffX)
						.addComponent(this.txtCoeffX))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblCoeffY)
						.addComponent(this.txtCoeffY))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblCoeffZ)
						.addComponent(this.txtCoeffZ))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblConstant)
						.addComponent(this.txtConstant)));
		
		this.setLayout(new BorderLayout());
		add(objectCreatePanel);
	}
	
	
	@Override
	public boolean isValidInput() {
		try {
			double a, b, c ,d;
			a=Double.parseDouble(txtCoeffX.getText());
			b=Double.parseDouble(txtCoeffY.getText());
			c=Double.parseDouble(txtCoeffZ.getText());
			d=Double.parseDouble(txtConstant.getText());
			if((a==0)&(b==0)&(c==0)) {
				errorMsg="invalid coefficients of plane";
				return false;
			}
			plane=new Plane3D(a,b,c,d);
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
			plane3D=new Element3DPlane(plane);
			return plane3D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3DPlane)) return;
		plane3D=(Element3DPlane) object3d;
		plane=plane3D.getPlane3D();
		this.txtCoeffX.setValue(plane.get_a());
		this.txtCoeffY.setValue(plane.get_b());
		this.txtCoeffZ.setValue(plane.get_c());
		this.txtConstant.setValue(plane.get_d());
	}

}
