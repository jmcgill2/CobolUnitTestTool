package org.jmcgill2.cobol.data

import org.jmcgill2.cobol.utils.CobolUtils

/**
 * Stores all the Working Storage data elements and generates the metadata necessary to generate a Unit Test version of
 * the Cobol Program.
 *
 * Created by jamesmcgill on 5/12/17.
 */
class WorkingStorage {

    ArrayList<DataElement> dataElements = []

    ArrayList<CobolCopybook> copybooks = []

    ArrayList<CobolLine> workingStorageLines = []

    CobolUtils cobolUtils = new CobolUtils()

    int startOfWorkingStorage

    int endOfWorkingStorage

    public WorkingStorage() {

    }

    public WorkingStorage(ArrayList<String> cobolLines) {
        findWorkingStorageLines(cobolLines)
        cobolUtils.identifySqlLines(workingStorageLines)
        populateCopybooks()
        populateDataElements()
    }

    public void populateDataElements() {

        int counter = 0
        boolean processing
        while (counter < workingStorageLines.size()){
            CobolLine cobolLine = workingStorageLines[counter]
            if (cobolLine.isStartOfDataElement){
                dataElements << new DataElement(workingStorageLines, counter)
            }
            counter++


        }

    }

    public void populateCopybooks() {
        workingStorageLines.eachWithIndex{ CobolLine cl, idx ->
            if (cl.lineWithoutLineNumber.trim().startsWith("++REPLACE")){

                copybooks << new CobolCopybook(cl.line, workingStorageLines[idx + 1].line, ["/Users/jamesmcgill/dqlcopy/"])
            }
        }
    }

    public ArrayList<CobolLine> getCobolLines() {
        return workingStorageLines
    }

    public void findWorkingStorageLines(ArrayList<String> cobolLines) {
        boolean workingStorageFound = false
        boolean workingStorageDone = false

        cobolLines.eachWithIndex{String line, idx ->
            if (!workingStorageDone){
                boolean isComment = cobolUtils.isComment(line)
                if (!isComment && line.contains("WORKING-STORAGE SECTION.")){
                    workingStorageFound = true
                    startOfWorkingStorage = idx
                }
                if (workingStorageFound) {
                    if (!isComment && (line.contains("LINKAGE SECTION") || line.contains("PROCEDURE DIVISION"))){
                        workingStorageDone = true
                        endOfWorkingStorage = idx - 1
                    }else {
                        CobolLine cobolLine = new CobolLine(line, idx)
                        workingStorageLines << cobolLine
                    }
                }
            }
        }
    }


}
