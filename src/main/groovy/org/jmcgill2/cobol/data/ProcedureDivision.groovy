package org.jmcgill2.cobol.data

import org.jmcgill2.cobol.utils.CobolUtils

/**
 * Contains all the information needed for the procedure division
 * Created by jamesmcgill on 5/12/17.
 */
class ProcedureDivision {

    ArrayList<CobolParagraph> paragraphs = []

    ArrayList<CobolLine> procedureLines = []

    CobolUtils cobolUtils = new CobolUtils()

    public ProcedureDivision() {

    }

    public ProcedureDivision(File f){

    }

    public ProcedureDivision(ArrayList<String> cobolLines){

        findProcedureDivisionLines(cobolLines)
        cobolUtils.identifySqlLines(procedureLines)
        populateParagraphs()

    }

    private void populateParagraphs() {

        CobolParagraph paragraph

        ArrayList<CobolLine> cobolLines = []

        procedureLines.eachWithIndex { CobolLine entry, int i ->
            if (entry.isColumnA && !entry.lineWithoutLineNumber.trim().contains("PROCEDURE DIVISION")){
                if (paragraph == null && cobolLines == []){
                    cobolLines << entry
                }else {
                    paragraph = new CobolParagraph(cobolLines)
                    paragraphs << paragraph
                    cobolLines = []
                    cobolLines << entry
                }
            }else {
                if (cobolLines.size() > 0){
                    cobolLines << entry
                }
            }
        }

        paragraphs << new CobolParagraph(cobolLines)

    }

    private void findProcedureDivisionLines(ArrayList<String> lines){

        boolean procedureDivisionFound = false

        lines.eachWithIndex{String line, idx ->
            if (!procedureDivisionFound){
                if (!cobolUtils.isComment(line)){
                    if (line.contains("PROCEDURE DIVISION")){
                        procedureDivisionFound = true
                    }
                }
            }
            if (procedureDivisionFound) {
                procedureLines << new CobolLine(line, idx)
            }
        }
    }

    public ArrayList<CobolLine> getCobolLines() {
        return procedureLines
    }

}
