/*
This library is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public
License as published by the Free Software Foundation; either
version 2 of the license, or (at your option) any later version.
*/
package org.gjt.jclasslib.structures.attributes;

import org.gjt.jclasslib.structures.AttributeInfo;
import org.gjt.jclasslib.structures.InvalidByteCodeException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 BootstrapMethods_attribute {
     u2 attribute_name_index;
     u4 attribute_length;
     u2 num_bootstrap_methods;
     {   u2 bootstrap_method_ref;
         u2 num_bootstrap_arguments;
         u2 bootstrap_arguments[num_bootstrap_arguments];
     } bootstrap_methods[num_bootstrap_methods];
 }
 * @author Scott Stark (sstark@redhat.com)
 * @version $Revision:$
 */
public class BootstrapMethodsAttribute  extends AttributeInfo {
   /** Name of the attribute as in the corresponding constant pool entry. */
   public static final String ATTRIBUTE_NAME = "BootstrapMethods";

   private static final int INITIAL_LENGTH = 6;
   private int attributeLength;
   /** */
   private BootstrapMethodEntry[] bootstrapMethods;

   public BootstrapMethodsAttribute(int attributeLength) {
      this.attributeLength = attributeLength;
   }

   public BootstrapMethodEntry[] getBootstrapMethods() {
      return bootstrapMethods;
   }

   public void setBootstrapMethods(BootstrapMethodEntry[] bootstrapMethods) {
      this.bootstrapMethods = bootstrapMethods;
   }

   /**
    * Get the BootstrapMethodEntry maximum argument count
    * @return max argument count of any BootstrapMethodEntry
    */
   public int getMaxArguments() {
      int max = 0;
      for(BootstrapMethodEntry entry : bootstrapMethods) {
         max = Math.max(max, entry.getArgumentCount());
      }
      return max;
   }

   @Override
   public int getAttributeLength() {
      return INITIAL_LENGTH + attributeLength;
   }

   @Override
   public void read(DataInput in)
       throws InvalidByteCodeException, IOException {

       int numberOfMethods = in.readUnsignedShort();
      bootstrapMethods = new BootstrapMethodEntry[numberOfMethods];

       for (int i = 0; i < numberOfMethods; i++) {
          bootstrapMethods[i] = BootstrapMethodEntry.create(in, classFile);
       }
       // Indicate we have child attributes for the BootstrapMethodEntrys
       setAttributes(bootstrapMethods);

       if (debug) debug("read ");
   }

   @Override
   public void write(DataOutput out)
       throws InvalidByteCodeException, IOException {

       super.write(out);

       int numberOfMethods = bootstrapMethods.length;

       out.writeShort(numberOfMethods);
       for (int i = 0 ; i < numberOfMethods; i++) {
          bootstrapMethods[i].write(out);
       }
       if (debug) debug("wrote ");
   }

   @Override
   protected void debug(String message) {
       super.debug(message + ATTRIBUTE_NAME+" attribute with " + bootstrapMethods.length + " methods");
   }
}
