/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.browser.detail.attributes;

import jsyntaxpane.JavaSyntaxKit;
import jsyntaxpane.actions.ActionUtils;
import org.gjt.jclasslib.browser.AbstractDetailPane;
import org.gjt.jclasslib.browser.BrowserServices;
import org.gjt.jclasslib.browser.detail.attributes.code.ByteCodeDetailPane;
import org.gjt.jclasslib.browser.detail.attributes.code.ExceptionTableDetailPane;
import org.gjt.jclasslib.browser.detail.attributes.code.MiscDetailPane;
import org.gjt.jclasslib.structures.attributes.CodeAttribute;
import org.gjt.jclasslib.structures.attributes.LineNumberTableAttribute;
import org.gjt.jclasslib.structures.attributes.SourceFileAttribute;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.TreePath;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
    Detail pane showing a <tt>Code</tt> attribute. Contains three other detail
    panes in its tabbed pane.

    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.5 $ $Date: 2003-08-18 08:19:03 $
*/
public class CodeAttributeDetailPane extends AbstractDetailPane {

    private JTabbedPane tabbedPane;
    
    private ExceptionTableDetailPane exceptionTablePane;
    private ByteCodeDetailPane byteCodePane;
    private MiscDetailPane miscPane;
    private JEditorPane sourcePane;
    
    /**
        Constructor.
        @param services the associated browser services.
     */
    public CodeAttributeDetailPane(BrowserServices services) {
        super(services);
    }

    protected void setupComponent() {
        setLayout(new BorderLayout());
        
        add(buildTabbedPane(), BorderLayout.CENTER);
    }
    
    /**
        Get the <tt>ByteCodeDetailPane</tt> showing the code
        of this <tt>Code</tt> attribute.
        @return the <tt>ByteCodeDetailPane</tt>
     */
    public ByteCodeDetailPane getCodeAttributeByteCodeDetailPane() {
        return byteCodePane;
    }
    
    /**
        Select the <tt>ByteCodeDetailPane</tt> showing the code
        of this <tt>Code</tt> attribute.
     */
    public void selectByteCodeDetailPane() {
        tabbedPane.setSelectedIndex(0);
    }
    
    private JTabbedPane buildTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Bytecode", buildByteCodePane());
        tabbedPane.addTab("Exception table", buildExceptionTablePane());
        tabbedPane.addTab("Misc", buildMiscPane());
        tabbedPane.addTab("Source", buildSourcePane());
        
        return tabbedPane;
    }

    private JPanel buildByteCodePane() {
        byteCodePane = new ByteCodeDetailPane(services);
        return byteCodePane;
    }

    private JPanel buildExceptionTablePane() {
        exceptionTablePane = new ExceptionTableDetailPane(services);
        return exceptionTablePane;
    }

    private JPanel buildMiscPane() {
        miscPane = new MiscDetailPane(services);
        return miscPane;
    }
    private JPanel buildSourcePane() {
       SourceFileAttribute source = (SourceFileAttribute) services.getClassFile().findAttribute(SourceFileAttribute.class);
       String text = source.getSourceText();
       int rows = source.getLineCount();
       JPanel frame = new JPanel(new BorderLayout());
       sourcePane = new JEditorPane();
       JScrollPane scrollPane = new JScrollPane (sourcePane);
     	 scrollPane.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     	 scrollPane.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
       sourcePane.setEditorKit(new JavaSyntaxKit());
       sourcePane.setText(text);
       sourcePane.setEditable(true);
       Dimension size = new Dimension(50 * 12, 1024);
       sourcePane.setPreferredSize(size);
       sourcePane.setMinimumSize(size);
       frame.add(scrollPane, BorderLayout.CENTER);
       scrollPane.setWheelScrollingEnabled(true);
       frame.validate();
       return frame;
    }
    
    public void show(TreePath treePath) {

        exceptionTablePane.show(treePath);
        byteCodePane.show(treePath);
        miscPane.show(treePath);
        CodeAttribute code = byteCodePane.getCodeAttribute();
        LineNumberTableAttribute lineNumbers = code.getLineNumbers();
        if(lineNumbers != null) {
           int firstLine = byteCodePane.getFirstLineNumber();
           int pos = ActionUtils.getDocumentPosition(sourcePane, firstLine, 0);
           if(pos >= 0)
            sourcePane.setCaretPosition(pos);
        }
    }
    
}

