package org.jmcgill2.cobol.data

import groovy.util.logging.Slf4j

@Slf4j
class FileDefinition {

    ArrayList<CobolLine> fileDefinitionLines

    ArrayList<DataElement> dataElements = []

    String fdName

    String blockContains

    String recordingMode

    String labelRecords

    String dataRecord

    String recordContains

    public FileDefinition() {

    }

    public FileDefinition(ArrayList<CobolLine> cobolLines, int startLine){
        int endLine = findEndLine(cobolLines, startLine)
    }

    public FileDefinition(ArrayList<CobolLine> cobolLines, int startLine, int endLine) {


    }

    public int findEndLine(ArrayList<CobolLine> cobolLines, int startLine){
        int endLineNum

        boolean atEnd = false
        int begin = startLine
        while(!atEnd){
            begin++
            if (cobolLines.size() < begin) {
                CobolLine cLine = cobolLines[begin]
                if (cLine.line.trim().endsWith(".")){
                    atEnd = true
                    endLineNum = begin
                }else if (cLine.lineWithoutLineNumber.trim().startsWith("FD ") ||
                        cLine.lineWithoutLineNumber.trim().startsWith("Working-Storage")){
                    atEnd = true
                    endLineNum = begin - 1
                }
            } else {
                atEnd = true
                endLineNum = -1
                log.info("Error - FD Not finished.")
            }
        }

        return endLineNum
    }
}
