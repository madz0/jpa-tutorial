In this article we investigate the impacts of model design and queries on database performance.

Here is our model

![Package_model](uploads/2c7a1eea5cf40bce04973d45b58a50f9/Package_model.png)

**1) Checking OneToOne relation between Author and Person**

Hibernate eagerly selects OneToOne relations (No matter you define it eager or lazy)

`List<Author> authors = authorityRepository.findAll();`

```
select * from author author0_
```

```
select * from person person0_ where person0_.author_id=?
```

```
select * from person person0_ where person0_.author_id=?
```


**2) OneToMany (Collection) relation between Publisher and Book**

`List<Publisher> publishers = publisherRepository.findAll();`

**Lazy:**
Hibernate does not fetch lazy relation by its own

```
select * from publisher publisher0_
```

**Eager:**
Hibernate sends extra select for eager OneToMany

```
select * from publisher publisher0_
```

```
select * from book books0_ where books0_.publisher_id=?
```

**3) ManyToMany (Set) relation between Author and Book**

`List<Author> authors = authorityRepository.findAll();`

```
select * from author author0_
```

**Lazy:**
Hibernate does not fetch lazy relation eagerly

```
select * from author author0_
```

**Eager:**
Hibernate executes extra select queries to get books eagerly

```
select * from author author0_
```
```
select * from author_book books0_ inner join book book1_ on books0_.book_id=book1_.id where books0_.author_id=?
```
```
select * from author_book books0_ inner join book book1_ on books0_.book_id=book1_.id where books0_.author_id=?
```


**4) OneToMany (Map) relations between Publisher and Editor**

There is no difference between Collections and Map


**5) EntityGraph**

`authorityRepository.findAll(EntityGraphUtils.fromAttributePaths("books"));`

**Lazy:**
Hibernate generates join query to get books of Authors and did't execute extra queries

```
select * from author author0_ left outer join author_book books1_ on author0_.id=books1_.author_id left outer join book book2_ on books1_.book_id=book2_.id
```

**Eager:**
Hibernate generates join query to get books of Authors and did't execute extra queries

```
select * from author author0_ left outer join author_book books1_ on author0_.id=books1_.author_id left outer join book book2_ on books1_.book_id=book2_.id
```

**6) Join query**

`select a from Author a join a.books`

**Lazy:**
Hibernate executes extra query to access book properties

```
select * from author author0_ inner join author_book books1_ on author0_.id=books1_.author_id inner join book book2_ on books1_.book_id=book2_.id
```
`author.getBooks().iterator().next().getName();`
```
select * from author_book books0_ inner join book book1_ on books0_.book_id=book1_.id where books0_.author_id=?
```

**Eager:**
Hibernate executes extra queries to access all books eagerly

```
select * from author author0_ inner join author_book books1_ on author0_.id=books1_.author_id inner join book book2_ on books1_.book_id=book2_.id
```

```
select * from author_book books0_ inner join book book1_ on books0_.book_id=book1_.id where books0_.author_id=?
```

```
select * from author_book books0_ inner join book book1_ on books0_.book_id=book1_.id where books0_.author_id=?
```

**7) Join Fetch**

`select a from Author a join fetch a.books`

**Lazy:**
Hibernate generates join query to get books of Authors and did't execute extra queries
 
```
select * from author author0_ inner join author_book books1_ on author0_.id=books1_.author_id inner join book book2_ on books1_.book_id=book2_.id
```

**Eager:**
Hibernate generates join query to get books of Authors and did't execute extra queries

```
select * from author author0_ inner join author_book books1_ on author0_.id=books1_.author_id inner join book book2_ on books1_.book_id=book2_.id
```

