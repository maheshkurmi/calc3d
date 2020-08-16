package com.calc3d.app.elements;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import com.calc3d.app.Globalsettings;
import com.calc3d.engine3d.Camera3D;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementCollection;
import com.calc3d.geometry3d.ElementPoly;
import com.calc3d.log.Logger;
import com.calc3d.math.MathUtils;
import com.calc3d.math.Vector3D;
import com.calc3d.mathparser.Calculable;
import com.calc3d.mathparser.ExpressionBuilder;
import com.calc3d.utils.ColorUtils;

/**
 * Class for Elements3DSurfacd representing Surface in 3D space
 * 
 * @author mahesh
 * 
 */
public class Element3Dfunction extends Element3D {

	private static Logger LOG = Logger.getLogger(Camera3D.class.getName());
	private transient ArrayList<String> vars ;
	private transient ArrayList<Double> vals ;
	private transient ArrayList<Calculable> calcs ;
	private String expr="";
	/** Dimension bounding the surfce */
	double min_u, max_u, min_v, max_v;
	/**no of divisions/grids for surface*/
	private int uGrids = 20, vGrids = 20;
	public Element3Dfunction(String expr) {
		splittable=false;
		this.min_u =0;
		this.min_v = 0;
		this.max_u = 1;
		this.max_v = 1;
		this.expr = expr;
		this.setFillmode(2);
	}


	@Override
	public Element createElement() {
		element=createSurface(null);
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
    	isCreated=true;
		return element;
	}

	@Override
	public Element createElement(Clip clip) {
		element=createSurface(clip);
		if (null==element) return null;
		element.setFillColor(getFillColor());
     	element.setLineColor(getLineColor());
    	element.setCurveWidth(getCurveWidth());
    	element.setDashed(isDashed());
    	isCreated=true;
		return element;
	}

	@Override
	public String getDefinition() {
		String str="<br>"+expr.replace("\n", "<br>");
		
		return  str +
				  "<br>"+" <br> <b>u-range: </b> &nbsp ["+ min_u + " , " + max_u +"]"+
				  "<br>"+" <br> <b>v-range: </b> &nbsp ["+ min_v + " , " + max_v +"]";
	}

	public String getExpression() {
		return expr;
	}
	
	public void setExpression(String expr) {
		this.expr = expr;
	}


	private ElementCollection createSurface(Clip clip) {
		try{
			parse();
		}catch(Exception e){
			return null;
		}
		float i, j;
		double u, v;
       
		ElementCollection surface3D = new ElementCollection();
    
		Vector3D v1, v2, v3, v4;
		for (i = 0; i < uGrids; i++) {
			Inner:
			for (j = 0; j < vGrids; j++) {
         		u = min_u + (max_u - min_u) * i / uGrids;
				v = min_v + (max_v - min_v) * j / vGrids;
				v1 = evaluate(u,v);
				if (v1==null)continue Inner;
				u = min_u + (max_u - min_u) * i / uGrids;
				v = min_v + (max_v - min_v) * (j + 1) / vGrids;
				v2 = evaluate(u,v);
				if (v2==null)continue Inner;
				u = min_u + (max_u - min_u) * (i + 1) / uGrids;
				v = min_v + (max_v - min_v) * (j + 1) / vGrids;
				v3 = evaluate(u,v);
				if (v3==null)continue Inner;
				u = min_u + (max_u - min_u) * (i + 1) / uGrids;
				v = min_v + (max_v - min_v) * j / vGrids;
				v4 = evaluate(u,v);
				if (v4==null)continue Inner;
				ElementPoly element = new ElementPoly();
				element.addPoint(v1);
				element.addPoint(v2);
				element.addPoint(v3);
				element.addPoint(v4);
				if(T!=null)element.transform(T);
				if (getFillmode()==1)
				{
					element.setFilled(false); 
			     	element.setLineColor(getLineColor());
			    	u=u*6.28;
					v=v*6.28;
					element.setBackColor(new Color((int) ((Math.sin(u) + 1) * 0.5 * 255) ,(int)((Math.cos(u) + 1) * 0.5 * 255),(int)((Math.cos(v) + 1) * 0.5 * 255)));
					element.setFillColor(ColorUtils.blendColors(element.getBackColor(), Color.gray, 0.5));
					element.setCurveWidth(Math.max(1,getCurveWidth()));
				}
				else if (getFillmode()==3){
					element.setBackColor(getBackColor());
					element.setFillColor(ColorModel.getPreset(2).getPolygonColor((float) element.getCentre().getZ()));
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
					u=u*6.28;
					v=v*6.28;
					element.setBackColor(new Color((int) ((Math.sin(u) + 1) * 0.5 * 255) ,(int)((Math.cos(u) + 1) * 0.5 * 255),(int)((Math.cos(v) + 1) * 0.5 * 255)));
					element.setFillColor(ColorUtils.blendColors(element.getBackColor(), Color.gray, 0.5));
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

	public int getuGrids() {
		return uGrids;
	}

	public void setuGrids(int uGrids) {
		if (uGrids > 0)
			this.uGrids = uGrids;
		else
			LOG.error("Illegral number of xgrids" + uGrids);
	}

	public int getvGrids() {
		return vGrids;
	}

	public void setvGrids(int vGrids) {
		if (vGrids > 0)
			this.vGrids = vGrids;
		else
			LOG.error("Illegral number of xgrids" + vGrids);
	}

 	public boolean parse()throws FunctionParseErrorException{
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
			throw new FunctionParseErrorException("IOError:Can't Read Expression String" );
		} 
    	
    	/*
    	 * prepareParser
    	 */
    	vars = new ArrayList<String>();
    	vals = new ArrayList<Double>();
    	calcs = new ArrayList<Calculable>();
        Calculable calc;
        
 		String str = "";
 		//Add variables that are already known to parser
		vars.add("u");
		vars.add("v");
		vars.add("x");
		vars.add("y");
		vars.add("z");
		vals.add(0.0d);
		vals.add(0.0d);
		vals.add(0.0d);
		vals.add(0.0d);
		vals.add(0.0d);
		
		//Iterate through each line/statement
		for (int i = 0; i < lines.size(); i++) {
			str = (String) lines.get(i);
			//Each line is of form <variable=expression>
			//Split variable and expression
			String s[] = str.split("=");
			String var, exp;
			//parsing is needed only when there is equalto sign
			if (s.length > 1) {
				var = s[0].trim();
				exp = s[1].trim();
				String[] temp = {};
				String[] variables = vars.toArray(temp);
				//add all variables  known just before this line(to the calculable)
				try {
					calc = new ExpressionBuilder(exp).withVariableNames(
							variables).build();
					//update the calculable and variable of this line to arraylist
					calcs.add(calc);
					vars.add(var);
					vals.add(0.0d);
					System.out.println(var + " :" + calc.getExpression());
				} catch (Exception e) {
					//System.out.println("Parse error element function: line "+(i+1));
					e.printStackTrace();
					throw new FunctionParseErrorException("Syntax error in line "+(i+1)+":"+e.getMessage());
				}
			}
		}
		//Everything is parsable so OK
		return true;
    }
    	    
		
	private Vector3D evaluate(double u, double v) {
		Calculable calc;
	
		double x, y, z;
		x = Globalsettings.mapX(2*u-1);
		y = Globalsettings.mapY(2*v-1);
		z = 0;
     	vals.set(0, u);//u
     	vals.set(1, v);  //v
     	vals.set(2, x);  //x
     	vals.set(3, y); //y
     	vals.set(4, z); //z
	
		String var;
		double val;
		for (int i = 0; i < calcs.size(); i++) {
			calc=calcs.get(i);
			var=vars.get(i+5);
			for (int j=0;j<i+5;j++){
				calc.setVariable(vars.get(j), vals.get(j));
			}
			val=calc.calculate();
			if (!MathUtils.isValidNumber(val))return null;
			vals.set(i+5, val);
			if (var.equalsIgnoreCase("x"))	x = val;
			if (var.equalsIgnoreCase("y"))	y = val;
			if (var.equalsIgnoreCase("z"))	z = val;
		}
		return new Vector3D(Globalsettings.inverseMapX(x), Globalsettings.inverseMapY(y), Globalsettings.inverseMapZ(z));
	}
	

}
