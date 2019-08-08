package org.jmcgill2.cobol.data

import groovy.util.logging.Slf4j
import org.jmcgill2.cobol.utils.FileUtils

/**
 * Contains all the program lines, cobol lines, etc.
 */
@Slf4j
class CobolLines {

    ArrayList<String> lines = []

    ArrayList<CobolLine> programCobolLines = []

    int commentColumnNumber

    int columnANumber

    int columnBNumber

    int lastCobolColumnNumber

    FileUtils fileUtils = new FileUtils()

    CobolLines(){

    }

    CobolLines(String filePathAndName, int commentColumnNumber, int columnANumber, int columnBNumber, int lastCobolColumnNumber){
        this.commentColumnNumber = commentColumnNumber
        this.columnANumber = columnANumber
        this.columnBNumber = columnBNumber
        this.lastCobolColumnNumber = lastCobolColumnNumber
        File cobolProgram = new File(filePathAndName)
        lines = fileUtils.readFile(cobolProgram)
        lines.eachWithIndex{String line, idx  ->
            int lineNumber = idx + 1
            programCobolLines << new CobolLine(line, lineNumber, commentColumnNumber, columnANumber, columnBNumber, lastCobolColumnNumber)
        }
        log.info("Successfully generated Cobol Lines")
    }

    static void main(String[] args) {
        int commentColumnNumber = 6
        int columnANumber = 7
        int columnBNumber = 11
        int lastCobolColumnNumber = 72

        CobolLines cobolLines = new CobolLines("/Users/jimmcgill/vagrant/Example2.cob",
                commentColumnNumber, columnANumber, columnBNumber, lastCobolColumnNumber)
        cobolLines.run()
    }

    void run(){
        for (x in 0..10){
            println programCobolLines[x]
        }
    }

}
