package org.jmcgill2.dsl

/**
 * Stores a test case Element Value
 */
class DataInput {

    /**
     * The name of the data element
     */
    String elementName

    /**
     * The data element type.  Valid Options are:
     *      PIC X
     *      PIC 9
     *      PIC S9
     *      COMP
     *      COMP-3
     *      88 Level
     */
    String elementType

    /**
     * Value that should be used.
     */
    String value
}
