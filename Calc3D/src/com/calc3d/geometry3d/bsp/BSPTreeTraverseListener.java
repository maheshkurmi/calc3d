package com.calc3d.geometry3d.bsp;
import java.util.List;

import com.calc3d.geometry3d.Element;
/**
    A BSPTreeTraverseListener is an interface for a
    BSPTreeTraverser to signal visited polygons.
*/
public interface BSPTreeTraverseListener {

    /**
        Visits a BSP polygon. Called by a BSPTreeTraverer.
        If this method returns true, the BSPTreeTraverer will
        stop the current traversal. Otherwise, the BSPTreeTraverer
        will continue if there are polygons in the tree that
        have not yet been traversed.
    */
    public void visitElements(List<Element> elements);
}