package org.jmcgill2.cobol

import groovy.util.logging.Slf4j
import org.jmcgill2.cobol.data.CobolProgram
import org.jmcgill2.cobol.data.ProgramElements
import org.jmcgill2.cobol.services.FileRetrievalService
import org.jmcgill2.cobol.services.ProgramRetrievalService
import org.jmcgill2.cobol.services.RetrievalInterface

@Slf4j
class CreateTestProgram {

    ProgramRetrievalService programRetrievalService

    CobolProgram cobolProgram

    public CreateTestProgram() {

    }

    public CreateTestProgram(ProgramRetrievalService programRetrievalService){
        this.programRetrievalService = programRetrievalService
    }

    public static void main(String[] args) {
        ProgramRetrievalService prs = new ProgramRetrievalService("/Users/jamesmcgill/mutual/cobol/", "WMS910_PGM.txt",
                ["/Users/jamesmcgill/mutual/copybooks/", "/Users/jamesmcgill/mutual/dqlcopy/"], (RetrievalInterface) new FileRetrievalService())

        CreateTestProgram createTestProgram = new CreateTestProgram(prs)

        createTestProgram.generateTestProgram()

    }

    public void generateTestProgram() {
        ProgramElements programElements = programRetrievalService.retrieveProgramElements()

        println programElements.programLines.size()

        log.info("Number of unique copybooks is ${programElements.copybooks.size()}.")

        cobolProgram = new CobolProgram(programElements)
    }
}
