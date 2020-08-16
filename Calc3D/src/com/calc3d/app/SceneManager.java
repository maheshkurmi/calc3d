package com.calc3d.app;

import java.awt.Color;
import java.util.ArrayList;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DCurve;
import com.calc3d.app.elements.Element3DImplicit;
import com.calc3d.app.elements.Element3DLine;
import com.calc3d.app.elements.Element3DLineSegment;
import com.calc3d.app.elements.Element3DParametricSurface;
import com.calc3d.app.elements.Element3DPlane;
import com.calc3d.app.elements.Element3DPolygon;
import com.calc3d.app.elements.Element3DPoint;
import com.calc3d.app.elements.Element3DSurface;
import com.calc3d.app.elements.Element3DVector;
import com.calc3d.app.elements.Element3DVectorField;
import com.calc3d.app.elements.Element3Dfunction;
import com.calc3d.app.elements.Element3Dimplicit2D;
import com.calc3d.engine3d.Scene3D;
import com.calc3d.geometry3d.Box3D;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementArrow;
import com.calc3d.geometry3d.ElementCollection;
import com.calc3d.geometry3d.ElementCurve;
import com.calc3d.geometry3d.ElementRuler;
import com.calc3d.geometry3d.ElementString;
import com.calc3d.geometry3d.Object3D;
import com.calc3d.geometry3d.Plane3D;
import com.calc3d.math.Vector3D;
import com.calc3d.utils.ColorUtils;

public class SceneManager  {
	private ArrayList<Element3D> element3Ds;

	/**bounding box of scene, all element are clipped against this box*/
	private Box3D box;
	
	/**Clipper for the bounding box*/
    private Clip clip;
    
  	/** True if axis are shown */
	private boolean axisVisible = false;

	/** True if bounding box is shown */
	private boolean boxVisible = false;

	/** True if XY grid is shown */
	private boolean gridXYVisible = false;

	/** Elements represetenting axes*/
	private ElementCollection axis3D, box3D, gridXY;
	
	/**object to store axes info*/
	private static AxesDefinition axesDefinition;
	
	
	public SceneManager(){
		box=new Box3D(-1,1,-1,1,-1,1);
		clip=new Clip(box);
		axesDefinition=new AxesDefinition();
	}
	
	public void createDemo(){
		element3Ds=new ArrayList<Element3D>();
		//Add demoPoints
		Element3DPoint p1,p2;
		p1=new Element3DPoint(new Vector3D(0.1,0.2,0.7),"A",Color.red);
		p2=new Element3DPoint(new Vector3D(0.6,0.6,-0.6),"B",Color.blue);
		p1.setName("A " +p1.getDefinition());
		//addElement(p1);
		//element3Ds.add(p2);
		//Add demoLine
		Element3DLine l1;
		l1=new Element3DLine(new Vector3D(-1,-1,-1),new Vector3D(1,1,3));
		l1.setFillColor(Color.green);
		l1.setLineColor(Color.black);
		l1.setCurveWidth(2);
		//Add demoCurve
		Element3DCurve ec=new Element3DCurve();
		ec.setExprX("3*sin(t)");
		ec.setExprY("3*cos(t)");
		ec.setExprZ("t/2");
		ec.setMin_t(-10);
		ec.setMax_t(10);
		ec.setNumSegments(100);
		ec.setFillColor(Color.red.darker());
		ec.setBackColor(Color.orange);
		ec.setCurveWidth(2);
		ec.setName("Helix");
		element3Ds.add(ec);
		
		//Add demoCurve
		Element3DVectorField ef=new Element3DVectorField();
		ef.setExprX("x/sqrt(x*x+y*y+z*z)");
		ef.setExprY("y/sqrt(x*x+y*y+z*z)");
		ef.setExprZ("z/sqrt(x*x+y*y+z*z)");
		ef.setFillColor(Color.red);
		ef.setBackColor(Color.orange);
		ef.setCurveWidth(1);
		ef.setName("Radial Field");
		element3Ds.add(ef);
		//addElement(ec);
		
		//addElement(new Element3DObject(4,3,1,9));
		//addElement(new Element3DObject(1));
		//addElement(new Element3DObject(2));
		
		
		//addElement(ec);
		//Add Demo Plane
		Element3DPlane ep=new Element3DPlane(1,0,1,0);
		ep.setFillColor(Color.pink);
		//ep.setName("saas");
		initialiseElement3D(ep);
		//addElement(ep);
		//Add Demo Vector
		Element3DVector ev=new Element3DVector(1,0.3,0.8);
		ev.setFillColor(Color.black);
		ev.setName("Vector3D " + ev.getDefinition());
		//addElement(ev);
		//Add Demo line
		Element3DLineSegment ls=new Element3DLineSegment(p1,p2);
		//element3Ds.add(ls);
		
		//Add Surface
		Element3DSurface es =new Element3DSurface("x-y");//0.3*(x*sin(5*y)+y*sin(5*x))");//"2*3*x*3*y/(2^((3*x)^2+(3*y)^2))");//
		es.setFillColor(Color.green);
		es.setLineColor(Color.gray);
		es.setxGrids(10);
		es.setyGrids(10);
		//element3Ds.add(es);
		//boxVisible=true;
		axisVisible=true;
		gridXYVisible=false;
		//Add demoCurve
		Element3DCurve ec1=new Element3DCurve();
		ec1.setExprX("0.3*sin(12*t)");
		ec1.setExprY("t");
		ec1.setExprZ("t");
		ec1.setMin_t(-1);
		ec1.setMax_t(1);
		ec1.setNumSegments(50);
		ec1.setLineColor(Color.red.darker());
		ec1.setCurveWidth(1);
		//	elements.add(ec1);
		Element3Dimplicit2D eic=new Element3Dimplicit2D("r-3*sin(t)");
		eic.setFuncType(1);
		eic.setCurveWidth(2);
		eic.setLineColor(Color.red);
		eic.setFillColor(Color.red);
		
		//element3Ds.add(eic);
		Element3Dfunction ef1=new Element3Dfunction("Pi=3.14;ir=.3+.1*sin(4*Pi*u)\nr=ir*sin(2*Pi*v)+.5\nx=r*sin(2*Pi*u)\ny=r*cos(2*Pi*u)\nz=1.5*ir*cos(Pi*v)\nx=x*5;y=y*5;z=z*5;");//"x=cos(u)\n y=sin(u)\n z=v");
		ef1.setName("Open pot");
		ef1.setLineColor(new Color(109,95,163));
		ef1.setFillmode(0);
		element3Ds.add(ef1);
			//Element3Dfunction ef1=new Element3Dfunction("u=-2+4*u; v=-2+4*v\n\nx=u-(u*u*u/3)+u*v*v\ny=v-(v*v*v/3)+u*u*v\nz=u*u-v*v\n\nn=10; x=x/n; y=y/n; z=z/n");
		Element3Dfunction ef2=new Element3Dfunction("u=u*6.28;v=v*6.28;x=(3 + sin(v) + cos(u)) * sin(2 * v);y=(3 + sin(v) + cos(u)) * cos(2 * v);z=sin(u) + 2 * cos(v);x=x/1.3;y=y/1.3;z=z/1.3;");
		ef2.setName("Closed loop");
		ef2.setuGrids(10);
		ef2.setvGrids(30);
		ef2.setFillmode(0);
		ef2.setLineColor(Color.gray);
	
		//element3Ds.add(ef2);
		Element3DImplicit ei=new Element3DImplicit("x*x+y*y+z*z-9");
	//	ei.setName("kauu");
		initialiseElement3D(ei);
		//element3Ds.add(ei);
		//element3Ds.add(new Element3DImplicitCurve("x*x+y*y+z*z-0.81"));
	//Element3DObject eo=new Element3DObject(4,0.6,1)	;

	
	//elements.add(eo);
	
	Element3DParametricSurface eps=new Element3DParametricSurface();
	//elements.add(eps);
	
//	Element3Dimplicit2D eic=new Element3Dimplicit2D("2*x*x+4*y*y-2*x*y-1");//"x*x+y*y-2*x*y-x-0.5");//0.3*(x*sin(5*y)+y*cos(5*x))");
	//elements.add(eic);
	
	//Element3Dcartesian2D ecc=new Element3Dcartesian2D("sin(x)");
	//ecc.setName("curve2D-SineCurve");
	//addElement(ecc);	
	
	
	}
	
	private void initialiseElement3D(Element3D element3D){
		setValidName(element3D);
		Color fc = ColorUtils.getRandomColor(0.5f, 1.0f);
		Color oc = fc.darker();
		element3D.setLineColor(oc);
		element3D.setFillColor(fc);
	}
	
	/**
	 * Adds Element3D to list of Element3D and also creates its renderable element 
	 * @param element3D
	 */
	public void addElement(Element3D element3D){
		setValidName(element3D);
		element3Ds.add(element3D);
		element3D.createElement(clip);
	}
	
	
	
	/**Removes element if found in list*/
	public void removeElement(Element3D element3D){
		element3Ds.remove(element3D);
	}
	
	public void setElement3DVisible(Element3D element3D,boolean visible){
		element3D.setVisible(visible);
	}
	
	/**
	 * Sets the name by appending name of object by numbers such that no 2 objects have same name
	 * @param element3D the element3D whose name is to be validated and set to new name if required
	 */
	public void setValidName(Element3D element3D){
		
		if (element3D.getName().trim().equalsIgnoreCase("")) 
		{
			element3D.setName(commonUtils.getobject3DName(element3D));
		}
		String tempName=element3D.getName();
		boolean isValid=false;
		int i=0;
		
		while (isValid == false) {
			isValid = true;
			for (Element3D e : element3Ds) {
				if (tempName.equalsIgnoreCase(e.getName())){
					i++;
					tempName =element3D.getName()+i;
					isValid =false;
					break;
				}
			}
		}
		element3D.setName(tempName);
	}


	/**
	 * Returns the arraylist containing all elements;
	 * @return Arraylist
	 */
	public ArrayList<Element3D> getElement3DList(){
	          return element3Ds;
	}
	
	/**
	 * Returns element at given index if exits else null;
	 * @param index
	 * @return
	 */
	public Element3D getElement3D(int index){
		if ((index>=0)&(index<element3Ds.size()))
		    return element3Ds.get(index);
		else
			return null;
	}
	
	/**
	 * Returns element at given index if exits else null;
	 * @param index
	 * @return
	 */
	public void setElement3D(int index,Element3D element){
		if ((index>=0)&(index<element3Ds.size()))
		{
		     element3Ds.remove(index);
		     element3Ds.add(index, element);
		     element.createElement(clip);
		}
		
	}
	/**
	 * returns number of element3Ds conatained by sceneManager
	 * @return
	 */
	public int getElementCount(){
		return element3Ds.size();
	}
	
	/**
	 * Creates the scene (for 3D Engine) based on Element3Ds of this manager
	 * @param reCreateEachElement Setting this true forces each Element3D to reCreate its renderable element
	 * @return
	 */
	public Scene3D createScene(boolean reCreateEachElement){
		Scene3D scene=new Scene3D();
		Object3D<Element> object3D =new Object3D<Element>();
		//Add axes whichever enable
		createAxes();
		//createBox(Color.blue);
		createXYGrid(Color.green.darker().darker());
		if (axisVisible)	for (Element e:axis3D.elements)	object3D.addElement(e);
		
		//if (boxVisible)		for (Element e:box3D.elements)	object3D.addElement(e);
		
		if (gridXYVisible)	for (Element e:gridXY.elements)	object3D.addElement(e);
		
		//Add plane first so that number of intersection of polygons in bsp may be minimum
		for(Element3D element3D:element3Ds){
			if (null==element3D)continue;
			if ((element3D.isVisible()==false) && (!reCreateEachElement)){
				continue;}
			if (element3D instanceof Element3DPlane ||element3D instanceof Element3DPolygon){
			//Create Element again only when needed
			Element e= reCreateEachElement?element3D.createElement(clip):element3D.getElement();
			if ((e==null)&& !reCreateEachElement)e=element3D.createElement(clip);
			if ((null!=e) && element3D.isVisible())object3D.addElement(e);
			}
		}
		
	
		//Add elements other than Plane
		for(Element3D element3D:element3Ds){
			if (null==element3D)continue;
			if ((element3D.isVisible()==false) && (!reCreateEachElement)){
				continue;}
			if (element3D instanceof Element3DPlane)continue;
			//Create Element again only when needed
			Element e= reCreateEachElement?element3D.createElement(clip):element3D.getElement();
			if ((e==null)&& !reCreateEachElement)e=element3D.createElement(clip);
			if ((e==null) || !element3D.isVisible()) continue;
			if (e instanceof ElementCollection){
				for (Element e1: ((ElementCollection)e).elements )if (null!=e1)object3D.addElement(e1);
			}else {
				if (null!=e) object3D.addElement(e);
			}
		}
	
		//Add all elements to scene
		scene.addObject3D(object3D);
		return scene;
		
	}
	
	private ElementCollection createAxes() {
	    Color color=Globalsettings.axisColor;
	    
        double minX,maxX,minY,maxY,minZ,maxZ;
        Box3D box=Globalsettings.axesBox;
        minX=Globalsettings.inverseMapX(box.getMinX());
		maxX=Globalsettings.inverseMapX(box.getMaxX());
		minY=Globalsettings.inverseMapX(box.getMinY());
		maxY=Globalsettings.inverseMapX(box.getMaxY());
		minZ=Globalsettings.inverseMapX(box.getMinZ());
		maxZ=Globalsettings.inverseMapX(box.getMaxZ());
	    double n=10; //no of parts axis is divided
	    int i;
		axis3D = new ElementCollection();
		if (Globalsettings.xAxisVisible & (maxX>0)){
			for ( i = 0; i < n-1; i++) {
				ElementCurve ex = new ElementCurve(new Vector3D(i/n*maxX, 0, 0),new Vector3D((i+1)/n*maxX, 0, 0));
				ex.setLineColor(color);
				ex.setCurveWidth(Globalsettings.axisWidth);
				ex.setFillColor(color);
				axis3D.addElement(ex);
			}
			ElementArrow ex = new ElementArrow(new Vector3D(i/n*maxX, 0, 0),new Vector3D((i+1)/n*maxX, 0, 0));
			ex.setLineColor(color);
			ex.setCurveWidth(Globalsettings.axisWidth);
			ex.setFillColor(color);
			ex.setArrowSize(3);
			axis3D.addElement(ex);
			axis3D.addElement(new ElementString("x", new Vector3D(maxX + 0.1, 0, 0),new Vector3D(maxX + 0.2, 0,0), color));
		}
			
		if (Globalsettings.yAxisVisible & (maxY>0)){
			for ( i = 0; i < n-1; i++) {
				ElementCurve ey = new ElementCurve(new Vector3D(0,i/n*maxY, 0),new Vector3D(0,(i+1)/n*maxY, 0));
				ey.setLineColor(color);
				ey.setCurveWidth(Globalsettings.axisWidth);
				ey.setFillColor(color);
				axis3D.addElement(ey);
			}
			ElementArrow ey = new ElementArrow(new Vector3D(0,i/n*maxY, 0),new Vector3D(0,(i+1)/n*maxY, 0));
			ey.setLineColor(color);
			ey.setCurveWidth(Globalsettings.axisWidth);
			ey.setFillColor(color);
			ey.setArrowSize(3);
			axis3D.addElement(ey);
			axis3D.addElement(new ElementString("y", new Vector3D(0,maxY + 0.1, 0),new Vector3D(0,maxY + 0.2, 0), color));
		}
		
		if (Globalsettings.zAxisVisible & (maxZ>0)){
			for ( i = 0; i < n-1; i++) {
				ElementCurve ez = new ElementCurve(new Vector3D(0,0,i/n*maxZ),new Vector3D(0, 0,(i+1)/n*maxZ));
				ez.setLineColor(color);
				ez.setCurveWidth(Globalsettings.axisWidth);
				ez.setFillColor(color);
				axis3D.addElement(ez);
			}
			ElementArrow ez = new ElementArrow(new Vector3D(0, 0,i/n*maxZ),new Vector3D(0, 0,(i+1)/n*maxZ));
			ez.setLineColor(color);
			ez.setCurveWidth(Globalsettings.axisWidth);
			ez.setFillColor(color);
			ez.setArrowSize(3);
			axis3D.addElement(ez);
			axis3D.addElement(new ElementString("z", new Vector3D(0, 0,maxZ + 0.1),new Vector3D(0, 0,maxZ + 0.2), color));
		}
		
		if (Globalsettings.xAxisVisible & (minX<0)){
			for ( i = 0; i < n-1; i++) {
				ElementCurve ex = new ElementCurve(new Vector3D(-i/n*Math.abs(minX), 0, 0),new Vector3D(-(i+1)/n*Math.abs(minX), 0, 0));
				ex.setLineColor(color);
				ex.setCurveWidth(Globalsettings.axisWidth);
				ex.setFillColor(color);
				axis3D.addElement(ex);
			}
			ElementArrow ex = new ElementArrow(new Vector3D(-i/n*Math.abs(minX), 0, 0),new Vector3D(-(i+1)/n*Math.abs(minX), 0, 0));
			ex.setLineColor(color);
			ex.setCurveWidth(Globalsettings.axisWidth);
			ex.setFillColor(color);
			ex.setArrowSize(3);
			axis3D.addElement(ex);
			axis3D.addElement(new ElementString("-x", new Vector3D(-Math.abs(minX) - 0.1, 0, 0),new Vector3D(-Math.abs(minX) - 0.2, 0,0), color));
		}
		
		if (Globalsettings.yAxisVisible & (minY<0)){
			for ( i = 0; i < n-1; i++) {
				ElementCurve ey = new ElementCurve(new Vector3D(0,-i/n*Math.abs(minY), 0),new Vector3D(0,-(i+1)/n*Math.abs(minY), 0));
				ey.setLineColor(color);
				ey.setCurveWidth(Globalsettings.axisWidth);
				ey.setFillColor(color);
				axis3D.addElement(ey);
			}
			ElementArrow ey = new ElementArrow(new Vector3D(0,-i/n*Math.abs(minY), 0),new Vector3D(0,-(i+1)/n*Math.abs(minY), 0));
			ey.setLineColor(color);
			ey.setCurveWidth(Globalsettings.axisWidth);
			ey.setFillColor(color);
			ey.setArrowSize(3);
			axis3D.addElement(ey);
			axis3D.addElement(new ElementString("-y", new Vector3D(0,-Math.abs(minY) - 0.1, 0),new Vector3D(0,-Math.abs(minY) - 0.2, 0), color));
		}
		
		if (Globalsettings.zAxisVisible & (minZ<0)){
			for ( i = 0; i < n-1; i++) {
				ElementCurve ez = new ElementCurve(new Vector3D(0,0,-i/n*Math.abs(minZ)),new Vector3D(0, 0,-(i+1)/n*Math.abs(minZ)));
				ez.setLineColor(color);
				ez.setCurveWidth(Globalsettings.axisWidth);
				ez.setFillColor(color);
				axis3D.addElement(ez);
			}
			ElementArrow ez = new ElementArrow(new Vector3D(0, 0,-i/n*Math.abs(minZ)),new Vector3D(0, 0,-(i+1)/n*Math.abs(minZ)));
			ez.setLineColor(color);
			ez.setCurveWidth(Globalsettings.axisWidth);
			ez.setFillColor(color);
			ez.setArrowSize(3);
			axis3D.addElement(ez);
			axis3D.addElement(new ElementString("-z", new Vector3D(0, 0,-Math.abs(minZ) - 0.1),new Vector3D(0, 0,-Math.abs(minZ) - 0.2), color));
		}
		
		/*
		 * Draw ticks
		
		if (Globalsettings.xAxisVisible & (maxX>0)){
		    n=Globalsettings.axisTicks;
		    for ( i = 1; i < n; i++) {
		    ElementCurve e = new ElementCurve(new Vector3D(-0.035, 0,i/n*Globalsettings.inverseMapZ(maxZ)),new Vector3D(0.035, 0,i/n*Globalsettings.inverseMapZ(maxZ)));
			e.setLineColor(color);
			axis3D.addElement(e);
			  e = new ElementCurve(new Vector3D(i/n*Globalsettings.inverseMapZ(maxZ),-0.035,0),new Vector3D(i/n*Globalsettings.inverseMapZ(maxZ),0.035, 0));
				e.setLineColor(color);
				axis3D.addElement(e);
				 e = new ElementCurve(new Vector3D(-0.035,i/n*Globalsettings.inverseMapZ(maxZ),0),new Vector3D(0.035,i/n*Globalsettings.inverseMapZ(maxZ), 0));
					e.setLineColor(color);
					axis3D.addElement(e);
				 
		    
		    }
		}
		 */
		return axis3D;
	}
	
	
	/**
	 * 3D axes
	 * @param color
	 * @return
	 */
	private ElementCollection createAxes1(Color color) {
		    axis3D = new ElementCollection();
	        final String[] axisNames={"X","Y","Z"};
	        for (int i=0; i<3; i++) {
	            if (!axesDefinition.shown[i]) continue;
	            Vector3D axisVector=axesDefinition.axisVectors[i];
	            double min=axesDefinition.min[i],max=axesDefinition.max[i];
	            // create the axis line segments
	            double incr=axesDefinition.incr;
	            for (double v=min; v<max-1e-8; v+=incr) {
	            	axis3D.addElement(new ElementCurve(axisVector.scale(v),
	                    axisVector.scale(v+incr),axesDefinition.color,axesDefinition.width,true));
	            }
	            // create the tick mark labels
	            for (double v=Math.ceil(min/axesDefinition.tickDensity)*axesDefinition.tickDensity;  v<max;
	                 v+=axesDefinition.tickDensity) {
	                double pos=(double)Math.round(v*1000)/1000;
	              //  if (pos!=0) axis3D.addElement(new ElementString(String.valueOf(pos),
	                	//	axisVector.scale(v),axesDefinition.color,axisVector));
	            }
	            // create the arrows
	            Vector3D planeVec=axesDefinition.axisVectors[i==0?2:0];
	            axis3D.addElement(new ElementCurve(axisVector.scale(max),
	                axisVector.scale(max*.97).subtract(planeVec.scale(.03)),
	                axesDefinition.color,axesDefinition.width,true));
	            axis3D.addElement(new ElementCurve(axisVector.scale(max),
	                axisVector.scale(max*.97).add(planeVec.scale(.03)),
	                axesDefinition.color,axesDefinition.width,true));
	            axis3D.addElement(new ElementString(axisNames[i],axisVector.scale(max*1.1),axisVector.scale(max*1.2),axesDefinition.color));

	        }
	        return axis3D;
	 }
	
	/**
	 * Bounding box of 3D axes
	 * @param color
	 * @return
	 */
	private  ElementCollection createBox(Color color) {
		box3D = new ElementCollection();
		Vector3D v1, v2;
		double minX, minY, minZ, maxX, maxY, maxZ;
		minX = box.getMinX();
		minY = box.getMinY();
		minZ = box.getMinZ();
		maxX = box.getMaxX();
		maxY = box.getMaxY();
		maxZ = box.getMaxZ();
	
        //Plane y=ymax
		v1 = new Vector3D(maxX, maxY, minZ);
		v2 = new Vector3D(maxX, maxY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(maxX, maxY, maxZ);
		v2 = new Vector3D(minX, maxY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, maxY, maxZ);
		v2 = new Vector3D(minX, maxY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, maxY, minZ);
		v2 = new Vector3D(maxX, maxY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		//Plane y=ymin
		v1 = new Vector3D(maxX, minY, minZ);
		v2 = new Vector3D(maxX, minY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(maxX, minY, maxZ);
		v2 = new Vector3D(minX, minY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, minY, maxZ);
		v2 = new Vector3D(minX, minY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, minY, minZ);
		v2 = new Vector3D(maxX, minY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		//Plane x=xmax
		v1 = new Vector3D(maxX, maxY, minZ);
		v2 = new Vector3D(maxX, maxY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(maxX, maxY, maxZ);
		v2 = new Vector3D(maxX, minY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(maxX, minY, maxZ);
		v2 = new Vector3D(maxX, minY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(maxX, minY, minZ);
		v2 = new Vector3D(maxX, maxY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		//Plane x=xmin
		v1 = new Vector3D(minX, maxY, minZ);
		v2 = new Vector3D(minX, maxY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, maxY, maxZ);
		v2 = new Vector3D(minX, minY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, minY, maxZ);
		v2 = new Vector3D(minX, minY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, minY, minZ);
		v2 = new Vector3D(minX, maxY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		//plane z=zmax
		v1 = new Vector3D(maxX, maxY, maxZ);
		v2 = new Vector3D(minX, maxY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, maxY, maxZ);
		v2 = new Vector3D(minX, minY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, minY, maxZ);
		v2 = new Vector3D(maxX, minY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(maxX, minY, maxZ);
		v2 = new Vector3D(maxX, maxY, maxZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		//Plane z=zmin
		v1 = new Vector3D(maxX, maxY, minZ);
		v2 = new Vector3D(minX, maxY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, maxY, minZ);
		v2 = new Vector3D(minX, minY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(minX, minY, minZ);
		v2 = new Vector3D(maxX, minY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		v1 = new Vector3D(maxX, minY, minZ);
		v2 = new Vector3D(maxX, maxY, minZ);
		box3D.addElement(new ElementCurve(v1, v2, color, 1, true));
		   
		ElementRuler erx=new ElementRuler(new Vector3D(-1,-1,-1),new Vector3D(1,-1,-1),new Vector3D(1,0,-1),0,1,5,5);
		ElementRuler ery=new ElementRuler(new Vector3D(-1,-1,-1),new Vector3D(-1,1,-1),new Vector3D(1,0,-1),0,1,5,5);
		erx.setSpilttable(false);
		ery.setSpilttable(false);
		
		//box3D.addElement(erx);
		//box3D.addElement(ery);
		
		float div=5;
		int subdiv=5;
		float delta,ticklength;
		delta=2/(div*subdiv);
		for (int i=0; i<div;i++){
			for (int j=0;j<subdiv;j++){
				ticklength=(j==0)?0.07f:0.04f;
			    ElementCurve c=new ElementCurve(new Vector3D(-1+(i*subdiv+j)*delta,-1,-1),new Vector3D(-1+(i*subdiv+j)*delta,-1+ticklength,-1));
			    c.setLineColor(color);
			    box3D.addElement(c);
			    if ((i>0)&&(j==0))box3D.addElement(new ElementString(String.format("%.1G",Globalsettings.mapX(-1+(i*subdiv+j)*delta)),new Vector3D(-1+(i*subdiv+j)*delta,-1,-1),new Vector3D(0,0,0),color.darker()));
			}
			ElementCurve c=new ElementCurve(new Vector3D(1,-1,-1),new Vector3D(1,-1+0.07,-1));
			box3D.addElement(c);
		}
		
		for (int i=0; i<div;i++){
			for (int j=0;j<subdiv;j++){
				ticklength=(j==0)?0.07f:0.04f;
			    ElementCurve c=new ElementCurve(new Vector3D(-1,-1+(i*subdiv+j)*delta,-1),new Vector3D(-1+ticklength,-1+(i*subdiv+j)*delta,-1));
			    c.setLineColor(color);
			    box3D.addElement(c);
			    if ((i>0)&&(j==0))box3D.addElement(new ElementString(String.format("%.1G",Globalsettings.mapY(-1+(i*subdiv+j)*delta)),new Vector3D(-1,-1+(i*subdiv+j)*delta,-1),new Vector3D(0,0,0),color.darker()));
				
			}
			ElementCurve c=new ElementCurve(new Vector3D(-1,1,-1),new Vector3D(-1+0.07,1,-1));
			box3D.addElement(c);
		}
		
		for (int i=0; i<div;i++){
			for (int j=0;j<subdiv;j++){
				ticklength=(j==0)?0.07f:0.04f;
			    ElementCurve c=new ElementCurve(new Vector3D(-1,1,-1+(i*subdiv+j)*delta),new Vector3D(-1+ticklength,1,-1+(i*subdiv+j)*delta));
			    c.setLineColor(color);
			    box3D.addElement(c);
			    if ((i>0)&&(j==0))box3D.addElement(new ElementString(String.format("%.1G",Globalsettings.mapZ(-1+(i*subdiv+j)*delta)),new Vector3D(-1,1,-1+(i*subdiv+j)*delta),new Vector3D(0,0,0),color.darker()));
				
			}
			ElementCurve c=new ElementCurve(new Vector3D(-1,1,1),new Vector3D(-1+0.07,1,1));
			box3D.addElement(c);
		}
		return box3D;
	}
	
	
	/**
	 * XY plane with grids
	 * @param color
	 * @return
	 */
	private  ElementCollection createXYGrid(Color color) {
		color=Color.DARK_GRAY;//.brighter().brighter();
		gridXY = new ElementCollection();
		double minX, minY, maxX, maxY;
		box=Globalsettings.mappedClipBox;
		minX = box.getMinX();
		minY = box.getMinY();
		//minZ = box.getMinZ();
		maxX = box.getMaxX();
		maxY = box.getMaxY();
		//maxZ = box.getMaxZ();
		int numGrids=8;
		double x, y, dx, dy;
		dx = (maxX - minX) / (numGrids );
		dy = (maxY - minY) / (numGrids);
		for (y = minY+dy; y < maxY; y += dy) {
			   Vector3D v1=new Vector3D(minX,y,-0.02);
			   Vector3D v2=new Vector3D(maxX,y,-0.02);
               ElementCurve ec=new ElementCurve(v1,v2);
               ec.setDashed(true);
               ec.setLineColor(color);
               ec.setFillColor(color);
               ec.setBackColor(color);
               gridXY.addElement(ec);
               if (Globalsettings.mapCliptoY(y)==0)continue;
               ElementString es=new ElementString(doubletoString(Globalsettings.mapCliptoY(y)),new Vector3D(maxX+0.12,y,0),new Vector3D(maxX+0.13,y,0),Color.black);
               gridXY.addElement(es);
		}
		
	   for (x = minX+dx; x < maxX; x += dx) {
			   Vector3D v1=new Vector3D(x,minY,-0.02);
			   Vector3D v2=new Vector3D(x,maxY,-0.02);
               ElementCurve ec=new ElementCurve(v1,v2);
               ec.setDashed(true);
               ec.setLineColor(color);
               ec.setFillColor(color);
               ec.setBackColor(color);
               gridXY.addElement(ec);
               if (Globalsettings.mapCliptoY(x)==0)continue;
               ElementString es=new ElementString(doubletoString(Globalsettings.mapCliptoX(x)),new Vector3D(x,maxY+0.12,0),new Vector3D(x,maxY+0.13,0),Color.black);
               gridXY.addElement(es);
		}
	   
	    Element xyPlane=clip.getClippedPolygonfromPlane(new Plane3D(0,0,1,0.04));
	    xyPlane.setFillColor(new Color(0,120,0,110));
	    xyPlane.setBackColor(new Color(0,120,0,100));
	    xyPlane.setFilled(true);  
	    gridXY.addElement(xyPlane);
	    return gridXY;
	}
	
	private String doubletoString(double d){
		return String.format("%.1f",((int)(d*10))/10.0);//String.format("%.0G", d);
	}
		
	/**
	 * Returns true if axes will be rendered.
	 */
	public boolean isAxisVisible() {
		return axisVisible;
	}
	
	/**
	 * Sets the axisRenderable flag.
	 * @param flag true if axis are to be displayed
	 */
	public void setAxisVisible(boolean axisVisible) {
		this.axisVisible = axisVisible;
	}

	/**
	 * Returns true if bounding Box will be rendered.
	 */
	public boolean isBoxVisible() {
		return boxVisible;
	}
	
	/**
	 * Sets the boxRenderable flag.
	 * @param flag true if bounding box is to be displayed
	 */
	public void setBoxVisible(boolean boxVisible) {
		this.boxVisible = boxVisible;
	}

	public boolean isGridXYVisible() {
		return gridXYVisible;
	}

	public void setGridXYVisible(boolean gridXYVisible) {
		this.gridXYVisible = gridXYVisible;
	}

	/**
	 * @return the clips
	 */
	public Clip getClip() {
		return clip;
	}

	/**
	 * @param clips the clips to set
	 */
	public void setClip(Clip clips) {
		this.clip = clips;
	}
	
}


/**
 * Class to store axes informations
 * @author mahesh
 *
 */
class AxesDefinition {
    public final Vector3D[] axisVectors={new Vector3D(1,0,0),new Vector3D(0,1,0),new Vector3D(0,0,1)};
    boolean[] shown={true,true,true};
    public double[] min={0,0,0},max={1,1,1};
    public double incr=0.2; //0.05
    public double tickDensity=.2;
    public Color color=Color.black;
    public int width=2;
}

