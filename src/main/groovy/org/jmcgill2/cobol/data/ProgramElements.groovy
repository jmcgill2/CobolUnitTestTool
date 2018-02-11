package org.jmcgill2.cobol.data

class ProgramElements {

    String programName

    ArrayList<String> programLines = []

    Map<String, ArrayList<String>> copybooks = [:]

    public ProgramElements() {

    }

    public ProgramElements(String programName){
        this.programName = programName
    }

}
