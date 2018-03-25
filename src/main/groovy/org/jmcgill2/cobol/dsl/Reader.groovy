package org.jmcgill2.cobol.dsl
/**
 * Reads the DSL Test Scripts and generates a Test Plan
 */
class Reader {

    String text

    public static void main(String[] args) {
        String text = getClass().getResourceAsStream("/generic_test_script.txt").getText()

        text = text.replaceAll("\n", " ")

        Reader reader = new Reader(text)

        TestPlan testPlan = reader.createTestPlan()

    }

    public Reader() {

    }

    public Reader(String fileText){
        text = fileText
    }

    public TestPlan createTestPlan() {
        TestPlan testPlan = new TestPlan()

        SourceProgram sourceProgram = new SourceProgram(getSourceProgramText())
        TargetProgram targetProgram = new TargetProgram(getTargetProgramText())
        ArrayList<TestCase> tests = generateTestCases(getTestCasesText())
        tests.each{println it}

        return testPlan

    }

    public ArrayList<TestCase> generateTestCases(String testCases){
        ArrayList<TestCase> cases = []
        boolean stillCreatingTestCases = true
        int start = testCases.indexOf(("test-case:"))
        if (start < 0){
            stillCreatingTestCases = false
        }

        while(stillCreatingTestCases) {
            cases << new TestCase(testCases.substring(start, testCases.indexOf("end-test-case.")))
            start = testCases.indexOf("end-test-case.")
            if (start < 0){
                   stillCreatingTestCases = false
            }else {
                testCases = testCases.substring(start)
            }
            start = testCases.indexOf("test-case:")
            if (start < 0){
                stillCreatingTestCases = false
            }else {
                testCases = testCases.substring(start)
                start = 0
            }
        }

        return cases

    }

    public String getTestCasesText(){
        String sourceText = text.substring(text.indexOf("test-cases:"), text.indexOf("end-test-cases."))
        sourceText = sourceText - "test-cases:"
        sourceText = sourceText - "end-test-cases."
        sourceText = sourceText.trim()

        return sourceText
    }

    public String getSourceProgramText() {
        String sourceText =  text.substring(text.indexOf("source:"), text.indexOf("end-source."))
        sourceText = sourceText - "source:"
        sourceText = sourceText - "end-source."
        sourceText = sourceText.trim()

        return sourceText
    }
    public String getTargetProgramText() {
        String sourceText =  text.substring(text.indexOf("target:"), text.indexOf("end-target."))
        sourceText = sourceText - "target:"
        sourceText = sourceText - "end-target."
        sourceText = sourceText.trim()

        return sourceText
    }

}
