package com.example.SpringBoot_project_2.repositories;

import com.example.SpringBoot_project_2.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    public void deleteById(int id);
}
