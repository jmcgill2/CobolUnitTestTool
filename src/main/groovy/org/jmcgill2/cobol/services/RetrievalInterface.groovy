package org.jmcgill2.cobol.services

interface RetrievalInterface {

    ArrayList<String> retrieveProgram(String programLocation, String programName)

    ArrayList<String> retrieveCopybook(String copybookLocation, String copybookName)

    Map<String, ArrayList<String>> retrieveCopybooks(ArrayList<String>copybookLocations, ArrayList<String>copybookNames)

}