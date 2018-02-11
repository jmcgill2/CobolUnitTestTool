package org.jmcgill2.cobol.services

import org.jmcgill2.cobol.utils.FileUtils

class FileRetrievalService implements RetrievalInterface{

    FileUtils fileUtils = new FileUtils()

    @Override
    ArrayList<String> retrieveProgram(String programLocation, String programName) {
        ArrayList<String> lines = []

        File f = new File("${programLocation}${programName}")
        f.eachLine {String line ->
            lines << line
        }

        return lines
    }



    @Override
    ArrayList<String> retrieveCopybook(String copybookLocation, String copybookName){
        ArrayList<String> lines = []
        File f = new File("${copybookLocation}${copybookName}")
        if (f.exists()){
            f.eachLine{String line ->
                lines << line
            }
        }
        return lines

    }

    Map<String, ArrayList<String>> retrieveCopybooks(ArrayList<String>copybookLocations, ArrayList<String> copybooks) {
        Map<String, ArrayList<String>> books = [:]

        copybooks.each{String copybook ->
            boolean fileNotFound = true
            int k = 0
            int maxLocations = copybookLocations.size() - 1
            while (fileNotFound && k <= maxLocations){
                String directory = copybookLocations[k]
                k++
                File f = new File("${directory}${copybook}")
                if (f.exists()){
                    fileNotFound = false
                    ArrayList<String> fileLines = []
                    f.eachLine{String line ->
                        fileLines << line
                    }
                    books.put(copybook, fileLines)
                }

            }
            if (fileNotFound){
                log.error("Failed to find Copybook ${copybook}!!!")
            }

        }

        return books

    }
}
