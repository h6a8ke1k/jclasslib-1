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
 <pre>BootstrapMethods_attribute {
     u2 attribute_name_index;
     u4 attribute_length;
     u2 num_bootstrap_methods;
     {
        u2 bootstrap_method_ref;
        u2 num_bootstrap_arguments;
        u2 bootstrap_arguments[num_bootstrap_arguments];
     } bootstrap_methods[num_bootstrap_methods];
 }
 </pre>
 * Each bootstrap_methods entry must contain the following three items:
 * <p/>
 * bootstrap_method_ref
 * The value of the bootstrap_method_ref item must be a valid index into the constant_pool table. The constant_pool
 * entry at that index must be a CONSTANT_MethodHandle_info structure (§4.4.8).
 * <p/>
 * The reference_kind item of the CONSTANT_MethodHandle_info structure should have the value 6 (REF_invokeStatic) or
 * 8 (REF_newInvokeSpecial) (§5.4.3.5) or else invocation of the bootstrap method handle during call site specifier
 * resolution for an invokedynamic instruction will complete abruptly.
 * <p/>
 * num_bootstrap_arguments
 * The value of the num_bootstrap_arguments item gives the number of items in the bootstrap_arguments array.
 * <p/>
 * bootstrap_arguments
 * Each entry in the bootstrap_arguments array must be a valid index into the constant_pool table. The constant_pool
 * entry at that index must be a CONSTANT_String_info, CONSTANT_Class_info, CONSTANT_Integer_info, CONSTANT_Long_info,
 * CONSTANT_Float_info, CONSTANT_Double_info, CONSTANT_MethodHandle_info, or CONSTANT_MethodType_info structure
 * (§4.4.3, §4.4.1, §4.4.4, §4.4.5), §4.4.8, §4.4.9).
 *
 * @author Scott Stark (sstark@redhat.com)
 * @version $Revision:$
 * @see <a href="http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.21">4.7.21. The BootstrapMethods attribute</a>
 */
public class BootstrapMethodsAttribute extends AttributeInfo {
    /**
     * Name of the attribute as in the corresponding constant pool entry.
     */
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
     *
     * @return max argument count of any BootstrapMethodEntry
     */
    public int getMaxArguments() {
        int max = 0;
        for (BootstrapMethodEntry entry : bootstrapMethods) {
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
        for (int i = 0; i < numberOfMethods; i++) {
            bootstrapMethods[i].write(out);
        }
        if (debug) debug("wrote ");
    }

    @Override
    protected void debug(String message) {
        super.debug(message + ATTRIBUTE_NAME + " attribute with " + bootstrapMethods.length + " methods");
    }
}
