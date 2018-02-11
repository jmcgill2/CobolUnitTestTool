package org.jmcgill2.cobol

import groovy.util.logging.Slf4j
import org.jmcgill2.cobol.data.CobolLine

/**
 * Processes the Program Information and turns it into a list of CopyBook classes.
 */
@Slf4j
class ProcessCopybooks {

    String copybookDirectory

    ArrayList<CobolLine> cobolLines = []

    public ProcessCopybooks () {

    }

    public ProcessCopybooks(String copybookDirectory, ArrayList<CobolLine> lines){
        this.copybookDirectory = copybookDirectory
        this.cobolLines = lines
    }
}
