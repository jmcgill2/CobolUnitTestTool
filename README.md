# CobolUnitTestTool
Cobol Unit Test Tool that allows users to create tests for cobol programs at the paragraph level.

At the moment, it is a messy combination of half baked ideas, and partially developed, completely untested code.  In a word, vaporware.

But the concept is, I believe, sound.  I think others have also worked on this problem.  There is nothing groundbreaking or new that is 
required to be successful.  

The idea in a nutshell is a JUnit for Cobol.  Since Cobol is a procedural language, you cannot just instantiate a program and call a 
method.

Instead, you will have to have a DSL script the test you want on the paragraph you want to test and that, coupled with the original program 
and copybooks will be used to generate a test program that will incorporate the original code and the code needed to do the tests and report 
out on them.
