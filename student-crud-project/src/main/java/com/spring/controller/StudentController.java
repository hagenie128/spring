package com.spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.dto.StudentDTO;
import com.spring.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list")
    public String list(Model model,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "keyword", required = false) String keyword) {
        List<StudentDTO> students;
        if (keyword != null && !keyword.isBlank()) {
            students = studentService.searchStudents(type, keyword);
            model.addAttribute("type", type);
            model.addAttribute("keyword", keyword);
        } else {
            students = studentService.findAllStudents();
        }
        model.addAttribute("students", students);
        return "/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("student", new StudentDTO());
        model.addAttribute("departments", studentService.findAllDepartments());
        return "/form";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        try {
            StudentDTO student = studentService.findById(id);
            model.addAttribute("student", student);
            model.addAttribute("departments", studentService.findAllDepartments());
            return "/form";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "redirect:/students/list";
        }
    }

    @PostMapping("/create")
    public String create(Model model, @ModelAttribute StudentDTO student) {
        try {
            studentService.insertStudent(student);
            return "redirect:/students/list";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("student", student);
            model.addAttribute("departments", studentService.findAllDepartments());
            return "/form";
        }
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute StudentDTO student) {
        try {
            studentService.updateStudent(student);
            return "redirect:/students/list";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            model.addAttribute("student", student);
            model.addAttribute("departments", studentService.findAllDepartments());
            return "/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students/list";
    }
}
