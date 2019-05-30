package org.jmcgill2.cobol.data

import groovy.util.logging.Slf4j
import org.jmcgill2.cobol.utils.CobolUtils

/**
 * Stores a Data Element and the key Information about it
 * Created by jamesmcgill on 5/12/17.
 */
@Slf4j
class DataElement {

    enum DataElementLocation{
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
     * True if the element is an 01 or 77 level element and false otherwise.
     */
    boolean isTopLevelElement

    /**
     * True if the data element either directly redefines another data element or is part of a group and that
     * group level data element redefines another data element.
     */
    boolean redefinesAnotherDataElement

    /**
     * Element Name of the redefined data element
     */
    String redefinedDataElementName

    /**
     * True if (1) the data element is either directly redefined by one or more data elements, (2) is not a group level
     * data element and it's group level data element is redefined by one or (3) both 1 and 2 are true.
     */
    boolean isRedefinedByAnotherDataElement

    /**
     * True if the data element has a predefined value.
     */
    boolean hasPredefinedValue = false

    /**
     * Stores the predefined value
     */
    String predefinedValue

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
     * Size of the data element.  If the element is a group element it will be the
     */
    int dataElementSize

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

    DataElement() {

    }

    /**
     * Constructor that takes a list of CobolLine objects and a line number.  This allows for the creation of a data
     * element that takes more than one CobolLine.
     * @param cobolLines    List of CobolLine objects
     * @param cobolLineNum  Location in the List of CobolLine objects that begins the data element information.
     */
    DataElement(ArrayList<CobolLine> cobolLines, int cobolLineNum ){

        dataElementLine = generateDataElement(cobolLines, cobolLineNum)

        dataElementName = generateDataElementName()

        isGroupElement = determineGroupElement()

        dataElementLevel = generateDataElementLevel()

        isTopLevelElement = determineTopLevelElement()

        if (dataElementLine.contains(" REDEFINES ")){
            redefinesAnotherDataElement = true
            redefinedDataElementName = determineRedefinedElementName()
        }

        dataElementPicClause = generatePicClause()

        value = identifyValueIfAny()

    }

    String identifyValueIfAny() {
        String val = ""

        if (dataElementLine.contains (" VALUE ")){
             String tmp = dataElementLine.substring(dataElementLine.indexOf(" VALUE ") + 7)
            tmp = tmp.trim()
            if (tmp.startsWith("'")){
                int loc = tmp.lastIndexOf("'") + 1
                tmp = tmp.substring(0, loc)
            }else if (tmp.startsWith('"')){
                int loc = tmp.lastIndexOf('"') + 1
                tmp = tmp.substring(0, loc)
            }else {
                def toks = tmp.tokenize(" ")
                tmp = toks[0]
            }
            if (tmp.endsWith(".")){
                tmp = tmp - "."
            }
            val = tmp
        }

        return val
    }

    String generatePicClause() {
        String picClause = ""

        if (dataElementLine.contains(" PIC ")){
            if (dataElementLine.contains(" VALUE ")){
                picClause = dataElementLine.substring(dataElementLine.indexOf(" PIC ") + 5, dataElementLine.indexOf(" VALUE "))
            } else {
                picClause = dataElementLine.substring(dataElementLine.indexOf(" PIC ") + 5).trim()
                if (picClause.endsWith(".")){
                    picClause = picClause - "."
                }
            }
        } else if (dataElementLine.contains(" PICTURE ")){

            if (dataElementLine.contains(" VALUE ")){
                picClause = dataElementLine.substring(dataElementLine.indexOf(" PICTURE ") + 9, dataElementLine.indexOf(" VALUE "))
            } else {
                picClause = dataElementLine.substring(dataElementLine.indexOf(" PICTURE ") + 9).trim()
                if (picClause.endsWith(".")){
                    picClause = picClause - "."
                }
            }
        }
        return picClause
    }

    /**
     * Extracts redefined data element from dataElementLine.
     * @return  String containing the name of the data element redefined by this data element.
     */
    String determineRedefinedElementName(){

        return determineRedefinedElementName(dataElementLine)
    }

    /**
     * Extracts redefined data element from line provided.
     * @param line  String containing data element information.
     * @return  String containing the name of the data element redefined by this data element.
     */
    String determineRedefinedElementName(String line){

        String tmpLine = line.substring(dataElementLine.indexOf("REDEFINES "))
        def toks = tmpLine.tokenize(" ")
        String redef = toks[1]
        if (redef.endsWith(".")){
            redef = redef.substring(0, redef.size() - 2)
        }

        return redef
    }

    /**
     * Returns true if the dataElementLevel is 01 or 77 and false otherwise.
     * @return true if the dataElementLevel is 01 or 77 and false otherwise.
     */
    boolean determineTopLevelElement() {
        return determineTopLevelElement(dataElementLevel)
    }

    /**
     * Returns true if the level is 01 or 77 and false otherwise.
     * @param level String containing a level indicator for a data element.
     * @return  true if the level is 01 or 77 and false otherwise.
     */
    boolean determineTopLevelElement(String level){
        return (dataElementLevel == '01' || dataElementLevel == "77")
    }

    /**
     * Returns true if the dataElementLine contains a group level element and false otherwise.
     * @return  true if the dataElementLine contains a group level element and false otherwise.
     */
    boolean determineGroupElement(){
        return determineGroupElement(dataElementLine)
    }

    /**
     * Returns true if the line contains a group level data element.
     * @param line  String containing data element information.
     * @return  true if the data element is a group level data element and false otherwise.
     */
    boolean determineGroupElement(String line){

        boolean bool

        if (!(line.contains(" PIC ") || line.contains(" PICTURE "))){
            bool = true
            groupDataElementName = dataElementName
        } else {
            //TODO Add group data element processing to identify the group element name.
            bool = false
        }

        return bool

    }

    /**
     * Joins the entire data element definition into a single line even if the definition is contained in mulitple
     * Cobol Lines.
     * @param cobolLines        ArrayList of CobolLine instances
     * @param cobolLineNum      The location in the Array to begin piecing together the data element definition.
     * @return                  String containing the data element information.
     */
    private String generateDataElement(ArrayList<CobolLine> cobolLines, int cobolLineNum){

        CobolLine cobolLine = cobolLines[cobolLineNum]
        String elementString = cobolLine.get7Thru72()
        int counter = cobolLineNum
        while (!cobolLine.endsWithAPeriod) {
            counter++
            cobolLine = cobolLines[counter]
            if (!cobolLine.isComment){
                elementString += cobolLine.get7Thru72()
            }
        }

        while(elementString.contains("  ")){
            elementString = elementString.replaceAll("  ", " ")
        }

        return elementString
    }

    /**
     * Finds the data element level number in the data element line and returns it as a String.
     * @return  String containing the data element leven number.
     */
    private String generateDataElementLevel(){
        def toks = dataElementLine.tokenize(" ")

        String level

        if (toks.size() > 0){
            level = toks[0]
        } else {
            level = ""
        }

        return level
    }

    /**
     * Finds the data element name in the data element line and returns it as a String.
     * @return  String containing the data element name.
     */
    private String generateDataElementName(){
        def toks = dataElementLine.tokenize(" ")

        String name
        if (toks.size() > 1) {
            name = toks[1]
        } else {
            name = ""
        }

        if (name.endsWith('.')){
            name = name - '.'
        }

        return name
    }

    String toString(){
        return dataElementLine
    }

}
