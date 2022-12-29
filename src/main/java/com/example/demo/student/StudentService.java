package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class StudentService {



    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> renderList(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> student1  = studentRepository.findStudentByEmail(student.getEmail());
        if (student1.isPresent()){
            throw  new IllegalStateException("emailTaken");
        }

        studentRepository.save(student);
        System.out.println(student);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.findById(studentId);
        boolean exists = studentRepository.existsById(studentId);
        if(!exists)
            throw new IllegalStateException("student with "+ studentId+ "doesn't exists ");


        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {

        Student student = studentRepository.findById(studentId).orElseThrow(()-> new IllegalArgumentException("student with id "+studentId+"doesn't exist"));

        //!Objects.equals(student.getEmail(),email) name provided is not the same as the current one
        if (name != null && name.length() >0 && !Objects.equals(student.getName(),name)){
            student.setName(name);
        }

        if (email != null && email.length() >0 && !Objects.equals(student.getEmail(),email) ){
            Optional<Student> student2  = studentRepository.findStudentByEmail(student.getEmail());
            if (student2.isPresent())
                throw new IllegalStateException("emailTaken");

            student.setEmail(email);
        }


    }
}
