package org.jmcgill2.cobol.data

import spock.lang.Specification

class CobolLineTest extends Specification {
    def "GetLineNumber"() {
        CobolLine cl

        when:
            cl = new CobolLine(lineNumber: 1)
        then:
            cl.getLineNumber() == 1
    }

    def "SetLineNumber"() {
        CobolLine cl

        when:
        cl = new CobolLine(lineNumber: 1)
        cl.setLineNumber(2)
        then:
        cl.getLineNumber() == 2
    }

    def "GetLine"() {
        CobolLine cl

        when:
            cl = new CobolLine(line: "LINE 1")
        then:
            cl.getLine() == "LINE 1"
    }

    def "SetLine"() {
        CobolLine cl

        when:
            cl = new CobolLine(line: "LINE 1")
            cl.line = "LINE 2"
        then:
            cl.getLine() == "LINE 2"
    }

    def "GetIsComment"() {
        CobolLine cl

        when:
            cl = new CobolLine(isComment: true)
        then:
            cl.getIsComment() == true
    }

    def "SetIsComment"() {
        CobolLine cl

        when:
            cl = new CobolLine(isComment: true)
            cl.setIsComment(false)
        then:
            cl.getIsComment() == false
    }

    def "GetIsColumnA"() {
        CobolLine cobolLine

        when:
            cobolLine = new CobolLine(isColumnA: true)
        then:
            cobolLine.getIsColumnA() == true
    }

    def "GetEndsWithAPeriod"() {
        CobolLine cobolLine

        when:
            cobolLine = new CobolLine(endsWithAPeriod: true)
        then:
            cobolLine.getEndsWithAPeriod() == true
    }

    def "SetEndsWithAPeriod"() {
        CobolLine cobolLine

        when:
            cobolLine = new CobolLine(endsWithAPeriod: true)
            cobolLine.setEndsWithAPeriod(false)
        then:
            cobolLine.getEndsWithAPeriod() == false
    }

    def "GetIsPartOfSqlStatement"() {
        CobolLine cobolLine

        when:
            cobolLine = new CobolLine(isPartOfSqlStatement: true)
        then:
            cobolLine.getIsPartOfSqlStatement() == true
    }

    def "SetIsPartOfSqlStatement"() {
        CobolLine cobolLine

        when:
            cobolLine = new CobolLine(isPartOfSqlStatement: true)
            cobolLine.setIsPartOfSqlStatement(false)
        then:
            cobolLine.getIsPartOfSqlStatement() == false
    }

    def "GetContainsCalledProgram"() {
        CobolLine cobolLine

        when:
            cobolLine = new CobolLine(containsCalledProgram: true)
        then:
            cobolLine.getContainsCalledProgram() == true
    }

    def "SetContainsCalledProgram"() {
        CobolLine cobolLine

        when:
            cobolLine = new CobolLine(containsCalledProgram: true)
            cobolLine.setContainsCalledProgram(false)
        then:
            cobolLine.getContainsCalledProgram() == false
    }

    def "GetIsStartOfDataElement"() {
        CobolLine cobolLine

        when:
            cobolLine = new CobolLine(isStartOfDataElement: true)
        then:
            cobolLine.getIsStartOfDataElement() == true
    }

    def "SetIsStartOfDataElement"() {
        CobolLine cobolLine

        when:
            cobolLine = new CobolLine(isStartOfDataElement: true)
            cobolLine.setIsStartOfDataElement(false)
        then:
            cobolLine.getIsStartOfDataElement() == false
    }

}
