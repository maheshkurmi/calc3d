package com.calc3d.geometry3d;

import java.awt.Color;

import com.calc3d.math.AffineTransform3D;
import com.calc3d.math.Vector3D;

public class ElementArrow extends Element{
	public Vector3D p1, p2;
    public boolean absoluteWidth=true;
    private int arrowSize=3;
    
	public ElementArrow(Vector3D p1, Vector3D p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.setLineColor(Color.black);
		this.setFillColor(Color.white);
		calculateCentre();
	}
	
	public ElementArrow(Vector3D p1, Vector3D p2,Color color,int curveWidth,boolean absoluteWidth) {
		this(p1,p2);
		this.setLineColor(color);
		this.setFillColor(color);
		this.curveWidth=curveWidth;
		this.absoluteWidth=absoluteWidth;
	}
	
	/** calculate the average of both vertices and stores in centre */
   private void calculateCentre() {
		this.centre = (p1.add(p2)).scale(0.5); 
   }
		
		
    public ElementArrow(ElementArrow element){
		p1=new Vector3D(element.p1);
		p2=new Vector3D(element.p2);
		this.fillColor=element.fillColor;
		this.lineColor=element.lineColor;
		this.centre=element.getCentre();
		this.curveWidth=element.curveWidth;
		this.absoluteWidth=element.absoluteWidth;
	}

	@Override
	public void transform(AffineTransform3D T) {
		T.transform(p1);
		T.transform(p2);
		T.transform(centre);
	}

	public int getArrowSize() {
		return arrowSize;
	}

	public void setArrowSize(int arrowSize) {
		this.arrowSize = arrowSize;
	}

}
