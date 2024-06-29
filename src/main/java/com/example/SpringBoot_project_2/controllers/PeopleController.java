package com.example.SpringBoot_project_2.controllers;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.SpringBoot_project_2.models.Book;
import com.example.SpringBoot_project_2.models.Person;
import com.example.SpringBoot_project_2.services.BookService;
import com.example.SpringBoot_project_2.services.PeopleService;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public PeopleController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));

        List<Book> books = peopleService.getBooksByPersonId(id);

        bookService.isOverdue(books);

//        List<Book> books = bookService.findAll().
//                stream().filter(book -> book.getOwner() != null && book.getOwner().getId() == id).collect(Collectors.toList());

//        ????????????????????????????????????????????????????????

//        List<Book> books = peopleService.findOne(id).getBooks();

//        ????????????????????????????????????????????????????????

        model.addAttribute("books", books);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person) {
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, @PathVariable("id") int id) {
        person.setId(id);
        peopleService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.deleteById(id);
        return "redirect:/people";
    }
}
