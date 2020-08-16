package com.calc3d.geometry3d;

import java.awt.Color;

import com.calc3d.math.AffineTransform3D;
import com.calc3d.math.Vector3D;

public class ElementCurve extends Element {
	public Vector3D p1, p2;
    public boolean absoluteWidth=true;
    
	public ElementCurve(Vector3D p1, Vector3D p2) {
		this.p1 = p1;
		this.p2 = p2;
		this.setLineColor(Color.black);
		this.setFillColor(Color.black);
		calculateCentre();
	}
	
	public ElementCurve(Vector3D p1, Vector3D p2,Color color,int curveWidth,boolean absoluteWidth) {
		this(p1,p2);
		this.setLineColor(color);
		this.setFillColor(color);
		this.curveWidth=curveWidth;
		this.absoluteWidth=absoluteWidth;
		calculateCentre();
	}
	
	/** calculate the average of both vertices and stores in centre */
   private void calculateCentre() {
		this.centre = (p1.add(p2)).scale(0.5); 
   }
		
		
    public ElementCurve(ElementCurve element){
		p1=new Vector3D(element.p1);
		p2=new Vector3D(element.p2);
		this.fillColor=element.fillColor;
		this.lineColor=element.lineColor;
		this.centre=element.getCentre();
		this.curveWidth=element.curveWidth;
		this.absoluteWidth=element.absoluteWidth;
    }

    public Line3D getLine3D(){
		return new Line3D(p1,p2);
    }
    
	@Override
	public void transform(AffineTransform3D T) {
		T.transform(p1);
		T.transform(p2);
		T.transform(centre);
	}

}
