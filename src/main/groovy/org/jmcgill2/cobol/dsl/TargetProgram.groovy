package org.jmcgill2.cobol.dsl

/**
 * Contains information on where the new test program should be stored and it's name.
 */
class TargetProgram {

    /**
     * Contains all the information used to generate the name, location and type.  Useful for debugging.
     */
    String sourceText

    /**
     * Name of the test program that we are creating.
     */
    String targetName

    /**
     * Location we will place the test program we are creating.
     */
    String targetLocation

    /**
     * Location type can be file, source control system, database or any other type that will drive how the generated text will be stored.
     */
    String locationType

    /**
     * Used for testing.
     */
    public TargetProgram () {

    }

    public TargetProgram(String sourceText){
        this.sourceText = sourceText
        setTargetName()
        setTargetLocation()
        setLocationType()
    }

    /**
     * Location Type can only be set without a String argument if it exists as part of the sourceText
     */
    public void setLocationType(){
        if (sourceText == null){
            setLocationType("")
        }else {
            setLocationType(extractFromSource("targetType"))
        }
    }

    /**
     * Location Type is a string containing the type of location.
     * @param locationType
     */
    public void setLocationType(String locationType){
        this.locationType = locationType
    }

    /**
     * Target Location can only be set without a String argument if it exists as part of the sourceText
     */
    public void setTargetLocation() {
        if (sourceText == null){
            setTargetLocation("")
        } else {
            setTargetLocation(extractFromSource("location"))
        }
    }

    /**
     * String containing the location that the target, or test, program will be written out to.
     * @param targetLocation
     */
    public void setTargetLocation (String targetLocation){
        this.targetLocation = targetLocation
    }

    /**
     * Target Name can only be set without a String argument if it exists as part of the sourceText
     */
    public void setTargetName() {
        if (sourceText == null){
            setTargetName("")
        }else {
            setTargetName(extractFromSource("name"))
        }
    }

    /**
     * Target name is the name of the test program that we are generating.
     * @param targetName
     */
    public void setTargetName(String targetName){
        this.targetName = targetName
    }

    /**
     * Extracts the value for a key from the sourceText. Format is key:value followed either by a space or end of string
     * @param key   String containing the key we use to find the value
     * @return      String value assigned to the key
     */
    public String extractFromSource(String key){
        int start = sourceText.indexOf("${key}:") + 1 + key.size()
        String tmp
        if (start > -1) {
            tmp = sourceText.substring(start).trim()
            if (tmp.indexOf(" ") > -1) {
                tmp = tmp.substring(0, tmp.indexOf(" ")).trim()
            } else {
                tmp = tmp.substring(0).trim()
            }
        }else {
            tmp = ""
        }
        return tmp
    }

    public String toString() {
        String str = ""
        str += "Program Name: '${targetName}'\n"
        str += "\tProgram Location: '${targetLocation}'\n"
        str += "\tLocation Type: ${locationType}\n"

        return str
    }

}
