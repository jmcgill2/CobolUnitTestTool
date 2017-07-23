package org.jmcgill2.cobol.utils

/**
 * Created by jamesmcgill on 5/12/17.
 */
class FileUtils {

    String dirNameMustContain = "test"

    public FileUtils () {

    }

    public void makeDir(String path){

        if (path.contains(dirNameMustContain)) {
            File f = new File(path)
            f.mkdirs()
        }else{
            println "Cannot create a Directory that does not contain '${dirNameMustContain}' in the path!"
        }
    }

    public void makeDirUnlessExists(String path){
        if (path.contains(dirNameMustContain)) {
            File f = new File(path)
            if (!f.isFile() && !f.isDirectory()) {
                f.mkdirs()
            } else {
                if (f.isFile()) {
                    println "Cannot create Directory with same name as an existing File"
                } else {
                    println "Directory already exists!"
                }
            }
        }else{
            println "Cannot create a Directory that does not contain '${dirNameMustContain}' in the path!"
        }
    }

    public ArrayList<String> readFile(File f){
        ArrayList<String> lines = []

        f.eachLine{
            lines << it
        }

        return lines
    }
}
