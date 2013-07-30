/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.structures.constants;

/**
 http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-5.html#jvms-5.4.3.5
       <table summary="Bytecode Behaviors for Method Handles" border="1">
          <colgroup>
             <col>
             <col>
             <col>
          </colgroup>
          <thead>
             <tr>
                <th>Kind</th>
                <th>Description</th>
                <th>Interpretation</th>
             </tr>
          </thead>
          <tbody>
             <tr>
                <td>1</td>
                <td><code class="literal">REF_getField</code></td>
                <td><code class="literal">getfield C.f:T</code></td>
             </tr>
             <tr>
                <td>2</td>
                <td><code class="literal">REF_getStatic</code></td>
                <td><code class="literal">getstatic C.f:T</code></td>
             </tr>
             <tr>
                <td>3</td>
                <td><code class="literal">REF_putField</code></td>
                <td><code class="literal">putfield C.f:T</code></td>
             </tr>
             <tr>
                <td>4</td>
                <td><code class="literal">REF_putStatic</code></td>
                <td><code class="literal">putstatic C.f:T</code></td>
             </tr>
             <tr>
                <td>5</td>
                <td><code class="literal">REF_invokeVirtual</code></td>
                <td><code class="literal">invokevirtual C.m:(A*)T</code></td>
             </tr>
             <tr>
                <td>6</td>
                <td><code class="literal">REF_invokeStatic</code></td>
                <td><code class="literal">invokestatic C.m:(A*)T</code></td>
             </tr>
             <tr>
                <td>7</td>
                <td><code class="literal">REF_invokeSpecial</code></td>
                <td><code class="literal">invokespecial C.m:(A*)T</code></td>
             </tr>
             <tr>
                <td>8</td>
                <td><code class="literal">REF_newInvokeSpecial</code></td>
                <td><code class="literal">new C; dup; invokespecial
                        C.<code class="literal">&lt;init&gt;</code>:(A*)void</code></td>
             </tr>
             <tr>
                <td>9</td>
                <td><code class="literal">REF_invokeInterface</code></td>
                <td><code class="literal">invokeinterface C.m:(A*)T</code></td>
             </tr>
          </tbody>
       </table>
    </div>
 * @author Scott Stark (sstark@redhat.com)
 * @version $Revision: 1.7 $ $Date: 2010-07-26 14:00:12 $
 */
public enum MethodHandleBytecode {
   REF_getField(1, "getfield C.f:T"),
   REF_getStatic(2, "getstatic C.f:T"),
   REF_putField(3, "putfield C.f:T"),
   REF_putStatic(4, "putstatic C.f:T"),
   REF_invokeVirtual(5, "invokevirtual C.m:(A*)T"),
   REF_invokeStatic(6, "invokestatic C.m:(A*)T"),
   REF_invokeSpecial(7, "invokespecial C.m:(A*)T"),
   REF_newInvokeSpecial(8, "new C; dup; invokespecial C.<init>:(A*)void"),
   REF_invokeInterface(9, "invokeinterface C.m:(A*)T")
   ;

   private int kind;
   private String interpretation;

   private MethodHandleBytecode(int kind, String interpretation) {
      this.kind = kind;
      this.interpretation = interpretation;
   }

   public String getInterpretation() {
      return interpretation;
   }

   public int getKind() {
      return kind;
   }

   public static MethodHandleBytecode valueOf(int kind) {
      MethodHandleBytecode bytecode = null;
      switch (kind) {
         case ConstantMethodHandleInfo.TYPE_GET_FIELD:
            bytecode = REF_getField;
            break;
         case ConstantMethodHandleInfo.TYPE_GET_STATIC:
            bytecode = REF_getStatic;
            break;
         case ConstantMethodHandleInfo.TYPE_INVOKE_INTERFACE:
            bytecode = REF_invokeInterface;
            break;
         case ConstantMethodHandleInfo.TYPE_INVOKE_SPECIAL:
            bytecode = REF_invokeSpecial;
            break;
         case ConstantMethodHandleInfo.TYPE_INVOKE_STATIC:
            bytecode = REF_invokeStatic;
            break;
         case ConstantMethodHandleInfo.TYPE_INVOKE_VIRTUAL:
            bytecode = REF_invokeVirtual;
            break;
         case ConstantMethodHandleInfo.TYPE_NEW_INVOKE_SPECIAL:
            bytecode = REF_newInvokeSpecial;
            break;
         case ConstantMethodHandleInfo.TYPE_PUT_FIELD:
            bytecode = REF_putField;
            break;
         case ConstantMethodHandleInfo.TYPE_PUT_STATIC:
            bytecode = REF_putStatic;
            break;
      }
      return bytecode;
   }
}

