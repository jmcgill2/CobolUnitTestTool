package org.jmcgill2.cobol.data

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Created by jamesmcgill on 5/29/17.
 */
@ToString(excludes='cobolLines')
@EqualsAndHashCode(includes = 'copybookName')
class CobolCopybook {

    String replaceValue

    String originalValue

    String copybookName

    ArrayList<CobolLine> cobolLines = []

    public CobolCopybook() {

    }

    public CobolCopybook(String replaceLine, String includeLine, ArrayList<String> copybookDirectories ) {

        copybookName = findCopybookName(includeLine)

        findOriginalAndReplaceValues(replaceLine)

        populateCopybookLines(copybookDirectories)
    }

    private void populateCopybookLines(ArrayList<String> copybookDirectories) {
        boolean stillLookingForCopybook = true
        for (x in 0..copybookDirectories.size() - 1){
            if (stillLookingForCopybook) {
                String dir = copybookDirectories[x]
                String fileName = "${dir}${copybookName}.txt"
                File f = new File(fileName)
                if (f.exists()) {
                    stillLookingForCopybook = false
                    def counter = 0
                    f.eachLine { String line ->
                        cobolLines << new CobolLine(line, counter)
                        counter++
                    }
                } else {
                    println "Cannot find File ${fileName}!"
                }
            }
        }
    }

    private String findOriginalAndReplaceValues(String replaceLine){

        def tokens = replaceLine.trim().tokenize(" ")

        def tokens2 = tokens[2].trim().tokenize(",")

        originalValue = tokens2[0]

        replaceValue = tokens2[1]
    }

    private String findCopybookName(String includeLine) {
        def tokens = includeLine.tokenize(" ")
        return tokens[1].trim()
    }

    public void printOriginalCopybook() {
        cobolLines.each{
            println it.line
        }
    }

    public void printConvertedCopybook() {
        cobolLines.each{
            String line = it.line
            line = line.replaceAll(originalValue, replaceValue)
            println line
        }
    }
}
