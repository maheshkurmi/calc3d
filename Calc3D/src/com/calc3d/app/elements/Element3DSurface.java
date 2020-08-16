package com.calc3d.app.elements;

import java.awt.Color;

import com.calc3d.app.Globalsettings;
import com.calc3d.engine3d.Camera3D;
import com.calc3d.geometry3d.Box3D;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementCollection;
import com.calc3d.geometry3d.ElementPoly;
import com.calc3d.log.Logger;
import com.calc3d.math.MathUtils;
import com.calc3d.math.Vector3D;
import com.calc3d.mathparser.Calculable;
import com.calc3d.mathparser.ExpressionBuilder;

/**
 * Class for Elements3DSurfacd representing Surface in 3D space
 * 
 * @author mahesh
 * 
 */
public class Element3DSurface extends Element3D {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8584466900933868834L;
	private static Logger LOG = Logger.getLogger(Camera3D.class.getName());
	private transient  Calculable calc;
	private String expr="";
	/** Dimension bounding the surfce */
	double minX, maxX, minY, maxY, minZ, maxZ;
	/**no of divisions/grids for surface*/
	private int xGrids = 20, yGrids = 20;
	public Element3DSurface(String expr) {
		this.minX = -1;
		this.minY = -1;
		this.minZ = -1;
		this.maxX = 1;
		this.maxY = 1;
		this.maxZ = 1;
		this.expr = expr;
		this.setFillmode(2);
	}

	public Element3DSurface(String expr, Box3D box) {
		this.minX = box.getMinX();
		this.minY = box.getMinY();
		this.minZ = box.getMinZ();
		this.maxX = box.getMaxX();
		this.maxY = box.getMaxY();
		this.maxZ = box.getMaxZ();
		this.expr = expr;
	}

	@Override
	public Element createElement() {
		element=createSurface(null);
		return element;
	}

	@Override
	public Element createElement(Clip clip) {
		element=createSurface(clip);
		return element;
	}

	@Override
	public String getDefinition() {
		return "z = " + expr +
			   "<br>"+" <br> <b>x-range: </b> &nbsp ["+ Globalsettings.minX + " , " +Globalsettings.maxX +"]"+
			   "<br>"+" <br> <b>y-range: </b> &nbsp ["+ Globalsettings.minY + " , " + Globalsettings.maxY +"]";
	}

	public String getExpression() {
		return expr;
	}
	
	public void setExpression(String expr) {
		this.expr = expr;
	}

	public Box3D getBox() {
		return new Box3D(minX, maxX, minY, maxY, minZ, maxZ);
	}

	public void setBox(Box3D box) {
		this.minX = box.getMinX();
		this.minY = box.getMinY();
		this.minZ = box.getMinZ();
		this.maxX = box.getMaxX();
		this.maxY = box.getMaxY();
		this.maxZ = box.getMaxZ();
	}

	private ElementCollection createSurface(Clip clip) {
		try {
			calc = new ExpressionBuilder(expr).withVariableNames("x", "y")
					.build();
		} catch (Exception e) {
			LOG.error(e);
			return null;
		}
		float i, j;
		double x, y, z;
       
		ElementCollection surface3D = new ElementCollection();
    
		Vector3D v1, v2, v3, v4;
		for (i = 0; i < xGrids; i++) {
			Inner:
			for (j = 0; j < yGrids; j++) {

				x = minX + (maxX - minX) * i / xGrids;
				y = minY + (maxY - minY) * j / yGrids;
				z = f(x, y);
				if (!MathUtils.isValidNumber(z))continue Inner;
				v1 = new Vector3D(x, y, z);
				x = minX + (maxX - minX) * i / xGrids;
				y = minY + (maxY - minY) * (j + 1) / yGrids;
				z = f(x, y);
				if (!MathUtils.isValidNumber(z))continue Inner;
				v2 = new Vector3D(x, y, z);
				x = minX + (maxX - minX) * (i + 1) / xGrids;
				y = minY + (maxY - minY) * (j + 1) / yGrids;
				z = f(x, y);
				if (!MathUtils.isValidNumber(z))continue Inner;
				v3 = new Vector3D(x, y, z);
				x = minX + (maxX - minX) * (i + 1) / xGrids;
				y = minY + (maxY - minY) * j / yGrids;
				z = f(x, y);
				if (!MathUtils.isValidNumber(z))continue Inner;
				v4 = new Vector3D(x, y, z);
				ElementPoly element = new ElementPoly();
				element.addPoint(v1);
				element.addPoint(v2);
				element.addPoint(v3);
				element.addPoint(v4);
				if (T!=null)element.transform(T);
				element.drawContours=true;
				if (getFillmode()==1)
				{
					element.setFilled(false); 
			     	element.setLineColor(new Color(i / xGrids, 0, j / yGrids));
		        	element.setCurveWidth(Math.max(1,getCurveWidth()));
		           	element.setFillColor(new Color(i / xGrids, 0, j / yGrids));	  
				}
				else if (getFillmode()==3){
					element.setFillColor(getFillColor());
					element.setFillColor(ColorModel.getPreset(2).getPolygonColor((float) element.getCentre().getZ()));
					element.setBackColor(Color.gray);
			     	element.setLineColor(getLineColor());
			    	element.setCurveWidth(getCurveWidth());
				}
				else if(getFillmode()==2){
					element.setFillColor(getFillColor());
					element.setBackColor(getBackColor());
				    element.setLineColor(getLineColor());
				    element.setCurveWidth(getCurveWidth());
				}
				else{
					element.setFillColor(new Color(i / xGrids, 0, j / yGrids));
					//element.setFillColor( Color.getHSBColor((float) (0.3+0.7*(z-minZ)/(maxZ - minZ)), 1f, 1f));
					element.setBackColor(Color.gray);	
				    element.setLineColor(getLineColor());
				    element.setCurveWidth(getCurveWidth());
				 }
				if (null != clip) {
					ElementPoly clippoly = new ElementPoly();
					if (clip.getClippedPoly(element.vertices, clippoly.vertices) != 2) {
						clippoly.reCalculateNormalandCentre();
						clippoly.setFillColor(element.getFillColor());
						clippoly.setBackColor(element.getBackColor());
						clippoly.setLineColor(element.getLineColor());
						clippoly.setCurveWidth(element.getCurveWidth());
						if (getFillmode()==1)clippoly.setFilled(false); else clippoly.setFilled(true);
						clippoly.setSpilttable(isSplittable());
						clippoly.drawContours=true;
						if (clippoly.vertices.size()>2)surface3D.addElement(clippoly);
					}
				} else {
					surface3D.addElement(element);
				}
			}
		}
		element=surface3D;
		return (surface3D.elements.size() > 0) ? surface3D : null;
	}

	private double f(double x, double y) {
		double z;
		calc.setVariable("x", Globalsettings.mapX(x));
		calc.setVariable("y", Globalsettings.mapY(y));
		z=calc.calculate();
		z=Globalsettings.inverseMapZ(z);
		/*
		if (z < minZ)
			return minZ;
		if (z > maxZ)
			return maxZ;
		*/
		return z;
	}

	public int getxGrids() {
		return xGrids;	
	}

	public void setxGrids(int xGrids) {
		if (xGrids > 0)
			this.xGrids = xGrids;
		else
			LOG.error("Illegral number of xgrids" + xGrids);
	}

	public int getyGrids() {
		return yGrids;
	}

	public void setyGrids(int yGrids) {
		if (yGrids > 0)
			this.yGrids = yGrids;
		else
			LOG.error("Illegral number of xgrids" + yGrids);
	}


}
