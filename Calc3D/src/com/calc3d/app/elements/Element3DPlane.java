package com.calc3d.app.elements;

import com.calc3d.app.Globalsettings;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementPoly;
import com.calc3d.geometry3d.Plane3D;
import com.calc3d.math.Vector3D;

/**
 * Class for Elements3D representing Plane in 3D space
 * 
 * @author mahesh
 * 
 */
public class Element3DPlane extends Element3D {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7685553015321609440L;
	private Plane3D plane3D;
	
	public Element3DPlane(Vector3D pt1, Vector3D pt2, Vector3D pt3) {
		ElementPoly e = new ElementPoly();
		e.addPoint(pt1);
		e.addPoint(pt2);
		e.addPoint(pt3);
		plane3D = new Plane3D(pt1, pt2, pt3);
	}

	
	public Element3DPlane(double a, double b, double c, double d) {
		plane3D = new Plane3D(a,b, c, d);
	}

	public Element3DPlane(Plane3D plane) {
		this(plane.get_a(),plane.get_b(),plane.get_c(),plane.get_d());
	}

	@Override
	public Element createElement(Clip clip) {
		if (T!=null){	//Apply transform to normal of plane only	
			Vector3D v=new Vector3D(plane3D.get_a(),plane3D.get_b(),plane3D.get_c());
			T.transform(v);
			element=clip.getClippedPolygonfromPlane(new Plane3D(v.getX(),v.getY(),
					v.getZ(), plane3D.get_d()));
		}else{
			element=clip.getClippedPolygonfromPlane(new Plane3D(Globalsettings.mapX(plane3D.get_a()), Globalsettings.mapY(plane3D.get_b()),
				                                Globalsettings.mapZ(plane3D.get_c()), plane3D.get_d()));
		}
		
		if (null==element) return null;
		element.setFillColor(getFillColor());
		element.setBackColor(getBackColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
    	element.setSpilttable(isSplittable());
		return element;
	}

	public String getExpression() {
		return plane3D.toString();
	}

	@Override
	public String getDefinition() {
		return plane3D.toString();
	}
	public Plane3D getPlane3D() {
		return plane3D;
	}

	public void setPlane3D(Plane3D plane3D1) {
		this.plane3D = plane3D1;
	}

	@Override
	public Element createElement() {
		return createElement(new Clip(-1,1,-1,1,-1,1));
	}

}