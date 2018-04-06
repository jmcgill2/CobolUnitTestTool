package org.jmcgill2.cobol.dsl

import groovy.transform.ToString
import groovy.util.logging.Slf4j

/**
 * Stores a test case Element Value
 */
@ToString
@Slf4j
class DataInput {

    /**
     * Data element name
     */
    String elementName

    /**
     * Data element type.
     */
    String elementType

    /**
     * Data element value
     */
    String value

    public DataInput() {

    }

    public DataInput(String input){
        def toks = input.tokenize(":")
        elementName = toks[0]
        elementType = toks[1]
        value = toks[2]
    }

    public DataInput(String elementName, String elementType, String value){
        this.elementName = elementName
        this.elementType = elementType
        this.value = value
    }

}
