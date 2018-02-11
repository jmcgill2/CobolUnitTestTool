package org.jmcgill2.cobol

import groovy.util.logging.Slf4j
import org.jmcgill2.cobol.data.CobolCopybook
import org.jmcgill2.cobol.data.CobolLine
import org.jmcgill2.cobol.data.CobolParagraph
import org.jmcgill2.cobol.data.CobolProgram
import org.jmcgill2.cobol.services.ProgramRetrievalService
import org.jmcgill2.cobol.services.RetrievalInterface
import org.jmcgill2.cobol.utils.FileUtils

import java.nio.file.Paths

/**
 * Created by jamesmcgill on 5/19/17.
 */
@Slf4j
class ProcessCobolFile {

    FileUtils fileUtis = new FileUtils()

    ArrayList<String> copybookLocations = ["/Users/jamesmcgill/mutual/copybooks/", "/Users/jamesmcgill/mutual/dqlcopy/"]

    String cobolProgramLocation = "/Users/jamesmcgill/mutual/cobol/"

    String cobolProgramName = "WMS910_PGM.txt"

    ProgramRetrievalService programRetrievalService

    public ProcessCobolFile() {

    }

    public ProcessCobolFile(String cobolProgramLocation, String cobolProgramName, ArrayList<String> copybookLocations){
        this.copybookLocations = copybookLocations
        this.cobolProgramLocation = cobolProgramLocation
        this.cobolProgramName = cobolProgramName
    }

    public static void main(String[] args) {
        ProcessCobolFile pcf = new ProcessCobolFile()
        pcf.run()
    }

    public void run() {
        log.info("HERE!")
        log.error("HERE2")
//        File f = Paths.get(Thread.currentThread().getContextClassLoader().getResource("WMS910_PGM.txt").toURI()).toFile()
        File f = new File(cobolFile)
        println "f.name = ${f.name}"

        ArrayList<String> lines = fileUtis.readFile(f)

//        lines.each{println it}

        CobolProgram cobolProgram = new CobolProgram(lines, copybookDir)

//        cobolProgram.procedureDivision.paragraphs.each{CobolParagraph cp ->
//            if (cp.containsCallsToOtherPrograms){
//                println cp
//            }
//        }

//        cobolProgram.workingStorage.dataElements.each{
//            if (it.value != null) {
//                println "${it.value} :: ${it}"
//            }
//        }

//        cobolProgram.procedureDivision.cobolLines.each{CobolLine cl ->
//            println cl.getLineWithoutLineNumber()
//        }

//        cobolProgram.workingStorageLines.each{ CobolLine cl ->
//            println cl.getLineWithoutLineNumber()
//        }

//        cobolProgram.workingStorage.copybooks.each{println it}

    }
}
