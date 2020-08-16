package com.calc3d.app.panels;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;


import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DPlane;
import com.calc3d.app.elements.Element3DPolygon;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.geometry3d.ElementPoly;
import com.calc3d.geometry3d.Geometry;
import com.calc3d.geometry3d.Plane3D;
import com.calc3d.math.Vector3D;

public class Polygon3DPanel extends Object3DCreatePanel implements  ActionListener  {

	private JLabel lblInfo;
	
	/** The default point count of the unit circle polygon */
	private static final int DEFAULT_COUNT = 5;
	
	/** The default radius of the unit circle polygon */
	private static final double DEFAULT_RADIUS = 0.5;
	
	/** The default polygon is just a unit circle polygon */
	private static final Element3DPolygon DEFAULT_POLYGON = Element3DPolygon.createUnitCirclePolygon(DEFAULT_COUNT, DEFAULT_RADIUS);
	
	/** The list of point panels */
	private List<PointPanel> pointPanels = new ArrayList<PointPanel>();
	
	/** The panel containing the point panels */
	private JPanel pnlPanel;
	
	/** The scroll panel for the point panels */
	private JScrollPane scrPane;
	
	/** The text label for the polygon help */
	private JTextPane lblText;
	
	private Element3DPolygon polygon3D;
	private Plane3D plane;
	
	
	public Polygon3DPanel(Element3DPolygon element){
		polygon3D=element;
		lblInfo=new JLabel(Messages.getString("panel.body.expression"), Icons.ABOUT, JLabel.LEFT);
        this.pnlPanel = new JPanel();
		
		this.lblText = new JTextPane();
		this.lblText.setContentType("text/html");
		this.lblText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		this.lblText.setText(Messages.getString("panel.polygon.convex.warning"));
		this.lblText.setEditable(false);
		this.lblText.setPreferredSize(new Dimension(350, 50));

        this.scrPane = new JScrollPane(this.pnlPanel);
		
       // ElementPoly e=ElementPoly.createUnitCirclePolygon(4, 4, 1);
    	
    	for (Vector3D v: element.vertices) {
			PointPanel panel = new PointPanel(v.getX(), v.getY(),v.getZ());
			panel.addActionListener(this);
			this.pointPanels.add(panel);
		}
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(this.lblText)
				.addComponent(this.scrPane)
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(this.lblText)
				.addComponent(this.scrPane)
				);
		
		this.createLayout();

	}
	/**
	 * Creates the layout for the panel.
	 */
	private void createLayout() {
		// remove all the components
		this.pnlPanel.removeAll();
		
		// recreate the layout
		GroupLayout layout = new GroupLayout(this.pnlPanel);
		this.pnlPanel.setLayout(layout);
		
		// set all the flags
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(false);
		
		int size = this.pointPanels.size();
		
		// create the horizontal layout
		ParallelGroup hGroup = layout.createParallelGroup();
		for (int i = 0; i < size; i++) {
			PointPanel panel = this.pointPanels.get(i);
			hGroup.addComponent(panel);
			if (i < 3) {
				panel.btnRemove.setEnabled(false);
			} else {
				panel.btnRemove.setEnabled(true);
			}
		}
		// create the vertical layout
		SequentialGroup vGroup = layout.createSequentialGroup();
		for (int i = 0; i < size; i++) {
			PointPanel panel = this.pointPanels.get(i);
			vGroup.addComponent(panel);
		}
		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// find the point panel issuing the event
		int index = this.pointPanels.indexOf(event.getSource());
		// check if its found
		if (index >= 0) {
			// check the type of event
			if ("add".equals(event.getActionCommand())) {
				// insert a new point panel after this one
				PointPanel panel = new PointPanel();
				panel.addActionListener(this);
				this.pointPanels.add(index + 1, panel);
				// redo the layout
				this.createLayout();
				//pnlPreview.setPoints(this.getPoints());
			} else if ("remove".equals(event.getActionCommand())) {
				// remove the point panel from the list
				this.pointPanels.remove(index);
				// redo the layout
				this.createLayout();
				//pnlPreview.setPoints(this.getPoints());
			} else if ("changed".equals(event.getActionCommand())) {
				// a value has changed
				//pnlPreview.setPoints(this.getPoints());
			}
		}
	}
	@Override
	public boolean isValidInput() {
		return (Geometry.cleanse(getPoints()).length<3)? false: true ;
	}

	
	@Override
	public void showInvalidInputMessage(Window owner) {
		JOptionPane.showMessageDialog(owner, errorMsg, Messages.getString("panel.invalid.title"), JOptionPane.ERROR_MESSAGE);
	}

	
	@Override
	public Element3D getObject3D() {
		if (isValidInput()) {
			polygon3D=new Element3DPolygon(Geometry.cleanse(getPoints()));
			return polygon3D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		// remove all the components
		this.pnlPanel.removeAll();
		for (Vector3D v: ((Element3DPolygon)object3d).vertices) {
			PointPanel panel = new PointPanel(v.getX(), v.getY(),v.getZ());
			panel.addActionListener(this);
			this.pointPanels.add(panel);
		}
	}


	/**
	 * Returns a list of points from the point panels.
	 * @return Vector2[]
	 */
	private Vector3D[] getPoints() {
		int size = this.pointPanels.size();
		Vector3D[] points = new Vector3D[size];
		for (int i = 0; i < size; i++) {
			PointPanel panel = this.pointPanels.get(i);
			points[i] = new Vector3D(panel.getValueX(), panel.getValueY(),panel.getValueZ());
		}
		return points;
	}
	
}
