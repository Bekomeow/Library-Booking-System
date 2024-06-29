package com.example.SpringBoot_project_2.services;

import com.example.SpringBoot_project_2.models.Book;
import com.example.SpringBoot_project_2.models.Person;
import com.example.SpringBoot_project_2.repositories.BookRepository;
import com.example.SpringBoot_project_2.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll(boolean sortByDate) {
        if(sortByDate) {
            return bookRepository.findAll(Sort.by("dateOfIssue"));
        }
        else {
            return bookRepository.findAll();
        }
    }

    public List<Book> findAll(int page, int booksPerPage, boolean sortByDate) {
        if(sortByDate) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("dateOfIssue"))).getContent();
        }
        else {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }

    public Book findOne(int id) {
        Optional<Book> foundPerson = bookRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Book person) {
        bookRepository.save(person);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        updatedBook.setOwner(bookRepository.findById(id).get().getOwner());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchByName(String name) {
        return bookRepository.searchByNameStartingWith(name);
    }

    @Transactional
    public void freeTheBook(int id) {
        Book book = bookRepository.getOne(id);
        book.getOwner().getBooks().remove(book);
        book.setOwner(null);
        book.setDateOfReceipt(null);
    }

    @Transactional
    public void assignOwner(int id, int personId) {
        Person person = peopleRepository.getOne(personId);
        Book book = bookRepository.getOne(id);
        book.setDateOfReceipt(new Date());

        book.setOwner(person);

        List<Book> books = person.getBooks();

        if(books == null) {
            person.setBooks(Collections.singletonList(book));
        }
        else {
            books.add(book);
        }
    }

    public void isOverdue(List<Book> books) {
        long now = new Date().getTime();
        long tenDaysInMillis = 864000000;
        for(Book book: books) {
            long dateOfReceipt = book.getDateOfReceipt().getTime();

            if(now-dateOfReceipt > tenDaysInMillis) {
                book.setOverdue(true);
            }
            else {
                book.setOverdue(false);
            }
        }
    }
}
