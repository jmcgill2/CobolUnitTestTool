package org.jmcgill2.dsl

import org.jmcgill2.org.jmcgill.testdsl.TestPlan

/**
 * Reads the DSL Test Scripts and generates a Test Plan
 */
class Reader {

    String text

    public static void main(String[] args) {
        String text = getClass().getResourceAsStream("/generic_test_script.txt").getText()

        text = text.replaceAll("\n", " ")



    }

    public Reader() {

    }

    public Reader(String fileText){

    }

    public TestPlan createTestPlan() {
        TestPlan testPlan = new TestPlan()

        return testPlan

    }
}
