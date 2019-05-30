package org.jmcgill2.cobol.data

import org.jmcgill2.cobol.ProcessCopybooks
import org.jmcgill2.cobol.utils.CobolUtils

/**
 * CobolProgram stores all the components of a Cobol Program that will be used
 * as the basis for generating a Test Version for Unit tests.
 *
 * It encapsulates all the functionality necessary to transform a file of a Cobol Program into the information
 * necessary to generate a Unit Test Version of the program.
 *
 * Created by jamesmcgill on 5/12/17.
 */
class CobolProgram {

    String programName

    IdentificationDivision identificationDivision

    WorkingStorage workingStorage

    ProcedureDivision procedureDivision

    ArrayList<String> cobolLines

    CobolUtils cobolUtils = new CobolUtils()



    CobolProgram() {

    }

    CobolProgram(File f){

        cobolLines = cobolUtils.readCobolFile(f)

        ProgramElements programElements = new ProgramElements(cobolLines)

        workingStorage = new WorkingStorage(programElements)

        procedureDivision = new ProcedureDivision(cobolLines)

    }

    CobolProgram(ProgramElements programElements){
        this.programName = programElements.programName
        this.cobolLines = programElements.programLines
        this.workingStorage = new WorkingStorage(programElements)
        this.procedureDivision = new ProcedureDivision(cobolLines)

    }

    ArrayList<CobolLine> getWorkingStorageLines() {
        return workingStorage.workingStorageLines
    }


}
