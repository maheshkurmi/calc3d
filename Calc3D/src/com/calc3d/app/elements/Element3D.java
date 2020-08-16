package com.calc3d.app.elements;

import java.awt.Color;
import java.io.Serializable;

import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.math.AffineTransform3D;
import com.calc3d.math.Angles3D;
import com.calc3d.math.Vector3D;

/**
 * Base Class for Elements used by scene manager
 * 
 * @author mahesh
 * 
 */
 public abstract class Element3D implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2177499520844129649L;

	/** True if Object is visible */
	private boolean visible = true;

	/** name of Object */
	private String name="";

	/** Element associated with this element recognised by 3D engine */
	protected transient Element element;

	protected Color fillColor=Color.white, lineColor=Color.black;
	protected Color backColor=Color.gray;
    
	/**Transform parameters*/
	private Vector3D translationVector=new Vector3D();
	private Angles3D rotationAngle=new Angles3D();
		
	protected int curveWidth=1;
	
	protected transient AffineTransform3D T;
	
	protected transient boolean isCreated=false;
	
	/**if line is drawn in dASHED MODE*/
	protected boolean dashed;
	
	/**if element is to be splitted while solving BSP Tree*/
	
	protected boolean splittable=true;
	/***/
	protected int fillmode=0;
	
	/** returns if this element is to be rendered or not */
	public boolean isVisible() {
		return visible;
	}

	/** sets flag if element is visible on screen(renderable) */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/** returns name asssociated with this Element3D */
	public String getName() {
		return name;
	}

	/** Sets name for this Ellement3D */
	public void setName(String name) {
		this.name = name;
	}

	public Element getElement() {
		if (element==null)return null;
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
		return element;
	}

	/** returns equation or expression associated with this element3D in HTML format*/
	public abstract String getDefinition();
	

	/** Transform element with input Transformation */
	public void setTransform(AffineTransform3D T) {
		this.T=T;
	}

	/** return current Transform of element*/
	public AffineTransform3D getTransform() {
		return T;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public int getCurveWidth() {
		return curveWidth;
	}

	public void setCurveWidth(int curveWidth) {
		if (curveWidth>=0)this.curveWidth = curveWidth;
	}
	
	/** Returns if line rendering pattern of element is dahsed/solid*/
	public boolean isDashed() {
		return dashed;
	}

	/** Sets the element line rendering pattern to be dashed/solid */
	public void setDashed(boolean isdashed) {
		this.dashed = isdashed;
	}

	/**
	 * returns element recognised by 3D engine, Without cliiping, returns
	 * null if there is no element
	 * @return 
	 */
	public abstract Element createElement();
	
	/**
	 * returns List of elements recognised by 3D engine, Elements are clipped
	 * against the box defined by clip Object
	 * 
	 * @param clip
	 *            Clip Object to clip the elements against bounding box defined
	 *            by it
	 * @return Element3D which define this element, if
	 *         there is no element in clipped region it returns <b> null </b>
	 */
	public abstract Element createElement(Clip clip);

	public int getFillmode() {
		return fillmode;
	}

	public void setFillmode(int fillmode) {
		if ((fillmode>2)||(fillmode<0))return;
		this.fillmode = fillmode;
	}

	/**
	 * @return the backColor
	 */
	public Color getBackColor() {
		return backColor;
	}

	/**
	 * @param backColor the backColor to set
	 */
	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	/**
	 * @return the splittable
	 */
	public boolean isSplittable() {
		return splittable;
	}

	/**
	 * @param splittable the splittable to set
	 */
	public void setSplittable(boolean splittable) {
		this.splittable = splittable;
	}
	
	/**
	 * Updates redndering properties of this to element in parameter
	 * @param e
	 */
	public void updateElement(Element3D e){
		this.setFillColor(e.getFillColor());
		this.setBackColor(e.getBackColor());
		this.setLineColor(e.getLineColor());
		this.setSplittable(e.splittable);
		this.setDashed(e.isDashed());
		this.setCurveWidth(e.getCurveWidth());
	}

	/**
	 * @return the translationVector
	 */
	public Vector3D getTranslation() {
		return translationVector;
	}

	/**
	 * @param translationVector the translationVector to set
	 */
	public void setTranslation(Vector3D translationVector) {
		this.translationVector = translationVector;
	}

	/**
	 * @return the rotationAngle
	 */
	public Angles3D getRotation() {
		return rotationAngle;
	}

	/**
	 * @param rotationAngle the rotationAngle to set
	 */
	public void setRotation(Angles3D rotationAngle) {
		this.rotationAngle = rotationAngle;
	}
	
}