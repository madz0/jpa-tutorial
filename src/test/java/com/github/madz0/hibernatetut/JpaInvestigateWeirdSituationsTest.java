package com.github.madz0.hibernatetut;

import static org.junit.Assert.assertEquals;

import com.github.madz0.hibernatetut.model.Book;
import com.github.madz0.hibernatetut.repository.BookRepository;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaInvestigateWeirdSituationsTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    EntityManager entityManager;
    Book book1 = new Book();

    @Before
    public void init() {
        book1.setName("book1");
    }

    @Test
    public void JpaWhenSettingIdToGeneratedValueIdAndPersits() {
        final UUID id = UUID.randomUUID();
        book1.setId(id);
        entityManager.persist(book1);
        entityManager.flush();
        List<Book> bookList = entityManager.createQuery("select b from Book b", Book.class)
            .getResultList();
        assertEquals(1, bookList.size());
        entityManager.persist(book1);
        assertEquals(1, bookList.size());
    }

    @Test
    public void hibernateWhenSettingIdToGeneratedValueIdAndPersits() {
        final UUID id = UUID.randomUUID();
        book1.setId(id);
        entityManager.unwrap(Session.class).persist(book1);
        entityManager.flush();
        List<Book> bookList = entityManager.createQuery("select b from Book b", Book.class)
            .getResultList();
        assertEquals(1, bookList.size());
        entityManager.unwrap(Session.class).persist(book1);
        assertEquals(1, bookList.size());
    }
}
