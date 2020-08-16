package com.calc3d.app.elements;

import com.calc3d.app.Globalsettings;
import com.calc3d.engine3d.Camera3D;
import com.calc3d.geometry3d.Box3D;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementCollection;
import com.calc3d.geometry3d.ElementCurve;
import com.calc3d.log.Logger;
import com.calc3d.math.Vector3D;
import com.calc3d.mathparser.Calculable;
import com.calc3d.mathparser.ExpressionBuilder;

/**
 * Class for Elements3DSurfacd representing Surface in 3D space
 * 
 * @author mahesh
 * 
 */
public class Element3Dimplicit2D extends Element3D{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1210367666504376166L;
	private static Logger LOG = Logger.getLogger(Camera3D.class.getName());
	private transient Calculable calc;
	private String expr="";
	/** Dimension bounding the surface */
	private double minX, maxX, minY, maxY, minZ, maxZ;
	/**no of divisions/grids for surface*/
	private int xGrids = 100, yGrids = 100;
	/**Type of function viz Polar=1 and cartesian=0*/
	private int funcType=0;
	
	public Element3Dimplicit2D(String expr) {
		this.minX = -1;
		this.minY = -1;
		this.minZ = -1;
		this.maxX = 1;
		this.maxY = 1;
		this.maxZ = 1;
		this.expr = expr;
		this.setName("Implicit3DSurface");
		splittable=false;
	}

	public Element3Dimplicit2D(String expr, Box3D box) {
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
		element=createImplicitCurve(null);
		if (null==element) return null;
		return element;
	}

	@Override
	public Element createElement(Clip clip) {
		element=createImplicitCurve(clip);
		return element;
	}


	@Override
	public String getDefinition() {
		return (expr.indexOf("=")>=0)?expr:expr +"= 0"+
				   "<br>"+" <br> <b>x-range: </b> &nbsp ["+ Globalsettings.minX + " , " +Globalsettings.maxX +"]"+
				   "<br>"+" <br> <b>y-range: </b> &nbsp ["+ Globalsettings.minY + " , " + Globalsettings.maxY +"]";
	}
	
	public String getExpression() {
		return expr;
	}

	public void setExpression(String expr) {
		this.expr = expr ;
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
	
/*
	public ElementCollection createImplicitCurve(Clip clip){
		ElementCollection ecreturn=new ElementCollection();
		Element3DSurface es =new Element3DSurface(expr);
		es.setxGrids(60);
		es.setyGrids(60);
		ElementCollection ec =(ElementCollection) es.createElement(clip);
		Plane3D plane=new Plane3D(0,0,1,0);
		for (Element e:ec.elements){
			Vector3D v1=new Vector3D(),v2=new Vector3D();
			if(plane.getIntersectionLine_withPoly(((ElementPoly)e).vertices,v1,v2)){
				ElementCurve eCurve=new ElementCurve(v1,v2);
				eCurve.setCurveWidth(curveWidth);
				eCurve.setLineColor(lineColor);
				eCurve.setFillColor(fillColor);
				eCurve.setDashed(isDashed());
				ecreturn.addElement(eCurve);
			}
		}
		return ecreturn;	
	
	}
	*/
	
	private double f(double x, double y) {
		double z=Double.NaN;
		if (funcType==0){
		    calc.setVariable("x", Globalsettings.mapX(x));
		    calc.setVariable("y", Globalsettings.mapY(y));
		    z=calc.calculate();
		    z=Globalsettings.inverseMapZ(z);
		}else{
			x=Globalsettings.mapX(x);
			y=Globalsettings.mapY(y);
			double r=Math.sqrt(x*x+y*y);
			double t=Math.atan2(y, x);
			calc.setVariable("r",r);
			calc.setVariable("t", t);
			z=calc.calculate();
			//z=r-3*Math.sin(3*t);
			//System.out.println(z);
		}
		return z;
	}
	
	public Element createImplicitCurve(Clip clip) {
		try {
			String[] temp;
			String exprNew;
			temp=expr.split("=");
			switch (temp.length)
			{
			case 1:
				exprNew=expr;
				break;
			case 2:
				exprNew=temp[0]+"-("+temp[1]+")";
				break;
			default:
				throw new Exception("Syntax Error");
			}
			if (funcType==0){
			   calc = new ExpressionBuilder(exprNew).withVariableNames("x", "y").build();
			}else{
				calc = new ExpressionBuilder(exprNew).withVariableNames("r", "t").build();
			}
		} catch (Exception e) {
			LOG.error(e);
			return null;
		}
		ElementCollection curve2D = new ElementCollection();
		double dx,dy;
		dx=(maxX-minX)/xGrids;
		dy=(maxY-minY)/yGrids;
		for (double x = minX; x < maxX-dx; x +=dx) {
			for (double y = minY; y < maxY-dy; y += dy) {
					Vector3D[] vertices=new Vector3D[4];
					double[] values= new double[4];
					double x1,y1;
					x1=x;y1=y;
					vertices[0]=new Vector3D(x1,y1,0);
					values[0]= f(x1,y1);
					x1=x;y1=y+dy;
					vertices[1]=new Vector3D(x1,y1,0);
					values[1]= f(x1,y1);
					x1=x+dx;y1=y+dy;
					vertices[2]=new Vector3D(x1,y1,0);
					values[2]= f(x1,y1);
					x1=x+dx;y1=y;
					vertices[3]=new Vector3D(x1,y1,0);
					values[3]= f(x1,y1);
				
                    GRID grid =new GRID();
                    grid.p=vertices;
                    grid.val=values;
                    Polygonise(grid,0,curve2D);
			}
		}
		element=curve2D;
		return (curve2D.elements.size() > 0) ? curve2D : null;
	}

	
	
	/*
	 * Given a grid cell and an isolevel, calculate the triangular facets
	 * required to represent the isosurface through the cell. Return the number
	 * of triangular facets, the array "triangles" will be loaded up with the
	 * vertices at most 5 triangular facets. 0 will be returned if the grid cell
	 * is either totally above of totally below the isolevel.
	 */
	public int Polygonise(GRID grid, double isolevel,
			ElementCollection lines) {
		int i, nlines;
		int squareindex;
		Vector3D[] vertlist = new Vector3D[4];
		// For any edge, if one vertex is inside of the surface and the other is outside of the surface
	//  then the edge intersects the surface
	// For each of the 4 vertices of the cube can be two possible states : either inside or outside of the surface
	// For any cube the are 2^8=256 possible sets of vertex states
	// This table lists the edges intersected by the surface for all 256 possible vertex states
	// There are 12 edges.  For each entry in the table, if edge #n is intersected, then bit #n is set to 1
		int[] edgeTable ={0x0,  0x9,  0x3,  0xa,  
				          0x6,  0xf, 0x5,  0xc, 
				          0xc, 0x5,  0xf, 0x6,
				          0xa, 0x3,  0x9,  0x0};
	
	//  For each of the possible vertex states listed in aiCubeEdgeFlags there is a specific triangulation
	//  of the edge intersection points.  a2iTriangleConnectionTable lists all of them in the form of
	//  0-5 edge triples with the list terminated by the invalid value -1.
	//  For example: a2iTriangleConnectionTable[3] list the 2 triangles formed when corner[0] 
	//  and corner[1] are inside of the surface, but the rest of the cube is not.
	//
		int[][] lineTable = {
				{ -1, -1, -1, -1 },
				{  3,  0, -1, -1 },
				{  0,  1, -1, -1 },
				{  3,  1, -1, -1 },
				{  2,  1, -1, -1 },
				{  3,  2,  1,  0 },
				{  2,  0, -1, -1 },
				{  3,  2, -1, -1 },
				{  3,  2, -1, -1 },
				{  2,  0, -1, -1 },
				{  3,  2,  1,  0 },
				{  2,  1, -1, -1 },
				{  3,  1, -1, -1 },
				{  1,  0, -1, -1 },
				{  3,  0, -1, -1 },
				{ -1, -1, -1, -1 }    };
				

		/*
		 * Determine the index into the edge table which tells us which vertices
		 * are inside of the surface
		 */
		squareindex = 0;
		if (grid.val[0] < isolevel)
			squareindex |= 1;
		if (grid.val[1] < isolevel)
			squareindex |= 2;
		if (grid.val[2] < isolevel)
			squareindex |= 4;
		if (grid.val[3] < isolevel)
			squareindex |= 8;
		
		/* Cube is entirely in/out of the surface */
		if (edgeTable[squareindex] == 0)
			return (0);

		/* Find the vertices where the surface intersects the cube */
		if ((edgeTable[squareindex] & 1) != 0)
			vertlist[0] = VertexInterp(isolevel, grid.p[0], grid.p[1],
					grid.val[0], grid.val[1]);
		if ((edgeTable[squareindex] & 2) != 0)
			vertlist[1] = VertexInterp(isolevel, grid.p[1], grid.p[2],
					grid.val[1], grid.val[2]);
		if ((edgeTable[squareindex] & 4) != 0)
			vertlist[2] = VertexInterp(isolevel, grid.p[2], grid.p[3],
					grid.val[2], grid.val[3]);
		if ((edgeTable[squareindex] & 8) != 0)
			vertlist[3] = VertexInterp(isolevel, grid.p[3], grid.p[0],
					grid.val[3], grid.val[0]);
		

		/* Create the curvesegments */
		nlines = 0;
		for (i = 0; (lineTable[squareindex][i] != -1); i += 2) {
			ElementCurve e = new ElementCurve(vertlist[lineTable[squareindex][i]],vertlist[lineTable[squareindex][i + 1]]);
			e.setFillColor(getFillColor());
		    e.setCurveWidth(getCurveWidth());
		    e.setLineColor(getLineColor());
		    e.setSpilttable(isSplittable());
		    if(T!=null)e.transform(T);
			lines.addElement(e);
			if (i>=2)break;
			nlines++;
		}

		return (nlines);
	}

	/*
	 * Linearly interpolate the position where an isosurface cuts an edge
	 * between two vertices, each with their own scalar value
	 */
	Vector3D VertexInterp(double isolevel, Vector3D p1, Vector3D p2,
			double valp1, double valp2)
	{
		double mu;
		Vector3D p;

		if (Math.abs(isolevel - valp1) < 0.00001)
			return (p1);
		if (Math.abs(isolevel - valp2) < 0.00001)
			return (p2);
		if (Math.abs(valp1 - valp2) < 0.00001)
			return (p1);
		mu = (isolevel - valp1) / (valp2 - valp1);
		p = Vector3D.intepolate(p1, p2, mu);
		p.setX(p1.getX() + mu * (p2.getX() - p1.getX()));
		p.setY(p1.getY() + mu * (p2.getY() - p1.getY()));
		p.setZ(p1.getZ() + mu * (p2.getZ() - p1.getZ()));

		return (p);
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


