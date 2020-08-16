package com.calc3d.app.elements;

import java.awt.Color;

import com.calc3d.app.Globalsettings;
import com.calc3d.app.Preferences;
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


public class Element3DVectorField extends Element3D {

	private static Logger LOG = Logger.getLogger(Element3DVectorField.class.getName());
	private transient Calculable calc_x,calc_y,calc_z;
	private String exprX="",exprY="",exprZ="";
	
	/**minimum and maximum values of parametric variable t*/
	double minX, maxX, minY, maxY, minZ, maxZ;
	
	/**no of divisions/grids for surface*/
	private int xGrids = 6, yGrids = 6, zGrids =6;
	
	private int numSegments=6;

	public Element3DVectorField (){
		splittable=false;
		
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
			LOG.error("Illegral number of ygrids" + yGrids);
	}

	/**
	 * @return the zGrids
	 */
	public int getzGrids() {
		return zGrids;
	}

	/**
	 * @param zGrids the zGrids to set
	 */
	public void setzGrids(int zGrids) {
		if (zGrids > 0)
			this.zGrids = zGrids;
		else
			LOG.error("Illegral number of zgrids" + zGrids);

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
		minX=Globalsettings.mappedClipBox.getMinX();
		minY=Globalsettings.mappedClipBox.getMinY();
		minZ=Globalsettings.mappedClipBox.getMinZ();
		maxX=Globalsettings.mappedClipBox.getMaxX();
		maxY=Globalsettings.mappedClipBox.getMaxY();
		maxZ=Globalsettings.mappedClipBox.getMaxZ();
			

		try {
			calc_x = new ExpressionBuilder(exprX).withVariableNames("x","y","z")
					.build();
			calc_y = new ExpressionBuilder(exprY).withVariableNames("x","y","z")
					.build();
			calc_z = new ExpressionBuilder(exprZ).withVariableNames("x","y","z")
					.build();
		} catch (Exception e) {
			LOG.error(e);
			return null;
		}
	
		ElementCollection curve3D = new ElementCollection();

		Vector3D v1, v2;
			double t;
		
		double dx,dy,dz;
		dx=(maxX-minX)/xGrids;
		dy=(maxY-minY)/yGrids;
		dz=(maxZ-minZ)/zGrids;	

		double arrowLength=(maxX-minX)/xGrids/2;
		double min=Double.POSITIVE_INFINITY;
		double max =Double.NEGATIVE_INFINITY;
		
		for (double x = minX; x <= maxX; x +=dx) {
			for (double y = minY; y <= maxY; y += dy) {
				for (double z = minZ; z <= maxZ; z += dz) {
					double vx=fx(x,y,z);
					if (!MathUtils.isValidNumber(x))continue;
					double vy=fy(x,y,z);
					if (!MathUtils.isValidNumber(y))continue;
					double  vz=fz(x,y,z);
					if (!MathUtils.isValidNumber(z))continue;
					
					Vector3D dir=new Vector3D(vx,vy,vz);
					double length=dir.getLength();
					if(length>max)max=length;
					if(length<min)min=length;
					if(length==0)continue;
					dir=dir.getUnitVector();
					dir.scaleEq(arrowLength);
					v1=new Vector3D(x,y,z);
					v2=new Vector3D(x+dir.getX(),y+dir.getY(),z+dir.getZ());
					Element ec=new ElementArrow(v1,v2);
					ec.depth=length;
					if(T!=null)ec.transform(T);
					//if (null!=clip) ec= clip.getClippedElement(ec);
					if (null!=ec) {
					   ec.setCurveWidth(getCurveWidth());
					   ec.setLineColor(getLineColor());
					   ec.setFillColor(getFillColor());
					   ec.setSpilttable(isSplittable());	  
					   ec.setDashed(isDashed());
					   curve3D.addElement(ec);
					}
				}
			}
		}

		if(!Double.isInfinite(max)&& !Double.isInfinite(min)&& max>min){
			//Assign colors as per magnitude of fields
			for(Element e:curve3D.elements){
				e.setFillColor( ColorUtils.blendColors(Globalsettings.backgroundColor,getFillColor(), Math.max(0.25,(e.depth-min)/(max-min))));
				e.depth=0;	
			}
		}
		element=curve3D;
		return curve3D;
		
	}
	public int getNumXSegments() {
		return xGrids;
	}
	public int getNumYSegments() {
		return yGrids;
	}
	public int getNumZSegments() {
		return zGrids;
	}
	public void setNumSegments(int numXSegments,int numYSegments,int numZSegments) {
		this.xGrids = numXSegments;
		this.yGrids = numYSegments;
		this.zGrids = numZSegments;

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
	
	double fx(double x,double y,double z){
		calc_x.setVariable("x", x);
		calc_x.setVariable("y", y);
		calc_x.setVariable("z", z);
		
		return Globalsettings.inverseMapX(calc_x.calculate());
	}
	
	double fy(double x,double y,double z){
		calc_y.setVariable("x", x);
		calc_y.setVariable("y", y);
		calc_y.setVariable("z", z);

		return Globalsettings.inverseMapZ(calc_y.calculate());
	}
	double fz(double x,double y,double z){
		calc_z.setVariable("x", x);
		calc_z.setVariable("y", y);
		calc_z.setVariable("z", z);

		return Globalsettings.inverseMapZ(calc_z.calculate());
	}
	
	@Override
	public String getDefinition(){
		return "<br>x = " + exprX +"<br>" + "y = " + exprY +"<br>" +"z = " + exprZ +
		   "<br>"+" <br> <b>x-range: </b> &nbsp ["+ Globalsettings.minX + " , " +Globalsettings.maxX +"]"+
		   "<br>"+" <br> <b>y-range: </b> &nbsp ["+ Globalsettings.minY + " , " + Globalsettings.maxY +"]"+
		   "<br>"+" <br> <b>z-range: </b> &nbsp ["+ Globalsettings.minZ + " , " + Globalsettings.maxZ +"]" ;

	}
}