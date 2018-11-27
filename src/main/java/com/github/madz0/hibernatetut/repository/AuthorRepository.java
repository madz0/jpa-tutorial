package com.github.madz0.hibernatetut.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import com.github.madz0.hibernatetut.model.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * @author Mohamad Zeinali [mohammad.basu@gmail.com] at 11/26/18
 */
@Repository
public interface AuthorRepository extends EntityGraphJpaRepository<Author, UUID>, EntityGraphJpaSpecificationExecutor<Author> {
    @Query("select a from Author a join a.books")
    List<Author> findBooksWithQuery();

    @Query("select a from Author a join fetch a.books")
    List<Author> findBooksWithQueryFetch();
}
