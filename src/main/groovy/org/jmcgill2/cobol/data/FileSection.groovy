package org.jmcgill2.cobol.data

/**
 * Contains the File Section information from the Data Division of the Cobol Program.
 */
class FileSection {

    ArrayList<CobolLine> fileSectionLines = []

    FileSection(){

    }

    FileSection(ArrayList<CobolLine> fileSectionLines){
        this.fileSectionLines = fileSectionLines
    }
}
