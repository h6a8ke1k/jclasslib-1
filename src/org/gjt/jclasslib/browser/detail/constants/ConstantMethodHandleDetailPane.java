/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.browser.detail.constants;

import org.gjt.jclasslib.browser.BrowserServices;
import org.gjt.jclasslib.structures.InvalidByteCodeException;
import org.gjt.jclasslib.structures.constants.ConstantMethodHandleInfo;
import org.gjt.jclasslib.structures.constants.MethodHandleBytecode;
import org.gjt.jclasslib.util.ExtendedJLabel;

import javax.swing.tree.TreePath;

/**
    Detail pane showing a <tt>CONSTANT_MethodHandle</tt> constant pool entry.

    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @author <a href="mailto:sstark@redhat.com">Scott Stark</a>
    @version $Revision: 1.6 $ $Date: 2004-02-10 16:06:56 $
*/
public class ConstantMethodHandleDetailPane extends AbstractConstantInfoDetailPane {

    // Visual components

    private ExtendedJLabel lblKind;
    private ExtendedJLabel lblKindVerbose;
    private ExtendedJLabel lblReference;
    private ExtendedJLabel lblReferenceVerbose;

    private ClassElementOpener classElementOpener;

    /**
        Constructor.
        @param services the associated browser services.
     */
    public ConstantMethodHandleDetailPane(BrowserServices services) {
        super(services);
    }
    
    protected void setupLabels() {
        
        addDetailPaneEntry(normalLabel("Reference kind:"),
           lblKind = linkLabel(),
           lblKindVerbose = highlightLabel());

        addDetailPaneEntry(normalLabel("Reference index:"),
                           lblReference = linkLabel(),
                           lblReferenceVerbose = highlightLabel());
    }

    protected int addSpecial(int gridy) {

        classElementOpener = new ClassElementOpener(this);
        if (getBrowserServices().canOpenClassFiles()) {
            return classElementOpener.addSpecial(this, gridy);
        } else {
            return 0;
        }
    }

    public void show(TreePath treePath) {
        
        int constantPoolIndex = constantPoolIndex(treePath);

        try {
            ConstantMethodHandleInfo entry = (ConstantMethodHandleInfo)services.getClassFile().getConstantPoolEntry(constantPoolIndex, ConstantMethodHandleInfo.class);
            classElementOpener.setCPInfo(entry);

            MethodHandleBytecode bytecode = MethodHandleBytecode.valueOf(entry.getType());
            lblKind.setText(bytecode.name());
            lblKindVerbose.setText(bytecode.getInterpretation());

            constantPoolHyperlink(lblReference,
                                  lblReferenceVerbose,
                                  entry.getReferenceIndex());
        } catch (InvalidByteCodeException ex) {
           lblKindVerbose.setText(MESSAGE_INVALID_CONSTANT_POOL_ENTRY);
        }
        
        super.show(treePath);
    }
    
}


