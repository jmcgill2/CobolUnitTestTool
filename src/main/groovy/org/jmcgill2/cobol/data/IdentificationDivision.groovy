package org.jmcgill2.cobol.data

import groovy.util.logging.Slf4j

/**
 * Contains all the information associated with the Identification Division.  Right now, that would include any
 * comments that preceded the Identification Division but that could change in the future.
 */
@Slf4j
class IdentificationDivision {

    /**
     * The name of the cobol program.  This should be limited to 8 characters.
     */
    String programId

    ArrayList<CobolLine> cobolLines

    IdentificationDivision(String programId){
        this.programId = programId
    }

    IdentificationDivision(String programId, ArrayList<CobolLine>cobolLines){
        this.programId = programId
        this.cobolLines = cobolLines
    }

    String toString(){
        return programId
    }
}
