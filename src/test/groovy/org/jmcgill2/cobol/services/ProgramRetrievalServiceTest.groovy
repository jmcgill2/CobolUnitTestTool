package org.jmcgill2.cobol.services

import spock.lang.Specification

class ProgramRetrievalServiceTest extends Specification {

    def "look for copybooks when there are not any"() {

        when:
            ProgramRetrievalService prs = new ProgramRetrievalService()
            ArrayList<String> lines = []
            lines << "THIS IS NOT A COPYBOOK LINE"
            lines << "THIS IS NOT A COPYBOOK LINE"
            lines << "THIS IS NOT A COPYBOOK LINE"
            lines << "THIS IS NOT A COPYBOOK LINE"
            lines << "THIS IS NOT A COPYBOOK LINE"
            lines << "THIS IS NOT A COPYBOOK LINE"

        then:
            prs.generateListOfCopybooks(lines) == []
    }

    def "look for copybooks when no lines exist"() {

        when:
        ProgramRetrievalService prs = new ProgramRetrievalService()
        ArrayList<String> lines = []

        then:
        prs.generateListOfCopybooks(lines) == []
    }

    def "find copybooks at start of paragraph"() {

        when:
        ProgramRetrievalService prs = new ProgramRetrievalService()
        ArrayList<String> lines = []
        lines << "THIS IS NOT A COPYBOOK LINE"
        lines << "THIS IS NOT A COPYBOOK LINE"
        lines << "THIS IS NOT A COPYBOOK LINE"
        lines << "-INC JIM"
        lines << "THIS IS NOT A COPYBOOK LINE"
        lines << "-INC JIM2"
        lines << "THIS IS NOT A COPYBOOK LINE"

        ArrayList<String> names = prs.generateListOfCopybooks(lines)

        then:
        names.size() == 2
        names == ['JIM', 'JIM2']
    }

    def "find copybooks at location 6"() {

        when:
        ProgramRetrievalService prs = new ProgramRetrievalService()
        ArrayList<String> lines = []
        lines << "THIS IS NOT A COPYBOOK LINE"
        lines << "THIS IS NOT A COPYBOOK LINE"
        lines << "THIS IS NOT A COPYBOOK LINE"
        lines << "AAAAA -INC JIM"
        lines << "THIS IS NOT A COPYBOOK LINE"
        lines << "BBBBB -INC JIM2"
        lines << "THIS IS NOT A COPYBOOK LINE"

        ArrayList<String> names = prs.generateListOfCopybooks(lines)

        then:
        names.size() == 2
        names == ['JIM', 'JIM2']
    }

}
