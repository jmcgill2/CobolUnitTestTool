package org.jmcgill2.cobol.data

import groovy.util.logging.Slf4j

/**
 * Contains the Input-Output information from the I-O Control in the Input-Output Section of the Environment Division.
 */
@Slf4j
class InputOutputControl {

    ArrayList<CobolLine> inputOutputControlLines = []

    InputOutputControl(){

    }

    InputOutputControl(ArrayList<CobolLine> inputOutputControlLines){
        this.inputOutputControlLines = inputOutputControlLines
    }

    String toString(){
        String str = ""
        inputOutputControlLines.each{CobolLine cl ->
            str += cl.lineWithoutLineNumber + "\n"
        }

        return str.toString()
    }

}
