package com.github.madz0.hibernatetut;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import com.github.madz0.hibernatetut.model.Book;
import com.github.madz0.hibernatetut.model.Publisher;
import com.github.madz0.hibernatetut.repository.PublisherRepository;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HibernateConnectionFailureTest {

    @SpyBean
    Connection connection;
    @SpyBean
    DataSource dataSource;
    @Autowired
    EntityManager entityManager;
    @Autowired
    PublisherRepository publisherRepository;
    Book book1 = new Book();
    Publisher publisher1 = new Publisher();

    @Before
    public void init() {
        book1.setName("book1");
        publisher1.setName("publisher1");
        book1.setPublisher(publisher1);
        publisher1.setBooks(new HashSet<>(Arrays.asList(book1)));
        publisherRepository.save(publisher1);
    }

    @Test
    public void connectionFailure() throws SQLException {
        doThrow(RuntimeException.class).when(dataSource).getConnection();
        doThrow(RuntimeException.class).when(dataSource).getConnection(any(), any());
        doThrow(RuntimeException.class).when(dataSource).getConnection(any(), any());
        doThrow(RuntimeException.class).when(connection).prepareStatement(any());
        book1.getPublisher().getBooks().remove(book1);
        try {
            entityManager.remove(book1);
            entityManager.flush();
            entityManager.clear();
        } catch (Exception e) {
            assertEquals(0, publisher1.getBooks().size());
        }
    }
}
