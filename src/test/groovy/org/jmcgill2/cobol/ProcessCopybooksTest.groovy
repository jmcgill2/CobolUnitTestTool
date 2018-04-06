package org.jmcgill2.cobol

import org.jmcgill2.cobol.data.CobolCopybook
import org.jmcgill2.cobol.data.CobolLine
import spock.lang.Specification

class ProcessCopybooksTest extends Specification {
    def "GetCopybookDirectory"() {
        ProcessCopybooks pcb
        when:
            pcb = new ProcessCopybooks("temp", null)
        then:
            pcb.copybookDirectory == "temp"

    }

    def "SetCopybookDirectory"() {

        ProcessCopybooks pcb
        when:
        pcb = new ProcessCopybooks("temp", null)
        pcb.setCopybookDirectory("temp2")
        then:
        pcb.copybookDirectory == "temp2"
    }

    def "GetCobolLines"() {
        ProcessCopybooks pcb

        when:
            pcb = new ProcessCopybooks("", [new CobolLine()])
        then:
            ArrayList<CobolCopybook> books = pcb.getCobolLines()
            books.size() == 1
    }

    def "SetCobolLines"() {

        ProcessCopybooks pcb

        when:
        pcb = new ProcessCopybooks("", [new CobolLine()])
        pcb.setCobolLines([new CobolLine(), new CobolLine()])
        then:
        ArrayList<CobolCopybook> books = pcb.getCobolLines()
        books.size() == 2
    }
}
