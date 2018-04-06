package org.jmcgill2.cobol.dsl

class TestCaseOutput {

    /**
     * The data element name.
     */
    String elementName

    /**
     * Data element type.
     */
    String elementType

    /**
     * The data element value
     */
    String elementValue

    /**
     * Assumes a single line with the values separated by a colon (:) with the element name first, element type second and
     * element value third.
     * @param input     String containing element name, element type and element value with each value separated by a colon (:).
     */
    public TestCaseOutput(String input){
        def toks = input.tokenize(":")
        elementName = toks[0]
        elementType = toks[1]
        elementValue = toks[2]
    }

    public TestCaseOutput(String elementName, String elementType, String value){
        this.elementName = elementName
        this.elementType = elementType
        this.elementValue = value
    }



}
