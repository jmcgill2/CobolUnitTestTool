package org.jmcgill2.cobol.data

import groovy.util.logging.Slf4j
import org.jmcgill2.cobol.utils.CobolUtils

/**
 * Stores a Data Element and the key Information about it
 * Created by jamesmcgill on 5/12/17.
 */
@Slf4j
class DataElement {

    public enum DataElementLocation{
        FILE_SECTION,
        WORKING_STORAGE,
        LINKAGE_SECTION
    }

    /**
     * Location the data element is defined in the Cobol program.
     */
    DataElementLocation dataElementLocation

    /**
     * True if the data element has no PIC clause and false otherwise.
     */
    boolean isGroupElement

    /**
     * True if the data element either directly redefines another data element or is part of a group and that
     * group level data element redefines another data element.
     */
    boolean isRedefines

    /**
     * True if (1) the data element is either directly redefined by one or more data elements, (2) is not a group level
     * data element and it's group level data element is redefined by one or (3) both 1 and 2 are true.
     */
    boolean isRedefinedBy

    /**
     * True if the data element has a predefined value.
     */
    boolean hasPredefinedValue = false

    /**
     * True if the data element has a blank when zero setting.
     */
    boolean blankWhenZero = false

    /**
     * True when the data element has an occurs clause.
     */
    boolean isOccurs = false

    /**
     * True when the data element has a signed clause.
     */
    boolean isSigned = false

    /**
     * True when the data elements has a Synchronized clause.
     */
    boolean isSynchronized = false

    /**
     * Name of the Group Element that this data element belongs to.  If the data element is a group element then
     * the name will be its name.
     */
    String groupDataElementName

    /**
     * All the data elements that redefined this data element.  If no data elements redefined this data element, the
     * list will be empty.
     */
    ArrayList<DataElement> dataElementsRedefinedBy = []

    /**
     * The data element redefined by this data element.  If this data element did not redefine a data element, then
     * it will be null.
     */
    DataElement dataElementRedefined

    /**
     * The numeric level (e.g. 01, 05, 49, 88) that a data element possesses.
     */
    String dataElementLevel

    /**
     * The Pic clause of the data element.  If none exists, as in the case of a group element, the value is null.
     */
    String dataElementPicClause

    /**
     * The data element name.
     */
    String dataElementName

    /**
     * The data element value.
     */
    String value

    /**
     * The entire data element line.
     */
    String dataElementLine

    /**
     * A list of the one or more CobolLine objects that together contain all the information for the data element.
     */
    ArrayList<CobolLine> lines = []

    /**
     * A list of all the cobol paragraphs where this data element is used.
     */
    ArrayList<CobolParagraph> paragraphsUsed = []

    /**
     * A list of the line numbers from the cobol program that are used for this data element.
     */
    ArrayList<Integer> lineNumbers = []

    /**
     * Contains utilities that will be used in instantiating this object
     */
    CobolUtils cobolUtils = new CobolUtils()

    public DataElement() {

    }

    public DataElement(ArrayList<CobolLine> cobolLines, int cobolLineNum ){
        CobolLine cobolLine = cobolLines[cobolLineNum]
        String elementString = cobolLine.get7Thru72()
        int counter = cobolLineNum
        while (!cobolLine.endsWithAPeriod) {
            counter++
            cobolLine = cobolLines[counter]
            elementString += cobolLine.get7Thru72()
        }

        while(elementString.contains("  ")){
            elementString = elementString.replaceAll("  ", " ")
        }

        dataElementLine = elementString

        dataElementName = generateDataElementName()

        dataElementLevel = generateDataElementLevel()

        if (!(dataElementLine.contains(" PIC ") || dataElementLine.contains(" PICTURE "))){
            isGroupElement = true
            groupDataElementName = dataElementName
        }

        if (dataElementLine.contains(" REDEFINES ")){
            isRedefines = true
        }


    }

    /**
     * Finds the data element level number in the data element line and returns it as a String.
     * @return  String containing the data element leven number.
     */
    private String generateDataElementLevel(){
        def toks = dataElementLine.tokenize(" ")
        String level = toks[0]

        return level
    }

    /**
     * Finds the data element name in the data element line and returns it as a String.
     * @return  String containing the data element name.
     */
    private String generateDataElementName(){
        def toks = dataElementLine.tokenize(" ")

        String name
        if (toks.size() > 2) {
            name = toks[1]
        } else {
            name = ""
        }

        if (name.endsWith('.')){
            name = name - '.'
        }

        name
    }

    public String toString(){
        return dataElementLine
    }

}
