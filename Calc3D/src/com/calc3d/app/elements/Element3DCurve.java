package com.calc3d.app.elements;

import java.awt.Color;

import com.calc3d.app.Globalsettings;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementArrow;
import com.calc3d.geometry3d.ElementCollection;
import com.calc3d.geometry3d.ElementCurve;
import com.calc3d.log.Logger;
import com.calc3d.math.MathUtils;
import com.calc3d.math.Vector3D;
import com.calc3d.mathparser.Calculable;
import com.calc3d.mathparser.ExpressionBuilder;
import com.calc3d.utils.ColorUtils;


public class Element3DCurve extends Element3D {

	private static Logger LOG = Logger.getLogger(Element3DCurve.class.getName());
	private transient Calculable calc_x,calc_y,calc_z;
	private String exprX="",exprY="",exprZ="";
	
	/**minimum and maximum values of parametric variable t*/
	private double min_t = -1, max_t = 1;
	
	/**No of segments of the curve*/
	private int numSegments=80;
	
	public Element3DCurve (){
		splittable=false;
	}
	public double getMin_t() {
		return min_t;
	}
	public void setMin_t(double min_t) {
		this.min_t = min_t;
	}
	public double getMax_t() {
		return max_t;
	}
	public void setMax_t(double max_t) {
		this.max_t = max_t;
	}
	
	
	@Override
	public Element createElement() {
		element=CreateCurve(null);
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
		return element;
	}

	@Override
	public Element createElement(Clip clip) {
		element=CreateCurve(clip);
		if (null==element) return null;
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
		return element;
	}
	
	public ElementCollection CreateCurve(Clip clip){
		try {
			calc_x = new ExpressionBuilder(exprX).withVariableNames("t")
					.build();
			calc_y = new ExpressionBuilder(exprY).withVariableNames("t")
					.build();
			calc_z = new ExpressionBuilder(exprZ).withVariableNames("t")
					.build();
		} catch (Exception e) {
			LOG.error(e);
			return null;
		}
	
		ElementCollection curve3D = new ElementCollection();

		Vector3D v1, v2;
		double x,y,z;
		double t;
		//sets first point
		x=fx(min_t);		
		y=fy(min_t);
		z=fz(min_t);
		
		for (int i = 1; i < numSegments-1; i++) {
			t = min_t + (max_t - min_t) * i / numSegments;
			//set first Point
			x=fx(t);
			if (!MathUtils.isValidNumber(x))continue;
			y=fy(t);
			if (!MathUtils.isValidNumber(y))continue;
			z=fz(t);
			if (!MathUtils.isValidNumber(z))continue;
			
			v1=new Vector3D(x,y,z);
			//set second point
			t = min_t + (max_t - min_t) * (i+1) / numSegments;
			x=fx(t);
			y=fy(t);
			z=fz(t);
			v2=new Vector3D(x,y,z);
			Element ec=(i%10==0)?new ElementArrow(v1,v2):new ElementCurve(v1,v2);
			
			if(T!=null)ec.transform(T);
			if (null!=clip) ec= clip.getClippedElement(ec);
			if (null!=ec) {
			   ec.setCurveWidth(getCurveWidth());
			   ec.setFillColor(getFillColor());
			   ec.setLineColor(getLineColor());
			   ec.setFillColor( ColorUtils.blendColors(getFillColor(), getBackColor(), (t-min_t)/(max_t - min_t)));
			   ec.setSpilttable(isSplittable());	  
			   ec.setDashed(isDashed());
			   curve3D.addElement(ec);
			}
		}
		element=curve3D;
		return curve3D;
		
	}
	public int getNumSegments() {
		return numSegments;
	}
	public void setNumSegments(int numSegments) {
		if (numSegments>1)this.numSegments = numSegments;
		else 
		LOG.error("illegal number of segments (" + numSegments +")" +"expected more than 1");
	}
	public String getExprX() {
		return exprX;
	}
	public void setExprX(String exprX) {
		this.exprX = exprX;
	}
	public String getExprY() {
		return exprY;
	}
	public void setExprY(String exprY) {
		this.exprY = exprY;
	}
	public String getExprZ() {
		return exprZ;
	}
	public void setExprZ(String exprZ) {
		this.exprZ = exprZ;
	}
	
	double fx(double t){
		calc_x.setVariable("t", t);
		return Globalsettings.inverseMapX(calc_x.calculate());
	}
	
	double fy(double t){
		calc_y.setVariable("t", t);
		return Globalsettings.inverseMapZ(calc_y.calculate());
	}
	double fz(double t){
		calc_z.setVariable("t", t);
		return Globalsettings.inverseMapZ(calc_z.calculate());
	}
	
	@Override
	public String getDefinition(){
		return "<br>x = " + exprX +"<br>" + "y = " + exprY +"<br>" +"z = " + exprZ +
			   "<br>"+" <br> <b>t-range: </b> &nbsp ["+ min_t + " , " + max_t +"]";
		
	}
}