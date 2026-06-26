package com.spring.controller;

import com.spring.dto.BookDTO;
import com.spring.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  // TODO R — GET /books
  @GetMapping
  public String list(Model model) {
    // model.addAttribute("books", bookService.findAll());
  }

  // TODO R — GET /books/{id}
  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model) {
    // model.addAttribute("book", bookService.findById(id));
  }

  // TODO C — GET /books/new
  @GetMapping("/new")
  public String form(Model model) {
    // model.addAttribute("book", new BookDTO());
  }

  // TODO C — POST /books
  @PostMapping
  public String create(@ModelAttribute BookDTO book) {
    // bookService.save(book);
    // return "redirect:/books";
  }

  // TODO U — GET /books/{id}/edit
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {
    // model.addAttribute("book", bookService.findById(id));
  }

  // TODO U — POST /books/{id}/edit
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable Long id, @ModelAttribute BookDTO book) {
    // book.setBookId(id);
    // bookService.update(book);
    // return "redirect:/books/" + id;
  }

  // TODO D — POST /books/{id}/delete
  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id) {
    // bookService.deleteById(id);
    // return "redirect:/books";
  }
}
