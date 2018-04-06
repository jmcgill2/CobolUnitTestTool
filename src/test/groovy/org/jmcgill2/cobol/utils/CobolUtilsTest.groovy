package org.jmcgill2.cobol.utils

import org.jmcgill2.cobol.data.CobolLine
import spock.lang.Specification
import spock.lang.Unroll

class CobolUtilsTest extends Specification {
    def "IdentifySqlLines where all lines are SQL lines"() {
        CobolUtils cobolUtils = new CobolUtils()

        when:
            ArrayList<CobolLine> cobolLines = []
            cobolLines << new CobolLine(line:"      EXEC-SQL SELECT")
            cobolLines << new CobolLine(line: "END-EXEC")
            cobolUtils.identifySqlLines(cobolLines)
        then:
            cobolLines.each{
                it.isPartOfSqlStatement == true
            }
    }

    def "IdentifySqlLines where no lines are SQL lines"() {

        CobolUtils cobolUtils = new CobolUtils()

        when:
        ArrayList<CobolLine> cobolLines = []
        cobolLines << new CobolLine(line:"      EXEC-SQ SELECT")
        cobolLines << new CobolLine(line: "END-EXE")
        cobolUtils.identifySqlLines(cobolLines)
        then:
        cobolLines.each{
            it.isPartOfSqlStatement == false
        }
    }

    def "IdentifySqlLines where there is a single SQL line"() {

        CobolUtils cobolUtils = new CobolUtils()

        when:
            ArrayList<CobolLine> cobolLines = []
            cobolLines << new CobolLine(line:"      EXEC SQL SELECT END-EXEC")
            cobolLines << new CobolLine(line: "END-EXE")
            cobolUtils.identifySqlLines(cobolLines)
        then:
            cobolLines[0].isPartOfSqlStatement == true
            cobolLines[1].isPartOfSqlStatement == false
    }

    def "IdentifySqlLines with multi line sql statement"() {

        CobolUtils cobolUtils = new CobolUtils()

        when:
            ArrayList<CobolLine> cobolLines = []
            cobolLines << new CobolLine(line:"      EXEC SQL SELECT")
            cobolLines << new CobolLine(line: "END-EXE")
            cobolLines << new CobolLine(line: "SELECT")
            cobolLines << new CobolLine(line: "END-EXEC")
            cobolLines << new CobolLine(line: "OTHER STATEMENT")
            cobolUtils.identifySqlLines(cobolLines)
        then:
            cobolLines[0].isPartOfSqlStatement == true
            cobolLines[1].isPartOfSqlStatement == true
            cobolLines[2].isPartOfSqlStatement == true
            cobolLines[3].isPartOfSqlStatement == true
            cobolLines[4].isPartOfSqlStatement == false
    }

    def "IdentifySqlLines for multiple multi line sql statements"() {

        CobolUtils cobolUtils = new CobolUtils()

        when:
            ArrayList<CobolLine> cobolLines = []
            cobolLines << new CobolLine(line:"      EXEC SQL SELECT")
            cobolLines << new CobolLine(line: "END-EXE")
            cobolLines << new CobolLine(line: "SELECT")
            cobolLines << new CobolLine(line: "END-EXEC")
            cobolLines << new CobolLine(line: "OTHER STATEMENT")
            cobolLines << new CobolLine(line:"      EXEC SQL SELECT")
            cobolLines << new CobolLine(line: "END-EXE")
            cobolLines << new CobolLine(line: "SELECT")
            cobolLines << new CobolLine(line: "END-EXEC")
            cobolLines << new CobolLine(line: "OTHER STATEMENT")
            cobolUtils.identifySqlLines(cobolLines)
        then:
            cobolLines[0].isPartOfSqlStatement == true
            cobolLines[1].isPartOfSqlStatement == true
            cobolLines[2].isPartOfSqlStatement == true
            cobolLines[3].isPartOfSqlStatement == true
            cobolLines[4].isPartOfSqlStatement == false
            cobolLines[5].isPartOfSqlStatement == true
            cobolLines[6].isPartOfSqlStatement == true
            cobolLines[7].isPartOfSqlStatement == true
            cobolLines[8].isPartOfSqlStatement == true
            cobolLines[9].isPartOfSqlStatement == false
    }

    @Unroll
    def "IsComment #a"() {
        CobolUtils cobolUtils = new CobolUtils()

        expect:
            cobolUtils.isComment(b) == c

        where:
            a                                                                                   | b                      || c
            "is false where no asterisk exists"                                                 | "There is no asterisk" || false
            "is false when the asterisk is not in the ${Constants.commentIndLocation} location" | "****** *********"     || false
            "is true when the asterisk is in the ${Constants.commentIndLocation} position"      | "      *       "       || true
            "is false for a blank line"                                                        || ""                     || false
            "is false for a null line"                                                         || null                   || false
            "is false for a 6 character line"                                                   | "      "               || false

    }

    @Unroll
    def "IsColumnA #a"() {
        CobolUtils cobolUtils = new CobolUtils()

        expect:
            cobolUtils.isColumnA(b) == c

        where:
            a                                                        | b                    || c
            "is false for a blank line"                              | ""                   || false
            "is false for a null line"                               | null                 || false
            "is false for a line less than 10 characters long"       | "         "          || false
            "is false for a blank line greater than 10 charcters"    | "           "        || false
            "is false for a line that has nothing in cols 7 thru 10" | "AAAAAAA   BBBBBBBB" || false
            "is true for a line that has a char in col 7"            | "       A        "   || true
            "is true for a line that has a char in col 8"            | "        A       "   || true
            "is true for a line that has a char in col 9"            | "         A      "   || true
    }

    def "FindFirstNonCommentCobolLine"() {
    }
}
