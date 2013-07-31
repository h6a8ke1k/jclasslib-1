/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.structures.attributes;

import org.gjt.jclasslib.structures.AttributeInfo;
import org.gjt.jclasslib.structures.ClassFile;
import org.gjt.jclasslib.structures.InvalidByteCodeException;
import org.gjt.jclasslib.structures.constants.ConstantMethodHandleInfo;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * Entry from the BootstrapMethods_attribute.bootstrap_methods
 {
   u2 bootstrap_method_ref;
   u2 num_bootstrap_arguments;
   u2 bootstrap_arguments[num_bootstrap_arguments];
 }
 This is not a proper AttributeInfo object because the base read/write do not apply as the initial two bytes
 do not match:
 u2 attribute_name_index;
 u4 attribute_length;

 This is an AttributeInfo so that the tree view includes the BootstrapMethodEntry for use as a hyperlink target
 @see org.gjt.jclasslib.browser.BootstrapMethodHyperlinkListener

 @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.21">4.7.21. The BootstrapMethods attribute</a>
 * @author Scott Stark
 * @version $Revision:$
 */
public class BootstrapMethodEntry extends AttributeInfo {
   private int bootstrapMethodRef;
   private int[] bootstrapArguments;

   public static BootstrapMethodEntry create(DataInput in, ClassFile classFile)
           throws InvalidByteCodeException, IOException {

       BootstrapMethodEntry methodEntry = new BootstrapMethodEntry();
       methodEntry.setClassFile(classFile);
       methodEntry.read(in);

       return methodEntry;
   }

   public int getArgumentCount() {
      return bootstrapArguments.length;
   }
   public int[] getBootstrapArguments() {
      return bootstrapArguments;
   }

   public void setBootstrapArguments(int[] bootstrapArguments) {
      this.bootstrapArguments = bootstrapArguments;
   }

   public int getBootstrapMethodRef() {
      return bootstrapMethodRef;
   }

   public void setBootstrapMethodRef(int bootstrapMethodRef) {
      this.bootstrapMethodRef = bootstrapMethodRef;
   }

   @Override
   public void read(DataInput in) throws InvalidByteCodeException, IOException {
      bootstrapMethodRef = in.readUnsignedShort();
      // Use the  allows the AttributeInfo.getName() to work
      ConstantMethodHandleInfo methodHandleInfo = (ConstantMethodHandleInfo) getClassFile().getConstantPool()[bootstrapMethodRef];
      int nameIndex = methodHandleInfo.resolveReferenceIndexNameIndex();
      super.setAttributeNameIndex(nameIndex);
      int numBootstrapArguments = in.readUnsignedShort();
      bootstrapArguments = new int[numBootstrapArguments];
      for(int n = 0; n < numBootstrapArguments; n ++) {
         bootstrapArguments[n] = in.readUnsignedShort();
      }
      if (debug) debug("read ");
   }

   @Override
   public void write(DataOutput out) throws InvalidByteCodeException, IOException {
      // We don't use super write
      out.writeShort(bootstrapMethodRef);
      out.writeShort(bootstrapArguments.length);
      for (int n = 0; n < bootstrapArguments.length; n ++) {
         out.writeShort(bootstrapArguments[n]);
      }
      if (debug) debug("wrote ");
  }
   @Override
   protected void debug(String message) {
       super.debug(message + "BootstrapMethod entry with bootstrap_method_ref " + bootstrapMethodRef +
               ", bootstrap_arguments " + Arrays.asList(bootstrapArguments));
   }
}
