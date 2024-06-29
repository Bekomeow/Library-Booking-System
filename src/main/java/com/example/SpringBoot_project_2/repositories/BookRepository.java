package com.example.SpringBoot_project_2.repositories;

import java.util.List;

import com.example.SpringBoot_project_2.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    public void deleteById(int id);

    public List<Book> searchByNameStartingWith(String name);
}
