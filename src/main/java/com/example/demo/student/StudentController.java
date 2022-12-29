package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {


    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public List<Student> renderList(){
         return studentService.renderList();
    }

    @PostMapping()
    public void RegisterNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }



    @DeleteMapping(path = "{studentId}")
    public void DeleteStudent(@PathVariable("studentId")Long id){

        studentService.deleteStudent(id);

    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId ,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email){
       studentService.updateStudent(studentId,name, email);

    }


}
