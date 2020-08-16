package com.calc3d.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import com.calc3d.app.dialogs.AboutDialog;
import com.calc3d.app.dialogs.AddObjectDialog;
import com.calc3d.app.dialogs.HelpDialog;
import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DCurve;
import com.calc3d.app.elements.Element3DImplicit;
import com.calc3d.app.elements.Element3DLine;
import com.calc3d.app.elements.Element3DObject;
import com.calc3d.app.elements.Element3DParametricSurface;
import com.calc3d.app.elements.Element3DPlane;
import com.calc3d.app.elements.Element3DPoint;
import com.calc3d.app.elements.Element3DPolygon;
import com.calc3d.app.elements.Element3DSurface;
import com.calc3d.app.elements.Element3DVector;
import com.calc3d.app.elements.Element3DVectorField;
import com.calc3d.app.elements.Element3Dcartesian2D;
import com.calc3d.app.elements.Element3Dfunction;
import com.calc3d.app.elements.Element3Dimplicit2D;
import com.calc3d.app.panels.ColorIcon;
import com.calc3d.app.panels.StatusBarPanel;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;
import com.calc3d.engine3d.Camera3D;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.ElementPoly;
import com.calc3d.math.Vector3D;
import com.calc3d.renderer.Canvas3D;
import com.calc3d.renderer.InteractionHandler;
import com.calc3d.renderer.Renderer;
import com.calc3d.utils.ColorUtils;
import com.calc3d.utils.FileUtils;


public class Gui extends JFrame implements ActionListener,  MouseListener{
	/** The app version */
	public static final String VERSION = "1.0.0";
	
	/** The main menu bar */
	private JMenuBar barMenu;
	
	/** The file menu */
	private JMenu mnuFile;
	
	/** The File_New menu option */
	private JMenuItem mnuNew;
	
	/** The File_Open menu option */
	private JMenuItem mnuOpen;
	
	/** The File_Save As... menu option */
	private JMenuItem mnuSave;
	
	/** The File_Save As... menu option */
	private JMenuItem mnuSaveAs;
	
	/** The File_Export to Image.. menu option */
	private JMenuItem mnuExport;
	
	/** The File_Print menu option */
	private JMenuItem mnuPrint;
	
	/** The File_Exit menu option */
	private JMenuItem mnuExit;
	
	
	/** The window menu */
	private JMenu mnuWindow;
	
	/** The window_Preferneces menu */
	private JMenuItem mnuPreferences;
	
	/** The Window_look and feel menu */
	private JMenu mnuLookAndFeel;
	
	/** The Insert menu */
	private JMenu mnuInsert;
	
	/** The Evaluate menu */
	private JMenu mnuEvaluate;
	
	/** The Settings menu */
	private JMenu mnuSettings;
	
	/** The Settings menu */
	private JMenu mnuTests;

	/** The help menu */
	private JMenu mnuHelp;
	
	/** The help_Content menu */
	private JMenuItem mnuContent;
	
	/** The help_GoToHomePage menu */
	private JMenuItem mnuGotoHomePage;
	
	/** The help_Check Updates menu */
	private JMenuItem mnuCheckUpdate;

	/** The help_About menu */
	private JMenuItem mnuAbout;
	
	
	/** The File_New toolbar button  */
	private JButton btnNew;
	
	/** The File_Open toolbar button */
	private JButton btnOpen;
	
	/** The File_Save toolbar button */
	private JButton btnSave;
	

	/** The File_Print toolbar button */
	private JButton btnPrint;
	
	/** The File_Export toolbar button */
	private JButton btnExport;
	
	/** The Preferences toolbar button */
	private JButton btnPreferences;
	
	/** The zoom in button */
	private JButton btnZoomIn;
	
	/** The zoom out button */
	private JButton btnZoomOut;
	
	/** The Reset Camera button */
	private JButton btnReset;
	
	/** The Axis Show/hide toggle button */
	private JToggleButton tglAxis;
	
	/** The Box Show/hide toggle button */
	private JToggleButton tglBox;
	
	/** The GridXY Show/hide toggle button */
	private JToggleButton tglGridXY;
	
	/** The Light Enable/disable toggle button */
	private JToggleButton tglLight;
	
	/** The Perspective on/off toggle button */
	private JToggleButton tglPerspective;
	
	/** The 3D Steteoscope on/off  toggle button */
	private JToggleButton tgl3D;
	
	/** The Antialiasing on/off  toggle button */
	private JToggleButton tglAntialias;
	
	/** The ComboBox for Drawing Modes*/
	private JComboBox comboDrawMode;
	
	/** The ComboBox for Hidden Surface Removal Modes*/
	private JComboBox comboHSR;
	
	/** Upper toolbar*/
	JToolBar fileToolbar ;
	
	/** Lower toolbar*/
	JToolBar editToolbar ;
	
	/** toolbars containers*/
	JPanel pnlToolBar;
	/** The Edit_addPoint toolbar button */
	private JButton btnAddPoint;
	
	/** The Edit_addLine toolbar button */
	private JButton btnAddLine;
	
	/** The Edit_addVector toolbar button */
	private JButton btnAddVector;

	/** The Edit_addPolygon toolbar button */
	private JButton btnAddPolygon;
	
	/** The Edit_addPlane toolbar button */
	private JButton btnAddPlane;
	
	/** The Edit_addCurve toolbar button */
	private JButton btnAddCurve3d;
	
	/** The Edit_addVectorField toolbar button */
	private JButton btnVectorField3d;
	

	/** The Edit_addCurve2d_cartesian toolbar button */
	private JButton btnAddCurve2d_cartesian;
	
	/** The Edit_addCurve2d_implicit toolbar button */
	private JButton btnAddCurve2d_implicit;

	/** The Edit_addSurface toolbar button */
	private JButton btnAddSurface_Explicit;
	
	/** The Edit_addSurface toolbar button */
	private JButton btnAddSurface_Parametric;
	
	/** The Edit_addSurface toolbar button */
	private JButton btnAddSurface_Implicit;
	
	/** The Edit_addPrimitive toolbar button */
	private JButton btnAddPrimitive;
	
	/** The Edit_removeElement toolbar button */
	private JButton btnRemoveElement;
	
	/** The panel to show the element eqn/info */
	private JPanel pnlInfo;
	
	/** The panel to show transform */
	private JPanel pnlTransform;
	
	/** The panel to edit element*/
	private JPanel pnlEdit;
	
	/**table to display elements*/
	private JTable table ;

	/**Editorpane to display objectInfo*/
	private JLabel editorPane ;
	
	/**Drawing Canvas*/
	private Canvas3D canvas3D;
	
	/**Renderer asssociated with canvas*/
	private Renderer renderer = new Renderer();
	
	/**Scenemanager keeps and manages Elements of Canvas*/
	private SceneManager sceneManager;
	
	/**if new Element added has Random Color*/
	private boolean bodyColorRandom=true;
	
	/**true means file has been modified and needs to be saved*/
	private boolean dirty=false;
	
	/**Status bar*/
	private StatusBarPanel statusBar = new StatusBarPanel();
	/**
	 * Default constructor.
	 */
	private ColorIcon bgColorIcon;
	public Gui(){
	    super();
		this.setTitle("Calc3D-A 3D calculus Visualizer");	
		// make sure tooltips and menus show up on top of the heavy weight canvas
		ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		this.barMenu = new JMenuBar();
		
		// file menu
		this.mnuFile = new JMenu(Messages.getString("menu.file"));
		
		this.mnuNew = new JMenuItem(Messages.getString("menu.file.new"));
		this.mnuNew.setIcon(Icons.NEW);
		this.mnuNew.setActionCommand("new");
		this.mnuNew.addActionListener(this);
		
		this.mnuSave = new JMenuItem(Messages.getString("menu.file.save"));
		this.mnuSave.setIcon(Icons.SAVE);
		this.mnuSave.setActionCommand("save");
		this.mnuSave.addActionListener(this);
		
		this.mnuSaveAs = new JMenuItem(Messages.getString("menu.file.saveas"));
		this.mnuSaveAs.setIcon(Icons.SAVEAS);
		this.mnuSaveAs.setActionCommand("saveas");
		this.mnuSaveAs.addActionListener(this);
		
		this.mnuOpen = new JMenuItem(Messages.getString("menu.file.open"));
		this.mnuOpen.setIcon(Icons.OPEN);
		this.mnuOpen.setActionCommand("open");
		this.mnuOpen.addActionListener(this);
		
		this.mnuExport = new JMenuItem(Messages.getString("menu.file.export"));
		this.mnuExport.setIcon(Icons.EXPORT);
		this.mnuExport.setActionCommand("export");
		this.mnuOpen.addActionListener(this);

		this.mnuPrint = new JMenuItem(Messages.getString("menu.file.print"));
		this.mnuPrint.setIcon(Icons.PRINT);
		this.mnuPrint.setActionCommand("print");
		this.mnuPrint.addActionListener(this);
		
		this.mnuExit = new JMenuItem(Messages.getString("menu.file.exit"));
		this.mnuExit.setActionCommand("exit");
		this.mnuExit.addActionListener(this);
		
		this.mnuFile.add(this.mnuNew);
		this.mnuFile.add(this.mnuOpen);
		this.mnuFile.addSeparator();
		this.mnuFile.add(this.mnuSave);
		this.mnuFile.add(this.mnuSaveAs);
		this.mnuFile.add(this.mnuExport);
		this.mnuFile.addSeparator();
		this.mnuFile.add(this.mnuPrint);
		this.mnuFile.addSeparator();
		this.mnuFile.add(mnuExit);
		
		// window menu
		this.mnuWindow = new JMenu(Messages.getString("menu.window"));

		this.mnuPreferences = new JMenuItem(Messages.getString("menu.window.preferences"));
		this.mnuPreferences.setIcon(Icons.PREFERENCES);
		this.mnuPreferences.setActionCommand("preferences");
		this.mnuPreferences.addActionListener(this);

		this.mnuLookAndFeel = new JMenu(Messages.getString("menu.window.laf"));
		this.mnuLookAndFeel.setIcon(Icons.LOOKANDFEEL);
		this.createLookAndFeelMenuItems(this.mnuLookAndFeel);
			
		this.mnuWindow.add(mnuPreferences);
		this.mnuWindow.addSeparator();
		
		/*
		JMenuItem menuItem = new JMenuItem(Messages.getString("menu.window.calculator"));
		menuItem.setIcon(Icons.CALCULATOR);
		menuItem.setActionCommand("calculator");
		menuItem.addActionListener(this);
		this.mnuWindow.add(menuItem);
		*/
		
		JMenuItem menuItem = new JMenuItem(Messages.getString("menu.window.toolbar1"));
		menuItem.setIcon(Icons.TOOLBAR);
		menuItem.setActionCommand("filetoolbar");
		menuItem.addActionListener(this);
		this.mnuWindow.add(menuItem);
	
		
		menuItem = new JMenuItem(Messages.getString("menu.window.toolbar2"));
		menuItem.setIcon(Icons.TOOLBAR);
		menuItem.setActionCommand("edittoolbar");
		menuItem.addActionListener(this);
		this.mnuWindow.add(menuItem);
		
		menuItem = new JMenuItem(Messages.getString("menu.window.statusbar"));
		menuItem.setIcon(Icons.STATUSBAR);
		menuItem.setActionCommand("statusbar");
		menuItem.addActionListener(this);
		this.mnuWindow.add(menuItem);
		
		this.mnuInsert = new JMenu(Messages.getString("menu.insert"));	
		this.mnuWindow.addSeparator();
		this.mnuWindow.add(this.mnuLookAndFeel);
		

		menuItem = new JMenuItem(Messages.getString("menu.insert.point"));
		menuItem.setIcon(Icons.ADDPOINT);
		menuItem.setActionCommand("addpoint");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);		
		mnuInsert.addSeparator();
		menuItem = new JMenuItem(Messages.getString("menu.insert.vector"));
		menuItem.setIcon(Icons.ADDVECTOR);
		menuItem.setActionCommand("addvector");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);		
		menuItem = new JMenuItem(Messages.getString("menu.insert.line"));
		menuItem.setIcon(Icons.ADDLINE);
		menuItem.setActionCommand("addline");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);	
		mnuInsert.addSeparator();		
		
		menuItem = new JMenuItem(Messages.getString("menu.insert.curve"));
		menuItem.setIcon(Icons.ADDCURVE3D);
		menuItem.setActionCommand("addcurve3d");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);	
		
		menuItem = new JMenuItem(Messages.getString("menu.insert.vectorfield"));
		menuItem.setIcon(Icons.VECTOR);
		menuItem.setActionCommand("addvectorfield");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);	
	
		
		menuItem = new JMenuItem(Messages.getString("menu.insert.curve2dexplicit"));
		menuItem.setIcon(Icons.ADDCURVE2DCARTESIAN);
		menuItem.setActionCommand("addcurve2dcartesian");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);	
		
		menuItem = new JMenuItem(Messages.getString("menu.insert.curve2dimplicit"));
		menuItem.setIcon(Icons.ADDCURVE2DIMPLICIT);
		menuItem.setActionCommand("addcurve2dimplicit");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);	
		mnuInsert.addSeparator();		
		
		//mnuInsert.addSeparator();
		menuItem = new JMenuItem(Messages.getString("menu.insert.polygon"));
		menuItem.setIcon(Icons.ADDPOLYGON);
		menuItem.setActionCommand("addpolygon");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.insert.plane"));
		menuItem.setIcon(Icons.ADDPLANE);
		menuItem.setActionCommand("addplane");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);
		mnuInsert.addSeparator();		
	
		menuItem = new JMenuItem(Messages.getString("menu.insert.surfaceexplicit"));
		menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("addsurface");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);		
		
		menuItem = new JMenuItem(Messages.getString("menu.insert.surfaceimplicit"));
		menuItem.setIcon(Icons.ADDSURFACEIMPLICIT);
		menuItem.setActionCommand("addsurfaceimplicit");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);		
	
		menuItem = new JMenuItem(Messages.getString("menu.insert.surfaceparametric"));
		menuItem.setIcon(Icons.ADDSURFACEPARAMETRIC);
		menuItem.setActionCommand("addsurfaceparametric");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);
		mnuInsert.addSeparator();		
		
		menuItem = new JMenuItem(Messages.getString("menu.insert.premitive"));
		menuItem.setIcon(Icons.ADDPREMITIVE);
		menuItem.setActionCommand("addprimitive");
		menuItem.addActionListener(this);
		mnuInsert.add(menuItem);		
	
		/*
		this.mnuEvaluate = new JMenu(Messages.getString("menu.evaluate"));
		menuItem = new JMenuItem(Messages.getString("menu.evaluate.pointvspoint"));
     	//	menu.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("pointvspoint");
		menuItem.addActionListener(this);
		mnuEvaluate.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.evaluate.pointvsline"));
     	//	menu.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("pointvsline");
		menuItem.addActionListener(this);
		mnuEvaluate.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.evaluate.linevsline"));
     	//	menu.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("linevsline");
		menuItem.addActionListener(this);
		mnuEvaluate.add(menuItem);
		mnuEvaluate.addSeparator();
		menuItem = new JMenuItem(Messages.getString("menu.evaluate.pointvsplane"));
     	//	menu.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("pointvsplane");
		menuItem.addActionListener(this);
		mnuEvaluate.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.evaluate.linevsplane"));
     	//	menu.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("linevsplane");
		menuItem.addActionListener(this);
		mnuEvaluate.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.evaluate.planevsplane"));
     	//	menu.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("planevsplane");
		menuItem.addActionListener(this);
		mnuEvaluate.add(menuItem);
		mnuEvaluate.addSeparator();
		
		JMenu menu = new JMenu(Messages.getString("menu.evaluate.surface"));
     	//	menu.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("surface");
		menuItem.addActionListener(this);
		menuItem = new JMenuItem(Messages.getString("menu.evaluate.surface.drawtangent"));
     	//	menu.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("drawtangent");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.evaluate.surface.drawnormal"));
		//	menu.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("drawnormal");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		mnuEvaluate.add(menu);
		*/
		this.mnuSettings = new JMenu(Messages.getString("menu.settings"));
		JMenu menu = new JMenu(Messages.getString("menu.settings.scenesettings"));
		menu.setIcon(Icons.SCENESETTINGS);
		menu.setActionCommand("scenesettings");
		menu.addActionListener(this);
		
		menuItem = new JMenuItem(Messages.getString("menu.settings.scenesettings.enableantialias"));
		menuItem.setActionCommand("antialias");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.settings.scenesettings.enablelights"));
		menuItem.setActionCommand("light");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.settings.scenesettings.enableperspective"));
		menuItem.setActionCommand("perspective");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem("Edit Scene Settings..");
		menuItem.setActionCommand("scenesettings");
		menuItem.addActionListener(this);
		menu.add(menuItem);
	
		mnuSettings.add(menu);
		mnuSettings.addSeparator();
		menu = new JMenu(Messages.getString("menu.settings.steroscopysettings"));
		menu.setIcon(Icons.STEREOSCOPE);
		menu.setActionCommand("steroscopysettings");
		menu.addActionListener(this);
		menuItem = new JMenuItem(Messages.getString("menu.settings.steroscopysettings.onoff"));
		menuItem.setActionCommand("stereoscope");
		menuItem.addActionListener(this);
		menu.add(menuItem);	
		menu.addSeparator();
	
		menuItem = new JMenuItem(Messages.getString("menu.settings.steroscopysettings.mode1"));
		menuItem.setActionCommand("mode1 ");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.settings.steroscopysettings.mode2"));
		menuItem.setActionCommand("mode2 ");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.settings.steroscopysettings.mode3"));
		menuItem.setActionCommand("mode3 ");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem(Messages.getString("menu.settings.steroscopysettings.mode4"));
		menuItem.setActionCommand("mode4 ");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.settings.steroscopysettings.mode5"));
		menuItem.setActionCommand("mode5 ");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		mnuSettings.add(menu);
		
		menu = new JMenu(Messages.getString("menu.settings.hsrsettings"));
		menu.setIcon(Icons.HSR);
		menu.setActionCommand("hsrsettings");
		menu.addActionListener(this);
		mnuSettings.addSeparator();
		
		/*
		menuItem = new JMenuItem(Messages.getString("menu.settings.hsrsettings.zsort"));
		//menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("zsort");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.settings.hsrsettings.zbuffer"));
		//menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("zbuffer");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.settings.hsrsettings.bsptree"));
		//menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("bsptree");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		mnuSettings.add(menu);
		*/
		menu = new JMenu(Messages.getString("menu.settings.axissettings"));
		menu.setIcon(Icons.AXIS);
		menu.setActionCommand("axissettings");
		menu.addActionListener(this);
		
		menuItem = new JMenuItem(Messages.getString("menu.settings.axissettings.showaxis"));
		//menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("axis");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.settings.axissettings.showbox"));
	
		//menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("box");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem(Messages.getString("menu.settings.axissettings.editaxis"));
		//menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("axissettings");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		mnuSettings.add(menu);
		
		
		// get the test listing file
				ResourceBundle bundle = ResourceBundle
						.getBundle("com.calc3d.examples.examples");
				this.mnuTests = createMenuForExamples(null,bundle.getString("examples"), bundle);
				this.mnuTests.setText("Examples");
		/*
		mnuSettings.addSeparator();
		menu = new JMenu(Messages.getString("menu.settings.drawmodesettings"));
		menu.setIcon(Icons.DRAWMODE);
		menu.setActionCommand("axissettings");
		menu.addActionListener(this);
		
		menuItem = new JMenuItem(Messages.getString("menu.settings.drawmodesettings.wireframe"));
		//menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("wireframe");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.settings.drawmodesettings.solid"));
		//menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("solid");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem(Messages.getString("menu.settings.drawmodesettings.solidflat"));
		//menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("solidflat");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem(Messages.getString("menu.settings.drawmodesettings.grayscale"));
		//menuItem.setIcon(Icons.ADDSURFACE);
		menuItem.setActionCommand("grayscale");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		mnuSettings.add(menu);
		
		*/
		// Help menu
		// help menu
		this.mnuHelp = new JMenu(Messages.getString("menu.help"));

		this.mnuContent = new JMenuItem(	Messages.getString("menu.help.content"));
		mnuContent.setIcon(Icons.CONTENT);
		mnuContent.setActionCommand("content");
		mnuContent.addActionListener(this);

			
		this.mnuGotoHomePage = new JMenuItem(	Messages.getString("menu.help.gotoHomePage"));
		mnuGotoHomePage.setIcon(Icons.GOTOHOMEPAGE);
		mnuGotoHomePage.setActionCommand("homepage");
		mnuGotoHomePage.addActionListener(this);

		this.mnuCheckUpdate = new JMenuItem(	Messages.getString("menu.help.checkUpdates"));
		mnuCheckUpdate.setIcon(Icons.CHECKUPDATE);
		mnuCheckUpdate.setActionCommand("about");
		mnuCheckUpdate.addActionListener(this);

		this.mnuAbout = new JMenuItem(	Messages.getString("menu.help.about"));
		mnuAbout.setIcon(Icons.ABOUT);
		mnuAbout.setActionCommand("about");
		mnuAbout.addActionListener(this);

		
		this.mnuHelp.add(mnuContent);
		this.mnuHelp.addSeparator();
		this.mnuHelp.add(mnuGotoHomePage);
		this.mnuHelp.add(mnuCheckUpdate);
		this.mnuHelp.addSeparator();
		this.mnuHelp.add(mnuAbout);
		
		//Add main menu to menu bar
		this.barMenu.add(this.mnuFile);
		this.barMenu.add(this.mnuWindow);	
		this.barMenu.add(this.mnuInsert);	
		this.barMenu.add(this.mnuTests);
		//this.barMenu.add(this.mnuEvaluate);	
		this.barMenu.add(this.mnuSettings);	
		this.barMenu.add(this.mnuHelp);	
		
		this.setJMenuBar(this.barMenu);
				
		this.fileToolbar = new JToolBar(Messages.getString("toolbar.file"), JToolBar.HORIZONTAL);
		//this.fileToolbar.setFloatable(false);
		
		this.btnNew = new JButton(Icons.NEW);
		this.btnNew.addActionListener(this);
		this.btnNew.setActionCommand("new");
		this.btnNew.setToolTipText(Messages.getString("toolbar.file.new"));
		
		this.btnOpen = new JButton(Icons.OPEN);
		this.btnOpen.addActionListener(this);
		this.btnOpen.setActionCommand("open");
		this.btnOpen.setToolTipText(Messages.getString("toolbar.file.open"));
		
		this.btnSave = new JButton(Icons.SAVE);
		this.btnSave.addActionListener(this);
		this.btnSave.setActionCommand("save");
		this.btnSave.setToolTipText(Messages.getString("toolbar.file.save"));
		
		this.btnPrint = new JButton(Icons.PRINT);
		this.btnPrint.addActionListener(this);
		this.btnPrint.setActionCommand("print");
		this.btnPrint.setToolTipText(Messages.getString("toolbar.file.print"));
		
		this.btnExport = new JButton(Icons.EXPORT);
		this.btnExport.addActionListener(this);
		this.btnExport.setActionCommand("export");
		this.btnExport.setToolTipText(Messages.getString("toolbar.file.export"));
		
		this.btnPreferences = new JButton(Icons.PREFERENCES);
		this.btnPreferences.addActionListener(this);
		this.btnPreferences.setActionCommand("preferences");
		this.btnPreferences.setToolTipText(Messages.getString("toolbar.file.preferences"));
		
		this.btnZoomOut = new JButton(Icons.ZOOM_OUT);
		this.btnZoomOut.setToolTipText(Messages.getString("toolbar.preferences.zoomOut"));
		this.btnZoomOut.setActionCommand("zoomout");
		this.btnZoomOut.addActionListener(this);
		
		this.btnZoomIn = new JButton(Icons.ZOOM_IN);
		this.btnZoomIn.setToolTipText(Messages.getString("toolbar.preferences.zoomIn"));
		this.btnZoomIn.setActionCommand("zoomin");
		this.btnZoomIn.addActionListener(this);
		
		this.btnReset = new JButton(Icons.RESET);
		this.btnReset.setToolTipText(Messages.getString("toolbar.preferences.reset"));
		this.btnReset.setActionCommand("reset");
		this.btnReset.addActionListener(this);
		
		this.tglAxis = new JToggleButton(Icons.AXIS);
		this.tglAxis.setToolTipText(Messages.getString("toolbar.preferences.axis"));
		this.tglAxis.setActionCommand("axis");
		this.tglAxis.addActionListener(this);
		this.tglAxis.setSelected(true);//Preferences.isAntiAliasingEnabled());
		
		this.tglBox = new JToggleButton(Icons.BOX);
		this.tglBox.setToolTipText(Messages.getString("toolbar.preferences.box"));
		this.tglBox.setActionCommand("box");
		this.tglBox.addActionListener(this);
		this.tglBox.setSelected(true);//Preferences.isAntiAliasingEnabled());
		
		this.tglGridXY = new JToggleButton(Icons.GRIDXY);
		this.tglGridXY.setToolTipText(Messages.getString("toolbar.preferences.gridxy"));
		this.tglGridXY.setActionCommand("gridxy");
		this.tglGridXY.addActionListener(this);
		this.tglGridXY.setSelected(false);//Preferences.isAntiAliasingEnabled());
				
		fileToolbar.add(this.btnNew);
		fileToolbar.add(this.btnOpen);
		fileToolbar.add(this.btnSave);
		fileToolbar.add(this.btnPrint);
		fileToolbar.add(this.btnExport);
		fileToolbar.addSeparator();
		fileToolbar.add(btnPreferences);
		fileToolbar.add(this.btnZoomOut);
		fileToolbar.add(this.btnZoomIn);

		fileToolbar.add(this.btnReset);
		fileToolbar.addSeparator();
		fileToolbar.add(this.tglAxis);
		fileToolbar.add(this.tglBox);
		fileToolbar.add(this.tglGridXY);
		
		this.editToolbar = new JToolBar(Messages.getString("toolbar.edit"), JToolBar.HORIZONTAL);
		//this.editToolbar.setFloatable(false);
		this.btnAddPoint = new JButton(Icons.ADDPOINT);
		this.btnAddPoint.addActionListener(this);
		this.btnAddPoint.setActionCommand("addpoint");
		this.btnAddPoint.setToolTipText(Messages.getString("toolbar.edit.addpoint"));
		
		this.btnAddVector = new JButton(Icons.ADDVECTOR);
		this.btnAddVector.addActionListener(this);
		this.btnAddVector.setActionCommand("addvector");
		this.btnAddVector.setToolTipText(Messages.getString("toolbar.edit.addvector"));
		
		this.btnAddLine = new JButton(Icons.ADDLINE);
		this.btnAddLine.addActionListener(this);
		this.btnAddLine.setActionCommand("addline");
		this.btnAddLine.setToolTipText(Messages.getString("toolbar.edit.addline"));
				
		this.btnAddPolygon = new JButton(Icons.ADDPOLYGON);
		this.btnAddPolygon.addActionListener(this);
		this.btnAddPolygon.setActionCommand("addpolygon");
		this.btnAddPolygon.setToolTipText(Messages.getString("toolbar.edit.addpolygon"));
		
		this.btnAddPlane = new JButton(Icons.ADDPLANE);
		this.btnAddPlane.addActionListener(this);
		this.btnAddPlane.setActionCommand("addplane");
		this.btnAddPlane.setToolTipText(Messages.getString("toolbar.edit.addplane"));
		
		this.btnAddCurve3d = new JButton(Icons.ADDCURVE3D);
		this.btnAddCurve3d.addActionListener(this);
		this.btnAddCurve3d.setActionCommand("addcurve3d");
		this.btnAddCurve3d.setToolTipText(Messages.getString("toolbar.edit.addcurve3d"));
		
		this.btnAddCurve3d = new JButton(Icons.VECTOR);
		this.btnAddCurve3d.addActionListener(this);
		this.btnAddCurve3d.setActionCommand("addvectorfield");
		this.btnAddCurve3d.setToolTipText(Messages.getString("toolbar.edit.addvectorfield"));
	

	
		this.btnAddCurve2d_cartesian = new JButton(Icons.ADDCURVE2DCARTESIAN);
		this.btnAddCurve2d_cartesian.addActionListener(this);
		this.btnAddCurve2d_cartesian.setActionCommand("addcurve2dcartesian");
		this.btnAddCurve2d_cartesian.setToolTipText(Messages.getString("toolbar.edit.addcurve2dcartesian"));
		
		this.btnAddCurve2d_implicit = new JButton(Icons.ADDCURVE2DIMPLICIT);
		this.btnAddCurve2d_implicit.addActionListener(this);
		this.btnAddCurve2d_implicit.setActionCommand("addcurve2dimplicit");
		this.btnAddCurve2d_implicit.setToolTipText(Messages.getString("toolbar.edit.addcurve2dimplicit"));
		
		this.btnAddSurface_Explicit = new JButton(Icons.ADDSURFACE);
		this.btnAddSurface_Explicit.addActionListener(this);
		this.btnAddSurface_Explicit.setActionCommand("addsurface");
		this.btnAddSurface_Explicit.setToolTipText(Messages.getString("toolbar.edit.addsurface"));
		
		this.btnAddPrimitive = new JButton(Icons.ADDPREMITIVE);
		this.btnAddPrimitive.addActionListener(this);
		this.btnAddPrimitive.setActionCommand("addprimitive");
		this.btnAddPrimitive.setToolTipText(Messages.getString("toolbar.edit.addprimitive"));
			
		this.btnAddSurface_Parametric = new JButton(Icons.ADDSURFACEPARAMETRIC);
		this.btnAddSurface_Parametric.addActionListener(this);
		this.btnAddSurface_Parametric.setActionCommand("addsurfaceparametric");
		this.btnAddSurface_Parametric.setToolTipText(Messages.getString("toolbar.edit.addsurfaceparametric"));
	
		this.btnAddSurface_Implicit = new JButton(Icons.ADDSURFACEIMPLICIT);
		this.btnAddSurface_Implicit.addActionListener(this);
		this.btnAddSurface_Implicit.setActionCommand("addsurfaceimplicit");
		this.btnAddSurface_Implicit.setToolTipText(Messages.getString("toolbar.edit.addsurfaceimplicit"));
			
		this.btnRemoveElement = new JButton(Icons.REMOVE);
		this.btnRemoveElement.addActionListener(this);
		this.btnRemoveElement.setActionCommand("remove");
		this.btnRemoveElement.setToolTipText(Messages.getString("toolbar.edit.removeelement"));
	
		this.tglPerspective = new JToggleButton(Icons.PERSPECTIVE);
		this.tglPerspective.setToolTipText(Messages.getString("toolbar.preferences.perspective"));
		this.tglPerspective.setActionCommand("perspective");
		this.tglPerspective.addActionListener(this);
		this.tglPerspective.setSelected(true);//Preferences.isAntiAliasingEnabled());
		
		this.tgl3D = new JToggleButton(Icons.STEREOSCOPE);
		this.tgl3D.setToolTipText(Messages.getString("toolbar.preferences.stereoscope"));
		this.tgl3D.setActionCommand("stereoscope");
		this.tgl3D.addActionListener(this);
		this.tgl3D.setSelected(false);//Preferences.isAntiAliasingEnabled());
		
		this.tglAntialias = new JToggleButton(Icons.ANTIALIAS);
		this.tglAntialias.setToolTipText(Messages.getString("toolbar.preferences.antialias"));
		this.tglAntialias.setActionCommand("antialias");
		this.tglAntialias.addActionListener(this);
		this.tglAntialias.setSelected(false);//Preferences.isAntiAliasingEnabled());
		
		this.tglLight = new JToggleButton(Icons.LIGHT);
		this.tglLight.setToolTipText(Messages.getString("toolbar.preferences.light"));
		this.tglLight.setActionCommand("light");
		this.tglLight.addActionListener(this);
		this.tglLight.setSelected(true);//Preferences.isAntiAliasingEnabled());
		editToolbar.add(btnAddPoint);
		editToolbar.add(btnAddVector);
		editToolbar.add(btnAddLine);
		editToolbar.addSeparator();
		
		editToolbar.add(btnAddCurve3d);
		editToolbar.add(btnAddCurve2d_cartesian);
		editToolbar.add(btnAddCurve2d_implicit);
		editToolbar.addSeparator();
		
		editToolbar.add(btnAddPolygon);
		editToolbar.add(btnAddPlane);
		editToolbar.add(btnAddSurface_Explicit);
		
		editToolbar.addSeparator();
		editToolbar.add(btnAddSurface_Parametric);
		editToolbar.add(btnAddSurface_Implicit);
		
		editToolbar.add(btnAddPrimitive);
		editToolbar.add(btnRemoveElement);
		editToolbar.addSeparator();
		editToolbar.add(this.tglAntialias);
		editToolbar.add(this.tglLight);
		editToolbar.add(this.tgl3D);
		editToolbar.add(this.tglPerspective);
		
		editToolbar.addSeparator();
		
		JLabel lblDrawMode=new JLabel(Icons.DRAWMODE);
		lblDrawMode.setText("");
		lblDrawMode.setToolTipText(Messages.getString("toolbar.preferences.drawmode"));
		/*
		JLabel lblHSR=new JLabel(Icons.HSR);
		lblHSR.setToolTipText(Messages.getString("toolbar.preferences.hsrmode"));
		lblHSR.setText("HSR Mode:");
		String[] str ={"WireFrame","Solid","SolidFlat","GrayScale"};
		comboDrawMode=new JComboBox(str);
		comboDrawMode.setSelectedIndex(1);
		comboDrawMode.setMaximumSize(new Dimension(80,40));
	    
		str =new String[]{"Z sort","Z Buffer","BSP Tree"};
		comboHSR=new JComboBox(str);
		comboHSR.setSelectedIndex(1);
		comboHSR.setMaximumSize(new Dimension(80,40));
		
		editToolbar.add(comboDrawMode);
		//editToolbar.addSeparator();
		editToolbar.add(lblHSR);
		editToolbar.add(comboHSR);
		*/
		//editToolbar.addSeparator();
		editToolbar.add(lblDrawMode);
		bgColorIcon=new ColorIcon(Globalsettings.backgroundColor);
		editToolbar.add(bgColorIcon);
		
		//Add toolbars
		pnlToolBar = new JPanel();
		pnlToolBar.setLayout(new GridLayout(2, 1));
		pnlToolBar.add(fileToolbar);
		pnlToolBar.add(editToolbar);
		this.add(pnlToolBar,BorderLayout.NORTH);
		
		canvas3D=new Canvas3D();
		canvas3D.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		//canvas3D.setBackground(Color.white);
		Camera3D cam = new Camera3D();
		cam.setOrthographic(!Globalsettings.perspectiveEnabled);
		canvas3D.setCamera(cam);
		
		canvas3D.setRenderer(renderer);
		// scenes
		//setFocusable(true);
		//.setf
		canvas3D.setInteractionHandler(new InteractionHandler());
		this.add(canvas3D);
		
		pnlInfo=new JPanel();
		pnlTransform = new JPanel();
		pnlEdit = new JPanel();
		
		JTabbedPane tabs = new JTabbedPane();
		tabs.setBorder(BorderFactory.createEmptyBorder(7, 0, 0, 0));
		tabs.addTab(Messages.getString("tab.info"), this.pnlInfo);
		tabs.addTab(Messages.getString("tab.transform"), this.pnlTransform);
		tabs.addTab(Messages.getString("tab.edit"), this.pnlEdit);
		
		JPanel pnlLeft = new JPanel();
		pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
	
		 //Create an editor pane.
        editorPane = new JLabel();//createEditorPane();
       // editorPane.setBackground(Color.white);
        editorPane.setOpaque(true);
        editorPane.setBorder(BorderFactory.createEmptyBorder(3, 5, 5, 0));
         JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        editorScrollPane.setPreferredSize(new Dimension(250, 145));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));
        editorScrollPane.setEnabled(false);
        editorScrollPane.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        
        //Create a table pane.
        table = new JTable(new MyTableModel());
        TableColumnModel cModel = table.getColumnModel();
        table.setPreferredScrollableViewportSize(new Dimension(120, 100));
        table.setAutoscrolls(true);
        table.addMouseListener(this);
        // table.setFocusable(false);
        cModel.getColumn(0).setMinWidth(cModel.getColumn(0).getMinWidth()+5);
        cModel.getColumn(0).setMaxWidth(cModel.getColumn(0).getMinWidth()); cModel.getColumn(0).setResizable(false);
        cModel.getColumn(2).setMinWidth(cModel.getColumn(2).getMinWidth()+5);
        cModel.getColumn(2).setMaxWidth(cModel.getColumn(2).getMinWidth()); cModel.getColumn(2).setResizable(false);
        
        JScrollPane paneScrollPane = new JScrollPane(table);
        paneScrollPane.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        paneScrollPane.setPreferredSize(new Dimension(250, 155));
        paneScrollPane.setMinimumSize(new Dimension(10, 10));
 
        //Put the editor pane and the text pane in a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,paneScrollPane,
                                              editorScrollPane
                                              );
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.5);
        JPanel leftPane = new JPanel(new GridLayout(1,0));
        splitPane.setRequestFocusEnabled(false);
        leftPane.add(splitPane);
         
		//txtInfo.se
		
		//pnlLeft.add(table);
		//pnlLeft.add(tabs);
		
	   //JSplitPane pneSplit1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, table, txtInfo);
	  // pneSplit1.setDividerLocation(200);
		//this.add(pnlLeft,BorderLayout.WEST);
	    this.add(leftPane,BorderLayout.WEST);
		
		JSplitPane pneSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, canvas3D);
		// setup the layout
		pneSplit.setOneTouchExpandable(true);
		/*
		Container container = this.getContentPane();
		
		GroupLayout layout = new GroupLayout(container);
		
		container.setLayout(layout);
			layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(pnlToolBar, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(pneSplit));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(pnlToolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(pneSplit));
		*/
		this.setLayout(new BorderLayout());
		this.add(pnlToolBar,BorderLayout.NORTH);
		this.add(pneSplit,BorderLayout.CENTER);
		
		this.add(statusBar, BorderLayout.SOUTH);

		//size everything
		this.pack();
		sceneManager=new SceneManager();
		sceneManager.createDemo();
		//canvas3D.setBackgroundColor(Color.lightGray.brighter());
		canvas3D.setScene(sceneManager.createScene(true));//ObjectFactory.createDemo());
		canvas3D.setRequestFocusEnabled(false);
		canvas3D.setFocusable(true);
		canvas3D.requestFocus();
		
		canvas3D.getRenderer().setBackgroundColor(Globalsettings.backgroundColor);
		bgColorIcon.setBackground(canvas3D.getRenderer().getBackgroundColor());
		bgColorIcon.setActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas3D.getRenderer().setBackgroundColor(bgColorIcon.getBackground());
				Globalsettings.backgroundColor=bgColorIcon.getBackground();
			}
			
		});
		updateTable();
		setLastFileName(null);
		this.setSize(800, 400);
		this.setIconImage(Icons.APP.getImage());
		// let the methods in this class handle closing the window
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
	            public void windowClosing(WindowEvent e) {
	                close();
	            }
	    });
		
		this.setVisible(true);
	}

	/**
	 * 
	 * @param menu
	 *            the parent menu,can be null (if not null appends menu to
	 *            parent)
	 * @param str
	 * @param bundle
	 * @return parentmenu if not null, else new menu created
	 */
	private JMenu createMenuForExamples(JMenu menu, String str,
			ResourceBundle bundle) {
		if (str == null || str.isEmpty())
			return menu;

		String[] s = str.split(",");
		if (s.length == 0)
			return menu;
		if (s.length == 1) {
			//String name = str.substring(str.lastIndexOf("/") + 1,str.length() - 4);
			String name=FileUtils.fileNameWithoutExtension(str);
			name = name.substring(name.lastIndexOf("/") + 1,name.length());
			JMenuItem mnuTest = new JMenuItem(name);
			mnuTest.setActionCommand("test+" + str);
			mnuTest.addActionListener(this);
			menu.add(mnuTest);
		} else {
			JMenu mnu = new JMenu(s[s.length - 1]);
			for (int i = 0; i <= s.length - 2; i++) {
				createMenuForExamples(mnu, bundle.getString(s[i]), bundle);
			}
			if (menu == null) {
				menu = mnu;
			} else {
				menu.add(mnu);
			}

		}

		return menu;
	}

	/**
	 * Adds menu items to the given menu for each look and feel
	 * installed in the running vm.
	 * @param menu the menu to add the items to
	 */
	private void createLookAndFeelMenuItems(JMenu menu) {
		LookAndFeel current = UIManager.getLookAndFeel();
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			JMenuItem mnuLaF = new JMenuItem(info.getName());
			if (current.getClass().getName().equals(info.getClassName())) {
				mnuLaF.setIcon(Icons.CHECK);
			}
			mnuLaF.setActionCommand("laf+" + info.getClassName());
			mnuLaF.addActionListener(this);
			menu.add(mnuLaF);
		}
	}
	


	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command=arg0.getActionCommand();
		if (command.startsWith("laf+")) {
			// make sure they are sure
			int choice = JOptionPane.showConfirmDialog(this, 
					Messages.getString("dialog.laf.warning.text"), 
					Messages.getString("dialog.laf.warning.title"), 
					JOptionPane.YES_NO_CANCEL_OPTION);
			// check the user's choice
			if (choice == JOptionPane.YES_OPTION) {
				// parse out the LAF class name
				String className = command.replace("laf+", "");
				try {
					// attempt to set the look and feel
					UIManager.setLookAndFeel(className);
					Globalsettings.lookandFeel=UIManager.getLookAndFeel().getName();
					// get the current windows open by this application
					Window windows[] = Frame.getWindows();
					// update the ui
			        for(Window window : windows) {
			            SwingUtilities.updateComponentTreeUI(window);
			        }
			        // we need to pack since certain look and feels may have different component
			        // gaps which can cause stuff not to be shown
			        this.pack();
			        // find the item in the menu to set the current one
			        for (Component component : this.mnuLookAndFeel.getPopupMenu().getComponents()) {
			        	JMenuItem item = (JMenuItem)component;
			        	// set the newly selected LAF to have a checked icon
			        	// and the rest to have no icon
			        	if (item.getActionCommand().equalsIgnoreCase(command)) {
			        		item.setIcon(Icons.CHECK);
			        	} else {
			        		item.setIcon(null);
			        	}
			        }
				} catch (Exception e) {
					System.out.println(e);
				}
			
			}
		}
		if (command=="perspective"){
			  Globalsettings.perspectiveEnabled=!canvas3D.getRenderer().isPerspectiveEnabled();
		      canvas3D.getRenderer().setPerspectiveEnabled(!canvas3D.getRenderer().isPerspectiveEnabled());
		      this.tglPerspective.setSelected(canvas3D.getRenderer().isPerspectiveEnabled());
		}else if(command=="stereoscope"){
			  Globalsettings.steroscopyEnabled=!canvas3D.getRenderer().isStereoscopyEnabled();
			  canvas3D.getRenderer().setStereoscopyEnabled(!canvas3D.getRenderer().isStereoscopyEnabled());
			  this.tgl3D.setSelected(Globalsettings.steroscopyEnabled);
		}else if(command=="light"){
			  canvas3D.getRenderer().setLightsEnabled(!canvas3D.getRenderer().isLightsEnabled());
			  this.tglLight.setSelected(canvas3D.getRenderer().isLightsEnabled());
			  canvas3D.refresh();
		}else if(command=="antialias"){
			  Globalsettings.antiAliasingEnabled=!canvas3D.getRenderer().isAntiAliasingEnabled();
			  canvas3D.getRenderer().setAntiAliasingEnabled(!canvas3D.getRenderer().isAntiAliasingEnabled());
			  this.tglAntialias.setSelected(Globalsettings.antiAliasingEnabled);
			  canvas3D.refresh();
		}else if(command=="box"){
			  //sceneManager.setBoxVisible(!sceneManager.isBoxVisible());
			  // canvas3D.setScene(sceneManager.createScene(false));
			  Globalsettings.boxVisible=!Globalsettings.boxVisible;
			  this.tglBox.setSelected(Globalsettings.boxVisible);
			  canvas3D.refresh();
        }else if(command=="axis"){
        	  sceneManager.setAxisVisible(!sceneManager.isAxisVisible());
        	  canvas3D.setScene(sceneManager.createScene(false));
        	  this.tglAxis.setSelected(sceneManager.isAxisVisible());
        	  canvas3D.refresh();
		}else if(command=="gridxy"){
			  sceneManager.setGridXYVisible(!sceneManager.isGridXYVisible());
      	      canvas3D.setScene(sceneManager.createScene(false));
      	      this.tglGridXY.setSelected(sceneManager.isGridXYVisible());
      	      canvas3D.refresh();
		}else if(command=="addpoint"){
			  Element3D element=new Element3DPoint(new Vector3D());
			  element.setName("Point3D");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
      	      canvas3D.refresh();
		}else if(command=="addline"){
			  Element3D element=new Element3DLine(new Vector3D(),new Vector3D(2,5,3));
			  element.setName("Line3D");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
    	      canvas3D.refresh();
		}else if (command=="addpolygon"){
			  Element3D element=new Element3DPolygon(ElementPoly.createUnitCirclePolygon(3, 4, 0));
			  element.setName("Polygon3D");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
   	          canvas3D.refresh();
		}else if(command=="addsurface"){
			  Element3D element=new Element3DSurface("0.2*(x*x+y*y)-5");
			  element.setName("Surface3D");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
    	      canvas3D.refresh();
		}else if(command=="addsurfaceparametric"){
			  Element3D element=new Element3Dfunction("Pi=3.14;ir=.3+.1*sin(4*Pi*u)\nr=ir*sin(2*Pi*v)+.5\nx=r*sin(2*Pi*u)\ny=r*cos(2*Pi*u)\nz=1.5*ir*cos(Pi*v)\nx=x*5;y=y*5;z=z*5;");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  element.setFillmode(0);
			  element.setLineColor(new Color(109,95,163));
			
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
   	          canvas3D.refresh();
		}else if(command=="addsurfaceparametricold"){
			  Element3D element=new Element3DParametricSurface();
			  element.setName("Parametric Surface3D");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
 	          canvas3D.refresh();      
		}else if(command=="addcurve2dcartesian"){
			  Element3D element=new Element3Dcartesian2D("||x|-2|, x<1\nlog(x), x>=1");
			  element.setName("Explicit Curve2D");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
 	          canvas3D.refresh();
		}else if(command=="addcurve2dimplicit"){
			  Element3D element=new Element3Dimplicit2D("x*x+y*y-9");
			  element.setName("Implicit Curve2D");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
 	          canvas3D.refresh();
		}else if(command=="addplane"){
			  Element3D element=new Element3DPlane(0,0,1,0);
			  element.setName("Plane3D");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
 	          canvas3D.refresh();
 	  	}else if(command=="addvector"){
			  Element3D element=new Element3DVector(new Vector3D(1,1,1));
			  element.setName("Vector3D");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
   	          canvas3D.refresh();
		}else if(command=="addcurve3d"){
			  Element3DCurve element=new Element3DCurve();
			  element.setName("Curve3D");
			  element.setExprX("3*sin(t)");
			  element.setExprY("3*cos(t)");
			  element.setExprZ("t/3");
			  element.setCurveWidth(2);
			  element.setMax_t(12);
			  element.setMin_t(-12);
			  initialiseElement3D(element);
			  element=(Element3DCurve) AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
 	          canvas3D.refresh();
		}else if(command=="addsurfaceimplicit"){
			  Element3D element=new Element3DImplicit("x*x+y*y+z*z-9");
			  element.setName("Implicit3D");
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
    	      canvas3D.refresh();
		}else if(command=="addvectorfield"){
			  Element3DVectorField element=new Element3DVectorField();
			  element.setName("VectorField3D");
			  element.setExprX("x*y");
			  element.setExprY("1");
			  element.setExprZ("0");
			  element.setCurveWidth(1);
			  initialiseElement3D(element);
			  element=(Element3DVectorField) AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
 	          canvas3D.refresh();
		}else if(command=="addprimitive"){
			  Element3D element=new Element3DObject(5);
			  initialiseElement3D(element);
			  element=AddObjectDialog.show(this,element);
			  if (null==element)return;
			  sceneManager.addElement(element);
			  canvas3D.setScene(sceneManager.createScene(false));
	          canvas3D.refresh();      
 	          
		}else if(command=="remove"){
			  if (table.getSelectedRowCount()>0) {
				if (JOptionPane.showConfirmDialog(this,"Are you sure you want to delete the selected elements from list")== 0 )	
				{
					int deleteCount=0;
					for (int i : table.getSelectedRows()) {
						sceneManager.removeElement(sceneManager.getElement3D(i-deleteCount));
						deleteCount++;
					}
					System.out.println("Items Deleted:"+ deleteCount);
					updateTable();
					canvas3D.setScene(sceneManager.createScene(false));
					canvas3D.refresh();
				}
			  }
		}else if(command=="zoomin"){
			  canvas3D.iCamera.setFov((canvas3D.iCamera.getFov() + 359) % 360);
			  if (Globalsettings.steroscopyEnabled){
				  canvas3D.iCameraL.setFov(canvas3D.iCamera.getFov());
				  canvas3D.iCameraR.setFov(canvas3D.iCamera.getFov());
		      }
			  Globalsettings.fov=canvas3D.iCamera.getFov();
			  canvas3D.refresh();
		}else if(command=="zoomout"){
			  canvas3D.iCamera.setFov((canvas3D.iCamera.getFov() + 1) % 360);
			  if (Globalsettings.steroscopyEnabled){
				  canvas3D.iCameraL.setFov(canvas3D.iCamera.getFov());
				  canvas3D.iCameraR.setFov(canvas3D.iCamera.getFov());
			  }
			  Globalsettings.fov=canvas3D.iCamera.getFov();
			  canvas3D.refresh();
		}else if(command=="reset"){
			  canvas3D.iCamera.reset();
	 	      canvas3D.refresh();
		}else if (command=="export"){
			  exportPNG();
		}else if (command=="new"){	  
			  newFile();
		}else if(command=="open"){
			  loadFromFile();
		}else if(command=="save"){
			  saveToFile(false);
	    }else if(command=="saveas"){
			  saveToFile(true);
	    }else if(command=="print"){
			  printGraphics();
	    }else if (command=="exit"){
	    	  close();
	    }else if (command=="preferences"){
		      Preferences preferences;
		      preferences=SettingsDialog.show(this,Globalsettings.getSettings(),0);
			  if (null==preferences)return;
			  applySettings(preferences,false,!preferences.getClipBox().equals(Globalsettings.getClipBox()));
	    }else if(command=="filetoolbar"){
			  Preferences preferences=Globalsettings.getSettings();
			  preferences.setFileToolbarVisible(!Globalsettings.fileToolbarVisible);
			  applySettings(preferences,false,false);
	    }else if(command=="edittoolbar"){
	    	  Preferences preferences=Globalsettings.getSettings();
			  preferences.setObjectToolbarVisible(!Globalsettings.objectToolbarVisible);
			  applySettings(preferences,false,false);
	    }else if(command=="statusbar"){
	    	  Preferences preferences=Globalsettings.getSettings();
			  preferences.setStatusbarVisible(!preferences.isStatusbarVisible());
			  applySettings(preferences,false,false);
	    }else if(command.startsWith("mode")){
	  	      Globalsettings.steroscopyEnabled=true;
		      canvas3D.getRenderer().setStereoscopyEnabled(true);
		      this.tgl3D.setSelected(true);
		      Globalsettings.steroscopicMode=Integer.valueOf(command.substring(4,5))-1;
		      canvas3D.stereoMode= Globalsettings.steroscopicMode;
		      canvas3D.refresh();
	    }else if(command=="axissettings"){
	    	  Preferences preferences;
		      preferences=SettingsDialog.show(this,Globalsettings.getSettings(),3);
			  if (null==preferences)return;
			  applySettings(preferences,false,preferences.getClipBox().equals(Globalsettings.getClipBox()));
	    }else if(command=="scenesettings"){
	    	  Preferences preferences;
		      preferences=SettingsDialog.show(this,Globalsettings.getSettings(),2);
			  if (null==preferences)return;
			  applySettings(preferences,false,preferences.getClipBox().equals(Globalsettings.getClipBox()));
	    }else if(command=="content" ){
	    	  HelpDialog helpDialog=new HelpDialog(this,"About Calc3D","about.html");
	    	  helpDialog.show();
	    }else if(command=="about" ){
	    	  AboutDialog aboutDialog=new AboutDialog(this);
	    	  aboutDialog.show();
	    }else if(command=="homepage"){
	    	 try {
	             //Set your page url in this string. For eg, I m using URL for Google Search engine
	             String url = "http://code.google.com/p/calc3d/";
	             java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
	           }
	           catch (java.io.IOException e) {
	               System.out.println(e.getMessage());
	           }
	    }else if (command.startsWith("test+")) {
			if(command.endsWith(FileUtils.SIM_EXTENSION)){
				String name = command.substring(command.lastIndexOf("/") + 1,
						command.length() - 4);
				String file = command.replace("test+", "");
				// If simulation is different frm current prompt user 
				try {
		    		  ObjectInputStream is = new ObjectInputStream(Gui.class.getResourceAsStream(file));
		    		  ArrayList<Element3D> list=(ArrayList<Element3D>) is.readObject();
		    		  sceneManager.getElement3DList().clear();
		    		  sceneManager.getElement3DList().addAll(list);
		    		  Preferences preferences=(Preferences) is.readObject();
		    		  preferences.setLookandFeel(Globalsettings.lookandFeel);
		    		  applySettings(preferences,true,true);
		 	          updateTable();
		 	          //setLastFileName(fileName);
		 	          dirty=false;
				} catch (Exception e) {
					  JOptionPane.showMessageDialog(this,e.getMessage()+file,"Error",JOptionPane.ERROR_MESSAGE);
				}
			 } 
		} 
	  				
		if (command.startsWith("add"))updateTable();
	}
	
	
	private void initialiseElement3D(Element3D element3D){
	// check if we need to randomize colors
			sceneManager.setValidName(element3D);
			if (bodyColorRandom) {
				Color fc = ColorUtils.getRandomColor(0.5f, 1.0f);
				Color oc = fc.darker();
				element3D.setLineColor(oc);
				element3D.setFillColor(fc);
			}
	}
	
	/**
	 * Prints the graphics on Canvas
	 */
	private void printGraphics() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintable(canvas3D);
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * returns true if dirty (file is changed and need to be saved) and if necessary shows a dialog box
	 * @param saveMessage
	 * @return
	 */
    public boolean isDirty(String saveMessage) {
        if (!dirty) return false;
        int result=JOptionPane.showConfirmDialog(this,saveMessage,"File Modified",
            JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
        switch (result) {
            case JOptionPane.YES_OPTION:
                if (saveToFile(false)) return false;
                break;
            case JOptionPane.NO_OPTION:
                return false;
        }
        return true;
    }
	
	
	/**
	 * Last Working directories/files
	 */
	private String lastDirectory=null;
	private String lastFileName=null;
	
	  
    // Just in case I decide to put filename on title bar
    private void setLastFileName(String string) {
        lastFileName=string;
        if (string==null)string="Untitled Document";
        this.setTitle("Calc3D-"+string.substring(string.lastIndexOf(File.separatorChar)+1));

        /*
        final String title="3D Graph Explorer";
        if (parentFrame!=null) {
            if (string==null) parentFrame.setTitle(title+" v1.01");
            else parentFrame.setTitle(title+" - "+string.substring(string.lastIndexOf(File.separatorChar)+1));
        }
        */
    }
    
    /**
     * Exit
     */
    private void close() {
        if (!isDirty("Do you want to save before exit?")) System.exit(0);
    }
    
    /**
     *  erases everything
     */
    private void newFile() {
    	if (!isDirty("Do you want to save before creating a new file?")) {
             sceneManager.getElement3DList().clear();
             canvas3D.setScene(sceneManager.createScene(true));
             canvas3D.refresh();
             updateTable();
             dirty=false;
             lastFileName=null;
             setLastFileName(null);
    	}
    }
    
    
	/**
	 * returns filename by showing Open/Save dialogue box
	 * @param save flag to select opn/save dialogue
	 * @param ext extention of file  (ext (like ".ser") is ignored for save=false)
	 * @return
	 */
	private String getFileName(boolean save,String ext,String title) {
	    final JFileChooser fileChooser=new JFileChooser();
	    fileChooser.setDialogTitle(title);
	    fileChooser.setCurrentDirectory(new File(lastDirectory==null?System.getProperty("user.dir"):lastDirectory));
	    int result;
	    if (save) result=fileChooser.showSaveDialog(this);
	    else result=fileChooser.showOpenDialog(this);
	    if (result==JFileChooser.APPROVE_OPTION) {
	        String fileName=fileChooser.getSelectedFile().getPath();
	        lastDirectory=fileName;
	        // it also works if the next line is commented out!
	        lastDirectory=lastDirectory.substring(0,lastDirectory.lastIndexOf(File.separatorChar));
	        if (save && fileName.indexOf('.')==-1) fileName+=ext;
	        if (save && (new File(fileName)).exists())
	            if (JOptionPane.showConfirmDialog(this,"File "+fileName+" already exists. Overwrite?","Warning",
	                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)==JOptionPane.NO_OPTION)
	                return null;
	        return fileName;
	    }
	    else return null;
	}
	
	/**
	 * Shows load dialog and loads file if dialogue is not canceled
	 */
	 private void loadFromFile() {
	        loadFromFile(getFileName(false,"txt","Open new file..."));
	 }
	    
	 /**
	  * Loads file if exits and valid
	  * @param fileName file to be loaded
	  */
	 public void loadFromFile(String fileName) {
	        if (fileName==null) return;
	        try {
	    		  ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
	    		  ArrayList<Element3D> list=(ArrayList<Element3D>) is.readObject();
	    		  sceneManager.getElement3DList().clear();
	    		  sceneManager.getElement3DList().addAll(list);
	    		  Preferences preferences=(Preferences) is.readObject();
	    		  preferences.setLookandFeel(Globalsettings.lookandFeel);
	    		  applySettings(preferences,true,true);
	    		  //canvas3D.setScene(sceneManager.createScene(true));
	 	          //canvas3D.refresh();
	 	          updateTable();
	 	          setLastFileName(fileName);
	 	          dirty=false;
			 } catch (Exception e) {
				  JOptionPane.showMessageDialog(this,e.getMessage()+fileName,"Error",JOptionPane.ERROR_MESSAGE);
			 }
	 }
	 
	 
	 /**
	 * Shows save dialog optionally (for save as ... or when file is being saved for firsttime), and saves, returns true if succesful
	 * @param askName if new filename is to be chosen to save
	 * @return
	 */
	 private boolean saveToFile(boolean askName) {
        String fileName;
        if (sceneManager.getElementCount()<1)return false;
        if (askName || lastFileName==null) fileName=getFileName(true,".c3d","Save file as ...");
        else fileName=lastFileName;
        if (fileName==null) return false;
        
   	    try {
   		      ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
	    	  os.writeObject(sceneManager.getElement3DList());
	    	  os.writeObject(Globalsettings.getSettings());
	    	  dirty=false;
	    	  setLastFileName(fileName);
	 	} catch (IOException e) {
			  JOptionPane.showMessageDialog(this,"Could not save to file "+fileName,"Warning",JOptionPane.WARNING_MESSAGE);
		      return false;  
		}
        return true;
    }
	 
	/**
	 * Updates table Enteries with elements in scenemanager Element3Dlist
	 * List is to be updated each time the Element3DList of sceneManager is updated
	 */
    private void updateTable(){
    	Object[][] data= new Object[sceneManager.getElementCount()][3] ;
    	for (int i=0; i<sceneManager.getElementCount();i++){
    	   data[i][0]  =commonUtils.getobject3DIcon(sceneManager.getElement3D(i));
    	   data[i][1]  =sceneManager.getElement3D(i).getName();
    	   data[i][2]  =sceneManager.getElement3D(i).isVisible();
    	}
    	((MyTableModel)table.getModel()).setData(data);
    	int i=table.getSelectedRow(); 
    	if (i>=0){
		   editorPane.setText(commonUtils.getobject3DInfoHTML(sceneManager.getElement3D(i)));
    	}else{
    	   editorPane.setText("");
    	}
		editorPane.updateUI();
    	table.updateUI();
    	dirty=true;
    }
    
    /**
     * Saves current Canvas Drawing in PNG Format
     */
    private void exportPNG() {
        String fileName=getFileName(true,".png","Export to image as....");
        if (fileName==null) return;
        try {
            canvas3D.saveImage(fileName);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"Could not export to file "+fileName,"Warning",JOptionPane.WARNING_MESSAGE);
        }
    }
	
    /**
     * Apply Settings
     * @param skipGuiChanges set it true if gui is not to changed
     * @param recreateScene set it true if scene is to be reCreated from scratch
     */
	public void applySettings(Preferences preferences, boolean skipGuiChanges, boolean reCreateScene){
		
		 this.tglAntialias.setSelected(preferences.isAntiAliasingEnabled());
		 canvas3D.getRenderer().setAntiAliasingEnabled(preferences.isAntiAliasingEnabled());
		 this.tglPerspective.setSelected(preferences.isPerspectiveEnabled());
		 canvas3D.getRenderer().setPerspectiveEnabled(preferences.isPerspectiveEnabled());
		 this.tgl3D.setSelected(preferences.isSteroscopyEnabled());
		 canvas3D.stereoMode=preferences.getSteroscopicMode();
		 canvas3D.stereoEnabled=preferences.isSteroscopyEnabled();
		 canvas3D.getRenderer().setFogEnabled(preferences.isFogEnabled());
		 canvas3D.getRenderer().setBackgroundColor(preferences.getBackColor());
		 Globalsettings.axisColor=preferences.getAxisColor();
		 bgColorIcon.setBackground(preferences.getBackColor());
		 canvas3D.getLights()[0].setEnabled(preferences.isLight1Enabled());
		 canvas3D.getLights()[1].setEnabled(preferences.isLight2Enabled());
		 canvas3D.getLights()[2].setEnabled(preferences.isLight3Enabled());
		 if (!skipGuiChanges){ 	
			fileToolbar.setVisible(preferences.isFileToolbarVisible());
		    editToolbar.setVisible(preferences.isObjectToolbarVisible());
			LookAndFeel current = UIManager.getLookAndFeel();
			if ((Globalsettings.fileToolbarVisible != preferences.isFileToolbarVisible())|| 
					(Globalsettings.objectToolbarVisible!= preferences.isObjectToolbarVisible())) {
				GridLayout layout;
				if (preferences.isObjectToolbarVisible()
						& preferences.isFileToolbarVisible())
					layout = new GridLayout(2, 1);
				else
					layout = new GridLayout(1, 1);

				if (!preferences.isFileToolbarVisible()) {
					this.pnlToolBar.remove(fileToolbar);
			} else {
					this.pnlToolBar.add(fileToolbar);
				}

				if (!preferences.isObjectToolbarVisible())
					this.pnlToolBar.remove(editToolbar);
				else
					this.pnlToolBar.add(editToolbar);
				pnlToolBar.setLayout(layout);
				
				// pnlToolBar.validate();
				this.validate();
			}
		    // this.editToolbar.setVisible(preferences.isObjectToolbarVisible());
		    this.statusBar.setVisible(preferences.isStatusbarVisible());
			if (!current.getName().equalsIgnoreCase(
					preferences.getLookandFeel())) {
				int choice = JOptionPane.showConfirmDialog(this,
						Messages.getString("dialog.laf.warning.text"),
						Messages.getString("dialog.laf.warning.title"),
						JOptionPane.YES_NO_CANCEL_OPTION);
				// check the user's choice
				if (choice == JOptionPane.YES_OPTION) {
					for (int i = 0; i < mnuLookAndFeel.getItemCount(); i++) {

						mnuLookAndFeel.getItem(i).setIcon(null);
						if (mnuLookAndFeel.getItem(i).getText()
								.equalsIgnoreCase(preferences.getLookandFeel())) {
							String className = mnuLookAndFeel.getItem(i)
									.getActionCommand().replace("laf+", "");
							try {
								// attempt to set the look and feel
								UIManager.setLookAndFeel(className);
								mnuLookAndFeel.getItem(i).setIcon(Icons.CHECK);
								// get the current windows open by this
								// application
								Window windows[] = Frame.getWindows();
								// update the ui
								for (Window window : windows) {
									SwingUtilities
											.updateComponentTreeUI(window);
								}
								this.pack();
							} catch (Exception e) {
								System.out.println(e);
							}
							;
						}
					}
				}
			}
		} else{ //if skipguiUpdate =true
				//Save current Gui settings in preference object so that Globalsettings for gui remain unchanged
				preferences.setLookandFeel(Globalsettings.lookandFeel);
				preferences.setFileToolbarVisible(Globalsettings.fileToolbarVisible);
				preferences.setObjectToolbarVisible(Globalsettings.objectToolbarVisible);
				preferences.setStatusbarVisible(Globalsettings.statusbarVisible);
		}
	  	Globalsettings.saveSettings(preferences); 
	    sceneManager.setClip(new Clip(Globalsettings.mappedClipBox));
 		canvas3D.setScene(sceneManager.createScene(reCreateScene));
        canvas3D.refresh();    
			
	}
	
	/**
	 * The main method; uses zero arguments in the args array.
	 * @param args the command line arguments
	 */
	public static final void main(String[] args) {
		 // activate opengl
        //System.setProperty("sun.java2d.opengl", "True");
       // System.setProperty("sun.awt.noerasebackground", "true");
    	//JMenuItem.set
		// attempt to use the nimbus look and feel
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		        	Globalsettings.lookandFeel=UIManager.getLookAndFeel().getName();
			        break;
		        }
		    }
		} catch (Exception e) {
			// completely ignore the error and just use the default look and feel
		}
		
		// show the GUI on the EDT
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Gui();
			}
		});
	}
	
	 class MyTableModel extends AbstractTableModel {
		    private boolean DEBUG=false;
	        private String[] columnNames = {"Type",
	                                         "Element3D",
	                                        "Show"};
	        private Object[][] data = {
		    {Icons.LINE,"Line",new Boolean(false) },
		    {Icons.CURVE,"Curve1",new Boolean(false)},
		    {Icons.VECTOR,"Vector1",new Boolean(false) },
		    {Icons.SURFACE,"Surface1",new Boolean(false) },
		    {Icons.PLANE,"Plane1",new Boolean(false)},
		    {Icons.CURVE,"Curve2",new Boolean(false)}
	        };

	        public void setData(Object[][] data ){
	        	this.data = data ;
	        }
	        
	        public int getColumnCount() {
	            return columnNames.length;
	        }

	        public int getRowCount() {
	            return data.length;
	        }

	        public String getColumnName(int col) {
	            return columnNames[col];
	        }

	        public Object getValueAt(int row, int col) {
	            return data[row][col];
	        }

	        /*
	         * JTable uses this method to determine the default renderer/
	         * editor for each cell.  If we didn't implement this method,
	         * then the last column would contain text ("true"/"false"),
	         * rather than a check box.
	         */
	        public Class getColumnClass(int c) {
	            return getValueAt(0, c).getClass();
	        }

	        /*
	         * Don't need to implement this method unless your table's
	         * editable.
	         */
	        public boolean isCellEditable(int row, int col) {
	            //Note that the data/cell address is constant,
	            //no matter where the cell appears onscreen.
	            if (col == 2) {
	                return true;
	            } else {
	                return false;
	            }
	        }

	        /*
	         * Don't need to implement this method unless your table's
	         * data can change.
	         */
	        public void setValueAt(Object value, int row, int col) {
	            if (DEBUG) {
	                System.out.println("Setting value at " + row + "," + col
	                                   + " to " + value
	                                   + " (an instance of "
	                                   + value.getClass() + ")");
	            }

	            data[row][col] = value;
	            fireTableCellUpdated(row, col);

	             if (col==2){
	            		sceneManager.getElement3D(row).setVisible((Boolean)value);
	            	    sceneManager.createScene(false);
	            	    canvas3D.setScene(sceneManager.createScene(false));
	         	        canvas3D.refresh();
	            	    System.out.println("Visibility of row:" + row +"="+(Boolean)value);
	             }
	             
	             if (DEBUG){
	            	 System.out.println("New value of data:");
		            printDebugData();
	             }
	        }

	        private void printDebugData() {
	            int numRows = getRowCount();
	            int numCols = getColumnCount();

	            for (int i=0; i < numRows; i++) {
	                System.out.print("    row " + i + ":");
	                for (int j=0; j < numCols; j++) {
	                    System.out.print("  " + data[i][j]);
	                }
	                System.out.println();
	            }
	            System.out.println("--------------------------");
	        }
	    }


	@Override
	public void mouseClicked(MouseEvent e) {
	  if (e.getClickCount()==2){
		  int i=table.getSelectedRowCount();
		  if (i!=1)return;
		  i=table.getSelectedRow();
		  Element3D element=AddObjectDialog.showEdit(this,sceneManager.getElement3D(i));
		  if (null==element)return;
		  sceneManager.setElement3D(i,element);
		  canvas3D.setScene(sceneManager.createScene(false));
		  canvas3D.refresh();
		  updateTable();
	  }
		  
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		 
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		 int i=table.getSelectedRow(); if (i<0)return;
		  editorPane.setText(commonUtils.getobject3DInfoHTML(sceneManager.getElement3D(i)));
		  editorPane.updateUI();
	}
 
	 
}
