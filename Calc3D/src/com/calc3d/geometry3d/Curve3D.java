package com.calc3d.geometry3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

public class Curve3D extends Object3D<Element>{
	   private Color curveColor;
	   private int curveWidth;boolean absoluteWidth;
	   public Collection<ElementCurve> quads = new ArrayList<ElementCurve>();
	   
		public Collection<ElementCurve> elements = new ArrayList<ElementCurve>();

		/**Default Constructor: does nothing*/
		public Curve3D() {}

		/**
		 * Creates a new ElementCurve set and allocate memory for storing the points.
		 * 
		 * @param n
		 *            the number of points to store
		 */
		public Curve3D(int n) {
			this.elements = new ArrayList<ElementCurve>(n);
		}

		public Curve3D(ElementCurve[] elements) {
			for (ElementCurve element : elements)
				this.elements.add(element);
		}
		
		/**
		 * Makes a copy of curve
		 * @param curve3D
		 */
		public Curve3D(Curve3D curve3D) {
			this();
			this.set(curve3D);
		}

		
		public void set(Curve3D curve3D) {
			this.elements.clear();
			for (ElementCurve element : curve3D.elements)
				this.elements.add(new ElementCurve(element));
			this.curveColor=curve3D.curveColor;
			this.curveWidth=curve3D.curveWidth;
			this.absoluteWidth=curve3D.absoluteWidth;
		}

		/**
		 * Adds a new ElementCurve to the set of point.
		 */
		public void addElement(ElementCurve element) {
			this.elements.add(element);
		}

		/**
		 * Add a series of ElementCurve
		 * 
		 * @param elements
		 *            an array of ElementCurve
		 */
		public void addElements(ElementCurve[] elements) {
			for (ElementCurve element : elements)
				this.addElement(element);
		}

		public void addCurve(Curve3D curve3D) {
			this.elements.addAll(curve3D.elements);
		}

	
}
