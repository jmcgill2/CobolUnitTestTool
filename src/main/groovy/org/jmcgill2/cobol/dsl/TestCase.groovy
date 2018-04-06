package org.jmcgill2.cobol.dsl

import groovy.util.logging.Slf4j

@Slf4j
class TestCase {

    /**
     * The Name of the test.
     */
    String testName

    /**
     * The description of the test.
     */
    String testDescription

    /**
     * A shorter description of the test.
     */
    String testShortDescription

    /**
     * The name of the cobol paragraph we are testing.
     */
    String paragraphName

    /**
     * The data element inputs we need to populate and the values we need to provide them.
     */
    ArrayList<TestCaseInput> inputs = []

    /**
     * The date elements we expect to change and the results we expect to have.
     */
    ArrayList<TestCaseExpectedResult> results = []

    /**
     * The changes we need to make to the paragraph to be able to test it in isolation.
     */
    ArrayList<ParagraphChange> paragraphChanges = []

    /**
     * The data elements that need to be initialized and verified if not part of the original group.  This is usually
     * when a redefined variable is referenced.
     */
    ArrayList<String> initializedVariables = []

    /**
     * The input used to generate the Test Case from a test script.
     */
    String sourceText

    /**
     * Used for testing.
     */
    public TestCase() {

    }

    /**
     * Generate a Test Case from the information provided by the test script.
     *
     * @param sourceText    String containing the test case information from the test script.
     */
    public TestCase(String sourceText){
        this.sourceText = sourceText
        setTestName()
        setTestDescription()
        setParagraphName()
        setDataInputs()
    }

    /**
     * Pulls the input data from the sourceText and processes it.
     */
    public void setDataInputs() {
        String line = sourceText.substring(sourceText.indexOf("input-data:"), sourceText.indexOf("end-input-data."))
        line = line - "end-input-data."
        line = line - "input-data:"
        line = line.trim()
        setDataInputs(line)
    }

    /**
     * Extracts the information needed from the string to generate one or more data element inputs
     * @param elementInputs String containing one or more element inputs for the test
     */
    public void setDataInputs(String elementInputs) {
        log.info("elementInputs = $elementInputs")
        String elements = elementInputs
        boolean moreElementsExist = true
        while(moreElementsExist){
//            println "elements = $elements"
            if (elements.indexOf("element:") < 0){
                moreElementsExist = false
            }else {
                String element = elements.substring(elements.indexOf("element:"), elements.indexOf("end-element."))
                element = element - "element:"
                element = element - "end-element."
                log.info("element = $element")
                inputs << new TestCaseInput(element)
                def idx = elements.indexOf("end-element.")
                if (idx > 0) {
                    println "elements = $elements"
                    elements = elements.substring(idx + 12)
                }
            }
        }
    }

    /**
     * Pulls paragraph information from the sourceText and processes it.
     */
    public void setParagraphName(){
        String line = sourceText.substring(sourceText.indexOf("paragraph-name:"), sourceText.indexOf("end-paragraph-name."))
        line = line - "paragraph-name:"
        line = line - "end-paragraph-name."
        line = line.trim()
        setParagraphName(line)
    }

    /**
     * Sets paragraph name
     * @param paragraphName String containing paragraph name.
     */
    public void setParagraphName(String paragraphName){
        this.paragraphName = paragraphName
    }

    /**
     * Generate test description from the information provided by the sourceText.
     */
    public void setTestDescription() {
        String line = sourceText.substring(sourceText.indexOf("test-description:"), sourceText.indexOf("end-test-description."))
        line = line - "test-description:"
        line = line - "end-test-description.".trim()
        line = line.trim()
        setTestDescription(line)
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription
    }

    /**
     * Generate test name from sourceText
     */
    public void setTestName(){
        String line = sourceText.substring(sourceText.indexOf("test-name:"), sourceText.indexOf("end-test-name."))
        line = line - "test-name:"
        line = line - "end-test-name."
        line = line.trim()
        setTestName(line)
    }

    public void setTestName(String testName){
        this.testName = testName
    }

    public String toString(){
        String str = "Test Case:\n"
        str += "\tTest Name:  ${testName}\n"
        str += "\tTest Description: ${testDescription}\n"
        str += "\tParagraph: ${paragraphName}\n"
        str += "\tInputs:\n"
        inputs.each{
            str += "\t\t${it}"
        }
        return str
    }

}
