package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DisplayNameGeneration(DisplayNameGenerator.Simple.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
        //System.out.println("BeforeEach executes before the execution of each test method");
    }

    @Test
    @DisplayName("Equals and Not Equals")
    @Order(1)
    void testEqualsOrNotEquals(){

        //System.out.println("Running test: testEqualsOrNotEquals");

        assertEquals(6, demoUtils.add(2, 4), "must be 6");
        assertNotEquals(6, demoUtils.add(1, 9), "must not be 6");

    }

    @Test
    @DisplayName("Null and Not Null")
    @Order(4)
    void testNullOrNotNull(){

        //System.out.println("Running test: testNullOrNotNull");

        String str1 = null;
        String str2 = "i love u";

        assertNull(demoUtils.checkNull(str1), "should be null");
        assertNotNull(demoUtils.checkNull(str2), "should not be null");

    }

    @DisplayName("Same and Not Same")
    @Test
    @Order(3)
    void testSameAndNotSame(){

        String str = "luv2code";
        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Object should refer to same object");
        assertNotSame(str, demoUtils.getAcademy(), "Object should not refer to the same");

    }

    @DisplayName("True and False")
    @Test
    @Order(2)
    void testTrueAndFalse(){

        int grade1 = 10;
        int grade2 = 5;

        assertTrue(demoUtils.isGreater(grade1, grade2), "This should return true");
        assertFalse(demoUtils.isGreater(grade2, grade1), "This should return false");
    }

    @DisplayName("Array Equals")
    @Test
    void testArrayEquals(){

        String [] stringArray = {"A", "B", "C"};

        assertArrayEquals(stringArray, demoUtils.getFirstThreeLettersOfAlphabet(), "Arrays should be equal");
    }


    @DisplayName("Iterable Equals")
    @Test
    void testIterableEquals(){
        List<String> theList = List.of("luv", "2", "code");
        assertIterableEquals(theList, demoUtils.getAcademyInList(), "Iterable should be equal");
    }

    @DisplayName("Lines match")
    @Test
    void testLinesMatch(){
        List<String> theList = List.of("luv", "2", "code");
        assertLinesMatch(theList, demoUtils.getAcademyInList(), "Lines should match");
    }


    @DisplayName("Throws and Does not Throw")
    @Test
    void testThrowsAndDoesNotThrow(){
        assertThrows(Exception.class, () ->{demoUtils.throwException(-1);}, "Should throw exception");
        assertDoesNotThrow(() ->{demoUtils.throwException(+ 1);}, "Should not throw exception");
    }


    @DisplayName("Timeout")
    @Test
    void testTimeout(){
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {demoUtils.checkTimeout();}, "Method should execute in 3 seconds");
    }

    @DisplayName("Multiply")
    @Test
    void testMultiply(){
        assertEquals(4, demoUtils.multiply(2, 2), "The result should be 4");
    }

    /*
    @AfterEach
    void tearDownsAfterEach(){
        System.out.println("Running @AfterEach");
    }

    @BeforeAll
    static void setupBeforeEachClass(){
        System.out.println("BeforeAll executes before the execution of any test method");
    }

    @AfterAll
    static void setupAfterEachClass(){
        System.out.println("AfterAll executes after the execution of all test method");
    }
    */
}
