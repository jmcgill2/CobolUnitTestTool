package org.jmcgill2.cobol.data

import groovy.util.logging.Slf4j

/**
 * Contains the File Control information for the Input-Output Section of the Environment Division.
 */
@Slf4j
class FileControl {

    ArrayList<CobolLine> fileControlLines = []

    FileControl(){

    }

    FileControl(ArrayList<CobolLine> fileControlLines){
        this.fileControlLines = fileControlLines
    }

    String toString() {
        String str = ""
        fileControlLines.each{CobolLine cl ->
            str += cl.lineWithoutLineNumber + "\n"
            return str.toString()
        }
    }
}
