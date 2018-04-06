package org.jmcgill2.cobol.data

import spock.lang.Specification

class CobolCopybookTest extends Specification {
    def "GetReplaceValue"() {
        CobolCopybook cc

        when:
            cc = new CobolCopybook("OriginalPrefix", "NewPrefix", "copybookName", [])
        then:
            cc.getReplaceValue() == "NewPrefix"
    }

    def "SetReplaceValue"() {
        CobolCopybook cc

        when:
            cc = new CobolCopybook("OriginalPrefix", "NewPrefix", "copybookName", [])
            cc.setReplaceValue("NewPrefix2")
        then:
            cc.getReplaceValue() == "NewPrefix2"
    }

    def "GetOriginalValue"() {
        CobolCopybook cc

        when:
            cc = new CobolCopybook("OriginalPrefix", "NewPrefix", "copybookName", [])
        then:
            cc.getOriginalValue() == "OriginalPrefix"
    }

    def "SetOriginalValue"() {
        CobolCopybook cc

        when:
            cc = new CobolCopybook("OriginalPrefix", "NewPrefix", "copybookName", [])
            cc.setOriginalValue("OriginalPrefix2")
        then:
        cc.getOriginalValue() == "OriginalPrefix2"

    }

    def "GetCopybookName"() {
        CobolCopybook cc

        when:
            cc = new CobolCopybook("OriginalPrefix", "NewPrefix", "copybookName", [])
        then:
            cc.getCopybookName() == "copybookName"

    }

    def "SetCopybookName"() {
        CobolCopybook cc

        when:
            cc = new CobolCopybook("OriginalPrefix", "NewPrefix", "copybookName", [])
            cc.setCopybookName("copybookName2")
        then:
            cc.getCopybookName() == "copybookName2"

    }

    def "GetCobolLines"() {
        CobolCopybook cc

        when:
            ArrayList<String> cobolLines = []

            cobolLines << "THIS IS A COBOL LINE"
            cc = new CobolCopybook("OriginalPrefix", "NewPrefix", "copybookName", cobolLines)
        then:
            cc.getCobolLines().size() == 1

    }

    def "SetCobolLines"() {
        CobolCopybook cc

        when:
            ArrayList<String> cobolLines = []
            cobolLines << "THIS IS A COBOL LINE"
            cc = new CobolCopybook("OriginalPrefix", "NewPrefix", "copybookName", cobolLines)
            cobolLines << "THIS IS A SECOND COBOL LINE"
            cc.setCobolLines(cobolLines)

        then:
            cc.getCobolLines().size() == 2

    }

    def "PopulateCopybookLines replaces original with new values"() {
        CobolCopybook cc

        when:
            ArrayList<String> cobolLines = []
            cobolLines << "     01   :NUL:-TOP-LEVEL."
            cobolLines << "          05    :NUL:-SEC-LEVEL-CHAR PIC X(10)."
            cobolLines <<"           05    :NULL:-SEC-LEVEL-NUM  PIC 9(5)."
            cc = new CobolCopybook(":NUL:", "TT01", "AABTT01", cobolLines)
        then:
            cc.cobolLines[0].lineNumber == 0
            cc.cobolLines[0].line == "     01   TT01-TOP-LEVEL."
            cc.cobolLines[2].line.contains(":NULL:")
    }
}
