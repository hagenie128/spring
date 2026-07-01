package com.spring.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spring.dto.DepartmentDTO;
import com.spring.dto.StudentDTO;

@Mapper
public interface StudentMapper {

    List<StudentDTO> findAllStudents();

    List<StudentDTO> searchStudents(Map<String, Object> map);

    List<DepartmentDTO> findAllDepartments();

    StudentDTO findById(Long id);

    void insertStudent(StudentDTO student);

    void updateStudent(StudentDTO eStudent);

    void deleteStudent(Long id);

}
