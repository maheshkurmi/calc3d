package com.calc3d.app.elements;

import com.calc3d.app.Globalsettings;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementArrow;
import com.calc3d.math.Vector3D;

/**
 * Class for Elements3D representing Line in 3D space
 * 
 * @author mahesh
 * 
 */
public class Element3DVector extends Element3D {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1312137775113914325L;
	private Vector3D vector3D;
    public Vector3D point1,point2;
	public Element3DVector(double x, double y, double z) {
		//element = new ElementArrow(new Vector3D(0, 0, 0), new Vector3D(x, y, z));
		vector3D = new Vector3D(x, y, z);
		point1=new Vector3D(0,0,0);
		point2=new Vector3D(x,y,z);
	}

	public Element3DVector(Vector3D pt) {
		this(pt.getX(),pt.getY(),pt.getZ());
	}

	public Element3DVector(Vector3D pt1, Vector3D pt2) {
		point1=new Vector3D(pt1);
		point2=new Vector3D(pt2);
		//element = new ElementArrow(point1, point2);
		vector3D = new Vector3D(pt1, pt2);
	}

	
	public Vector3D getPoint1() {
		return point1;
	}
	
	public Vector3D getPoint2() {
		return point2;
	}
	
	public void setPoint1(Vector3D pt) {
		 point1=new Vector3D(pt);
		 //element = new ElementArrow(point1, point2);
	}
	
	public void setPoint2(Vector3D pt) {
		point1=new Vector3D(pt);
		//element = new ElementArrow(point1, point2);
	}

	@Override
	public String getDefinition() {
		return vector3D.toString();
	}

	@Override
	public Element createElement() {
		
		Vector3D pt1,pt2;
		pt1=new Vector3D(Globalsettings.inverseMapX(point1.getX()),Globalsettings.inverseMapY(point1.getY()),Globalsettings.inverseMapZ(point1.getZ()));
		pt2=new Vector3D(Globalsettings.inverseMapX(point2.getX()),Globalsettings.inverseMapY(point2.getY()),Globalsettings.inverseMapZ(point2.getZ()));
		if(T!=null)T.transform(pt1);
		if(T!=null)T.transform(pt2);
		element=new ElementArrow(pt1, pt2);
		element.setFillColor(getFillColor());
	    element.setLineColor(getLineColor());
	    element.setCurveWidth(getCurveWidth());
	    element.setDashed(isDashed());
		element.setSpilttable(isSplittable());
	    isCreated=true;
		return element;
	}
	
	@Override
	public Element createElement(Clip clip) {
		Vector3D pt1,pt2;
		pt1=new Vector3D(Globalsettings.inverseMapX(point1.getX()),Globalsettings.inverseMapY(point1.getY()),Globalsettings.inverseMapZ(point1.getZ()));
		pt2=new Vector3D(Globalsettings.inverseMapX(point2.getX()),Globalsettings.inverseMapY(point2.getY()),Globalsettings.inverseMapZ(point2.getZ()));
		if(T!=null)T.transform(pt1);
		if(T!=null)T.transform(pt2);
		element=new ElementArrow(pt1, pt2);
		element=clip.getClippedElement(element);
		if (null==element) return null;
		element.setFillColor(getFillColor());
	    element.setLineColor(getLineColor());
	    element.setCurveWidth(getCurveWidth());
	    element.setDashed(isDashed());
		element.setSpilttable(isSplittable());
	    isCreated=true;
		return element;
	}

}
