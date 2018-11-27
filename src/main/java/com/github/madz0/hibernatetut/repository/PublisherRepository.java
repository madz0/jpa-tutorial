package com.github.madz0.hibernatetut.repository;

import com.github.madz0.hibernatetut.model.Book;
import com.github.madz0.hibernatetut.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Mohamad Zeinali [mohammad.basu@gmail.com] at 11/26/18
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, UUID> {
    @Query("select a from Publisher a join a.books")
    List<Publisher> findBooksWithQuery();

    @Query("select b from Publisher a join a.books b")
    List<Book> findBooksWithQuery2();
}
