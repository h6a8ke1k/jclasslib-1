/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.browser.detail.attributes;

import org.gjt.jclasslib.browser.BrowserServices;
import org.gjt.jclasslib.browser.ConstantPoolHyperlinkListener;
import org.gjt.jclasslib.structures.AttributeInfo;
import org.gjt.jclasslib.structures.attributes.BootstrapMethodEntry;
import org.gjt.jclasslib.structures.attributes.BootstrapMethodsAttribute;

/**
    Detail pane showing an <tt>BootstrapMethods</tt> attribute.

    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.5 $ $Date: 2003-08-18 08:18:11 $
*/
public class BootstrapMethodsAttributeDetailPane extends AbstractAttributeListDetailPane {

    /**
        Constructor.
        @param services the associated browser services.
     */
    public BootstrapMethodsAttributeDetailPane(BrowserServices services) {
        super(services);
    }
    
    protected AbstractAttributeTableModel createTableModel(AttributeInfo attribute) {
        return new BootstrapMethodsTableModel(attribute);
    }

    protected float getRowHeightFactor() {
        return 2f;
    }

    private class BootstrapMethodsTableModel extends AbstractAttributeTableModel {

        private static final int METHOD_REF_COLUMN_INDEX = BASE_COLUMN_COUNT;
        private static final int COLUMN_WIDTH = 160;

        private BootstrapMethodEntry[] methods;
        private final int columnCount;
        
        private BootstrapMethodsTableModel(AttributeInfo attribute) {
            super(attribute);
            BootstrapMethodsAttribute bootstrapMethods = (BootstrapMethodsAttribute) attribute;
            methods = bootstrapMethods.getBootstrapMethods();
            columnCount = BASE_COLUMN_COUNT + bootstrapMethods.getMaxArguments();
        }

        public int getColumnWidth(int column) {
            return COLUMN_WIDTH;
        }
        
        public void link(int row, int column) {
            
            int constantPoolIndex;
            switch (column) {
                case METHOD_REF_COLUMN_INDEX:
                    constantPoolIndex = methods[row].getBootstrapMethodRef();
                    break;
                default:
                    int index = column - 1;
                    constantPoolIndex = methods[row].getBootstrapArguments()[index];
                    break;
            }
            ConstantPoolHyperlinkListener.link(services, constantPoolIndex);
        }
        
        public int getRowCount() {
            return methods.length;
        }
        
        public int getColumnCount() {
            return columnCount;
        }
        
        protected String doGetColumnName(int column) {
            switch (column) {
                case METHOD_REF_COLUMN_INDEX:
                   return "method_ref";
                default:
                   return "arg#"+(column-1);
            }
        }

        protected Class doGetColumnClass(int column) {
           return Link.class;
        }
        
        protected Object doGetValueAt(int row, int column) {

           BootstrapMethodEntry method = methods[row];
            switch (column) {
                case METHOD_REF_COLUMN_INDEX:
                    return createCommentLink(method.getBootstrapMethodRef());
                default:
                   Object value = "NoArg";
                   int index = column - 1;
                   if(index < method.getArgumentCount()) {
                     int argRef = method.getBootstrapArguments()[index];
                      value = createCommentLink(argRef);
                   } else {

                   }
                   return value;
            }
        }
    }
}

