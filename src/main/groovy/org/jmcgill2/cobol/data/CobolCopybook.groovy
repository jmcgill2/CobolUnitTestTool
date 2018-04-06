package org.jmcgill2.cobol.data

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Created by jamesmcgill on 5/29/17.
 */
@ToString(excludes='cobolLines')
@EqualsAndHashCode(includes = 'copybookName')
class CobolCopybook {

    /**
     * The value, if any, that will be used when the copybook is instantiated in place of the value in the copybook.
     * This is done to allow multiple versions of the same copybook to be used in the same program.
     */
    String replaceValue

    /**
     * The value in the copybook that will be replaced.  Usually, this is a value like :NUL: that is designed to be
     * replaced.
     */
    String originalValue

    /**
     * The name of the copybook.
     */
    String copybookName

    /**
     * The contents of the copybook stored in CobolLine classes
     */
    ArrayList<CobolLine> cobolLines = []

    /**
     * Empty Constructor helps with testing.
     */
    public CobolCopybook() {

    }

    /**
     * Stores the information necessary to track the contents, including Data Element information, of the Copybook.
     * @param originalPrefix    String containing the original copybook value that will be replaced.
     * @param newPrefix         String containing the new value that will replace the original value in the Copybook.
     * @param copybookName      String containing the Copybook name.
     * @param copybookLines     Array of Strings containing all the Copybook values.
     */
    public CobolCopybook(String originalPrefix, String newPrefix, String copybookName, ArrayList<String> copybookLines ) {

        this.copybookName = copybookName

        originalValue = originalPrefix

        replaceValue = newPrefix

        populateCopybookLines(copybookLines)
    }

    /**
     * Takes the raw Copbyook file String information and generate CobolLine instances.  THIS OUTPUT INCORPORATES THE
     * NEW VALUE IN PLACE OF THE ORIGINAL VALUE.
     * @param lines Strings containing the original content of the Copybook file.
     */
    private void populateCopybookLines(ArrayList<String> lines) {

        lines.eachWithIndex{ String entry, int i ->
            entry = entry.replaceAll(originalValue, replaceValue)
            cobolLines << new CobolLine(entry, i)
        }
    }

    public void setCobolLines(ArrayList<String>lines){
        this.cobolLines = []
        populateCopybookLines(lines)
    }

    /**
     * Prints the contents of the Copybook the way it would be incorporated into the program.
     */
    public void printCopybook() {
        cobolLines.each{
            println it.line
        }
    }
}
