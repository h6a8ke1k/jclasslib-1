/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.structures.constants;

import org.gjt.jclasslib.structures.CPInfo;
import org.gjt.jclasslib.structures.InvalidByteCodeException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
    Describes a <tt>CONSTANT_MethodHandle_info</tt> constant pool data structure.
    CONSTANT_MethodHandle_info {
        u1 tag;
        u1 reference_kind;
        u2 reference_index;
    }
    @author <a href="mailto:jclasslib@ej-technologies.com">Hannes Kegel</a>
    @version $Revision: 1.1 $ $Date: 2010-07-26 14:00:09 $
*/
public class ConstantMethodHandleInfo extends CPInfo {

    /** Length of the constant pool data structure in bytes. */
    public static final int SIZE = 3;

    public static final int TYPE_GET_FIELD = 1;
    public static final int TYPE_GET_STATIC = 2;
    public static final int TYPE_PUT_FIELD = 3;
    public static final int TYPE_PUT_STATIC = 4;
    public static final int TYPE_INVOKE_VIRTUAL = 5;
    public static final int TYPE_INVOKE_STATIC = 6;
    public static final int TYPE_INVOKE_SPECIAL = 7;
    public static final int TYPE_NEW_INVOKE_SPECIAL = 8;
    public static final int TYPE_INVOKE_INTERFACE = 9;

    private int referenceIndex;
    private int type;


    public byte getTag() {
        return CONSTANT_METHOD_HANDLE;
    }

    public String getTagVerbose() {
        return CONSTANT_METHOD_HANDLE_VERBOSE;
    }

    public String getVerbose() throws InvalidByteCodeException {
        return getName();
    }

    /**
        Get the index of the constant pool entry containing the reference.
        @return the index
     */
    public int getReferenceIndex() {
        return referenceIndex;
    }

    /**
        Set the index of the constant pool entry containing the reference.
        @param referenceIndex the index
     */
    public void setReferenceIndex(int referenceIndex) {
        this.referenceIndex = referenceIndex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

   /**
    * Resolve the name index in the constant_pool table that the reference_index points to. From the SE7 VM spec:

    If the value of the reference_kind item is 1 (REF_getField), 2 (REF_getStatic), 3 (REF_putField), or
    4 (REF_putStatic), then the constant_pool entry at that index must be a CONSTANT_Fieldref_info (§4.4.2)
    structure representing a field for which a method handle is to be created.

    If the value of the reference_kind item is 5 (REF_invokeVirtual), 6 (REF_invokeStatic), 7 (REF_invokeSpecial),
    or 8 (REF_newInvokeSpecial), then the constant_pool entry at that index must be a CONSTANT_Methodref_info structure
    (§4.4.2) representing a class's method or constructor (§2.9) for which a method handle is to be created.

    If the value of the reference_kind item is 9 (REF_invokeInterface), then the constant_pool entry at that index must
    be a CONSTANT_InterfaceMethodref_info (§4.4.2) structure representing an interface's method for which a method
    handle is to be created.

    If the value of the reference_kind item is 5 (REF_invokeVirtual), 6 (REF_invokeStatic), 7 (REF_invokeSpecial),
    or 9 (REF_invokeInterface), the name of the method represented by a CONSTANT_Methodref_info structure must not
    be <init> or <clinit>.

    If the value is 8 (REF_newInvokeSpecial), the name of the method represented by a CONSTANT_Methodref_info structure
    must be <init>.
    */
    public int resolveReferenceIndexNameIndex() {
       MethodHandleBytecode kind = getBytecodeType();
       CPInfo refIndexInfo = getClassFile().getConstantPool()[referenceIndex];
       int nameIndex = -1;
       switch (kind) {
          case REF_getField:
          case REF_getStatic:
          case REF_putField:
          case REF_putStatic:
             ConstantFieldrefInfo fieldrefInfo = (ConstantFieldrefInfo) refIndexInfo;
             nameIndex = fieldrefInfo.getNameAndTypeIndex();
             break;
          case REF_invokeVirtual:
          case REF_invokeStatic:
          case REF_invokeSpecial:
          case REF_newInvokeSpecial:
          case REF_invokeInterface:
             ConstantMethodrefInfo methodrefInfo = (ConstantMethodrefInfo) refIndexInfo;
             nameIndex = methodrefInfo.getNameAndTypeIndex();
             break;
       }
       // Convert Name and type to name index
       if(nameIndex >= 0) {
          ConstantNameAndTypeInfo nameTypeInfo = (ConstantNameAndTypeInfo) getClassFile().getConstantPool()[nameIndex];
          nameIndex = nameTypeInfo.getNameIndex();
       }
       return nameIndex;
    }

    public MethodHandleBytecode getBytecodeType() {
       return MethodHandleBytecode.valueOf(type);
    }

    /**
        Get the descriptor.
        @return the descriptor
        @throws org.gjt.jclasslib.structures.InvalidByteCodeException if the byte code is invalid
     */
    public String getName() throws InvalidByteCodeException {
        return classFile.getConstantPoolEntryName(referenceIndex);
    }

    public void read(DataInput in)
        throws InvalidByteCodeException, IOException {

        type = in.readByte();
        referenceIndex = in.readUnsignedShort();
        if (debug) debug("read ");
    }

    public void write(DataOutput out)
        throws InvalidByteCodeException, IOException {
        
        out.writeByte(CONSTANT_METHOD_HANDLE);
        out.write(type);
        out.writeShort(referenceIndex);
        if (debug) debug("wrote ");
    }
    
    public boolean equals(Object object) {
        if (!(object instanceof ConstantMethodHandleInfo)) {
            return false;
        }
        ConstantMethodHandleInfo constantMethodHandleInfo = (ConstantMethodHandleInfo)object;
        return super.equals(object) && constantMethodHandleInfo.referenceIndex == referenceIndex && constantMethodHandleInfo.type == type;
    }

    public int hashCode() {
        return super.hashCode() ^ referenceIndex;
    }
    
    protected void debug(String message) {
        super.debug(message + getTagVerbose() + " with reference_index " + referenceIndex + " and type " + type);
    }
    
}
