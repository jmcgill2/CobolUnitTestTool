package org.jmcgill2.cobol.data

import org.jmcgill2.cobol.utils.CobolUtils

/**
 * Generic Cobol Line with basic Metadata about the type of Cobol Line it is
 * Created by jamesmcgill on 5/12/17.
 */

class CobolLine {

    int lineNumber

    String line

    boolean isComment = false

    boolean isColumnA

    boolean endsWithAPeriod = false

    boolean isPartOfSqlStatement

    boolean containsCalledProgram

    boolean isStartOfDataElement = false

    CobolUtils cobolUtils = new CobolUtils()

    public CobolLine(String line, int lineNumber) {
        this.isComment = cobolUtils.isComment(line)
        this.isColumnA = this.cobolUtils.isColumnA(line)
        this.lineNumber = lineNumber
        lookForCalledProgram(line)
        this.line = line
        if (line.substring(6,72).trim().endsWith(".")){
            endsWithAPeriod = true
        }

        if (this.getLineWithoutLineNumber().trim() != "") {
            def tokens = this.getLineWithoutLineNumber().tokenize(" ")
            String firstValue = tokens[0]
            if (firstValue.isNumber()) {
                isStartOfDataElement = true
            }
        }

    }

    public lookForCalledProgram (String line) {
        if (!isComment && !isColumnA && line.contains(" CALL ")){
            containsCalledProgram = true
        }else {
            containsCalledProgram = false
        }
    }

    public String getLineWithoutLineNumber() {

        String newLine

        if (line == null || line.trim() == "" || !line[0].isNumber()){
            newLine = line
        } else {
            newLine = "      " + line.substring(6)
        }

        return newLine
    }

    public String get7Thru72(){
        return "      " + line.substring(6, 72)
    }

}
