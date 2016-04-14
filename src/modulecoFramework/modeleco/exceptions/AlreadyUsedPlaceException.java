/*
 * AlreadyUsedPlaceException.java
* @author  beugnard
 *
 * Created on 5 avril 2001, 16:45
* @version 1.2  august,5, 2002

 */

   package modulecoFramework.modeleco.exceptions;

/**
 *
 */
   public class AlreadyUsedPlaceException extends java.lang.Exception {
   
    /**
   * Creates new <code>AlreadyUsedPlaceException</code> without detail message.
     */
      public AlreadyUsedPlaceException() {
      }
   
   
    /**
   * Constructs an <code>AlreadyUsedPlaceException</code> with the specified detail message.
     * @param msg the detail message.
     */
      public AlreadyUsedPlaceException(String msg) {
         super(msg);
      }
   }


