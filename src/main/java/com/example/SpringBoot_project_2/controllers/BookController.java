package com.example.SpringBoot_project_2.controllers;

import com.example.SpringBoot_project_2.models.Book;
import com.example.SpringBoot_project_2.models.Person;
import com.example.SpringBoot_project_2.services.BookService;
import com.example.SpringBoot_project_2.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final PeopleService peopleService;
    private final BookService bookService;

    @Autowired
    public BookController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                        @RequestParam(value = "books_per_page", required = false, defaultValue = "0") int booksPerPage,
                        @RequestParam(value = "sort_by_date", required = false, defaultValue = "false") boolean sortByDate
    ) {
        if(page == 0 || booksPerPage == 0) {
            model.addAttribute("books", bookService.findAll(sortByDate));
        }
        else {
            model.addAttribute("books", bookService.findAll(page-1, booksPerPage, sortByDate));
        }
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("owner", bookService.findOne(id).getOwner());
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("person", new Person());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, @PathVariable("id") int id) {
        book.setId(id);
        bookService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/free")
    public String freeTheBook(@PathVariable("id") int id) {
        bookService.freeTheBook(id);
        return "redirect:/books/"+id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        System.out.println("assign book BEKOOOOOOOOOOOO");
        bookService.assignOwner(id, person.getId());
        return "redirect:/books/"+id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookService.searchByName(query));
        return "books/search";
    }
}
