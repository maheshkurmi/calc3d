package com.calc3d.geometry3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import com.calc3d.math.AffineTransform3D;

public class Surface3D extends Object3D<Element>{
	/**
	 * shinyNess: Lower values will spread out the shine shineIntensity: Maximum
	 * intensity of the shine
	 */
	public double shinyNess = 0.15, shineIntensity = 0.5;

	/** edge color of surface */
	public Color curveColor;

	/** fill color of surface */
	public Color surfaceColor;

	public Collection<ElementRect> elements = new ArrayList<ElementRect>();

	/**Default Constructor: does nothing*/
	public Surface3D() {}

	/**
	 * Creates a new ElementRect set and allocate memory for storing the points.
	 * 
	 * @param n
	 *            the number of points to store
	 */
	public Surface3D(int n) {
		this.elements = new ArrayList<ElementRect>(n);
	}

	public Surface3D(ElementRect[] elements) {
		for (ElementRect element : elements)
			this.elements.add(element);
	}
	
	/**
	 * Makes a copy of surface
	 * @param surface3D
	 */
	public Surface3D(Surface3D surface3D) {
		this();
		this.set(surface3D);
	}

	
	public void set(Surface3D surface3D) {
		this.elements.clear();
		for (ElementRect element : surface3D.elements)
			this.elements.add(new ElementRect(element));
		this.surfaceColor=surface3D.surfaceColor;
		this.curveColor=surface3D.surfaceColor;
	}

	/**
	 * Adds a new ElementRect to the set of point.
	 */
	public void addElement(ElementRect element) {
		this.elements.add(element);
	}

	/**
	 * Add a series of ElementRect
	 * 
	 * @param elements
	 *            an array of ElementRect
	 */
	public void addElements(ElementRect[] elements) {
		for (ElementRect element : elements)
			this.addElement(element);
	}

	public void addSurface(Surface3D surface3D) {
		this.elements.addAll(surface3D.elements);
	}


	public void transform(AffineTransform3D T){
		for (Element e : elements) e.transform(T);
	}

}
