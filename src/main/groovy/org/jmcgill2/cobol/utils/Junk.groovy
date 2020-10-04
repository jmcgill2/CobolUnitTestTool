package org.jmcgill2.cobol.utils

import groovy.util.logging.Slf4j

@Slf4j
class Junk {

    public static void main(String[] args) {
        Junk junk = new Junk()
        junk.run()
    }

    public void run(){
        File file = new File("/Users/jimmcgill/vagrant/Example2.cob")
        file.eachLine{println it}
    }
}
