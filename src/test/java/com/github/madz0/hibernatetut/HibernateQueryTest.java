package com.github.madz0.hibernatetut;

import com.cosium.spring.data.jpa.entity.graph.domain.DynamicEntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphType;
import com.github.madz0.hibernatetut.model.*;
import com.github.madz0.hibernatetut.repository.AuthorRepository;
import com.github.madz0.hibernatetut.repository.BookRepository;
import com.github.madz0.hibernatetut.repository.PublisherRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Mohamad Zeinali [mohammad.basu@gmail.com] at 11/26/18
 */
@Slf4j
@DataJpaTest
@RunWith(SpringRunner.class)
public class HibernateQueryTest {
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    BookRepository bookRepository;
    @Before
    public void init() {
        if(!atomicBoolean.getAndSet(true)) {
            Author author1 = new Author();
            author1.setName("author1");
            Author author2 = new Author();
            author2.setName("author2");
            Person person1 = new Person();
            person1.setFirstName("person1");
            person1.setLastName("person1i");
            Person person2 = new Person();
            person2.setFirstName("person2");
            person2.setLastName("person2i");
            Book book1 = new Book();
            book1.setName("book1");
            Publisher publisher1 = new Publisher();
            publisher1.setName("publisher1");
            Editor editor1 = new Editor();
            editor1.setSpeciality("science");
            Editor editor2 = new Editor();
            editor2.setSpeciality("Poem");

            author1.setPerson(person1);
            person1.setAuthor(author1);
            author1.setBooks(new HashSet<>(Arrays.asList(book1)));
            author2.setPerson(person2);
            person2.setAuthor(author2);
            author2.setBooks(new HashSet<>(Arrays.asList(book1)));
            book1.setAuthors(new HashSet<>(Arrays.asList(author1, author2)));
            publisher1.setBooks(new HashSet<>(Arrays.asList(book1)));
            book1.setPublisher(publisher1);
            editor1.setPublisher(publisher1);
            editor2.setPublisher(publisher1);
            publisher1.setEditorMap(Stream.of(editor1, editor2).collect(Collectors.toMap(Editor::getSpeciality, x->x)));

            authorRepository.saveAll(Arrays.asList(author1, author2));
            publisherRepository.save(publisher1);
            entityManager.flush();
            entityManager.clear();
        }
    }

    @Test
    public void testSimpleFetch() {
        List<Author> authors = authorRepository.findAll();
        assertEquals(2, authors.size());
        assertNotNull("Person should not be null", authors.get(0).getPerson());
        assertEquals(authors.get(0).getPerson().getFirstName(), "person1");
    }

    @Test
    public void testOneToManyFetch() {
        List<Publisher> publishers = publisherRepository.findAll();
        assertEquals(1, publishers.size());
    }

    @Test
    public void testManyToOneFetch() {
        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
    }

    @Test
    public void testEntityGraph() {
        Iterable<Author> authors = authorRepository.findAll(new DynamicEntityGraph(EntityGraphType.LOAD, Arrays.asList("books")));
        assertEquals("book1", authors.iterator().next().getBooks().iterator().next().getName());
    }

    @Test
    public void testJoinQuery() {
        List<Author> authors = authorRepository.findBooksWithQuery();
        assertEquals("book1", authors.get(0).getBooks().iterator().next().getName());
    }

    @Test
    public void testJoinFetch() {
        List<Author> authors = authorRepository.findBooksWithQueryFetch();
        assertEquals("book1", authors.get(0).getBooks().iterator().next().getName());
    }

    @Test
    public void testJoinQueryOneToMany() {
        List<Book> books = publisherRepository.findBooksWithQuery2();
        assertEquals("book1", books.get(0).getName());
    }
}
