package org.jmcgill2.cobol.dsl

import spock.lang.Specification

class TestCaseTest extends Specification {
    def "SetDataInputs using sourceText"() {

        TestCase tc = new TestCase()
        tc.sourceText = """input-data:
                            element:
                                D-BK21-9RPCNT:NUMBER:0
                            end-element.
                            element:
                                D-BK21-9TPCNT:NUMBER:0
                            end-element.
                        end-input-data."""
        when:
        tc.setDataInputs()

        then:
            tc.inputs.size() > 0
    }

    def "SetDataInputs with a single input"() {
        TestCase tc = new TestCase()

        String inputData = """input-data:
                            element:
                                D-BK21-9RPCNT:NUMBER:0
                            end-element.
                        end-input-data."""

        when:
            tc.setDataInputs(inputData)
        then:
           tc.inputs.size() == 1
    }

    def "SetDataInputs with multiple inputs"() {

        TestCase tc = new TestCase()

        String inputData = """input-data:
                            element:
                                D-BK21-9RPCNT:NUMBER:0
                            end-element.
                            element:
                                D-BK21-9TPCNT:NUMBER:0
                            end-element.
                        end-input-data."""

        when:
        tc.setDataInputs(inputData)
        then:
        tc.inputs.size() == 2
    }

    def "SetDataInputs error due to badly formed request"() {
        TestCase tc = new TestCase()

        String inputData = """input-data:
                            element:
                                D-BK21-9RPCNT:NUMBER:0
                            .
                        end-input-data."""
        boolean error = false

        when:
            try {
                tc.setDataInputs(inputData)
            }catch(Exception e){
                error = true
            }
        then:
            error
    }

    def "SetParagraphName using sourceText"() {

        TestCase tc = new TestCase()

        String inputData = """    paragraph-name:
                                F90D1
                            end-paragraph-name."""

        when:
            tc.sourceText = inputData
            tc.setParagraphName()
        then:
            tc.paragraphName == 'F90D1'
    }

    def "SetParagraphName"() {
        TestCase tc = new TestCase()

        when:
            tc.setParagraphName("NEWNAME")
        then:
            tc.paragraphName == "NEWNAME"
    }

    def "SetTestDescription using sourceText"() {
        TestCase tc = new TestCase()
        tc.sourceText = """    test-description:
                                    Sql Call returns row not found
                                end-test-description.""".replaceAll("\n", "")

        when:
            tc.setTestDescription()
        then:
            tc.testDescription == "Sql Call returns row not found"
    }

    def "SetTestDescription"() {
        TestCase tc = new TestCase()

        when:
            tc.setTestDescription("TEST DESCRIPTION")
        then:
            tc.testDescription == "TEST DESCRIPTION"
    }

    def "SetTestName using sourceText"() {
        TestCase tc = new TestCase()
        tc.sourceText = """    test-name:
                            Test F90D1-1
                        end-test-name.""".replaceAll("\n", "")

        when:
            tc.setTestName()
        then:
            tc.testName == "Test F90D1-1"
    }

    def "SetTestName"() {
        TestCase tc = new TestCase()

        when:
            tc.setTestName("TEST NAME")
        then:
            tc.testName == "TEST NAME"
    }

    def "ToString test"() {
        String sourceText = """source:
                            name:WMS910_PGM.txt
                            location:/Users/jamesmcgill/mutual/cobol/
                            locationType:file
                            copybooks:[/Users/jamesmcgill/mutual/copybooks/, /Users/jamesmcgill/mutual/dqlcopy/]
                        end-source.
                        
                        target:
                            name:TST_PGM.txt
                            location:/Users/jamesmcgill/mutual/cobol/
                            targetType:file
                        end-target.
                        
                        test-cases:
                        test-case:
                            test-name:
                                Test F90D1-1
                            end-test-name.
                        
                            paragraph-name:
                                F90D1
                            end-paragraph-name.
                        
                            test-description:
                                Sql Call returns row not found
                            end-test-description.
                        
                            input-data:
                                element:
                                    D-BK21-9RPCNT:NUMBER:0
                                end-element.
                                element:
                                    D-BK21-9TPCNT:NUMBER:0
                                end-element.
                            end-input-data.
                        
                            overlays:
                            end-overlays.
                        
                            expected-values:
                                D-BK21-9RPCNT:NUMBER:0
                                count-times:NUMBER:1
                            end-expected-values.
                        
                        end-test-case.
                        end-test-cases."""
        TestCase tc = new TestCase(sourceText)

        when:
            String ts = tc.toString()
        then:
            ts != null
    }
}
