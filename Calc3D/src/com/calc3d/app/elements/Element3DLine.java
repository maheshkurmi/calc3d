package com.calc3d.app.elements;

import com.calc3d.app.Globalsettings;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementCollection;
import com.calc3d.geometry3d.ElementCurve;
import com.calc3d.geometry3d.ElementPoint;
import com.calc3d.geometry3d.Line3D;
import com.calc3d.math.Vector3D;

public class Element3DLine extends Element3D {


	private Line3D line3D;
	private Vector3D pt1,pt2;
    private boolean lineSegment=true;
    private boolean drawPoints=true;
	public Element3DLine(Vector3D pt1, Vector3D pt2) {
		this.pt1=pt1;
		this.pt2=pt2;
		splittable=true;
		line3D = new Line3D(pt1, pt2);
	}

	@Override
	public Element createElement(Clip clip) {
		//Vector3D Pt1=new Vector3D(Globalsettings.inverseMapX(line3D.Pt3D.getX()),Globalsettings.inverseMapY(line3D.Pt3D.getY()),Globalsettings.inverseMapZ(line3D.Pt3D.getZ()));
		//Vector3D pt2=pt1.add(line3D.DirVector3D);
		Vector3D tempPt1=new Vector3D(Globalsettings.inverseMapX(getPt1().getX()),Globalsettings.inverseMapY(getPt1().getY()),Globalsettings.inverseMapZ(getPt1().getZ()));
		Vector3D tempPt2=new Vector3D(Globalsettings.inverseMapX(getPt2().getX()),Globalsettings.inverseMapY(getPt2().getY()),Globalsettings.inverseMapZ(getPt2().getZ()));
		if(T!=null)T.transform(tempPt1);
		if(T!=null)T.transform(tempPt2);
		
		element=new ElementCurve(tempPt1,tempPt2);
		element.setFillColor(getFillColor());
		element.setBackColor(getBackColor());
		element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
    	element.setSpilttable(isSplittable());
		
		ElementCollection e=new ElementCollection();
    	if (!lineSegment){
    		Line3D line=new Line3D(tempPt1,tempPt2);
			ElementCurve ec=clip.getClippedLinefromLine3D(line);
	        ec.updateElement(element);
	        e.addElement(ec);
	  	}else{
			Element ec=new ElementCurve(tempPt1,tempPt2);
			ec=clip.getClippedElement(ec);
			if (ec!=null){
			   ec.updateElement(element);
			   e.addElement(ec);
			}
		}
    	
    	if(drawPoints){
    		Element ep1= new ElementPoint(tempPt1);
    		ep1=clip.getClippedElement(ep1);
    		if (ep1!=null){
    			ep1.updateElement(element);	e.addElement(ep1);
    		}
    		Element ep2= new ElementPoint(tempPt2);
    		ep2=clip.getClippedElement(ep2);
    		if (ep2!=null){
    			ep2.updateElement(element);e.addElement(ep2);
        	}
       	}
    	
    	element=e;
		if (null==element) return null;
		return	element;
	}

	@Override
	public Element createElement() {
		element=new Clip(-1,1,-1,1,-1,1).getClippedLinefromLine3D(line3D);
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
    	if(T!=null)element.transform(T);
    	element.setSpilttable(isSplittable());
		return	element;
	}
	@Override
	public String getDefinition() {
		return "Point1 = "+getPt1().getPointText()+" <b>&</b> Point2 = "+getPt2().getPointText()+"<br><br>"+"<b>Equation: </b> "+line3D.toString();
	}

	public String getExpression() {
		return line3D.toString();
	}

	public Line3D getLine() {
		return line3D;
	}

	public void setLine(Line3D line) {
		 line3D=line;
	}

	/**
	 * @return if the element is a lineSegment or a line joining the 2 points
	 */
	public boolean isLineSegment() {
		return lineSegment;
	}

	/**
	 * @param lineSegment Setting it true creates line segment joining 2 points specified, setting it false creates
	 * infinite line (clipped) joining the points, i.e. line extends beyond the points.
	 */
	public void setLineSegment(boolean lineSegment) {
		this.lineSegment = lineSegment;
	}

	/**
	 * @return if the end points of line are two be drawn or not
	 */
	public boolean isDrawPoints() {
		return drawPoints;
	}

	/**
	 * @param drawPoints Setting it true creates end points as well
	 */
	public void setDrawPoints(boolean drawPoints) {
		this.drawPoints = drawPoints;
	}

	/**
	 * @return the pt1
	 */
	public Vector3D getPt1() {
		return pt1;
		
	}

	/**
	 * @param pt1 the pt1 to set
	 */
	public void setPt1(Vector3D pt1) {
		this.pt1 = pt1;
		line3D = new Line3D(pt1, pt2);
	}

	/**
	 * @return the pt2
	 */
	public Vector3D getPt2() {
		return pt2;
	}

	/**
	 * @param pt2 the pt2 to set
	 */
	public void setPt2(Vector3D pt2) {
		this.pt2 = pt2;
		line3D = new Line3D(pt1, pt2);
	}

}

/**
 * Class for Elements3D representing Line in 3D space
 * 
 * @author mahesh
 * 
 */