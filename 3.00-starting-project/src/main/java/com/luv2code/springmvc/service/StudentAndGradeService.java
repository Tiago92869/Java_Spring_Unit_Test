package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    @Autowired
    @Qualifier("mathGrades")
    private MathGrade mathGrade;

    @Autowired
    @Qualifier("scienceGrades")
    private ScienceGrade scienceGrade;

    @Autowired
    @Qualifier("historyGrades")
    private HistoryGrade historyGrade;

    @Autowired
    private StudentGrades studentGrades;

    public void createStudent(String firstname, String lastname, String email){

        CollegeStudent collegeStudent = new CollegeStudent(firstname, lastname, email);
        collegeStudent.setId(0);
        studentDao.save(collegeStudent);

    }

    public boolean checkIfStudentIsNull(int id){

        Optional<CollegeStudent> student = studentDao.findById(id);

        if(student.isPresent()){
            return true;
        }
        return false;
    }

    public void deleteStudent(int i) {

        if(checkIfStudentIsNull(i)){
            studentDao.deleteById(i);
            mathGradesDao.deleteByStudentId(i);
            scienceGradesDao.deleteByStudentId(i);
            historyGradesDao.deleteByStudentId(i);
        }
    }

    public Iterable<CollegeStudent> getGradebook() {

        Iterable<CollegeStudent> collegeStudents = studentDao.findAll();
        return collegeStudents;
    }

    public boolean createGrade(double grade, int studentId, String gradeType){

        if(!checkIfStudentIsNull(studentId)){
            return false;
        }

        if(grade >= 0 && grade <= 100){
            if(gradeType.equals("math")){
                mathGrade.setId(0);
                mathGrade.setGrade(grade);
                mathGrade.setStudentId(studentId);
                mathGradesDao.save(mathGrade);

                return true;
            }

            if(gradeType.equals("science")){
                scienceGrade.setId(0);
                scienceGrade.setGrade(grade);
                scienceGrade.setStudentId(studentId);
                scienceGradesDao.save(scienceGrade);

                return true;
            }

            if(gradeType.equals("history")){
                historyGrade.setId(0);
                historyGrade.setGrade(grade);
                historyGrade.setStudentId(studentId);
                historyGradesDao.save(historyGrade);

                return true;
            }
        }
        return false;
    }

    public int deleteGrade(int id, String gradeType) {

        int studentId = 0;

        if(gradeType.equals("math")){
            Optional<MathGrade> grade = mathGradesDao.findById(id);
            if(!grade.isPresent()){
                return studentId;
            }
            studentId = grade.get().getStudentId();
            mathGradesDao.deleteById(id);
        }
        if(gradeType.equals("science")){
            Optional<ScienceGrade> grade = scienceGradesDao.findById(id);
            if(!grade.isPresent()){
                return studentId;
            }
            studentId = grade.get().getStudentId();
            scienceGradesDao.deleteById(id);
        }
        if(gradeType.equals("history")){
            Optional<HistoryGrade> grade = historyGradesDao.findById(id);
            if(!grade.isPresent()){
                return studentId;
            }
            studentId = grade.get().getStudentId();
            historyGradesDao.deleteById(id);
        }

        return studentId;
    }

    public GradebookCollegeStudent studentInformation(int id) {

        if(!checkIfStudentIsNull(id)){
            return null;
        }

        Optional<CollegeStudent> student =studentDao.findById(id);
        Iterable<MathGrade> mathGrades= mathGradesDao.findGradeByStudentId(id);
        Iterable<HistoryGrade> historyGrades= historyGradesDao.findGradeByStudentId(id);
        Iterable<ScienceGrade> scienceGrades= scienceGradesDao.findGradeByStudentId(id);

        List<Grade> mathGradesList = new ArrayList<>();
        mathGrades.forEach(mathGradesList::add);

        List<Grade> historyGradesList = new ArrayList<>();
        historyGrades.forEach(historyGradesList::add);

        List<Grade> scienceGradesList = new ArrayList<>();
        scienceGrades.forEach(scienceGradesList::add);

        studentGrades.setMathGradeResults(mathGradesList);
        studentGrades.setScienceGradeResults(scienceGradesList);
        studentGrades.setHistoryGradeResults(historyGradesList);

        GradebookCollegeStudent gradebookCollegeStudent= new GradebookCollegeStudent(student.get().getId(),
                student.get().getFirstname(), student.get().getLastname(), student.get().getEmailAddress(), studentGrades);

        return gradebookCollegeStudent;
    }

    public void configureStudentInformationModel(int id, Model m){

        GradebookCollegeStudent student = studentInformation(id);

        m.addAttribute("student", student);

        if(student.getStudentGrades().getMathGradeResults().size() > 0){
            m.addAttribute("mathAverage", student.getStudentGrades().findGradePointAverage(student.getStudentGrades().getMathGradeResults()));
        }else {
            m.addAttribute("mathAverage", "N/A");
        }

        if(student.getStudentGrades().getScienceGradeResults().size() > 0){
            m.addAttribute("scienceAverage", student.getStudentGrades().findGradePointAverage(student.getStudentGrades().getScienceGradeResults()));
        }else {
            m.addAttribute("scienceAverage", "N/A");
        }

        if(student.getStudentGrades().getHistoryGradeResults().size() > 0){
            m.addAttribute("historyAverage", student.getStudentGrades().findGradePointAverage(student.getStudentGrades().getHistoryGradeResults()));
        }else {
            m.addAttribute("historyAverage", "N/A");
        }
    }
}
