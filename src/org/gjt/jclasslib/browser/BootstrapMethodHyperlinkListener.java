/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.browser;

import org.gjt.jclasslib.structures.attributes.BootstrapMethodsAttribute;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
    Listens for mouse clicks and manages linking into the ClassFile BootstrapAttribute list.
 
    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.6 $ $Date: 2003-08-18 08:02:07 $
*/
public class BootstrapMethodHyperlinkListener extends MouseAdapter {

    private BrowserServices services;
    private int methodIndex;

    /**
        Constructor.
        @param services the browser services
        @param methodIndex the index of the constant pool to lonk to.
     */
    public BootstrapMethodHyperlinkListener(BrowserServices services, int methodIndex) {
        
        this.services = services;
        this.methodIndex = methodIndex;
    }
    
    public void mouseClicked(MouseEvent event) {
        link(services, methodIndex);
    }

    /**
        Link to a specific constant pool entry.
        @param services browser services
        @param methodIndex the index of the constant pool entry
     */
    public static void link(BrowserServices services, int methodIndex) {
        
        if (methodIndex < 0) {
            return;
        }
        
        JTree tree = services.getBrowserComponent().getTreePane().getTree();
        TreePath newPath = linkPath(services, methodIndex);
        tree.setSelectionPath(newPath);
        tree.scrollPathToVisible(newPath);
    }
    
    private static TreePath linkPath(BrowserServices services, int methodIndex) {
        
        TreePath attributesPath = services.getBrowserComponent().getTreePane().getPathForCategory(BrowserTreeNode.NODE_ATTRIBUTE);
        BrowserTreeNode attributesNode = (BrowserTreeNode)attributesPath.getLastPathComponent();
        BrowserTreeNode bootstrapMethodsNode = null;
        // Find the BootstrapMethods attribute node
        for(int n = 0; n < attributesNode.getChildCount(); n ++) {
           BrowserTreeNode child = (BrowserTreeNode) attributesNode.getChildAt(n);
           String name = child.getUserObject().toString();
           if(name.contains(BootstrapMethodsAttribute.ATTRIBUTE_NAME)) {
              bootstrapMethodsNode = child;
              break;
           }
        }
        attributesPath = attributesPath.pathByAddingChild(bootstrapMethodsNode);
        TreeNode targetNode = bootstrapMethodsNode.getChildAt(methodIndex);
        TreePath linkPath = attributesPath.pathByAddingChild(targetNode);
        
        return linkPath;
    }
    
}

