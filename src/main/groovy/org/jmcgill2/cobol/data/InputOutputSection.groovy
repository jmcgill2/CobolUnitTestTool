package org.jmcgill2.cobol.data

import groovy.util.logging.Slf4j

/**
 * Contains the Input-Output Section Information from the Environment Division.
 */
@Slf4j
class InputOutputSection {

    FileControl fileControl

    InputOutputControl inputOutputControl

    ArrayList<CobolLine> cobolLines

    InputOutputSection() {

    }

    InputOutputSection(ArrayList<CobolLine> cobolLines){
        this.cobolLines = cobolLines
    }

    InputOutputSection(FileControl fileControl, InputOutputControl inputOutputControl, ArrayList<CobolLine> cobolLines){
        this.fileControl = fileControl
        this.inputOutputControl = inputOutputControl
        this.cobolLines = cobolLines
    }


}
