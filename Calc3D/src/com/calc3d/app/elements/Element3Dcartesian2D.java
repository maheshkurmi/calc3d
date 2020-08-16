package com.calc3d.app.elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;

import com.calc3d.app.Globalsettings;
import com.calc3d.engine3d.Camera3D;
import com.calc3d.geometry3d.Box3D;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementCollection;
import com.calc3d.geometry3d.ElementCurve;
import com.calc3d.geometry3d.ElementPoint;
import com.calc3d.geometry3d.Plane3D;
import com.calc3d.log.Logger;
import com.calc3d.math.MathUtils;
import com.calc3d.math.Vector3D;
import com.calc3d.mathparser.Calculable;
import com.calc3d.utils.ColorUtils;

/**
 * Class for Elements3DSurfacd representing Surface in 3D space
 * 
 * @author mahesh
 * 
 */
public class Element3Dcartesian2D extends Element3D{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7712642062900492130L;
	private static Logger LOG = Logger.getLogger(Camera3D.class.getName());
	private transient Calculable calc;
	Plane3D plane3D;
	private String expr="";
	/** Dimension bounding the surface */
	double minX, maxX, minY, maxY, minZ, maxZ;
	/**no of divisions for curve*/
	private int numSegments ;
	/**Type of function viz Polar=1 and cartesian=0*/
	private int funcType=0;
	private transient ArrayList<PieceFunction>funcList;
	public Element3Dcartesian2D (){
		numSegments = 100;
		splittable=false;
	}
	
	public Element3Dcartesian2D(String expr) {
		this.minX = -1;
		this.minY = -1;
		this.minZ = -1;
		this.maxX = 1;
		this.maxY = 1;
		this.maxZ = 1;
		this.expr = expr;
		numSegments = 100;
		splittable=false;
	
	}
	
	public void parse() throws FunctionParseErrorException{
		    if (expr.trim()=="")throw new FunctionParseErrorException("The Expression can not be empty");
	    	StringReader sr = new StringReader(expr); 
	    	BufferedReader br = new BufferedReader(sr); 
	    	String nextLine = "";
	    	//Store all statements in String List
	    	ArrayList<String> lines=new ArrayList<String>();
	    	try {
				while ((nextLine = br.readLine()) != null){ 
					String[] str=nextLine.split(";") ;
					for (String string: str){
						string=string.trim();
						if (string.length()>0)	lines.add(string);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new FunctionParseErrorException("Can't parse Function:"+e.getMessage());
			} 
	    	/*
	    	 * prepareParser
	    	 */
	    	funcList=new ArrayList<PieceFunction>();
	    	try {
	    		for (String s:lines){
	    			PieceFunction func=PieceFunction.parse(s,funcType==1);
	    			funcList.add(func);
	    		}
			} catch (FunctionParseErrorException e) {
				funcList.clear();
				e.printStackTrace();
				throw new FunctionParseErrorException("Can't parse Function:"+e.getMessage());
			}  		
			if (!checkDomain())throw new FunctionParseErrorException("Domain can not be Overloaping");;
	}
	    	    
	
	private boolean checkDomain() {
		Collections.sort(funcList);
		PieceFunction f1=funcList.get(0);
		for (int i=1;i<funcList.size();i++){
			PieceFunction f2=funcList.get(i);
			if ((f1.max>f2.min)||(((f1.max)==(f2.min))&&(f1.rightClosed && f2.leftClosed))){
				System.out.println("Domain Overlapping in function no."+i+"and"+(i+1));
				return false;
			}
			f1=f2;
		}
		return true;
	}

	public Element3Dcartesian2D(String expr, Box3D box) {
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
		element=(funcType==0)?createExplicitCurve(null):createPolarCurve(null);
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
		return element;
	}

	@Override
	public Element createElement(Clip clip) {
		try {
			parse();
		} catch (FunctionParseErrorException e) {
			return null;
		}
		
		element=(funcType==0)?createExplicitCurve(clip):createPolarCurve(clip);
		if (null==element) return null;
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
		return element;
	}

	@Override
	public String getDefinition() {
		String str="<table>";
		for (PieceFunction f:funcList){
			str=str+"<tr>";
			str=str+"<td> "+((funcType==1)?"r = ":"y = ")+f.getExpr()+"</td>" +"<td> "+f.getInterval()+"</td>";//"<br>";
			str=str+"</tr>";
			
		}
		return  str+"</table>";
	}

	public String getExpression() {
		return  expr;
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

		
	private ElementCollection createExplicitCurve(Clip clip) {
		/*
		try {
			calc = new ExpressionBuilder(expr).withVariableNames("x")
					.build();
		} catch (Exception e) {
			LOG.error(e);
			return null;
		}
		*/
		ElementCollection curve3D = new ElementCollection();

		for (PieceFunction f : funcList) {
			int n=(int) (numSegments*(f.max-f.min)/(Globalsettings.maxX-Globalsettings.minX));
			
			minX = Globalsettings.inverseMapX(f.min);
			maxX = Globalsettings.inverseMapX(f.max);
			
			double x, dx,y;
			//Add circle only if it is inside clipRegion and valid
			if (minX > -1) {
				x = minX;
				y = Globalsettings.inverseMapY(f.evaluate(Globalsettings.mapX(x)));
				if (MathUtils.isValidNumber(y)){
					Element e1 = new ElementPoint(new Vector3D(x, y, 0));
					e1.setFillColor(getFillColor());
					e1.setLineColor(getLineColor());
					e1.setFilled(f.leftClosed);
					if(T!=null)e1.transform(T);
					e1 = clip.getClippedElement(e1);
					if (e1 != null)	curve3D.addElement(e1);
				}
			}
			//Add circle only if it is inside clipRegion and valid
			if (maxX < 1) {
				x=maxX;
				y = Globalsettings.inverseMapY(f.evaluate(Globalsettings.mapX(x)));
				if (MathUtils.isValidNumber(y)){
					Element e2 = new ElementPoint(new Vector3D(x, y, 0));
					e2.setFilled(f.rightClosed);
					e2.setFillColor(getFillColor());
					e2.setLineColor(getLineColor());
					if(T!=null)e2.transform(T);
					e2 = clip.getClippedElement(e2);
				if (e2 != null)	curve3D.addElement(e2);
				}
			}
			if ((maxX == minX)||(n<1)) continue;
			dx = (maxX - minX) / (n - 1);
			for (x = minX; x < maxX; x += dx) {
				
				y = Globalsettings.inverseMapY(f.evaluate(Globalsettings.mapX(x)));
				if (!MathUtils.isValidNumber(y))
					continue;
				Vector3D v1 = new Vector3D(x, y, 0);
				y = Globalsettings.inverseMapY(f.evaluate(Globalsettings.mapX(x + dx)));
				if (!MathUtils.isValidNumber(y))
					continue;
				Vector3D v2 = new Vector3D(x + dx, y, 0);
				Element ec = new ElementCurve(v1, v2);
				if(T!=null)ec.transform(T);
				ec = clip.getClippedElement(ec);
				if (ec == null)
					continue;
				ec.setDashed(false);
				ec.setLineColor(getLineColor());
				ec.setFillColor(ColorUtils.blendColors(getFillColor(),getBackColor(), (x - minX) / (maxX - minX)));
				ec.setCurveWidth(getCurveWidth());
				ec.setDashed(isDashed());
				ec.setSpilttable(isSplittable());
				curve3D.addElement(ec);
			}
			element = curve3D;
		}
		return (curve3D.elements.size() > 0) ? curve3D : null;
	}

	private ElementCollection createPolarCurve(Clip clip) {
		ElementCollection curve3D = new ElementCollection();
		for (PieceFunction f : funcList) {
			int n = numSegments / funcList.size();
			minX = f.min;
			maxX = f.max;
			double t, dt,r;

			/*
			//add circles at end points of curve (ignored for polar curves)
			t = minX;
			r = Globalsettings.inverseMapX(f.evaluate(t));
			Element e1 = new ElementPoint(new Vector3D(r * Math.cos(t), r* Math.sin(t), 0));
			e1.setFillColor(getFillColor());
			e1.setLineColor(getLineColor());
			e1.setFilled(f.leftClosed);
			e1 = clip.getClippedElement(e1);
			if (e1 != null)	curve3D.addElement(e1);
			t = maxX;
			r = Globalsettings.inverseMapX(f.evaluate(t));
			Element e2 = new ElementPoint(new Vector3D(r * Math.cos(t), r* Math.sin(t), 0));
			e2.setFilled(f.rightClosed);
			e2.setFillColor(getFillColor());
			e2.setLineColor(getLineColor());
			e2 = clip.getClippedElement(e2);
			if (e2 != null)	curve3D.addElement(e2);
			*/
			if ((maxX == minX) || (n < 1))return curve3D;
			dt = (maxX - minX) / (n - 1);
			
			//Draw the curve
			for (t = minX; t < maxX-dt; t += dt) {
				r = Globalsettings.inverseMapX(f.evaluate(t));
				if (!MathUtils.isValidNumber(r))
					continue;
				if (!MathUtils.isValidNumber(r))continue;
				Vector3D v1 = new Vector3D(r * Math.cos(t), r * Math.sin(t), 0);
				r = Globalsettings.inverseMapX(f.evaluate(t + dt));
				if (!MathUtils.isValidNumber(r))
					continue;
				Vector3D v2 = new Vector3D(r * Math.cos(t + dt), r* Math.sin(t + dt), 0);
				Element ec = new ElementCurve(v1, v2);
				if(T!=null)ec.transform(T);
				ec = clip.getClippedElement(ec);
				if (ec == null)continue;
				ec.setDashed(false);
				ec.setLineColor(getLineColor());
				ec.setFillColor(ColorUtils.blendColors(getFillColor(),getBackColor(), (t - minX) / (maxX - minX)));
				ec.setCurveWidth(getCurveWidth());
				ec.setDashed(isDashed());
				ec.setSpilttable(isSplittable());
				curve3D.addElement(ec);
			}
		}
		element = curve3D;
		return (curve3D.elements.size() > 0) ? curve3D : null;
	}
	
	//Calcukate f(X)
	/*
	double f_x(double x){
		return Globalsettings.inverseMapY(func.evaluate(Globalsettings.mapX(x)));
		/*
		 * calc.setVariable("x", Globalsettings.mapX(x));
		 * return Globalsettings.inverseMapY(calc.calculate());
		 */
	//}
    

	//Calculate r(t)
	double r_t(double t){
			calc.setVariable("t", t);
			return Globalsettings.inverseMapX(calc.calculate());
	}
	
	public double getMin_X() {
		return minX;
	}
	
	public double getMax_X() {
		return maxX;
	}
	
	public void setMin_X(double minX) {
		this.minX=minX;
	}
	
	public void setMax_X(double maxX) {
		this.maxX=maxX;
	}
	
	public int getNumSegments() {
		return numSegments;
	}
	public void setNumSegments(int numSegments) {
		if (numSegments>1)this.numSegments = numSegments;
		else 
		LOG.error("illegal number of segments (" + numSegments +")" +"expected more than 1");
	}
	/**
	 * @return the funcType
	 */
	public int getFuncType() {
		return funcType;
	}
	/**
	 * @param funcType the funcType to set
	 */
	public void setFuncType(int funcType) {
		
		if (funcType==1||funcType==0)this.funcType = funcType;
	}
	
	
}

