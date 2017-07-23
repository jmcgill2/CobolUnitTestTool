package org.jmcgill2.cobol.data

import org.jmcgill2.cobol.utils.CobolUtils

/**
 * Stores information and contents of Procedure Division Paragraphs
 * Created by jamesmcgill on 5/12/17.
 */
class CobolParagraph {

    String name

    boolean containsCallsToOtherPrograms

    boolean containsSqlStatement

    ArrayList<CobolLine> cobolLines = []

    CobolUtils cobolUtils = new CobolUtils()

    public CobolParagraph() {

    }

    public CobolParagraph(ArrayList<CobolLine> cobolLines) {
        this.cobolLines = cobolLines
        setName()
        findSqlStatement()
        findCalledPrograms()
    }

    private void findSqlStatement() {
        CobolLine sqlCobolLine = cobolLines.find{it.isPartOfSqlStatement}
        if (sqlCobolLine == null){
            containsSqlStatement = false
        }else {
            containsSqlStatement = true
        }
    }

    private void findCalledPrograms() {
        CobolLine calledProgramCobolLine = cobolLines.find{it.containsCalledProgram}
        if (calledProgramCobolLine == null){
            containsCallsToOtherPrograms = false
        } else {
            containsCallsToOtherPrograms = true
        }
    }

    private void findParagraphName() {
        CobolLine line = cobolUtils.findFirstNonCommentCobolLine()

    }

    public void setName(){
        CobolLine line = cobolLines[0]
        String n = line.lineWithoutLineNumber.trim()
        n = n.substring(0, n.size() - 2)
        name = n
    }

    public String toString() {
        String line = ""
        cobolLines.each{CobolLine cl ->
            line += cl.lineWithoutLineNumber
            line += "\n"

        }
        return line
    }
}
