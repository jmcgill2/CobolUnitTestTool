package org.jmcgill2.cobol.dsl

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
    ArrayList<TestCase> tests = []

    public TestPlan() {

    }

    public TestPlan(SourceProgram sourceProgram, TargetProgram targetProgram, ArrayList<TestCase> tests){

        this.sourceProgram = sourceProgram

        this.targetProgram = targetProgram

        this.tests = tests
    }
}
