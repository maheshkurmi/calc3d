package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3Dcartesian2D;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;

public class CartesianCurve2DPanel extends Object3DCreatePanel implements ItemListener {

	private JLabel lblFuncType;
	private JLabel lblExpression;
	private JLabel lblMinX;
	private JLabel lblMaxX;
	private JLabel lblSegments;
	
	/** The expression text field */
	private JTextArea txtExpression;
		
	private JFormattedTextField txtMinX;
	private JFormattedTextField txtMaxX;
	
	/** The Spinner for number of sements of curve */
	private JSpinner spinSegments;
	
	/**Combo box to select function modes Polr/cartesian*/
	private JComboBox<String> comboFunctype;
	
	private Element3Dcartesian2D curve2D;
	
	private int funcType=0;
	
	public CartesianCurve2DPanel(Element3Dcartesian2D object3D){
		curve2D=object3D;
		lblFuncType=new JLabel("Type", Icons.ABOUT, JLabel.LEFT);
		lblExpression=new JLabel("y(x)", Icons.ABOUT, JLabel.LEFT);
		lblMinX=new JLabel("Min. value of  x", Icons.ABOUT, JLabel.LEFT);
		lblMaxX=new JLabel("Max. value of  x", Icons.ABOUT, JLabel.LEFT);
		lblSegments=new JLabel("Segments", Icons.ABOUT, JLabel.LEFT);
		
		spinSegments=new JSpinner();
		spinSegments.setModel(new javax.swing.SpinnerNumberModel(40, 1, 200, 1));
		spinSegments.setValue(object3D.getNumSegments());
		spinSegments.setMaximumSize(getSize());
	
		this.txtExpression =  new JTextArea(object3D.getExpression());
		JScrollPane txtPane =new JScrollPane(txtExpression);
		txtPane.setPreferredSize(new Dimension(200,100));
		txtPane.setMinimumSize(new Dimension(200,90));
		
		this.txtMinX = new JFormattedTextField(new DecimalFormat());
		this.txtMinX.setValue(curve2D.getMin_X());
		this.txtMinX.setColumns(7);
		this.txtMaxX = new JFormattedTextField(new DecimalFormat());
		this.txtMaxX.setValue(curve2D.getMax_X());
		this.txtMaxX.setColumns(7);
	
		funcType=curve2D.getFuncType();
		comboFunctype = new JComboBox<String>();
		comboFunctype.setModel(new DefaultComboBoxModel<String>(new String[] { "Cartesian Curve", "Polar Curve"}));
		comboFunctype.addItemListener(this);
		comboFunctype.setSelectedIndex(funcType);
		txtExpression.setText(object3D.getExpression());
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
						.addComponent(this.lblSegments)
						)
					
				.addGroup(layout.createParallelGroup()
						.addComponent(this.comboFunctype)
						.addComponent(txtPane)
						.addComponent(this.spinSegments)
						));
					
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblFuncType)
						.addComponent(this.comboFunctype))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblExpression)
						.addComponent(txtPane))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(this.lblSegments)
						.addComponent(this.spinSegments))
	           );
		this.setLayout(new BorderLayout());
		add(objectCreatePanel);
	}
	
	
	@Override
	public boolean isValidInput() {
		Element3Dcartesian2D e=new Element3Dcartesian2D(txtExpression.getText());
		e.setFuncType(funcType);
        try {
			e.parse();
		} catch (Exception e1) {
			e1.printStackTrace();
			errorMsg=e1.getMessage();
			return false;
		}
        return true;
		/*
		Calculable calc;
		double minx,maxx;
		try {
			PieceFunction.parse(txtExpression.getText());
			/*
			calc = new ExpressionBuilder(txtExpression.getText()).withVariableNames((funcType==0)?"x":"t")
			 	.build();
			minx=((Number)txtMinX.getValue()).doubleValue();
			maxx=((Number)txtMaxX.getValue()).doubleValue();
			if (minx>=maxx) return false;
			
		} catch (Exception e) {
			errorMsg=e.toString();
			return false;
		}
		return true;
		*/
	}

	
	@Override
	public void showInvalidInputMessage(Window owner) {
		JOptionPane.showMessageDialog(owner,errorMsg, Messages.getString("panel.invalid.title"), JOptionPane.ERROR_MESSAGE);
	}

	
	@Override
	public Element3D getObject3D() {
		if (isValidInput()) {
			curve2D=new Element3Dcartesian2D(txtExpression.getText());
			curve2D.setMin_X(((Number)txtMinX.getValue()).doubleValue());
			curve2D.setMax_X(((Number)txtMaxX.getValue()).doubleValue());
			curve2D.setNumSegments((Integer) spinSegments.getValue());
			curve2D.setFuncType(funcType);
			return curve2D;
		}else
		return null;
	}


	@Override
	public void setObject3D(Element3D object3d) {
		if (!(object3d instanceof Element3Dcartesian2D)) return;
		curve2D=(Element3Dcartesian2D) object3d;
		txtExpression.setText(curve2D.getExpression());
		txtMinX.setValue(curve2D.getMin_X());
		txtMaxX.setValue(curve2D.getMax_X());
		spinSegments.setValue(curve2D.getNumSegments());
		funcType=curve2D.getFuncType();
		comboFunctype.setSelectedIndex(funcType);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		 if (e.getStateChange() == ItemEvent.SELECTED) {
	            JComboBox combo = (JComboBox) e.getSource();
	            funcType  = combo.getSelectedIndex();
	            switch(funcType){
	            case 0:{
	            	lblExpression.setText("Expression y(x)");
	            	txtExpression.setText("");
	            	lblMinX.setText("Min. value of  x:");
	            	lblMaxX.setText("Max. value of  x:");
	            	break;
	            }
	            case 1:{	
	            	lblExpression.setText("Expression r(t)");
	            	txtExpression.setText("");
	            	lblMinX.setText("Min. value of  t:");
	            	lblMaxX.setText("Max. value of  t:");
	            
	            	break;
	            }
	            	
	            }
	     }
	}
}
