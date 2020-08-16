package com.calc3d.app.elements;



import java.util.ArrayList;
import com.calc3d.app.Globalsettings;
import com.calc3d.app.resources.Messages;
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
public class Element3DPolygon extends Element3D {

	public ArrayList<Vector3D> vertices=new ArrayList<Vector3D>();
	
	public Element3DPolygon(Vector3D... points) {
		for(Vector3D v:points)vertices.add(v);
		
	}

	public Element3DPolygon(ElementPoly e) {
		vertices = e.vertices;
	}

	public Element3DPolygon() {
		
	}
	
	@Override
	public String getDefinition() {
		return ((ElementPoly) element).getPlane3D().toString();
	}

	@Override
	public Element createElement() {
		element=new ElementPoly();
		if (vertices.size()<3) return null;
		for (Vector3D v:vertices){
			vertices.add(new Vector3D(Globalsettings.mapX(v.getX()),Globalsettings.mapX(v.getX()),Globalsettings.mapX(v.getX())));
		}
		element.setFillColor(getFillColor());
		element.setBackColor(getBackColor());
	   	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
    	element.setSpilttable(isSplittable());
		return element;
	}

	@Override
	public Element createElement(Clip clip) {
		element=new ElementPoly();
		if (vertices.size()<3) return null;
		for (Vector3D v:vertices){
		  ((ElementPoly)element).vertices.add(new Vector3D(Globalsettings.inverseMapX(v.getX()),Globalsettings.inverseMapY(v.getY()),Globalsettings.inverseMapZ(v.getZ())));
		}
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
    	if(T!=null)element.transform(T);
		if (null != clip) {
			ElementPoly clippoly = new ElementPoly();
			if (clip.getClippedPoly(((ElementPoly)element).vertices, clippoly.vertices) != 2) {
				clippoly.reCalculateNormalandCentre();
				clippoly.setFillColor(element.getFillColor());
				clippoly.setBackColor(element.getBackColor());
				clippoly.setLineColor(element.getLineColor());
				clippoly.setCurveWidth(element.getCurveWidth());
				if (getFillmode()==1)clippoly.setFilled(false); else clippoly.setFilled(true);
				clippoly.setSpilttable(isSplittable());
				if (clippoly.vertices.size()>2)element=clippoly;
		      }
		
		}
		return element;
	}

	public Plane3D getPlane3D() {
		((ElementPoly)element).getPlane3D();
		return null;
	}

	public static Element3DPolygon createUnitCirclePolygon(int defaultCount,
			double defaultRadius) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Full constructor.
	 * <p>
	 * Creates a new {@link ElementPoly} using the given vertices.  The center of the polygon
	 * is calculated using an area weighted method.
	 * <p>
	 * A polygon must have 3 or more vertices, of which one is not colinear with the other two.
	 * <p>
	 * A polygon must also be convex and have anti-clockwise winding.
	 * @param vertices the array of vertices
	 * @throws NullPointerException if vertices is null or contains a null element
	 * @throws IllegalArgumentException if vertices contains less than 3 points, contains coincident points, is not convex, or has clockwise winding
	 */
	public static void createPolygon(Vector3D... vertices) {
		// check the vertex array
		if (vertices == null) throw new NullPointerException(Messages.getString("geometry.polygon.nullArray"));
		// get the size
		int size = vertices.length;
		// check the size
		if (size < 3) throw new IllegalArgumentException(Messages.getString("geometry.polygon.lessThan3Vertices"));
		// check for null vertices
		for (int i = 0; i < size; i++) {
			if (vertices[i] == null) throw new NullPointerException(Messages.getString("geometry.polygon.nullVertices"));
		}
		// check for convex
		for (int i = 0; i < size; i++) {
			Vector3D p0 = (i - 1 < 0) ? vertices[size - 1] : vertices[i - 1];
			Vector3D p1 = vertices[i];
			Vector3D p2 = (i + 1 == size) ? vertices[0] : vertices[i + 1];
		
			// check for coincident vertices
			if (p1.equals(p2)) {
				throw new IllegalArgumentException(Messages.getString("geometry.polygon.coincidentVertices"));
			}
		
		}
		
		// set the vertices
		//this.vertices = vertices;
		
	}
	
	
}

