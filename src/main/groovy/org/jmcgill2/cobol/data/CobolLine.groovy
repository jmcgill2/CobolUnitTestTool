package org.jmcgill2.cobol.data

import org.jmcgill2.cobol.utils.CobolUtils

/**
 * Generic Cobol Line with basic Metadata about the type of Cobol Line it is
 * Created by jamesmcgill on 5/12/17.
 */

class CobolLine {

    /**
     * The line number in the program or copybook.
     */
    int lineNumber

    /**
     * The entire line contents
     */
    String line

    /**
     * The line contents that are valid cobol code.  For a comment this is everything passed the commentColumnNumber.
     */
    String cobolContent

    /**
     * This is any content that is priror to the commentColumnNumber.
     */
    String preCobolContent

    /**
     * This is everything past the last valid cobol code column number
     */
    String postCobolContent

    /**
     * True if there is an asterik in the commentColumnNumber and false otherwise.
     */
    boolean isComment

    /**
     * Whether this cobol line starts in column A or not.  For comments this is false.
     */
    boolean isColumnA

    /**
     * Whether this cobol line starts in column B or not.  For comments this is false.
     */
    boolean isColumnB

    /**
     * Does the cobol line end in a period or is it part of a larger whole?  For comments this is false.
     */
    boolean endsWithAPeriod

    /**
     * Is the cobol statement part of an SQL statement.  For comments, this is false.
     */
    boolean isPartOfSqlStatement

    /**
     * Does the line Call another program?
     */
    boolean containsCalledProgram

    /**
     * Does the cobol line start a Data Element.  For comments, this is false.
     */
    boolean isStartOfDataElement

    /**
     * Is this cobol line part of a data element?  True if it is part of a data element but not the start of a data
     * element.  False if both the start and end of the data element are on the same line.
     */
    boolean isPartOfDataElement

    /**
     * Is this cobol line the end of a data element.  It is if it ends with a period.
     */
    boolean isEndOfDataElement

    /**
     * The comment column line number.  For IBM programs it is 6.  For others it could be different.
     */
    int commentColumnNumber

    /**
     * The starting column number for Column A.
     */
    int columnANumber

    /**
     * The starting column number for Column B.
     */
    int columnBNumber

    /**
     * The last valid column for cobol code in the String
     */
    int lastCobolColumnNumber

    CobolUtils cobolUtils = new CobolUtils()

    CobolLine() {

    }

    CobolLine(String line, int lineNumber, int commentColumnNumber, int columnANumber, int columnBNumber,
                     int lastCobolColumnNumber) {
        this.commentColumnNumber = commentColumnNumber
        this.columnANumber = columnANumber
        this.columnBNumber = columnBNumber
        this.lastCobolColumnNumber = lastCobolColumnNumber
        this.isComment = cobolUtils.isComment(line, commentColumnNumber)
        this.isColumnA = this.cobolUtils.isColumnA(line)
        this.isColumnB = (!this.isComment && line.trim().size() > 11 && !this.isColumnA) ? true : false
        this.lineNumber = lineNumber
        this.line = line
        this.cobolContent = extractCobolContent(line)
        this.endsWithAPeriod = doesCobolLineEndWithParagrah()
        this.containsCalledProgram = lookForCalledProgram()
        this.preCobolContent = findPreCobolContent()
        this.postCobolContent = findPostCobolContent()
//        this.isStartOfDataElement = (!isComment && )

    }

    String findPostCobolContent(){
        String postCobolInfo = ""
        if (line.size() > lastCobolColumnNumber){
            postCobolInfo = line.substring(lastCobolColumnNumber)
        }
        return postCobolInfo
    }

    String findPreCobolContent(){
        String preCobolInfo = ""
        if (line.size() > commentColumnNumber ){
            preCobolInfo = line.substring(0, commentColumnNumber)
        }
        return preCobolInfo
    }

    /**
     * Determines if cobol content is the start of a data element by checking to see if the first character is numeric.
     * This is not a perfect algorithm by any means and will need to be improved.
     * @return
     */
    boolean determineIfCobolContentIsStartOfDataElement() {
        boolean startsWtihANumber = false
        if (!isComment) {
            def tokens = this.getLineWithoutLineNumber().tokenize(" ")
            String firstValue = tokens[0]
            if (firstValue.isNumber()) {
                startsWtihANumber = true
            }
        }
        return startsWtihANumber
    }

    /**
     * Determines if the cobol content ends with a period.
     * @return  true if the cobol content does end with a period and false otherwise.
     */
    boolean doesCobolLineEndWithParagrah(){
        return (!isComment && cobolContent.endsWith("."))
    }

    /**
     * Eliminates any extra content before and/or after the part of the line that is within the columns of a proper cobol
     * line.  If the line is a comment, the entire line is legal cobol.
     * @param line
     */
    String extractCobolContent(String line){
        String cobolLine

        if (line.size() < commentColumnNumber){
            cobolLine = ""
        }else if (line.size() < lastCobolColumnNumber){
            cobolLine = line.substring(commentColumnNumber)
        }else {
            cobolLine = line.substring(commentColumnNumber, (lastCobolColumnNumber - commentColumnNumber))
        }
        return cobolLine
    }

    /**
     * Returns true if the line is not a comment, is not in Column A and calls a program.
     */
    boolean lookForCalledProgram () {
         return !isComment && !isColumnA && cobolContent.contains(" CALL ")
    }

    String getLineWithoutLineNumber() {

        String newLine

        if (line == null || line.trim() == "" || !line[0].isNumber()){
            newLine = line
        } else {
            newLine = "      " + line.substring(6)
        }

        return newLine
    }

    String toString(){
        String str = ""

        str + "lineNumber: ${lineNumber}\n"
        str += "line:\t${line}\n"
        str += "cobolContent: '${cobolContent}'\n"
        str += "isComment: ${isComment}\n"
        str += "commentColumnNumber: ${commentColumnNumber}\n"
        str += "columnANumber: ${columnANumber}\n"
        str += "columnBNumber: ${columnBNumber}\n"
        str += "preCobolContent: ${preCobolContent}\n"
        str += "postCobolContent: ${postCobolContent}\n\n"
        return str.toString()
    }
}
