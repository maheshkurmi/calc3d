package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.calc3d.app.commonUtils;
import com.calc3d.app.dialogs.ColorDialog;
import com.calc3d.app.elements.Element3D;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.utils.ColorUtils;

public class Object3DgeneralPanel extends JPanel implements InputPanel,ActionListener {
	/** The body being edited */
	private Element3D body;
	
	
	/** The name label */
	private JLabel lblName;
	
	/** The name text field */
	private JTextField txtName;
	
	/**Renderable CurveWidth Spinner*/
	private JLabel lblCurveWidth;
	
	/**Renderable check box label*/
	private JLabel lblDashed;
	
	/**Splittable check box label*/
	private JLabel lblSplittable;
	
	/**CurveWidthSpinner */
	private JSpinner spinCurveWidth;
	
	/**Renderable check box */
	private JCheckBox chkDashed;
	
	/**Splittable check box */
	private JCheckBox chkSplittable;
	
	// color controls
	
	/** The outline color */
	private JLabel lblLineColor;
	
	/** The outline color change button */
	private JButton btnOutlineColor;
	
	/** The fill color1 (facecolor) label */
	private JLabel lblFillColor;
	
	/** The fill color (facecolor) change button */
	private JButton btnFillColor;
	
	/** The fill color1 (backcolor) label */
	private JLabel lblBackColor;
	
	/** The fill color (backcolor) change button */
	private JButton btnBackColor;
	
	
	/** The sample text for the color panel */
	private JLabel lblSample;
	
	/** A panel to show the selected colors */
	private JPanel pnlColor;
	
	boolean isCurve;
	
	public Object3DgeneralPanel(Element3D object3d) {
		super();
		// name

		String name = object3d.getName();
		body=object3d;
		this.lblName = new JLabel(Messages.getString("panel.body.name"), Icons.ABOUT, JLabel.LEFT);
		this.lblName.setToolTipText(Messages.getString("panel.body.name.tooltip"));
		this.txtName = new JTextField(name);
		this.txtName.addFocusListener(new SelectTextFocusListener(this.txtName));
		this.txtName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent event) {
				body.setName(txtName.getText());
			}
			@Override
			public void insertUpdate(DocumentEvent event) {
				body.setName(txtName.getText());
			}
			@Override
			public void changedUpdate(DocumentEvent event) {}
		});
		
		isCurve=commonUtils.isCurve(object3d);
		this.lblCurveWidth= new JLabel(Messages.getString("panel.body.curvewidth"), Icons.ABOUT, JLabel.LEFT);
		this.lblDashed= new JLabel(Messages.getString("panel.body.dashed"), Icons.ABOUT, JLabel.LEFT);
		this.lblSplittable= new JLabel("Splittable", Icons.ABOUT, JLabel.LEFT);
		
		this.spinCurveWidth=new JSpinner();
		this.spinCurveWidth.setBorder(null);
		spinCurveWidth.setModel(new javax.swing.SpinnerNumberModel(1, 0, 10, 1));
		spinCurveWidth.setValue(body.getCurveWidth());
		spinCurveWidth.setMaximumSize(spinCurveWidth.getSize());
		this.chkDashed=new JCheckBox();
		this.chkDashed.setBorder(null);
		this.chkDashed.setText("");
		this.chkDashed.setSelected(body.isDashed());
		this.chkSplittable=new JCheckBox();
		this.chkSplittable.setBorder(null);
		this.chkSplittable.setText("");
		this.chkSplittable.setSelected(body.isSplittable());
	
		// the color panel
		this.lblSample = new JLabel(Messages.getString("panel.body.color.sample"));
		this.lblSample.setForeground(ColorUtils.getForegroundColorFromBackgroundColor((body.getFillColor())));
		this.lblSample.setHorizontalAlignment(JLabel.CENTER);
		this.pnlColor = new JPanel();
		this.pnlColor.setBackground(object3d.getFillColor());
		this.pnlColor.setBorder(BorderFactory.createLineBorder(body.getLineColor(), 3));
		//this.pnlColor.setPreferredSize(new Dimension(150,200));
		this.pnlColor.setLayout(new BorderLayout());
		this.pnlColor.add(this.lblSample, BorderLayout.CENTER);
		
		// outline color
		this.lblLineColor = new JLabel(Messages.getString("panel.body.color.outline"), Icons.ABOUT, JLabel.LEFT);
		this.lblLineColor.setToolTipText(Messages.getString("panel.body.color.outline.tooltip"));
		this.btnOutlineColor = new JButton(Messages.getString("button.select"));
		this.btnOutlineColor.setActionCommand("outlineColor");
		this.btnOutlineColor.addActionListener(this);
		
		// fill color1
		this.lblFillColor = new JLabel(Messages.getString("panel.body.color.fill"), Icons.ABOUT, JLabel.LEFT);
		this.lblFillColor.setToolTipText(Messages.getString("panel.body.color.fill.tooltip"));
		this.btnFillColor = new JButton(Messages.getString("button.select"));
		this.btnFillColor.setActionCommand("fillColor");
		this.btnFillColor.addActionListener(this);
		
		// fill color2
		this.lblBackColor = new JLabel(Messages.getString("panel.body.color.fill"), Icons.ABOUT, JLabel.LEFT);
		this.lblBackColor.setToolTipText(Messages.getString("panel.body.color.fill.tooltip"));
		this.btnBackColor = new JButton(Messages.getString("button.select"));
		this.btnBackColor.setActionCommand("backColor");
		this.btnBackColor.addActionListener(this);
	
		this.lblFillColor.setText(isCurve?"StartColor":"FaceColor");
		this.lblBackColor.setText(isCurve?"EndColor":"BackColor");
		
		/*
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), Messages.getString("panel.body.general"));
		border.setTitlePosition(TitledBorder.TOP);
		this.setBorder(border);
		*/
		//Create General panel container
		JPanel container=new JPanel();
		GroupLayout layout;
		layout = new GroupLayout(container);
		container.setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(this.lblName)
						.addComponent(this.lblCurveWidth)
						.addComponent(this.lblDashed)
						.addComponent(this.lblSplittable)
						.addComponent(this.lblLineColor)
						.addComponent(this.lblFillColor)
						.addComponent(this.lblBackColor))
				.addGroup(layout.createParallelGroup()
						.addComponent(this.txtName)
						.addComponent(this.spinCurveWidth)
						.addComponent(this.chkDashed)
						.addComponent(this.chkSplittable)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup()
										.addComponent(this.btnOutlineColor)
										.addComponent(this.btnFillColor)
										.addComponent(this.btnBackColor)
										)
								.addComponent(this.pnlColor))));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.lblName)
						.addComponent(this.txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.lblCurveWidth)
						.addComponent(this.spinCurveWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.lblDashed)
						.addComponent(this.chkDashed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(this.lblSplittable)
						.addComponent(this.chkSplittable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				
				.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
										.addComponent(this.lblLineColor)
										.addComponent(this.btnOutlineColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
										.addComponent(this.lblFillColor)
										.addComponent(this.btnFillColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
										.addComponent(this.lblBackColor)
										.addComponent(this.btnBackColor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(this.pnlColor)));
		this.setLayout(new BorderLayout());
		this.add(container,BorderLayout.NORTH);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		// check the commands
		if (command.equalsIgnoreCase("outlineColor") || command.equalsIgnoreCase("fillColor") ||command.equalsIgnoreCase("backColor")) {
			// get the current color
			Color c;
			if (command.equalsIgnoreCase("outlineColor")) {
				c = this.body.getLineColor();
			} else if (command.equalsIgnoreCase("backColor")) {
				c = this.body.getBackColor();
			} else{
				c = this.body.getFillColor();
			}
			
			// show the color selection dialog
			Color nc = ColorDialog.show(this, c, true);
			// make sure it wasnt canceled
			if (nc != null) {
				// check for the action command
				if (command.equalsIgnoreCase("outlineColor")) {
					this.body.setLineColor(nc);
					// set the outline color of the color panel
					this.pnlColor.setBorder(BorderFactory.createLineBorder(nc, 4));
				} else if (command.equalsIgnoreCase("fillColor")) {
					this.body.setFillColor(nc);
					// set the fill color of the color panel
					this.pnlColor.setBackground(nc);
				}
				else if (command.equalsIgnoreCase("backColor")) {
					this.body.setBackColor(nc);
					// set the foreground color of the label
					this.lblSample.setForeground(nc);

				}
			}
		}
	}
	

	@Override
	public boolean isValidInput() {
	   return  (body.getName()=="")?false:true;
    }

	public void UpdateElement(Element3D element){
	   //	if (!isValidInput()) return;
		element.setName(body.getName());
		element.setFillColor(body.getFillColor());
		element.setLineColor(body.getLineColor());
		element.setBackColor(body.getBackColor());
		element.setVisible(body.isVisible());
		element.setCurveWidth((Integer)spinCurveWidth.getValue());
		element.setDashed(chkDashed.isSelected());
		element.setSplittable(chkSplittable.isSelected());
	}
	
	public void setElement(Element3D element){
		body=element;
		txtName.setText(body.getName());
		this.chkDashed.setSelected(body.isDashed());
		spinCurveWidth.setValue(body.getCurveWidth());	
		this.pnlColor.setBackground(body.getFillColor());
		this.pnlColor.setBorder(BorderFactory.createLineBorder(body.getLineColor(), 3));
	}
	
	@Override
	public void showInvalidInputMessage(Window owner) {
		JOptionPane.showMessageDialog(owner, Messages.getString("panel.body.missingName"), Messages.getString("panel.invalid.title"), JOptionPane.ERROR_MESSAGE);
	}

	
	
	
}
