package com.spring.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.spring.dto.DepartmentDTO;
import com.spring.dto.StudentDTO;
import com.spring.mapper.StudentMapper;

@Service
public class StudentService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^010-\\d{4}-\\d{4}$");

    private final StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public List<StudentDTO> findAllStudents() {
        return studentMapper.findAllStudents();
    }

    public List<StudentDTO> searchStudents(String type, String keyword) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("keyword", keyword == null ? "" : keyword.trim());
        return studentMapper.searchStudents(map);
    }

    public List<DepartmentDTO> findAllDepartments() {
        return studentMapper.findAllDepartments();
    }

    public StudentDTO findById(Long id) {
        return studentMapper.findById(id);
    }

    public void insertStudent(StudentDTO student) {
        validateAndFormatStudent(student);
        if (findAllStudents().stream().anyMatch(s -> s.getSno().equals(student.getSno()))) {
            throw new IllegalArgumentException("이미 존재하는 학번입니다.");
        }
        studentMapper.insertStudent(student);
    }

    public void updateStudent(StudentDTO student) {
        validateAndFormatStudent(student);
        StudentDTO eStudent = studentMapper.findById(student.getSid());
        if (eStudent == null) {
            throw new IllegalArgumentException("존재하지 않는 학생입니다.");
        }
        eStudent.setSno(student.getSno());
        eStudent.setSname(student.getSname());
        eStudent.setDid(student.getDid());
        eStudent.setGrade(student.getGrade());
        eStudent.setPhone(student.getPhone());
        eStudent.setEmail(student.getEmail());
        eStudent.setUpdatedAt(LocalDateTime.now());
        studentMapper.updateStudent(eStudent);
    }

    public void deleteStudent(Long id) {
        if (findAllStudents().stream().noneMatch(s -> s.getSid().equals(id))) {
            throw new IllegalArgumentException("존재하지 않는 학생입니다.");
        }
        studentMapper.deleteStudent(id);
    }

    private void validateAndFormatStudent(StudentDTO student) {
        student.setPhone(formatPhone(student.getPhone()));
        student.setEmail(formatEmail(student.getEmail()));
    }

    private String formatPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("전화번호를 입력하세요.");
        }

        String digits = phone.replaceAll("[^0-9]", "");
        if (digits.length() == 11 && digits.startsWith("010")) {
            String formattedPhone = digits.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
            if (PHONE_PATTERN.matcher(formattedPhone).matches()) {
                return formattedPhone;
            }
        }

        throw new IllegalArgumentException("전화번호는 010-0000-0000 형식으로 입력하세요.");
    }

    private String formatEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("이메일을 입력하세요.");
        }

        String formattedEmail = email.trim();
        if (!EMAIL_PATTERN.matcher(formattedEmail).matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
        return formattedEmail;
    }
}
