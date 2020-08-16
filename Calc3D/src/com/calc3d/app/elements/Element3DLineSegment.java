package com.calc3d.app.elements;

import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementCurve;
import com.calc3d.math.Vector3D;


public class Element3DLineSegment extends Element3D {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5198536529439559238L;
	private Vector3D point1,point2;
	public Element3DLineSegment(Vector3D pt1, Vector3D pt2) {
		splittable=true;
		element = new ElementCurve(pt1, pt2);
		point1=new Vector3D(pt1);
		point2=new Vector3D(pt2);
	}

	public Element3DLineSegment(Element3DPoint pt1, Element3DPoint pt2) {
		element = new ElementCurve(pt1.element.getCentre().clone(),
				pt2.element.getCentre().clone());
		point1=new Vector3D(pt1.element.getCentre());
		point2=new Vector3D(pt2.element.getCentre());
	}

	@Override
	public String getDefinition() {
		return ((ElementCurve) element).getLine3D().toString();
	}

	public Vector3D getPoint1() {
		return point1;
	}
	
	public Vector3D getPoint2() {
		return point2;
	}
	
	public void setPoint1(Vector3D pt) {
		 point1=new Vector3D(pt);
		 element = new ElementCurve(point1, point2);
	}
	
	public void setPoint2(Vector3D pt) {
		point1=new Vector3D(pt);
		element = new ElementCurve(point1, point2);
	}

	@Override
	public Element createElement() {
		element=new ElementCurve(point1, point2);
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
    	element.setSpilttable(isSplittable());
		return element;
	}

	@Override
	public Element createElement(Clip clip) {
		element=new ElementCurve(point1, point2);
		element=clip.getClippedElement(element);
		if (null==element) return null;
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
    	element.setSpilttable(isSplittable());
		return element;
	}

	
}