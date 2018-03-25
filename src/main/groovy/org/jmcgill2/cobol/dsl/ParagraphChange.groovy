package org.jmcgill2.cobol.dsl

/**
 * Stores information about a change to the original paragraph.
 */
class ParagraphChange {

    /**
     * The paragraph line to change
     */
    String lineToChange

    /**
     * The type of change.  Options include:
     *   Replace Read
     *   Replace Write
     *   Replace Perform
     *   Replace Call
     */
    String typeOfChange

    boolean incrementIndicator

    ArrayList<TestCaseInput> dataElements = []

}
