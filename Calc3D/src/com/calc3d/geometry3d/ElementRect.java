package com.calc3d.geometry3d;

import java.awt.Color;

import com.calc3d.math.AffineTransform3D;
import com.calc3d.math.Vector3D;

	/** class to represent a rectangle Element, which acts as building block for surface
	 * 
	 * @author mahesh
	 *
	 */
	public class ElementRect extends Element {
	
		public Vector3D[] vertices =new Vector3D[4];
	    /**
	     * Unit vector perpendicular to the plane
	     */
		public Vector3D normal;
		
		
		/**Default Constructor 
		 * Used to supply this as parameter eg Split
		 */
		public ElementRect(){}
		/**
		 * Create rectangular elements, with vertices in clockwise order
		 * @param 4 vertices in clockwise order
		 */
	    public ElementRect(Vector3D p1, Vector3D p2, Vector3D p3, Vector3D p4) {
	    	vertices[0]=p1;
			vertices[1]=p2;
			vertices[2]=p3;
			vertices[3]=p4;
			calculateCentre();
			calculateSurfaceNormal();
			this.lineColor=Color.BLACK;
			this.fillColor=Color.white;
		}
	    
	    /**
	     * Makes copy of element
	     * @param element
	     */
	    public ElementRect(ElementRect element){
			vertices[0]=new Vector3D(element.vertices[0]);
			vertices[1]=new Vector3D(element.vertices[1]);
			vertices[2]=new Vector3D(element.vertices[2]);
			vertices[3]=new Vector3D(element.vertices[3]);
			lineColor=element.lineColor;
			fillColor=element.fillColor;
			this.centre=element.getCentre();
			calculateSurfaceNormal();
		}
	    
	    /**
		 * Create rectangular elements, with vertices in clockwise order
		 * @param 4 vertices in clockwise order
		 */
	    public ElementRect(Vector3D p1, Vector3D p2, Vector3D p3, Vector3D p4,Color surfacecolor,Color curvecolor) {
	       	this(p1,p2,p3,p4);	
	    	this.fillColor=surfacecolor;
	    	this.lineColor=curvecolor;
	     }
	    
	    /**
	     * calculate the average of all 4 vertices and stores in centre
	     */
		private void calculateCentre() {
			centre=new Vector3D();
			for (Vector3D v:vertices){
				centre.addEq(v);
			}
			centre.scaleEq(0.25);
		}
		
		/**
		 * Calculate this surface's normal vector.
		 */
		private void calculateSurfaceNormal()
		{
			 normal =Vector3D.getSurfaceNormal( 
	    	        vertices[0], 
	    	        vertices[1], 
	    	        vertices[2]);
		}
		
		@Override
		public void transform(AffineTransform3D T) {
			for (Vector3D v:vertices){
				T.transform(v);
			}
			T.transform(centre);
			T.transform(normal);
		}
	
}
