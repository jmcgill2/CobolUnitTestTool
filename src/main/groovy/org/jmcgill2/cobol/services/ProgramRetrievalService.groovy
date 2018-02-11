package org.jmcgill2.cobol.services

import groovy.util.logging.Slf4j
import org.jmcgill2.cobol.data.ProgramElements

/***
 * Retrieves the Original Cobol Program and Any Copybooks used by the Program.
 */
@Slf4j
class ProgramRetrievalService {

    String programName

    String programLocation

    ArrayList<String> cobolLocations = []

    RetrievalInterface retrievalService

    public ProgramRetrievalService() {

    }

    public ProgramRetrievalService(String programName, String programLocation, ArrayList<String> cobolLocations){
        this.programName = programName
        this.programLocation = programLocation
        this.cobolLocations = cobolLocations
    }

    public ProgramRetrievalService(String programLocation, String programName, ArrayList<String> cobolLocations,
                                   RetrievalInterface retrievalService) {
        this.programName = programName
        this.programLocation = programLocation
        this.cobolLocations = cobolLocations
        this.retrievalService = retrievalService

    }

    public static void main(String[] args) {
        ProgramRetrievalService prs = new ProgramRetrievalService("/Users/jamesmcgill/mutual/cobol/", "WMS910_PGM.txt",
        ["/Users/jamesmcgill/mutual/copybooks/", "/Users/jamesmcgill/mutual/dqlcopy/"], (RetrievalInterface) new FileRetrievalService())
        ArrayList<String> lines = prs.retrieveProgramLines()
        log.info("lines.size() = ${lines.size()}")

        ArrayList<String> books = prs.generateListOfCopybooks(lines)

        println "Copybooks"
        books.each{println it}
        println "End println of ${books.size()} Copybooks"

        Map<String, ArrayList<String>> junk = prs.retrieveCopybooks(prs.cobolLocations, books)

        println "junk.size() = ${junk.size()}"
    }

    /**
     * Retrieves the Cobol program file contents and any copybook file contents as an Array of Strings in a
     * ProgramElements class.
     *
     * @return  ProgramElements containing file contents for the Cobol program and copybooks.
     */
    public ProgramElements retrieveProgramElements(){

        ProgramElements programElements = new ProgramElements(programName)

        programElements.programLines = retrieveProgramLines()

        ArrayList<String> copybookList = generateListOfCopybooks(programElements.programLines)

        programElements.copybooks = retrieveCopybooks(cobolLocations, copybookList)

        return programElements

    }

    /**
     *
     * @param locations
     * @param copybookList
     * @return
     */
    public Map<String, ArrayList<String>> retrieveCopybooks(ArrayList<String> locations, ArrayList<String>copybookList){
        return retrievalService.retrieveCopybooks(locations, copybookList)
    }

    public ArrayList<String> retrieveProgramLines() {
        return retrievalService.retrieveProgram(programLocation, programName)
    }

    public ArrayList<String> generateListOfCopybooks(ArrayList<String> lines) {
        ArrayList<String> copybooks = []

        lines.each{String line ->
            if (line.trim().startsWith("-INC ") || line.substring(6).startsWith("-INC ")){
                def toks = line.tokenize(" ")
                boolean nameNotFound = true
                boolean includeFound = false
                int k = 0
                int maxToks = toks.size() - 1
                while (nameNotFound && k <= maxToks){
                    if (includeFound){
                        copybooks << toks[k]
                        nameNotFound = false
                    } else {
                        if (toks[k] == '-INC') {
                            includeFound = true
                        }
                    }
                    k++
                }
            }
        }
        return copybooks.unique()

    }



}
