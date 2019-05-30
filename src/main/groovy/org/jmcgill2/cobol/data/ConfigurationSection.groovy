package org.jmcgill2.cobol.data

import groovy.util.logging.Slf4j

/**
 * Contains all the information about the Configuration-Section in the Environment Division.
 */
@Slf4j
class ConfigurationSection {

    String sourceComputer

    String objectComputer

    ArrayList<CobolLine> cobolLines

    ConfigurationSection(){

    }

    ConfigurationSection(String sourceComputer, String objectComputer){
        this.sourceComputer = sourceComputer
        this.objectComputer = objectComputer
    }

    ConfigurationSection(String sourceComputer, String objectComputer, ArrayList<CobolLine> cobolLines){
        this.sourceComputer = sourceComputer
        this.objectComputer = objectComputer
        this.cobolLines = cobolLines
    }

    String toString(){
        String str = "        CONFIGURATION SECTION.\n"
        str += "           SOURCE-COMPUTER. ${sourceComputer}.\n"
        str += "           OBJECT-COMPUTER. ${objectComputer}.\n"

        return str.toString()
    }
}
