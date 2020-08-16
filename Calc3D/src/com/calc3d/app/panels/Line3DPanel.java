package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DLine;
import com.calc3d.app.elements.Element3DLineSegment;
import com.calc3d.app.elements.Element3DPlane;
import com.calc3d.app.elements.Element3DPoint;
import com.calc3d.app.elements.Element3DSurface;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.geometry3d.Line3D;
import com.calc3d.geometry3d.Plane3D;
import com.calc3d.math.Vector3D;
import com.calc3d.mathparser.Calculable;
import com.calc3d.mathparser.ExpressionBuilder;

public class Line3DPanel extends Object3DCreatePanel  {

	private JLabel lblEqn;
	private JLabel lblXcoord1;
	private JLabel lblYcoord1;
	private JLabel lblZcoord1;
	private JLabel lblXcoord2;
	private JLabel lblYcoord2;
	private JLabel lblZcoord2;
	private JLabel lblPoint1;
	private JLabel lblPoint2;
	private JLabel lblsegment;
	private JLabel lbldrawPoints;
	
	/** The expression text field */
	private JTextField txtEqn;
	private JFormattedTextField txtXcoord1;
	private JFormattedTextField txtYcoord1;
	private JFormattedTextField txtZcoord1;
	private JFormattedTextField txtXcoord2;
	private JFormattedTextField txtYcoord2;
	private JFormattedTextField txtZcoord2;
	
	/*checkbox to toggle between line segment and line*/
	private JCheckBox chkSegment;
	/*checkbox to toggle between line segment and lindraw end points as circcles or not*/
	private JCheckBox chkdrawPoints;
	
	private Element3DLine line3D;
	private Vector3D point1;
	private Vector3D point2;
	
	
	public Line3DPanel(Element3DLine object3D){
		line3D=object3D;
		point1=line3D.getPt1();
		point2=line3D.getPt2();		
		lblEqn=new JLabel("Equation", Icons.ABOUT, JLabel.LEFT);
		lblXcoord1=new JLabel("x  ", null, JLabel.LEFT);
		lblYcoord1=new JLabel("y  ", null, JLabel.LEFT);
		lblZcoord1=new JLabel("z", null, JLabel.LEFT);
		lblXcoord2=new JLabel("x  ", null, JLabel.LEFT);
		lblYcoord2=new JLabel("y  ", null, JLabel.LEFT);
		lblZcoord2=new JLabel("z", null, JLabel.LEFT);
		lblPoint1=new JLabel("Point1", Icons.ABOUT, JLabel.LEFT);
		lblPoint2=new JLabel("Point2", Icons.ABOUT, JLabel.LEFT);
		lblsegment=new JLabel("Line Segment", Icons.ABOUT, JLabel.LEFT);
		lbldrawPoints=new JLabel("Draw Points", Icons.ABOUT, JLabel.LEFT);
		chkdrawPoints=new JCheckBox();
		chkSegment=new JCheckBox();
		chkdrawPoints.setSelected(line3D.isDrawPoints());
		chkSegment.setSelected(line3D.isLineSegment());
		this.txtEqn=new JTextField();
		txtEqn.setText(line3D.getExpression());
		txtEqn.setEditable(false);
		
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
		
		JPanel pnlPoint1=new JPanel();
		
		pnlPoint1.setLayout(new BoxLayout(pnlPoint1,BoxLayout.LINE_AXIS));
		pnlPoint1.add(txtXcoord1);
		pnlPoint1.add(lblXcoord1);
		pnlPoint1.add(txtYcoord1);
		pnlPoint1.add(lblYcoord1);
		pnlPoint1.add(txtZcoord1);
		pnlPoint1.add(lblZcoord1);
		
		JPanel pnlPoint2=new JPanel();
		pnlPoint2.setLayout(new BoxLayout(pnlPoint2,BoxLayout.LINE_AXIS));
		pnlPoint2.add(txtXcoord2);
		pnlPoint2.add(lblXcoord2);
		pnlPoint2.add(txtYcoord2);
		pnlPoint2.add(lblYcoord2);
		pnlPoint2.add(txtZcoord2);
		pnlPoint2.add(lblZcoord2);
				
			
				
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
						.addComponent(this.lblEqn)
						.addComponent(this.lblPoint1)
						.addComponent(this.lblPoint2)
						.addComponent(this.lbldrawPoints)
						.addComponent(this.lblsegment)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(this.txtEqn)
						.addComponent(pnlPoint1)
						.addComponent(pnlPoint2)
						.addComponent(this.chkdrawPoints)
						.addComponent(this.chkSegment)
						));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblEqn)
						.addComponent(this.txtEqn))				
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblPoint1)
						.addComponent(pnlPoint1))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblPoint2)
						.addComponent(pnlPoint2))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lbldrawPoints)
						.addComponent(this.chkdrawPoints))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblsegment)
						.addComponent(this.chkSegment))
				);
		
		this.setLayout(new BorderLayout());
		add(objectCreatePanel);
	}
	
	int radius =1;
	@Override
	public boolean isValidInput() {
		try {
			double a, b, c;
			a=Double.parseDouble(txtXcoord1.getText());
			b=Double.parseDouble(txtYcoord1.getText());
			c=Double.parseDouble(txtZcoord1.getText());
			point1.set(a,b,c);
			a=Double.parseDouble(txtXcoord2.getText());
			b=Double.parseDouble(txtYcoord2.getText());
			c=Double.parseDouble(txtZcoord2.getText());
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
			line3D=new Element3DLine(point1,point2);
			line3D.setLineSegment(chkSegment.isSelected());
			line3D.setDrawPoints(chkdrawPoints.isSelected());
			return line3D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3DLine)) return;
		line3D=(Element3DLine) object3d;
		point1=line3D.getPt1();
		point2=line3D.getPt2();	
		this.txtEqn.setText(line3D.getExpression());
		this.txtXcoord1.setValue(point1.getX());
		this.txtYcoord1.setValue(point1.getY());
		this.txtZcoord1.setValue(point1.getZ());
		this.txtXcoord2.setValue(point2.getX());
		this.txtYcoord2.setValue(point2.getY());
		this.txtZcoord2.setValue(point2.getZ());
		this.chkdrawPoints.setSelected(line3D.isDrawPoints());
		this.chkSegment.setSelected(line3D.isLineSegment());
	}

}
