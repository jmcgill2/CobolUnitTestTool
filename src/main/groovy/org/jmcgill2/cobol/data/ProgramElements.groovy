package org.jmcgill2.cobol.data

class ProgramElements {

    String programName

    ArrayList<String> programLines = []

    Map<String, ArrayList<String>> copybooks = [:]

    ProgramElements() {

    }

    ProgramElements(String programName){
        this.programName = programName
    }

    ProgramElements(ArrayList<String> programLines){
        this.programLines = programLines
    }

    ProgramElements(String programName, ArrayList<String>programLines){
        this.programName = programName
        this.programLines = programLines
    }

}
