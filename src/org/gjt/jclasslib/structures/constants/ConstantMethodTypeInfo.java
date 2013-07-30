/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.structures.constants;

import org.gjt.jclasslib.structures.InvalidByteCodeException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
    Describes a <tt>CONSTANT_MethodType_info</tt> constant pool data structure.
 
    @author <a href="mailto:jclasslib@ej-technologies.com">Hannes Kegel</a>
    @version $Revision: 1.1 $ $Date: 2010-07-26 14:00:09 $
*/
public class ConstantMethodTypeInfo extends ConstantStringInfo {

    /** Length of the constant pool data structure in bytes. */
    public static final int SIZE = 2;

    @Override
    public byte getTag() {
        return CONSTANT_METHOD_TYPE;
    }

    public String getTagVerbose() {
        return CONSTANT_METHOD_TYPE_VERBOSE;
    }

    public String getVerbose() throws InvalidByteCodeException {
        return getName();
    }

    /**
        Get the index of the constant pool entry containing the descriptor of the method.
        @return the index
     */
    public int getDescriptorIndex() {
        return super.getStringIndex();
    }

    /**
        Set the index of the constant pool entry containing the descriptor of the method.
        @param descriptorIndex the index
     */
    public void setDescriptorIndex(int descriptorIndex) {
        super.setStringIndex(descriptorIndex);
    }

    /**
        Get the descriptor.
        @return the descriptor
        @throws org.gjt.jclasslib.structures.InvalidByteCodeException if the byte code is invalid
     */
    public String getName() throws InvalidByteCodeException {
        return classFile.getConstantPoolUtf8Entry(getDescriptorIndex()).getString();
    }

    public void read(DataInput in)
        throws InvalidByteCodeException, IOException {
            
        super.read(in);
        if (debug) debug("read ");
    }

    public void write(DataOutput out)
        throws InvalidByteCodeException, IOException {
        
        out.writeByte(CONSTANT_METHOD_TYPE);
        out.writeShort(getDescriptorIndex());
        if (debug) debug("wrote ");
    }

    public boolean equals(Object object) {
        if (!(object instanceof ConstantMethodTypeInfo)) {
            return false;
        }
        ConstantMethodTypeInfo constantMethodTypeInfo = (ConstantMethodTypeInfo)object;
        return super.equals(object) && constantMethodTypeInfo.getDescriptorIndex() == getDescriptorIndex();
    }

    public int hashCode() {
        return super.hashCode() ^ getDescriptorIndex();
    }
    
    protected void debug(String message) {
        super.debug(message + getTagVerbose() + " with descriptor_index " + getDescriptorIndex());
    }
    
}
