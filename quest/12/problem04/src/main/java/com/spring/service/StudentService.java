package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.entity.Student;
import com.spring.repository.StudentRepository;

@Service
@Transactional(readOnly = true)
public class StudentService {

	private final StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> findAll() {
		return studentRepository.findAll();
	}
}
