package com.github.madz0.hibernatetut.repository;

import com.github.madz0.hibernatetut.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Mohamad Zeinali [mohammad.basu@gmail.com] at 11/27/18
 */
public interface BookRepository extends JpaRepository<Book, UUID> {
}
