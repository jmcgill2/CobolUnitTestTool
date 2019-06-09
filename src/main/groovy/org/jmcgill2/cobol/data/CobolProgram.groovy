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

    /**
     * The Cobol Program Name
     */
    String programName

    /**
     * The contents of the Identification Division
     */
    IdentificationDivision identificationDivision

    /**
     * The contents of the Environment Division
     */
    EnvironmentDivision environmentalDivision

    /**
     * The contents of the Procedure Division.
     */
    ProcedureDivision procedureDivision

    /**
     * The Cobol Program lines
     */
    ArrayList<String> fileLines

    ArrayList<CobolCopybook> copybooks = []

    Map<Integer, String> programLines

    CobolUtils cobolUtils = new CobolUtils()

    ArrayList<CobolLine> cobolLines


    CobolProgram() {

    }

    CobolProgram(File f, ArrayList<String>copybookLocations){

        fileLines = cobolUtils.readCobolFile(f)

        fileLines.eachWithIndex{line, index ->
            programLines.put(index, line)
        }

        copybooks = cobolUtils.readCopybooks(copybookLocations, programLines)

        workingStorage = new WorkingStorageSection(programElements)

        procedureDivision = new ProcedureDivision(cobolLines)

    }

    ArrayList<CobolLine> getWorkingStorageLines() {
        return workingStorage.workingStorageLines
    }


}
