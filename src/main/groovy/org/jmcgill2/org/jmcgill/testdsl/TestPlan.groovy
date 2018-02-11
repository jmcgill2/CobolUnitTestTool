package org.jmcgill2.org.jmcgill.testdsl

class TestPlan {

    /**
     * The Cobol program that we are testing.
     */
    SourceProgram sourceProgram

    /**
     * The new Cobol Program that tests the individual paragraphs of the source program.
     */
    TargetProgram targetProgram

    /**
     * List of paragraph tests that can be run.  More than one test can be run for each paragraph.
     */
    ArrayList<ParagraphTest> paragraphTests = []

    public TestPlan() {

    }

    public TestPlan(SourceProgram sourceProgram, TargetProgram targetProgram, ArrayList<ParagraphTest> paragraphTests){

        this.sourceProgram = sourceProgram

        this.targetProgram = targetProgram

        this.paragraphTests = paragraphTests
    }
}
