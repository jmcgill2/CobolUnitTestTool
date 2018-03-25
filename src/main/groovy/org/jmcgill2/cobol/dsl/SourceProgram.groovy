package org.jmcgill2.cobol.dsl
/**
 * Stores information about the Cobol Program to use for the tests.
 */
class SourceProgram {

    /**
     * The location of the program that we will create a test program for
     */
    String programLocation

    /**
     * The name of the program that we will create a test program for
     */
    String programName

    /**
     * The location type - which could be a file, source coontrol system or database
     */
    String locationType

    /**
     * List of Strings containing the locations of the copybooks.  It is assumed they are in the same location type as
     * the program.
     */
    ArrayList<String> copybookLocations

    /**
     * String containing the raw data used to generate the values for the class.  Expected to be taken from a portion
     * of the Test script.
     */
    String sourceText

    /**
     * Used for testing.
     */
    public SourceProgram() {

    }


    public SourceProgram(String sourceText){

        this.sourceText = sourceText

        setProgramLocation()
        setProgramName()
        setLocationType()
        setCopybookLocations()
    }

    /**
     * Sets Copybook Locations using the sourceText
     */
    public void setCopybookLocations() {
        if (sourceText == null){
            setCopybookLocations([])
        }else {
            int start = sourceText.indexOf("[")
            int end = sourceText.indexOf("]")
            String tmp = sourceText.substring(start, end).trim()
            tmp = tmp - "["
            tmp = tmp - "]"

            tmp = tmp.trim()
            tmp = tmp.replaceAll(" ", "")
            def toks = tmp.tokenize(",")
            ArrayList<String> list = []
            toks.each{it ->
                list << it
            }
            setCopybookLocations(list)
        }

    }

    public void setCopybookLocations(ArrayList<String> copybookLocations){
        this.copybookLocations = copybookLocations
    }

    /**
     * Sets location type using sourceText
     */
    public void setLocationType(){
        if (sourceText == null){
            setLocationType("")
        } else {
            setLocationType(extractFromSource("locationType"))
        }
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType
    }

    /**
     * Sets program name using sourceText
     */
    public void setProgramName(){
        if (sourceText == null){
            setProgramName("")
        } else {
            setProgramName(extractFromSource("name"))
        }
    }

    /**
     * Pulls a value from the sourceText based on a provided key.  Expects the format to be key:value with a space or end
     * of String after the value
     *
     * @param key   String containing the name of the value we are looking for.  It is a Key value pair separated by
     *              a colon and ending with a space or the end of a string.
     * @return      String containing value
     */
    public String extractFromSource(String key){
        int start = sourceText.indexOf("${key}:") + 1 + key.size()
        String tmp = sourceText.substring(start).trim()
        int end = tmp.indexOf(" ")
        if (end > -1){
            tmp = tmp.substring(0, tmp.indexOf(" ")).trim()
        }else {
            tmp = tmp.substring(0).trim()
        }
        return tmp
    }

    public void setProgramName(String programName){
        this.programName = programName
    }

    public void setProgramLocation() {
        if (sourceText == null){
            setProgramLocation("")
        }else {

            setProgramLocation(extractFromSource("location"))

        }
    }

    public String setProgramLocation(String programLocation) {
        this.programLocation = programLocation
    }

    public String toString() {
        String str = ""
        str += "Program Name: '${programName}'\n"
        str += "\tProgram Location: '${programLocation}'\n"
        str += "\tLocation Type: ${locationType}\n"
        str += "\tCopybook Locations: ${copybookLocations}\n"

        return str
    }

}
