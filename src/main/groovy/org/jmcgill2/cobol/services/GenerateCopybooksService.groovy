package org.jmcgill2.cobol.services

import org.jmcgill2.cobol.data.CobolCopybook
import org.jmcgill2.cobol.data.CobolLine
import org.jmcgill2.cobol.data.ProgramElements

class GenerateCopybooksService {

    /**
     * List of Cobol Lines from which to search for copybooks
     */
    ArrayList<CobolLine> cobolLines = []

    /**
     * Map containing the key of the copybook name and value is the list of cobol lines in that copybook
     */
    Map<String, ArrayList<String>> copybooks = [:]

    public GenerateCopybooksService() {

    }

    public GenerateCopybooksService(ArrayList<CobolLine> cobolLines, Map<String, ArrayList<String>> copybooks){
        this.copybooks = copybooks
        this.cobolLines = cobolLines
    }

    public ArrayList<CobolCopybook> generateCopybooks() {

        return generateCopybooks(copybooks, cobolLines)

    }

    /**
     * Identifies all the copybooks used in a list of CobolLines and generates a list of CobolCopybook instances containing
     * all the information needed about the copybook
     * @param books Map containing key of Copybook name and value of all the lines in the copybook
     * @param lines CobolLines from which to find the copybooks to process
     * @return List of all copybooks used in the program, each stored in a CobolCopybook instance
     */
    public ArrayList<CobolCopybook>generateCopybooks(Map<String, ArrayList<String>> books, ArrayList<CobolLine> lines){

        ArrayList<Map<String, String>> includes = []
        lines.eachWithIndex{CobolLine cl, idx ->
            if (cl.lineWithoutLineNumber.trim().startsWith("-INC")){
                int prevLine = idx - 1
                String replace = lines[prevLine].lineWithoutLineNumber.trim()
                if (replace.startsWith("++REPLACE")){
                    String tmp = replace.substring(9).trim()
                    def toks = tmp.tokenize(",")
                    String orig = toks[0].trim()
                    String tmp2 = toks[1]
                    toks = tmp2.tokenize(" ")
                    String newl = toks[0]
                    toks = cl.lineWithoutLineNumber.tokenize(" ")
                    String copybookName = toks[1]
                    includes << ["original": orig, "new": newl, "name": copybookName]
                }
            }

        }
        ArrayList<CobolCopybook> copybookList = []
        includes.each{
            ArrayList<String> copybookLines = copybooks.get(it.name)
            if (copybookLines == null){
                println "Error - could not find copybook ${it.name}"
                def x = 1/0
            }else {
                copybookList << new CobolCopybook(it.original, it.new, it.name, copybookLines)
            }
        }
        return copybookList
    }

}
