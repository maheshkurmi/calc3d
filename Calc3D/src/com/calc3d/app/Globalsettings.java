package com.calc3d.app;

import java.awt.Color;

import com.calc3d.geometry3d.Box3D;

public class Globalsettings {
public static double minX=-5;
public static double maxX=5;
public static double minY=-5;
public static double maxY=5;
public static double minZ=-5;
public static double maxZ=5;

public static Box3D mappedClipBox=new Box3D(-1,1,-1,1,-1,1);
/*
 * Gui Settings
 */
public static String lookandFeel;
public static boolean fileToolbarVisible=true;
public static boolean objectToolbarVisible=true;
public static boolean statusbarVisible=true;
public static boolean tipofDayEnabled=true;
public static boolean splashScreenEnabled=true;

/*
 * Renderer Settings
 */
public static  boolean antiAliasingEnabled=false;
public static  boolean perspectiveEnabled=true;
public static  boolean steroscopyEnabled=false;
public static  int steroscopicMode=2;
public static  boolean fogEnabled=true;
public static  boolean light1Enabled=true;
public static  boolean light2Enabled=false;
public static  boolean light3Enabled=false;
public static Color backgroundColor=Color.black;//new Color(230,230,240);//new Color(0,35,7);

/*
 * bOX sETTINGS
 */
public static boolean boxVisible=true;
public static boolean gridsVisible=false;
public static boolean planesVisible=false;
public static boolean labelsVisible=true;
public static boolean rulersVisible=true;
public static boolean ticksVisible=true;
public static boolean showMajorGrids,showMinorGrids;
public static double divisions=5;
public static double subdivisions=3;

/*
 * Axis Settings
 */
public static boolean xAxisVisible=true;
public static boolean yAxisVisible=true;
public static boolean zAxisVisible=true;
public static boolean xyGridVisible=false;
public static Box3D axesBox=new Box3D(-5,5,-5,5,-5,5);
public static int axisTicks=5;
public static int axisWidth=2;
public static Color axisColor=Color.white;//new Color(132,145,135).brighter();
public static Color planeColor=Color.white;//new Color(190,240,220);
public static Color gridColor=planeColor.darker();

public static double fov=50;

/**
 * maps clipcoordiantes to value between minx and maxX
 */
public static double mapCliptoX(double x){
	return minX+(maxX-minX)*(x-mappedClipBox.getMinX())/(mappedClipBox.getWidth());
}

/**
 * maps clipcoordiantes to value between minx and maxX
 */
public static double mapCliptoY(double y){
	return minY+(maxY-minY)*(y-mappedClipBox.getMinY())/(mappedClipBox.getHeight());
}

/**
 * maps clipcoordiantes to value between minx and maxX
 */
public static double mapCliptoZ(double z){
	return minZ+(maxZ-minZ)*(z-mappedClipBox.getMinZ())/(mappedClipBox.getDepth());
}
/**
 * maps x (value between -1 and 1) to value between minx and maxX
 */
public static double mapX(double x){
	return minX+(maxX-minX)*(x-mappedClipBox.getMinX())/(mappedClipBox.getWidth());
}
/**
 * maps y (value between -1 and 1) to value between minx and maxX
 */
public static double mapY(double y){
	return minY+(maxY-minY)*(y-mappedClipBox.getMinY())/(mappedClipBox.getHeight());
}
/**
 * maps z (value between -1 and 1) to value between minx and maxX
 */
public static double mapZ(double z){
	return minZ+(maxZ-minZ)*(z-mappedClipBox.getMinZ())/(mappedClipBox.getDepth());
}

/**
 * maps z(value between minz to max z) to value between -1 to 1
 */
public static double inverseMapZ(double z){
	return mappedClipBox.getMinZ()+mappedClipBox.getDepth()*(z-minZ)/(maxZ-minZ);
}
/**
 * maps y(value between minz to max z) to value between -1 to 1
 */
public static double inverseMapY(double y){
	return mappedClipBox.getMinY()+mappedClipBox.getHeight()*(y-minY)/(maxY-minY);
}
/**
 * maps x(value between minz to max z) to value between -1 to 1
 */
public static double inverseMapX(double x){
	return mappedClipBox.getMinX()+mappedClipBox.getWidth()*(x-minX)/(maxX-minX);
}


public static void recalculateClip(){
	double max=1;
	max=Math.max(Math.abs(minX), Math.abs(maxX));
	max=Math.max(max, Math.abs(minY));
	max=Math.max(max, Math.abs(maxY));
	max=Math.max(max, Math.abs(minZ));
	max=Math.max(max, Math.abs(maxZ));
	mappedClipBox=new Box3D(minX/max,maxX/max,minY/max,maxY/max,minZ/max,maxZ/max);
}

public static void saveSettings(Preferences preferences){
	minX=preferences.getClipBox().getMinX();
	maxX=preferences.getClipBox().getMaxX();
	minY=preferences.getClipBox().getMinY();
	maxY=preferences.getClipBox().getMaxY();
	minZ=preferences.getClipBox().getMinZ();
	maxZ=preferences.getClipBox().getMaxZ();

	divisions=preferences.getDivisions();
	subdivisions=preferences.getSubDivisions();
	showMajorGrids=preferences.isGridsVisible();
	showMinorGrids=preferences.isGridsVisible();
	backgroundColor=preferences.getBackColor();

	boxVisible=preferences.isBoxVisible();
	gridsVisible=preferences.isGridsVisible();
	planesVisible=preferences.isPlanesVisible();
	labelsVisible=preferences.isLabelsVisible();
	rulersVisible=preferences.isTicksVisible();
	ticksVisible=preferences.isTicksVisible();
	xAxisVisible=preferences.isxAxisVisible();
	yAxisVisible=preferences.isyAxisVisible();
	zAxisVisible=preferences.iszAxisVisible();
	xyGridVisible=preferences.isXyGridVisible();
	axesBox=preferences.getAxesBox();
	axisTicks=preferences.getAxisTicks();
	axisWidth=preferences.getAxisWidth();
	axisColor=preferences.getAxisColor();
	
	lookandFeel=preferences.getLookandFeel();
	fileToolbarVisible=preferences.isFileToolbarVisible();
	objectToolbarVisible=preferences.isObjectToolbarVisible();
	statusbarVisible=preferences.isStatusbarVisible();
	tipofDayEnabled=preferences.isTipofDayEnabled();
	splashScreenEnabled=preferences.isSplashScreenEnabled();
	
	antiAliasingEnabled=preferences.isAntiAliasingEnabled();
	perspectiveEnabled=preferences.isPerspectiveEnabled();
	steroscopyEnabled=preferences.isSteroscopyEnabled();
	steroscopicMode=preferences.getSteroscopicMode();
	backgroundColor=preferences.getBackColor();
	fogEnabled=preferences.isFogEnabled();
	light1Enabled=preferences.isLight1Enabled();
	light2Enabled=preferences.isLight2Enabled();
	light3Enabled=preferences.isLight3Enabled();
	recalculateClip();
}

public static Preferences getSettings(){
	Preferences preferences=new Preferences();
	preferences.setLookandFeel(lookandFeel);

	preferences.setFileToolbarVisible(fileToolbarVisible);
	preferences.setObjectToolbarVisible(objectToolbarVisible);
	preferences.setStatusbarVisible(statusbarVisible);
    preferences.setTipofDayEnabled(tipofDayEnabled);
    preferences.setSplashScreenEnabled(splashScreenEnabled);
    
    preferences.setClipBox(new Box3D(minX,maxX,minY,maxY,minZ,maxZ));
    
    preferences.setAntiAliasingEnabled(antiAliasingEnabled);
    preferences.setPerspectiveEnabled(perspectiveEnabled);
    preferences.setSteroscopyEnabled(steroscopyEnabled);
    preferences.setSteroscopicMode(steroscopicMode);
  
    preferences.setFogEnabled(fogEnabled); 
    preferences.setLight1Enabled(light1Enabled); 
    preferences.setLight2Enabled(light2Enabled); 
    preferences.setLight3Enabled(light3Enabled); 
      
    preferences.setxAxisVisible(xAxisVisible);
    preferences.setyAxisVisible(yAxisVisible);
    preferences.setzAxisVisible(zAxisVisible);
            
    preferences.setAxesBox(axesBox);
    preferences.setAxisColor(axisColor);
    preferences.setAxisWidth(axisWidth);
    preferences.setAxisTicks(axisTicks);
    preferences.setBackColor(backgroundColor);
	
    preferences.setGridsVisible(gridsVisible);
    preferences.setLabelsVisible(labelsVisible);
    preferences.setBoxVisible(boxVisible);
    preferences.setPlanesVisible(planesVisible);
    preferences.setTicksVisible(ticksVisible);
    preferences.setDivisions((int)divisions);
    preferences.setSubDivisions((int)subdivisions);
   
    return preferences;
}

/**
 * @return the clipBox
 */
public static Box3D getClipBox() {
	return new Box3D(minX,maxX,minY,maxY,minZ,maxZ);
}


}
