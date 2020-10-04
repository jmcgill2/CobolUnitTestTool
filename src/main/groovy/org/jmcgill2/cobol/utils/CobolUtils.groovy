package org.jmcgill2.cobol.utils

import org.jmcgill2.cobol.data.CobolLine

/**
 * Created by jamesmcgill on 5/12/17.
 */
class CobolUtils {

    FileUtils fileUtils = new FileUtils()

    int commentColumnNumber

    int columnANumber

    int columnBNumber

    public CobolUtils() {


    }

    public CobolUtils(int commentColumnNumber, int columnANumber, int columnBNumber){
        this.commentColumnNumber = commentColumnNumber
        this.columnANumber = columnANumber
        this.columnBNumber = columnBNumber
    }

    public void identifySqlLines(ArrayList<CobolLine> cobolLines){
        boolean isSql = false

        cobolLines.each{CobolLine cl ->
            if (isSql){
                if (!cl.isComment && cl.line.contains("END-EXEC")){
                    cl.isPartOfSqlStatement = true
                    isSql = false
                }else {
                    cl.isPartOfSqlStatement = true
                }
            }else {
                if (!cl.isComment && cl.line.contains("EXEC SQL")){
                    isSql = true
                    cl.isPartOfSqlStatement = true
                    if (cl.line.contains("END-EXEC")){
                        isSql = false
                    }
                }
            }
        }
    }

    public ArrayList<String> readCobolFile(File f){
        return fileUtils.readFile(f)
    }

    public isComment(String line){
        return isComment(line, commentColumnNumber)
    }

    public boolean isComment(String line, int _commentColumnNumber){

        boolean isComment = false
        if (line != null && line.size() >= _commentColumnNumber) {
            if (line.substring(_commentColumnNumber).startsWith("*")) {
                isComment = true
            }
        }

        return isComment
    }

    public isColumnA(String line){
        boolean isColumnA = false

        if (line != null && line.size() >= 10) {
            if (!isComment(line) && line.substring(7, 10).trim() != "") {
                isColumnA = true
            }
        }

        return isColumnA
    }

    public CobolLine findFirstNonCommentCobolLine(ArrayList<CobolLine> cobolLines){

        CobolLine cobolLine

        boolean stillLooking = true

        int maxLines = (cobolLines.size() == 0) ? 0 : cobolLines.size() - 1

        int x = 0

        while (x < maxLines && stillLooking){
            if (!cobolLines[x].isComment) {
                cobolLine = cobolLines[x]
                stillLooking = false
            }
            x++
        }

        return cobolLine
    }

}
