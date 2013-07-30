/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.browser.detail.constants;

import org.gjt.jclasslib.browser.BrowserServices;
import org.gjt.jclasslib.structures.InvalidByteCodeException;
import org.gjt.jclasslib.structures.constants.ConstantInvokeDynamicInfo;
import org.gjt.jclasslib.util.ExtendedJLabel;

import javax.swing.tree.TreePath;

/**
 * Detail pane showing a <tt>CONSTANT_InvokeDynamic_info</tt> constant pool entry.
 * CONSTANT_InvokeDynamic_info {
     u1 tag;
     u2 bootstrap_method_attr_index;
     u2 name_and_type_index;
 }
 * @author Scott Stark (sstark@redhat.com)
 * @version $Revision: 1.1 $ $Date: 2003-08-18 08:14:43 $
 */
public class ConstantInvokeDynamicInfoDetailPane extends AbstractConstantInfoDetailPane {
       // Visual components

    private ExtendedJLabel lblBootstrapMethodIndex;
    private ExtendedJLabel lblBootstrapMethodIndexVerbose;
    private ExtendedJLabel lblNameType;
    private ExtendedJLabel lblNameTypeVerbose;

    private ClassElementOpener classElementOpener;

    /**
        Constructor.
        @param services the associated browser services.
     */
    public ConstantInvokeDynamicInfoDetailPane(BrowserServices services) {
        super(services);
    }

   protected int addSpecial(int gridy) {

       classElementOpener = new ClassElementOpener(this);
       if (getBrowserServices().canOpenClassFiles()) {
           return classElementOpener.addSpecial(this, gridy);
       } else {
           return 0;
       }
   }

   protected void setupLabels() {


       addDetailPaneEntry(normalLabel("Bootstrap method index:"),
                          lblBootstrapMethodIndex = linkLabel(),
                          lblBootstrapMethodIndexVerbose = highlightLabel());

       addDetailPaneEntry(normalLabel("Name and type:"),
         lblNameType = linkLabel(),
         lblNameTypeVerbose = highlightLabel());
   }

   public void show(TreePath treePath) {

       int constantPoolIndex = constantPoolIndex(treePath);

       try {
           ConstantInvokeDynamicInfo entry = (ConstantInvokeDynamicInfo)services.getClassFile().getConstantPoolEntry(constantPoolIndex, ConstantInvokeDynamicInfo.class);
           classElementOpener.setCPInfo(entry);

          bootstrapMethodHyperlink(lblBootstrapMethodIndex,
                                 lblBootstrapMethodIndexVerbose,
                                 entry.getBootstrapMethodAttributeIndex());
          constantPoolHyperlink(lblNameType,
                                 lblNameTypeVerbose,
                                 entry.getNameAndTypeIndex());
       } catch (InvalidByteCodeException ex) {
          lblBootstrapMethodIndexVerbose.setText(MESSAGE_INVALID_CONSTANT_POOL_ENTRY);
       }

       super.show(treePath);
   }
}
