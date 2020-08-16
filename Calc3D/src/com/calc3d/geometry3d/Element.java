package com.calc3d.geometry3d;

import java.awt.Color;

import com.calc3d.app.elements.Element3D;
import com.calc3d.math.AffineTransform3D;
import com.calc3d.math.MathUtils;
import com.calc3d.math.Vector3D;


// superclass for drawable objects
public abstract class Element implements Comparable<Element> {
    
	public static final int DRAWMODE_SOLID=0;
	public static final int DRAWMODE_DASHED=1;
	
	public static final int FILLMODE_SOLID=0;
	public static final int FILLMODE_GRAY=1;
	public static final int FILLMODE_NONE=2;
	
	/**true if element can be split against partition plane in BSP*/
	private boolean spilttable=true;
	/**
     * flag to toggle renderability of element, it should be set to false if 
     * this object does not render (eg when it is culled)
     */
	private boolean renderable=true;    
   	
	/** true if this element is to be filled*/
    private boolean isfilled=true;
    
    /** true if lines drawn for this element are dashed*/
    private boolean isdashed;
    
	protected Color lineColor,fillColor,backColor;

	protected int curveWidth=1;
	
    /**
     * represents z depth of centre of element, which is needed for z sort
     */
    public double depth;
    
    /**
     * represents centre of element (must be defined by its subclasses in their constructors)
     */
    protected Vector3D centre=new Vector3D();
    /**
     * Compare method for z sort painter algorithm
     */
    
    public Element(){
    	lineColor=Color.black;
    	fillColor=Color.white;
    	backColor=Color.gray;
    }
    
    public int compareTo(Element e) {
    	if ((e==null)||(this==null))return 0;
    	if (!MathUtils.isValidNumber(depth)||!MathUtils.isValidNumber(e.depth))return 0;
        if (depth>e.depth) return -1;
        else if (depth<e.depth) return 1;
        else return 0;
    }
  
    /**
     * returns centre of element, needed to calculate depth and normal vector of plane
     */
    public Vector3D getCentre(){ return centre;};
    
    public abstract void transform(AffineTransform3D T);

	public boolean isRenderable() {
		return renderable;
	}

	public void setRenderable(boolean renderable) {
		this.renderable = renderable;
	}

	/** Returns if fill pattern of element is solid/hollow*/
	public boolean isFilled() {
		return isfilled;
	}

	/** Sets the element fill pattern to be hollow/solid */
	public void setFilled(boolean isfilled) {
		this.isfilled = isfilled;
	}

	/** Returns if line rendering pattern of element is dahsed/solid*/
	public boolean isDashed() {
		return isdashed;
	}

	/** Sets the element line rendering pattern to be dashed/solid */
	public void setDashed(boolean isdashed) {
		this.isdashed = isdashed;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public int getCurveWidth() {
		return curveWidth;
	}

	public void setCurveWidth(int curveWidth) {
		this.curveWidth = curveWidth;
	}

	public boolean isSpilttable() {
		return spilttable;
	}

	public void setSpilttable(boolean spilttable) {
		this.spilttable = spilttable;
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
	 * updates rendering properties of this element to element in parameter
	 * @param e
	 */
	public void updateElement(Element e){
		this.setFillColor(e.getFillColor());
		this.setBackColor(e.getBackColor());
		this.setLineColor(e.getLineColor());
		this.setSpilttable(e.isSpilttable());
		this.setDashed(e.isDashed());
		this.setCurveWidth(e.getCurveWidth());
	}
  }

