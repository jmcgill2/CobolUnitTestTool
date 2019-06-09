package org.jmcgill2.cobol.data

import org.jmcgill2.cobol.utils.CobolUtils

/**
 * Contains the contents of the Cobol program Data Division.  This includes the File Section, Working Storage Section and
 * Linkage Section.
 */
class DataDivision {

    FileSection fileSection

    WorkingStorageSection workingStorageSection

    LinkageSection linkageSection

    ArrayList<CobolLine> dataDivisionLines = []

    CobolUtils cobolUtils = new CobolUtils()

    DataDivision(){

    }

    DataDivision(ArrayList<CobolLine> dataDivisionLines){
        this.dataDivisionLines = dataDivisionLines
    }

    DataDivision(FileSection fileSection, WorkingStorageSection workingStorageSection, LinkageSection linkageSection,
                ArrayList<CobolLine> dataDivisionLines){
        this.fileSection = fileSection
        this.workingStorageSection = workingStorageSection
        this.linkageSection = linkageSection
        this.dataDivisionLines = dataDivisionLines
    }


}
