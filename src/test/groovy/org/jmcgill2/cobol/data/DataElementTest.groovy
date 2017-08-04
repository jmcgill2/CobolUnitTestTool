package org.jmcgill2.cobol.data

import spock.lang.Specification
import spock.lang.Unroll

class DataElementTest extends Specification {

    @Unroll("Generated data element name '#name' from line '#line'")
    def "Generate Data Element Names"(){
        setup: "Create Data Element with Data Element Line"
        DataElement de = new DataElement(dataElementLine: line)

        expect: 'Invoke generateDataElementName method'
        de.generateDataElementName() == name

        where:

        name                | line
        ""                  | ""
        ""                  | "     "
        ""                  | "  01  "
        "DATA-ELEMENT-NAME" | " 01    DATA-ELEMENT-NAME  "
        "DATA-ELEMENT-NAME" | " 01    DATA-ELEMENT-NAME  REDEFINES  "
        "DATA-ELEMENT-NAME" | ' 01    DATA-ELEMENT-NAME. '
    }

    @Unroll ("Generated data element level '#level' from line '#line'" )
    def "Generate Data Element Level"() {
        setup: "Create data element with data element level"
        DataElement de = new DataElement(dataElementLine: line)

        expect: 'Invoke generateDataElementLevel method'
        de.generateDataElementLevel() == level

        where:

        level | line
        ""    | ""
        ""    | "     "
        "01"  | "   01   "
        "01"  | "   01  DATA-ELEMENT-NAME "
        "88"  | "88 DATA-ELEMENT-NAME"
    }


}
