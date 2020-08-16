package com.calc3d.geometry3d.bsp;

import java.util.ArrayList;
import java.util.List;

import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementArrow;
import com.calc3d.geometry3d.ElementCurve;
import com.calc3d.geometry3d.ElementPoly;
import com.calc3d.geometry3d.ElementRuler;
import com.calc3d.geometry3d.Plane3D;
import com.calc3d.math.Vector3D;

/**
 * The BSPTreeBuilder class builds a BSP tree from a list of Elements. Adds
 * polygons at node and all other elements at nodes (since elements other than
 * polygons can not hide each other)
 * 
 * Currently, the builder does not try to optimize the order of the partitions,
 * and could be optimized by choosing partitions in an order that minimizes
 * polygon splits and provides a more balanced, complete tree.
 */

public class BSPTreeBuilder {

	/**
	 * The bsp tree currently being built.
	 */
	public BSPTree currentTree;

	/**
	 * Builds a BSP tree.
	 */
	public BSPTree build(List<Element> elements) {
		currentTree = new BSPTree(createNewNode(elements));
		buildNode(currentTree.getRoot());
		return currentTree;
	}

	/**
	 * Builds a node in the BSP tree.
	 */
	protected void buildNode(BSPTree.Node node) {

		// nothing to build if it's a leaf (it is no partition plane)
		if (node.isLeaf) {
			return;
		}

		// classify all polygons relative to the partition
		// (front, back, or collinear)
		ArrayList<Element> collinearList = new ArrayList<Element>();
		ArrayList<Element> frontList = new ArrayList<Element>();
		ArrayList<Element> backList = new ArrayList<Element>();
		List<Element> allElements = node.elements;
		// remove all elements from list as we are going to build node
		// and we will add elements according to whether they are front of back
		// to partition
		node.elements = null;
		collinearList.add(allElements.get(0));
		for (int i = 1; i < allElements.size(); i++) {
			Element element = allElements.get(i);
			if (element==null)continue;
			if ((element.isSpilttable()==false) && (allElements.get(0).isSpilttable()==false)){
				collinearList.add(element);
				continue;
			}
			int side = node.partition.getSide(element);
			if (side == Plane3D.COLLINEAR) {
				collinearList.add(element);
			} else if (side == Plane3D.FRONT) {
				frontList.add(element);
			} else if (side == Plane3D.BACK) {
				backList.add(element);
			} else if (side == Plane3D.SPANNING) {
				// The elements which can split are Polygon , lines and arrow
				if (element instanceof ElementPoly) {
					ElementPoly e = (ElementPoly) element;
					ElementPoly front = new ElementPoly();
					front.setFillColor( element.getFillColor());
					front.setCurveWidth(element.getCurveWidth());
					front.setFilled(element.isFilled());
					front.setDashed(element.isDashed());
					front.setSpilttable(element.isSpilttable());
					front.setBackColor(e.getBackColor());
					front.drawContours=e.drawContours;
					ElementPoly back = new ElementPoly();
					front.setLineColor(e.getLineColor());
					back.setLineColor(e.getLineColor());
					back.setFillColor( element.getFillColor());
					back.setCurveWidth(element.getCurveWidth());
					back.setFilled(element.isFilled());
					back.setDashed(element.isDashed());
					back.setBackColor(e.getBackColor());
					back.setSpilttable(element.isSpilttable());
					front.setDashed(e.isDashed());
					back.setDashed(e.isDashed());
					back.drawContours=e.drawContours;
					
					node.partition.splitPoly(e.vertices, front.vertices,
							back.vertices);

					if (front.vertices.size() > 2) {
						front.reCalculateNormalandCentre();
						if (null!=front.normal)frontList.add(front);
					}
					if (back.vertices.size() > 2) {
						back.reCalculateNormalandCentre();
						if (null!=back.normal)backList.add(back);
					}

				} else if (element instanceof ElementCurve) {
					ElementCurve e = (ElementCurve) element;
					Vector3D vi = node.partition.getIntersection(e.p1, e.p2);
					ElementCurve front, back;
					{
						if (node.partition.getSideThick(e.p1) == Plane3D.FRONT) {
							front = new ElementCurve(e.p1, vi);
							back = new ElementCurve(e.p2, vi);
						} else {
							back = new ElementCurve(e.p1, vi);
							front = new ElementCurve(e.p2, vi);
						}
						front.setFillColor( e.getFillColor());
						front.setBackColor(e.getBackColor());
						back.setFillColor( e.getFillColor());
						front.setLineColor( e.getLineColor());
						back.setLineColor( e.getLineColor());
						front.setCurveWidth(e.getCurveWidth());
						back.setCurveWidth(e.getCurveWidth());
						front.setRenderable(e.isRenderable());
						back.setRenderable(e.isRenderable());
						back.setBackColor(e.getBackColor());
						front.setDashed(e.isDashed());
						back.setDashed(e.isDashed());
						frontList.add(front);
						backList.add(back);
					}
				} else if (element instanceof ElementArrow) {
					ElementArrow e = (ElementArrow) element;
					Vector3D vi = node.partition.getIntersection(e.p1, e.p2);
					{   
						Element front,back;
						if (node.partition.getSideThick(e.p1) == Plane3D.FRONT) {
							
							if (e instanceof ElementRuler){
								front = new ElementRuler(e.p1, vi,(ElementRuler) e);
								back = new ElementRuler( vi,e.p2,(ElementRuler) e);
							}else{
							    front = new ElementCurve(e.p1, vi);
							    back = new ElementArrow( vi,e.p2);
							    ((ElementArrow)back).setArrowSize(e.getArrowSize());
							}
							front.setFillColor( e.getFillColor());
							front.setFillColor( e.getFillColor());
							front.setLineColor( e.getLineColor());
							front.setLineColor( e.getLineColor());
							back.setLineColor( e.getLineColor());
							back.setCurveWidth(e.getCurveWidth());
							back.setBackColor(e.getBackColor());
							front.setCurveWidth(e.getCurveWidth());
							front.setDashed(e.isDashed());
							back.setDashed(e.isDashed());
							frontList.add(front);
							backList.add(back);
						} else {
							
							if (e instanceof ElementRuler){
								front = new ElementRuler(vi, e.p2,(ElementRuler) e);
								back = new ElementRuler( e.p1,vi,(ElementRuler) e);
							}else{
							    front =  new ElementArrow(vi,e.p2);
							    back = new ElementCurve(e.p1, vi);
							    ((ElementArrow)front).setArrowSize(e.getArrowSize());
							}
							// back = new ElementCurve(e.p1, vi);
							// front = new ElementArrow(vi,e.p2);
							front.setFillColor( e.getFillColor());
							back.setFillColor( e.getFillColor());
							front.setLineColor( e.getLineColor());
							back.setLineColor( e.getLineColor());
							front.setCurveWidth(e.getCurveWidth());
							back.setCurveWidth(e.getCurveWidth());
							front.setDashed(e.isDashed());
							back.setDashed(e.isDashed());
							
							frontList.add(front);
							backList.add(back);
						}
						
					}
				
				}

			}
		}

		if (allElements.get(0).isSpilttable()==false){
			collinearList.addAll(backList);
			collinearList.addAll(frontList);
			frontList.clear();
			backList.clear();
		}
		// clean and assign lists
		collinearList.trimToSize();
		frontList.trimToSize();
		backList.trimToSize();
		node.elements = collinearList;
		// build front and back nodes
		if (frontList.size() > 0) {
			node.front = createNewNode(frontList);
			buildNode(node.front);
		}
		if (backList.size() > 0) {
			node.back = createNewNode(backList);
			buildNode(node.back);
		}

	}

	/**
	 * Creates a new node from a list of polygons. If none of the polygons are
	 * walls, a leaf is created.
	 */
	protected BSPTree.Node createNewNode(List<Element> elements) {

		Plane3D partition = choosePartition(elements);
		BSPTree.Node node = new BSPTree.Node();
		node.elements = elements;
		node.partition = partition;
		// no partition available, so it's a leaf
		if (partition == null)
			node.isLeaf = true;
		return node;
	}

	/**
	 * Chooses a polygon from a list of elements (if exists) to use as a
	 * partition. This method just returns the first polygon found in list of
	 * elements or null if none found. A smarter method would choose a partition
	 * that minimizes polygon splits and provides a more balanced, complete
	 * tree.
	 */
	protected Plane3D choosePartition(List<Element> elements) {
		for (Element element : elements) {
			if (element instanceof ElementPoly) {
				ElementPoly e=(ElementPoly) element;
				elements.remove(element);
				elements.add(0,e);
				return ((ElementPoly) element).getPlane3D();
			}
		}
		// no Polygon found so no partition is there in list of elements
		return null;
	}

}