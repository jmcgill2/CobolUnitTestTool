package org.jmcgill2.cobol.data

/**
 * Processes and stores all the information needed for a Linkage Section.  Question - should we roll this into
 * Working Storage?  I think maybe we should.
 *
 * Created by jim mcgill on 5/12/17.
 */
class LinkageSection {

    ArrayList<CobolLine> cobolLines = []

    LinkageSection(ArrayList<CobolLine> cobolLines){
        this.cobolLines = cobolLines
    }


}
