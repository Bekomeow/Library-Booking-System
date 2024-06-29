package com.example.SpringBoot_project_2.services;

import com.example.SpringBoot_project_2.models.Book;
import com.example.SpringBoot_project_2.models.Person;
import com.example.SpringBoot_project_2.repositories.BookRepository;
import com.example.SpringBoot_project_2.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    private final BookRepository bookRepository;

    @Autowired
    public PeopleService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        }
        else {
            return Collections.emptyList();
        }
    }
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public void deleteById(int id) {
        peopleRepository.deleteById(id);
    }
}
