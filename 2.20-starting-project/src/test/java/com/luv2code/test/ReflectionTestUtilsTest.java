package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ReflectionTestUtilsTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent student1;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void beforeEach(){
        student1.setFirstname("Eric");
        student1.setLastname("Roby");
        student1.setEmailAddress("eric.roby@luv2code_school.com");
        student1.setStudentGrades(studentGrades);

        ReflectionTestUtils.setField(student1, "id", 1);
        ReflectionTestUtils.setField(student1, "studentGrades", new StudentGrades(new ArrayList<>(Arrays.asList(100.0, 95.0, 76.50, 91.75))));
    }

    @Test
    public void getPrivateField(){
        assertEquals(1, ReflectionTestUtils.getField(student1, "id"));
    }

    @Test
    public void invokePrivateMethod(){
        assertEquals("Eric 1", ReflectionTestUtils.invokeMethod(student1, "getFirstNameAndId"), "Fail private method not call");
    }
}
