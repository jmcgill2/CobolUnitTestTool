package org.jmcgill2.cobol.data

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

    WorkingStorage workingStorage

    ProcedureDivision procedureDivision

    ArrayList<String> cobolLines

    CobolUtils cobolUtils = new CobolUtils()

    public CobolProgram() {

    }

    public CobolProgram(File f){

        cobolLines = cobolUtils.readCobolFile(f)

        workingStorage = new WorkingStorage(cobolLines)

        procedureDivision = new ProcedureDivision(cobolLines)

    }

    public CobolProgram(ArrayList<String> lines){
        cobolLines = lines
        workingStorage = new WorkingStorage(lines)

        procedureDivision = new ProcedureDivision(lines)
    }

    public ArrayList<String> getWorkingStorageLines() {
        return workingStorage.workingStorageLines
    }


}
