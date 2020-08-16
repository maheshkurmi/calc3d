package com.calc3d.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.panels.ColorIcon;
import com.calc3d.app.resources.Icons;
import com.calc3d.geometry3d.Box3D;


/**
 *
 * @author mahesh
 */
public class SettingsDialog extends JDialog {

	/** The dialog canceled flag */
	private boolean canceled = true;
	
	private Gui guiOwner;
    /**
     * Creates new form NewJFrame
     */
    public SettingsDialog() {
         this(null,Preferences.getDefaultSettings(),0);
    }
    
    public SettingsDialog(Window owner,Preferences preferences, int index) {
    	 super(owner, "Preferences", ModalityType.APPLICATION_MODAL);
    	 guiOwner=(Gui) owner;
    	 initComponents();
         loadSettings(preferences);
         jTabbedPane1.setSelectedIndex(index);
         pack();
    }
    
    public void loadSettings(Preferences preferences){
    	 comboLandF.removeAll();
	   	 LookAndFeel current = UIManager.getLookAndFeel();
    	 int i=0;
 		 for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
 			comboLandF.addItem(info.getName());
 			if (current.getClass().getName().equals(info.getClassName())) {
 				comboLandF.setSelectedIndex(i);
			}
 			i++;
		}
    	
    	chkFileTooBar.setSelected(preferences.isFileToolbarVisible());
    	chkObjectToolBar.setSelected(preferences.isObjectToolbarVisible());
    	chkStatusBar.setSelected(preferences.isStatusbarVisible());
    	chkStatusBar.setSelected(preferences.isStatusbarVisible());
        chkTipofDay.setSelected(preferences.isTipofDayEnabled());
        chkSplashScreen.setSelected(preferences.isSplashScreenEnabled());
        
        spinClipXmin.setValue(preferences.getClipBox().getMinX());
        spinClipXmax.setValue(preferences.getClipBox().getMaxX());
        spinClipYmin.setValue(preferences.getClipBox().getMinY());
        spinClipYmax.setValue(preferences.getClipBox().getMaxY());
        spinClipZmin.setValue(preferences.getClipBox().getMinZ());
        spinClipZmax.setValue(preferences.getClipBox().getMaxZ());
        
        chkAntiAlias.setSelected(preferences.isAntiAliasingEnabled());
        chkPerspective.setSelected(preferences.isPerspectiveEnabled());
        chkStereoScopy.setSelected(preferences.isSteroscopyEnabled());
        if ((preferences.getSteroscopicMode()>-1)||(preferences.getSteroscopicMode()<comboLandF.getItemCount())) {
        	comboStereoMode.setSelectedIndex(preferences.getSteroscopicMode());
    	}
        chkEnableFog.setSelected(preferences.isFogEnabled()); 
        chkEnableLight1.setSelected(preferences.isLight1Enabled()); 
        chkEnableLight2.setSelected(preferences.isLight2Enabled()); 
        chkEnableLight3.setSelected(preferences.isLight3Enabled()); 
        
        chkAxisX.setSelected(preferences.isxAxisVisible());
        chkAxisY.setSelected(preferences.isyAxisVisible());  
        chkAxisZ.setSelected(preferences.iszAxisVisible());
    
        pnlAxisColor.setBackground(preferences.getAxisColor());
        
        spinAxisXmin.setValue(preferences.getAxesBox().getMinX());
        spinAxisXmax.setValue(preferences.getAxesBox().getMaxX());
        spinAxisYmin.setValue(preferences.getAxesBox().getMinY());
        spinAxisYmax.setValue(preferences.getAxesBox().getMaxY());
        spinAxisZmin.setValue(preferences.getAxesBox().getMinZ());
        spinAxisZmax.setValue(preferences.getAxesBox().getMaxZ());
        pnlAxisColor.setBackground(preferences.getAxisColor());
        spinAxisWidth.setValue(preferences.getAxisWidth());
        spinAxisTicks.setValue(preferences.getAxisTicks());
        
        chkBoxGrids.setSelected(preferences.isGridsVisible());
        chkBoxLabels.setSelected(preferences.isLabelsVisible());
        chkBoundingBox.setSelected(preferences.isBoxVisible());
        chkBoxPlanes.setSelected(preferences.isPlanesVisible());
        chkBoxTicks.setSelected(preferences.isTicksVisible());
        spinDivisions.setValue(preferences.getDivisions());
        spinSubDivisions.setValue(preferences.getSubDivisions());
    }
    
    public Preferences getSettings(){
    	Preferences preferences=new Preferences();
    	//first get current settings and then update them
    	preferences=Globalsettings.getSettings();
    	preferences.setLookandFeel((String)comboLandF.getSelectedItem());
    	preferences.setFileToolbarVisible(chkFileTooBar.isSelected());
    	preferences.setObjectToolbarVisible(chkObjectToolBar.isSelected());
    	preferences.setStatusbarVisible(chkStatusBar.isSelected());
        preferences.setTipofDayEnabled(chkTipofDay.isSelected());
        preferences.setSplashScreenEnabled(chkSplashScreen.isSelected());
        
        double minX,maxX,minY,maxY,minZ,maxZ;
        minX=((Number)(spinClipXmin.getValue())).doubleValue();
        maxX=((Number)(spinClipXmax.getValue())).doubleValue();
        minY=((Number)(spinClipYmin.getValue())).doubleValue();
        maxY=((Number)(spinClipYmax.getValue())).doubleValue();
        minZ=((Number)(spinClipZmin.getValue())).doubleValue();
        maxZ=((Number)(spinClipZmax.getValue())).doubleValue();
        
        preferences.setClipBox(new Box3D(minX,maxX,minY,maxY,minZ,maxZ));
        
        preferences.setAntiAliasingEnabled(chkAntiAlias.isSelected());
        preferences.setPerspectiveEnabled(chkPerspective.isSelected());
        preferences.setSteroscopyEnabled(chkStereoScopy.isSelected());
        preferences.setSteroscopicMode(comboStereoMode.getSelectedIndex());
      
        preferences.setFogEnabled(chkEnableFog.isSelected()); 
        preferences.setLight1Enabled(chkEnableLight1.isSelected()); 
        preferences.setLight2Enabled(chkEnableLight2.isSelected()); 
        preferences.setLight3Enabled(chkEnableLight3.isSelected()); 
        
        preferences.setxAxisVisible(chkAxisX.isSelected());
        preferences.setyAxisVisible(chkAxisY.isSelected());
        preferences.setzAxisVisible(chkAxisZ.isSelected());
                
        minX=((Number)(spinAxisXmin.getValue())).doubleValue();
        maxX=((Number)(spinAxisXmax.getValue())).doubleValue();
        minY=((Number)(spinAxisYmin.getValue())).doubleValue();
        maxY=((Number)(spinAxisYmax.getValue())).doubleValue();
        minZ=((Number)(spinAxisZmin.getValue())).doubleValue();
        maxZ=((Number)(spinAxisZmax.getValue())).doubleValue();
        
        preferences.setAxesBox(new Box3D(minX,maxX,minY,maxY,minZ,maxZ));
        
        preferences.setAxisWidth(((Number)(spinAxisWidth.getValue())).intValue());
        preferences.setAxisTicks(((Number)(spinAxisTicks.getValue())).intValue());
        preferences.setAxisColor(pnlAxisColor.getBackground());
        
        preferences.setGridsVisible(chkBoxGrids.isSelected());
        preferences.setLabelsVisible(chkBoxLabels.isSelected());
        preferences.setBoxVisible(chkBoundingBox.isSelected());
        preferences.setPlanesVisible(chkBoxPlanes.isSelected());
        preferences.setTicksVisible(chkBoxTicks.isSelected());
       
        preferences.setDivisions(((Number)(spinDivisions.getValue())).intValue());
        preferences.setSubDivisions(((Number)(spinSubDivisions.getValue())).intValue());
        
     	return preferences;
    }

	
	/**
	 * Shows a new Settings dialog and returns a preferences Object if the user clicked the ok/apply button.
	 * <p>
	 * Returns null if the user clicked the cancel button or closed the dialog.
	 * @param owner the dialog owner
	 * @return {@link Element3D}
	 */
	public static final Preferences show(Window owner,Preferences preferences, int index) {
		SettingsDialog dialog = new SettingsDialog(owner,preferences, index);
		dialog.setLocationRelativeTo(owner);
		dialog.setIconImage(Icons.PREFERENCES.getImage());
		dialog.setVisible(true);
		// control returns to this method when the dialog is closed
		// check the canceled flag
		if (!dialog.canceled) {
			// return the setting Object
			return dialog.getSettings();
		}
		
		// if it was canceled then return null
		return null;
	} 
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlGuiSettings = new javax.swing.JPanel();
        lblLandF = new javax.swing.JLabel();
        comboLandF = new javax.swing.JComboBox();
        pnlGuiContainer = new javax.swing.JPanel();
        chkFileTooBar = new javax.swing.JCheckBox();
        chkObjectToolBar = new javax.swing.JCheckBox();
        chkStatusBar = new javax.swing.JCheckBox();
        chkTipofDay = new javax.swing.JCheckBox();
        chkSplashScreen = new javax.swing.JCheckBox();
        pnlClipSetting = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        paneClipInfo = new javax.swing.JTextPane();
        pnlClipContainer = new javax.swing.JPanel();
        spinClipXmin = new javax.swing.JSpinner();
        lblClipZmax = new javax.swing.JLabel();
        lblClipYmin = new javax.swing.JLabel();
        btnAutoClip = new javax.swing.JButton();
        chkClipZ = new javax.swing.JCheckBox();
        lblClipYmax = new javax.swing.JLabel();
        spinClipZmin = new javax.swing.JSpinner();
        lblClipXmax = new javax.swing.JLabel();
        spinClipYmin = new javax.swing.JSpinner();
        spinClipZmax = new javax.swing.JSpinner();
        spinClipYmax = new javax.swing.JSpinner();
        chkClipX = new javax.swing.JCheckBox();
        spinClipXmax = new javax.swing.JSpinner();
        chkClipY = new javax.swing.JCheckBox();
        lblClipXmin = new javax.swing.JLabel();
        lblClipZmin = new javax.swing.JLabel();
        pnlRenderSetting = new javax.swing.JPanel();
        pnlRenererContainer = new javax.swing.JPanel();
        chkAntiAlias = new javax.swing.JCheckBox();
        chkPerspective = new javax.swing.JCheckBox();
        chkStereoScopy = new javax.swing.JCheckBox();
        comboStereoMode = new javax.swing.JComboBox();
        pnlLightContainer = new javax.swing.JPanel();
        chkEnableLight1 = new javax.swing.JCheckBox();
        chkEnableFog = new javax.swing.JCheckBox();
        chkEnableLight2 = new javax.swing.JCheckBox();
        chkEnableLight3 = new javax.swing.JCheckBox();
        pnlAxisSetting = new javax.swing.JPanel();
        pnlAxisContainer = new javax.swing.JPanel();
        spinAxisXmin = new javax.swing.JSpinner();
        lblAxisZmax = new javax.swing.JLabel();
        lblAxisYmin = new javax.swing.JLabel();
        chkAxisZ = new javax.swing.JCheckBox();
        lblAxisYmax = new javax.swing.JLabel();
        spinAxisZmin = new javax.swing.JSpinner();
        lblAxisXmax = new javax.swing.JLabel();
        spinAxisYmin = new javax.swing.JSpinner();
        spinAxisZmax = new javax.swing.JSpinner();
        spinAxisYmax = new javax.swing.JSpinner();
        chkAxisX = new javax.swing.JCheckBox();
        spinAxisXmax = new javax.swing.JSpinner();
        lblAxisZmin = new javax.swing.JLabel();
        lblAxisXmin = new javax.swing.JLabel();
        chkAxisY = new javax.swing.JCheckBox();
        lblAxisColor = new javax.swing.JLabel();
        pnlAxisColor = new ColorIcon(Color.black);
        pnlAxisColor.setPreferredSize(new Dimension(16, 16));
        pnlAxisColor.setMaximumSize(new Dimension(16, 16));
        pnlAxisColor.setMinimumSize(new Dimension(16, 16));
        spinAxisWidth = new javax.swing.JSpinner();
        spinAxisTicks = new javax.swing.JSpinner();
        lblAxisTicks = new javax.swing.JLabel();
        lblAxisWidth = new javax.swing.JLabel();
        pnlBoxContainer = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        chkBoxGrids = new javax.swing.JCheckBox();
        chkBoxPlanes = new javax.swing.JCheckBox();
        chkBoundingBox = new javax.swing.JCheckBox();
        chkBoxTicks = new javax.swing.JCheckBox();
        chkBoxLabels = new javax.swing.JCheckBox();
        spinSubDivisions = new javax.swing.JSpinner();
        lblDivisions = new javax.swing.JLabel();
        lblSubDivisions = new javax.swing.JLabel();
        spinDivisions = new javax.swing.JSpinner();
        lblAxisSettingsTitle = new javax.swing.JLabel();
        lblBoxSettingsTitle = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnApply = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.setName("");

        lblLandF.setText("Look and Feel of Application:");

        //comboLandF.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Metal (Java)", "Motif", "Nimbus", "Windows ", "Windos Classic" }));

        pnlGuiContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        chkFileTooBar.setText("Show File ToolBar");

        chkObjectToolBar.setText("Show Object ToolBar");

        chkStatusBar.setText("Show StatusBar");

        chkTipofDay.setText("Show Tip of the Day");

        chkSplashScreen.setText("Show Splash Screen before launch of application");

        javax.swing.GroupLayout pnlGuiContainerLayout = new javax.swing.GroupLayout(pnlGuiContainer);
        pnlGuiContainer.setLayout(pnlGuiContainerLayout);
        pnlGuiContainerLayout.setHorizontalGroup(
            pnlGuiContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGuiContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGuiContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkFileTooBar)
                    .addComponent(chkObjectToolBar)
                    .addComponent(chkStatusBar)
                    .addComponent(chkTipofDay)
                    .addComponent(chkSplashScreen))
                .addContainerGap(210, Short.MAX_VALUE))
        );
        pnlGuiContainerLayout.setVerticalGroup(
            pnlGuiContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGuiContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkFileTooBar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkObjectToolBar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkStatusBar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkTipofDay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkSplashScreen)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlGuiSettingsLayout = new javax.swing.GroupLayout(pnlGuiSettings);
        pnlGuiSettings.setLayout(pnlGuiSettingsLayout);
        pnlGuiSettingsLayout.setHorizontalGroup(
            pnlGuiSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGuiSettingsLayout.createSequentialGroup()
                .addGroup(pnlGuiSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlGuiSettingsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlGuiContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlGuiSettingsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblLandF)
                        .addGap(18, 18, 18)
                        .addComponent(comboLandF, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlGuiSettingsLayout.setVerticalGroup(
            pnlGuiSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGuiSettingsLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlGuiSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboLandF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLandF))
                .addGap(18, 18, 18)
                .addComponent(pnlGuiContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("GUI Settings", pnlGuiSettings);

        paneClipInfo.setContentType("text/html");
        paneClipInfo.setText("<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"margin-top: 0\">\r\n      \rAll Elements added to the scene are clipped against this box , the box have 6 parameters representing minimum and maximum value in each direction x,y,z.\n    </p>\r\n<p >\n    Uncheck the direction to let application choose minimum and maximum clip values along that direction automatically.\n    </p>\n\n  </body>\r\n</html>\r\n");
        jScrollPane1.setViewportView(paneClipInfo);

        pnlClipContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        spinClipXmin.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        lblClipZmax.setText("Max:");

        lblClipYmin.setText("Min:");

        btnAutoClip.setText("Auto Compute");
        btnAutoClip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutoClipActionPerformed(evt);
            }
        });

        chkClipZ.setText("Z");
        chkClipZ.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        lblClipYmax.setText("Max:");

        spinClipZmin.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        lblClipXmax.setText("Max:");

        spinClipYmin.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        spinClipZmax.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        spinClipYmax.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        chkClipX.setText("X");
        chkClipX.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        spinClipXmax.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        chkClipY.setText("Y");
        chkClipY.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        lblClipXmin.setText("Min:");

        lblClipZmin.setText("Min:");

        javax.swing.GroupLayout pnlClipContainerLayout = new javax.swing.GroupLayout(pnlClipContainer);
        pnlClipContainer.setLayout(pnlClipContainerLayout);
        pnlClipContainerLayout.setHorizontalGroup(
            pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClipContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(chkClipX)
                        .addComponent(chkClipY))
                    .addComponent(chkClipZ))
                .addGap(18, 18, 18)
                .addGroup(pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClipXmin)
                    .addComponent(lblClipYmin)
                    .addComponent(lblClipZmin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinClipXmin, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addComponent(spinClipYmin)
                    .addComponent(spinClipZmin))
                .addGap(18, 18, 18)
                .addGroup(pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClipYmax)
                    .addComponent(lblClipXmax)
                    .addComponent(lblClipZmax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinClipZmax, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addComponent(spinClipXmax)
                    .addComponent(spinClipYmax))
                .addGap(50, 50, 50)
                .addComponent(btnAutoClip, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlClipContainerLayout.setVerticalGroup(
            pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClipContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblClipXmax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spinClipXmax)
                    .addComponent(lblClipXmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spinClipXmin)
                    .addComponent(chkClipX, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinClipYmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClipYmax)
                    .addComponent(spinClipYmax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClipYmin)
                    .addComponent(chkClipY, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(pnlClipContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spinClipZmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClipZmax)
                    .addComponent(spinClipZmax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAutoClip)
                    .addComponent(lblClipZmin)
                    .addComponent(chkClipZ, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pnlClipContainerLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblClipXmax, lblClipXmin, lblClipYmax, lblClipYmin, lblClipZmax, lblClipZmin, spinClipXmax, spinClipXmin, spinClipYmax, spinClipYmin, spinClipZmax, spinClipZmin});

        javax.swing.GroupLayout pnlClipSettingLayout = new javax.swing.GroupLayout(pnlClipSetting);
        pnlClipSetting.setLayout(pnlClipSettingLayout);
        pnlClipSettingLayout.setHorizontalGroup(
            pnlClipSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClipSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlClipSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlClipContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlClipSettingLayout.setVerticalGroup(
            pnlClipSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClipSettingLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlClipContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clip Settings", pnlClipSetting);

        pnlRenererContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        chkAntiAlias.setText("AntiAliasing");

        chkPerspective.setText("Perspective View");

        chkStereoScopy.setText("StereoScopic Mode");

        comboStereoMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RedGreen Anaglyps", "Color Anaglyps", "OptimizedAnaglyps", "No Anaglyps Left", "NoAnaglyps Left" }));

        javax.swing.GroupLayout pnlRenererContainerLayout = new javax.swing.GroupLayout(pnlRenererContainer);
        pnlRenererContainer.setLayout(pnlRenererContainerLayout);
        pnlRenererContainerLayout.setHorizontalGroup(
            pnlRenererContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRenererContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRenererContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRenererContainerLayout.createSequentialGroup()
                        .addComponent(chkStereoScopy)
                        .addGap(37, 37, 37)
                        .addComponent(comboStereoMode, 0, 303, Short.MAX_VALUE))
                    .addGroup(pnlRenererContainerLayout.createSequentialGroup()
                        .addGroup(pnlRenererContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkAntiAlias)
                            .addComponent(chkPerspective))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlRenererContainerLayout.setVerticalGroup(
            pnlRenererContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRenererContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkAntiAlias)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkPerspective)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlRenererContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkStereoScopy)
                    .addComponent(comboStereoMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLightContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        chkEnableLight1.setText("Light 1");

        chkEnableFog.setText("Enable fog");
        chkEnableFog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEnableFogActionPerformed(evt);
            }
        });

        chkEnableLight2.setText("Light 2");

        chkEnableLight3.setText("Light 3");

        javax.swing.GroupLayout pnlLightContainerLayout = new javax.swing.GroupLayout(pnlLightContainer);
        pnlLightContainer.setLayout(pnlLightContainerLayout);
        pnlLightContainerLayout.setHorizontalGroup(
            pnlLightContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLightContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLightContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkEnableLight1)
                    .addComponent(chkEnableLight2)
                    .addComponent(chkEnableFog)
                    .addComponent(chkEnableLight3))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlLightContainerLayout.setVerticalGroup(
            pnlLightContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLightContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkEnableFog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkEnableLight1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkEnableLight2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkEnableLight3)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlRenderSettingLayout = new javax.swing.GroupLayout(pnlRenderSetting);
        pnlRenderSetting.setLayout(pnlRenderSettingLayout);
        pnlRenderSettingLayout.setHorizontalGroup(
            pnlRenderSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRenderSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRenderSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLightContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlRenererContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlRenderSettingLayout.setVerticalGroup(
            pnlRenderSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRenderSettingLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pnlRenererContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLightContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Renderer Settings", pnlRenderSetting);

        pnlAxisContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        spinAxisXmin.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        lblAxisZmax.setText("Max:");

        lblAxisYmin.setText("Min:");

        chkAxisZ.setText("Z");
        chkAxisZ.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        lblAxisYmax.setText("Max:");

        spinAxisZmin.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        lblAxisXmax.setText("Max:");

        spinAxisYmin.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        spinAxisZmax.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        spinAxisYmax.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        chkAxisX.setText("X");
        chkAxisX.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        spinAxisXmax.setModel(new javax.swing.SpinnerNumberModel(0.0d, -5000.0d, 5000.0d, 0.1d));

        lblAxisZmin.setText("Min:");

        lblAxisXmin.setText("Min:");

        chkAxisY.setText("Y");
        chkAxisY.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        lblAxisColor.setText("Color");

              spinAxisWidth.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));

        spinAxisTicks.setModel(new javax.swing.SpinnerNumberModel(2, 0, 10, 1));

        lblAxisTicks.setText("Ticks");

        lblAxisWidth.setText("Width");

        javax.swing.GroupLayout pnlAxisContainerLayout = new javax.swing.GroupLayout(pnlAxisContainer);
        pnlAxisContainer.setLayout(pnlAxisContainerLayout);
        pnlAxisContainerLayout.setHorizontalGroup(
            pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAxisContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(chkAxisX)
                        .addComponent(chkAxisY))
                    .addComponent(chkAxisZ))
                .addGap(18, 18, 18)
                .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAxisXmin)
                    .addComponent(lblAxisYmin)
                    .addComponent(lblAxisZmin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinAxisXmin, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(spinAxisYmin)
                    .addComponent(spinAxisZmin))
                .addGap(18, 18, 18)
                .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAxisYmax)
                    .addComponent(lblAxisXmax)
                    .addComponent(lblAxisZmax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinAxisZmax, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(spinAxisXmax)
                    .addComponent(spinAxisYmax))
                .addGap(68, 68, 68)
                .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAxisTicks, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblAxisColor, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblAxisWidth))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(spinAxisWidth, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(spinAxisTicks)
                    .addComponent(pnlAxisColor, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlAxisContainerLayout.setVerticalGroup(
            pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAxisContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkAxisX, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAxisXmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spinAxisXmin, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAxisXmax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spinAxisXmax)
                    .addComponent(lblAxisColor)
                    .addComponent(pnlAxisColor))
                .addGap(4, 4, 4)
                .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkAxisY, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lblAxisYmin)
                    .addComponent(spinAxisYmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAxisYmax)
                    .addComponent(spinAxisYmax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAxisTicks)
                    .addComponent(spinAxisTicks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(pnlAxisContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(chkAxisZ, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAxisZmin)
                    .addComponent(spinAxisZmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAxisZmax)
                    .addComponent(spinAxisZmax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAxisWidth)
                    .addComponent(spinAxisWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        pnlBoxContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        chkBoxGrids.setText("Grids");

        chkBoxPlanes.setText("Planes");

        chkBoundingBox.setText("Bounding Box");

        chkBoxTicks.setText("Ticks");

        chkBoxLabels.setText("Labels");

        lblDivisions.setText("Major Divisions");

        lblSubDivisions.setText("Minor Divisions");

        javax.swing.GroupLayout pnlBoxContainerLayout = new javax.swing.GroupLayout(pnlBoxContainer);
        pnlBoxContainer.setLayout(pnlBoxContainerLayout);
        pnlBoxContainerLayout.setHorizontalGroup(
            pnlBoxContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBoxContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBoxContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkBoxGrids)
                    .addComponent(chkBoxLabels))
                .addGap(24, 24, 24)
                .addGroup(pnlBoxContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBoxContainerLayout.createSequentialGroup()
                        .addComponent(chkBoxPlanes)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                        .addComponent(lblDivisions))
                    .addGroup(pnlBoxContainerLayout.createSequentialGroup()
                        .addComponent(chkBoxTicks)
                        .addGap(27, 27, 27)
                        .addComponent(chkBoundingBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSubDivisions)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBoxContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spinSubDivisions)
                    .addComponent(spinDivisions, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBoxContainerLayout.setVerticalGroup(
            pnlBoxContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBoxContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBoxContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBoxContainerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel17)
                        .addGap(49, 49, 49))
                    .addGroup(pnlBoxContainerLayout.createSequentialGroup()
                        .addGroup(pnlBoxContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(chkBoxGrids)
                            .addComponent(chkBoxPlanes)
                            .addComponent(lblDivisions)
                            .addComponent(spinDivisions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBoxContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(chkBoxLabels)
                            .addComponent(chkBoxTicks)
                            .addComponent(chkBoundingBox)
                            .addComponent(lblSubDivisions)
                            .addComponent(spinSubDivisions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        lblAxisSettingsTitle.setText("Axis Settings:");

        lblBoxSettingsTitle.setText("Box Settings:");

        javax.swing.GroupLayout pnlAxisSettingLayout = new javax.swing.GroupLayout(pnlAxisSetting);
        pnlAxisSetting.setLayout(pnlAxisSettingLayout);
        pnlAxisSettingLayout.setHorizontalGroup(
            pnlAxisSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAxisSettingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAxisSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlAxisContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBoxContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlAxisSettingLayout.createSequentialGroup()
                        .addGroup(pnlAxisSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAxisSettingsTitle)
                            .addComponent(lblBoxSettingsTitle))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlAxisSettingLayout.setVerticalGroup(
            pnlAxisSettingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAxisSettingLayout.createSequentialGroup()
                .addComponent(lblAxisSettingsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pnlAxisContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBoxSettingsTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(pnlBoxContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Axis Settings", pnlAxisSetting);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnApply.setText("Apply");
        btnApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApplyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnApply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnOk)
                    .addComponent(btnApply))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void btnAutoClipActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void chkEnableFogActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void btnApplyActionPerformed(java.awt.event.ActionEvent evt) {
  		  if (null!=guiOwner)guiOwner.applySettings(getSettings(),false,getSettings().getClipBox().equals(Globalsettings.getClipBox()));
	}

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {
    	this.canceled = false;
    	this.setVisible(false);
    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
    	// set the canceled flag and close the dialog
    	this.setVisible(false);
    	this.canceled = true;
    	//this.dispose(); no need JVM will dispose it automatically
    }

  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SettingsDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SettingsDialog().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnAutoClip;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOk;
    private javax.swing.JCheckBox chkAntiAlias;
    private javax.swing.JCheckBox chkAxisX;
    private javax.swing.JCheckBox chkAxisY;
    private javax.swing.JCheckBox chkAxisZ;
    private javax.swing.JCheckBox chkBoundingBox;
    private javax.swing.JCheckBox chkBoxGrids;
    private javax.swing.JCheckBox chkBoxLabels;
    private javax.swing.JCheckBox chkBoxPlanes;
    private javax.swing.JCheckBox chkBoxTicks;
    private javax.swing.JCheckBox chkClipX;
    private javax.swing.JCheckBox chkClipY;
    private javax.swing.JCheckBox chkClipZ;
    private javax.swing.JCheckBox chkEnableFog;
    private javax.swing.JCheckBox chkEnableLight1;
    private javax.swing.JCheckBox chkEnableLight2;
    private javax.swing.JCheckBox chkEnableLight3;
    private javax.swing.JCheckBox chkFileTooBar;
    private javax.swing.JCheckBox chkObjectToolBar;
    private javax.swing.JCheckBox chkPerspective;
    private javax.swing.JCheckBox chkSplashScreen;
    private javax.swing.JCheckBox chkStatusBar;
    private javax.swing.JCheckBox chkStereoScopy;
    private javax.swing.JCheckBox chkTipofDay;
    private javax.swing.JComboBox comboLandF;
    private javax.swing.JComboBox comboStereoMode;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAxisColor;
    private javax.swing.JLabel lblAxisSettingsTitle;
    private javax.swing.JLabel lblAxisTicks;
    private javax.swing.JLabel lblAxisWidth;
    private javax.swing.JLabel lblAxisXmax;
    private javax.swing.JLabel lblAxisXmin;
    private javax.swing.JLabel lblAxisYmax;
    private javax.swing.JLabel lblAxisYmin;
    private javax.swing.JLabel lblAxisZmax;
    private javax.swing.JLabel lblAxisZmin;
    private javax.swing.JLabel lblBoxSettingsTitle;
    private javax.swing.JLabel lblClipXmax;
    private javax.swing.JLabel lblClipXmin;
    private javax.swing.JLabel lblClipYmax;
    private javax.swing.JLabel lblClipYmin;
    private javax.swing.JLabel lblClipZmax;
    private javax.swing.JLabel lblClipZmin;
    private javax.swing.JLabel lblDivisions;
    private javax.swing.JLabel lblLandF;
    private javax.swing.JLabel lblSubDivisions;
    private javax.swing.JTextPane paneClipInfo;
    private ColorIcon pnlAxisColor;
    private javax.swing.JPanel pnlAxisContainer;
    private javax.swing.JPanel pnlAxisSetting;
    private javax.swing.JPanel pnlBoxContainer;
    private javax.swing.JPanel pnlClipContainer;
    private javax.swing.JPanel pnlClipSetting;
    private javax.swing.JPanel pnlGuiContainer;
    private javax.swing.JPanel pnlGuiSettings;
    private javax.swing.JPanel pnlLightContainer;
    private javax.swing.JPanel pnlRenderSetting;
    private javax.swing.JPanel pnlRenererContainer;
    private javax.swing.JSpinner spinAxisTicks;
    private javax.swing.JSpinner spinAxisWidth;
    private javax.swing.JSpinner spinAxisXmax;
    private javax.swing.JSpinner spinAxisXmin;
    private javax.swing.JSpinner spinAxisYmax;
    private javax.swing.JSpinner spinAxisYmin;
    private javax.swing.JSpinner spinAxisZmax;
    private javax.swing.JSpinner spinAxisZmin;
    private javax.swing.JSpinner spinClipXmax;
    private javax.swing.JSpinner spinClipXmin;
    private javax.swing.JSpinner spinClipYmax;
    private javax.swing.JSpinner spinClipYmin;
    private javax.swing.JSpinner spinClipZmax;
    private javax.swing.JSpinner spinClipZmin;
    private javax.swing.JSpinner spinDivisions;
    private javax.swing.JSpinner spinSubDivisions;
  
    // End of variables declaration
}
