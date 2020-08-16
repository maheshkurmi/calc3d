package com.calc3d.app.dialogs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.calc3d.app.Globalsettings;
import com.calc3d.app.commonUtils;
import com.calc3d.app.elements.Element3D;
import com.calc3d.app.panels.BottomButtonPanel;
import com.calc3d.app.panels.Object3DCreatePanel;
import com.calc3d.app.panels.Object3DgeneralPanel;
import com.calc3d.app.panels.TransformPanel;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.math.AffineTransform3D;
import com.calc3d.math.MathUtils;

/**
 * Dialog to add a new body without any fixtures.
 * @author William Bittle
 * @version 1.0.1
 * @since 1.0.0
 */
public class AddObjectDialog extends JDialog implements ActionListener {
	/** The version id */
	private static final long serialVersionUID = -1809110047704548125L;
	

	/** The dialog canceled flag */
	private boolean canceled = true;
	
	/** The lable showing info and help*/
	private JLabel lblInfo;
	
	/** The body Creation panel */
	private Object3DCreatePanel pnlObjectCreate;
	
	/** The body General Panel*/
	private Object3DgeneralPanel pnlObjectGeneral;
	
	/**Panel to hold Object3DCrate panel and lblInfo*/
	private JPanel pnlObject;
	
	/** The transform config panel */
	private TransformPanel pnlTransform;
	
	/** The body using in configuration */
	private  Element3D object3D;
	
	private JButton btnCancel,btnAdd ;
	
	/**
	 * Full constructor.
	 * @param owner the dialog owner
	 */
	private AddObjectDialog(Window owner,Element3D element) {
		super(owner, "Add new "+commonUtils.getobject3DName(element), ModalityType.APPLICATION_MODAL);
		
		this.object3D = element;
		if (element.getName()=="")this.object3D.setName(commonUtils.getobject3DName(element));
		
		JTabbedPane tabs = new JTabbedPane();
		
		this.pnlObjectCreate= commonUtils.getobject3DPanel(element);
		this.pnlObjectGeneral=new Object3DgeneralPanel(element);
		this.pnlTransform = new TransformPanel(element.getTranslation(),element.getRotation());
	
		this.pnlObject=new JPanel();
		pnlObject.setLayout(new BorderLayout());
		pnlObject.add(pnlObjectCreate);
		lblInfo=new JLabel(commonUtils.getobject3DIcon(element),JLabel.LEFT);
		lblInfo.setText("<html> <b>"+commonUtils.getobject3DName(element) +"</b><br>" +commonUtils.getobject3DInfo(element)+"</html>");
		lblInfo.setAlignmentX(JLabel.LEFT);
		lblInfo.setVerticalAlignment(JLabel.TOP);
		lblInfo.setHorizontalAlignment(JLabel.LEFT);
		lblInfo.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		//lblInfo.setHorizontalTextPosition(JLabel.LEFT);
		//Set the position of the text, relative to the icon:
		//lblInfo.setVerticalTextPosition(JLabel.);
		//lblInfo.setHorizontalTextPosition(JLabel.LEFT);
		
		//	"\n  Mahesh the hero \\\n\\ bahut bada");
		//lblInfo.
			
		pnlObject.add(lblInfo,BorderLayout.NORTH);
		tabs.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
		tabs.addTab(Messages.getString("dialog.body.tab.element"), pnlObject);
		tabs.addTab(Messages.getString("dialog.body.tab.body"), pnlObjectGeneral);
		tabs.addTab(Messages.getString("dialog.body.tab.transform"), this.pnlTransform);
		
		btnCancel = new JButton(Messages.getString("button.cancel"));
		btnAdd = new JButton(Messages.getString("button.add"));
		btnCancel.setActionCommand("cancel");
		btnAdd.setActionCommand("add");
		btnCancel.addActionListener(this);
		btnAdd.addActionListener(this);
		
		JPanel pnlButtons = new BottomButtonPanel();
		pnlButtons.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnlButtons.add(btnCancel);
		pnlButtons.add(btnAdd);

		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(tabs, BorderLayout.CENTER);
		container.add(pnlButtons, BorderLayout.PAGE_END);
		
		this.pack();
	}
	
	

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// check the action command
		if ("cancel".equals(event.getActionCommand())) {
			// if its canceled then set the canceled flag and
			// close the dialog
			this.setVisible(false);
			this.canceled = true;
		} else {
			// check the body input
			if (this.pnlObjectCreate.isValidInput()) {
				// check the transform input
				if (this.pnlTransform.isValidInput()) {
					// if its valid then close the dialog
					this.canceled = false;
					this.setVisible(false);
				} else {
					this.pnlTransform.showInvalidInputMessage(this);
				}
			} else {
				this.pnlObjectCreate.showInvalidInputMessage(this);
			}
		}
	}
	
	/**
	 * Shows an add new body dialog and returns a new body if the user clicked the add button.
	 * <p>
	 * Returns null if the user clicked the cancel button or closed the dialog.
	 * @param owner the dialog owner
	 * @return {@link Element3D}
	 */
	public static final Element3D show(Window owner,Element3D object3D) {
		AddObjectDialog dialog = new AddObjectDialog(owner,object3D);
		dialog.setLocationRelativeTo(owner);
		dialog.setIconImage(commonUtils.getAddobject3DIcon(object3D).getImage());//Icons.ADDCURVE3D.getImage());
		dialog.setVisible(true);
		
		// control returns to this method when the dialog is closed
		// check the canceled flag
		if (!dialog.canceled) {
			// get the body and fixture
			Element3D element3D = dialog.pnlObjectCreate.getObject3D();
			dialog.pnlObjectGeneral.UpdateElement(element3D);
			// apply the transform
			AffineTransform3D T;
			T=AffineTransform3D.getTranslateInstance(Globalsettings.inverseMapX(dialog.pnlTransform.getTranslation().getX()),
						Globalsettings.inverseMapY(dialog.pnlTransform.getTranslation().getY()),
						Globalsettings.inverseMapZ(dialog.pnlTransform.getTranslation().getZ()));
			T.concatenate(AffineTransform3D.getRotateInstance(MathUtils.RADIANS_PER_DEGREE*dialog.pnlTransform.getRotation().iAngleX,
						MathUtils.RADIANS_PER_DEGREE*dialog.pnlTransform.getRotation().iAngleY,
						MathUtils.RADIANS_PER_DEGREE*dialog.pnlTransform.getRotation().iAngleZ));
			element3D.setTranslation(dialog.pnlTransform.getTranslation());
			element3D.setRotation(dialog.pnlTransform.getRotation());
			
			//System.out.println(T);
			//System.out.println(T.isIdentity());
			if (!T.isIdentity())
			{
				element3D.setTransform(T);
			}
			if (element3D.getName()=="") element3D.setName(commonUtils.getobject3DName(element3D));
		
			// return the body
			return element3D;
		}
		
		// if it was canceled then return null
		return null;
	}

	/**
	 * Shows an add new body dialog and returns a new body if the user clicked the add button.
	 * <p>
	 * Returns null if the user clicked the cancel button or closed the dialog.
	 * @param owner the dialog owner
	 * @return {@link Element3D}
	 */
	public static final Element3D showEdit(Window owner,Element3D object3D) {
		AddObjectDialog dialog = new AddObjectDialog(owner,object3D);
		dialog.setTitle("Edit "+object3D.getName());
		dialog.btnAdd.setText("Ok");
		dialog.setIconImage(commonUtils.getobject3DIcon(object3D).getImage());//Icons.ADDCURVE3D.getImage());
		dialog.setLocationRelativeTo(owner);
		dialog.setIconImage(Icons.ADDCURVE3D.getImage());
		dialog.setVisible(true);
		// control returns to this method when the dialog is closed
		
		// check the canceled flag
		if (!dialog.canceled) {
			// get the body and fixture
			Element3D element3D = dialog.pnlObjectCreate.getObject3D();
			dialog.pnlObjectGeneral.UpdateElement(element3D);
			
			// apply the transform
			AffineTransform3D T;
			T=AffineTransform3D.getTranslateInstance(Globalsettings.inverseMapX(dialog.pnlTransform.getTranslation().getX()),
					Globalsettings.inverseMapY(dialog.pnlTransform.getTranslation().getY()),
					Globalsettings.inverseMapZ(dialog.pnlTransform.getTranslation().getZ()));
			T.concatenate(AffineTransform3D.getRotateInstance(MathUtils.RADIANS_PER_DEGREE*dialog.pnlTransform.getRotation().iAngleX,
					MathUtils.RADIANS_PER_DEGREE*dialog.pnlTransform.getRotation().iAngleY,
					MathUtils.RADIANS_PER_DEGREE*dialog.pnlTransform.getRotation().iAngleZ));
			//System.out.println(T);
			//System.out.println(T.isIdentity());
			element3D.setTranslation(dialog.pnlTransform.getTranslation());
			element3D.setRotation(dialog.pnlTransform.getRotation());
			
			if (!T.isIdentity())
				{
				element3D.setTransform(T);
				}
			
			if (element3D.getName().trim()=="") element3D.setName(commonUtils.getobject3DName(element3D));
		
			// return the body
			return element3D;
		}
		
		// if it was canceled then return null
		return null;
	}

	
	
	
}
