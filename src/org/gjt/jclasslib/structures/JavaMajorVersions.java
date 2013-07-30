package org.gjt.jclasslib.structures;

/**
 * Enum for the class file major version codes
 */
public enum JavaMajorVersions {

   JAVA_1(45, "Java 1.1"),
   JAVA_2(46, "Java 1.2"),
   JAVA_3(47, "Java 1.3"),
   JAVA_4(48, "Java 1.4"),
   JAVA_5(49, "Java 5"),
   JAVA_6(50, "Java 6"),
   JAVA_7(51, "Java 7"),
   JAVA_8(52, "Java 8")
   ;

   public static final int JAVA_1_CODE = 45;
   public static final int JAVA_2_CODE = 46;
   public static final int JAVA_3_CODE = 47;
   public static final int JAVA_4_CODE = 48;
   public static final int JAVA_5_CODE = 49;
   public static final int JAVA_6_CODE = 50;
   public static final int JAVA_7_CODE = 51;
   public static final int JAVA_8_CODE = 52;

   private final int code;
   private final String aka;

   private JavaMajorVersions(int code, String aka) {
      this.code = code;
      this.aka = aka;
   }

   public int getMajorVersion() {
      return code;
   }

   /**
    * The common version string for the major version
    * @return
    */
   public String getKnownAs() {
      return aka;
   }

   /**
    * Map a java class file major version into the JavaMajorVersions enum
    * @param code - class file major version code
    * @return the JavaMajorVersions enum if code is valid, null otherwise
    */
   public static JavaMajorVersions valueOf(int code) {
      JavaMajorVersions version = null;
      switch (code) {
         case JAVA_1_CODE:
            version = JAVA_1;
            break;
         case JAVA_2_CODE:
            version = JAVA_2;
            break;
         case JAVA_3_CODE:
            version = JAVA_3;
            break;
         case JAVA_4_CODE:
            version = JAVA_4;
            break;
         case JAVA_5_CODE:
            version = JAVA_5;
            break;
         case JAVA_6_CODE:
            version = JAVA_6;
            break;
         case JAVA_7_CODE:
            version = JAVA_7;
            break;
         case JAVA_8_CODE:
            version = JAVA_8;
            break;
      }
      return version;
   }
}

