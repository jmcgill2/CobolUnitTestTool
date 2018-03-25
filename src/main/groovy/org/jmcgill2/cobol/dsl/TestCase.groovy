package org.jmcgill2.cobol.dsl

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
        println "Test case = $sourceText"
        setTestName()
        setTestDescription()
    }

    /**
     * Generate test description from the information provided by the test script.
     */
    public void setTestDescription() {
        String line = sourceText.substring(sourceText.indexOf("test-description:"), sourceText.indexOf("end-test-description."))
        line = line - "test-description:"
        line = line - "end-test-description.".trim()
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
        line = line - "end-test-name.".trim()
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
        return str
    }

}
