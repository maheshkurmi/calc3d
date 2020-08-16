package com.calc3d.geometry3d;

import java.util.ArrayList;
import java.util.List;

import com.calc3d.math.AffineTransform3D;

public class ElementCollection extends Element{

	public ArrayList<Element> elements = new ArrayList<Element>();

	/**Default Constructor: does nothing*/
	public ElementCollection() {}

	/**
	 * Creates a new ElementRect set and allocate memory for storing the points.
	 * 
	 * @param n
	 *            the number of points to store
	 */
	public ElementCollection(int n) {
		this.elements = new ArrayList<Element>(n);
	}

	public ElementCollection(Element[] elements) {
		for (Element element : elements)
			this.elements.add(element);
	}
	
	public ElementCollection(List<Element> elements) {
		this.elements.addAll(elements);
	}
		
	
	/**
	 * Adds a new ElementRect to the set of point.
	 */
	public void addElement(Element element) {
		this.elements.add(element);
	}

	/**
	 * Add a series of ElementRect
	 * 
	 * @param elements
	 *            an array of ElementRect
	 */
	public void addElements(Element[] elements) {
		for (Element element : elements)
			this.addElement(element);
	}

	public void addCollection(ElementCollection collection) {
		this.elements.addAll(collection.elements);
	}


	public void transform(AffineTransform3D T){
		for (Element e : elements) e.transform(T);
	}

	@Override
	public void setRenderable(boolean renderable) {
		for (Element element : elements)
			element.setRenderable(renderable);
	}
	
	public void updateCollection(Element e){
		for (Element element : elements)
			element.updateElement(e);
	}

}
