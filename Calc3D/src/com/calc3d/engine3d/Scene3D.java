package com.calc3d.engine3d;

import java.util.ArrayList;
import java.util.Collection;

import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.Object3D;

public class Scene3D {
	  public Collection<Object3D<Element>> object3Ds = new ArrayList<Object3D<Element>>();
	  
	  public void addObject3D(Object3D<Element> obj) {
	        this.object3Ds.add(obj);
	    }
	
}
