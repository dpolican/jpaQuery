jpaQuery
========

Utility for executing JPA queries using CriteriaBuilder in a more fluid manner.

At the basic level, instead of writing:

  CriteriaBuilder builder = entityManager.getCriteriaBuilder();
  CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
  Root<Person> fromPerson = criteria.from(Person.class);
  TypedQuery<Person> query = entityManager.createQuery(criteria);
  List<Person> peeps = query.getResultList();

you can write:

  List<Person> peeps = Query.using(entityManager).selectFrom(Person.class).resultList();

With predicates and sorting:

  List<Person> peeps = Query.using(em).selectFrom(Person.class)
    .with(Person_.lastName).startsWith("S")
    .and(Person_.gender).equalTo("F")
    .ascendingBy(Person_.lastName).ascendingBy(Person_.firstName).resultList();

With inner join of 2 entities:

  List<Person> peeps = Query.using(em).selectFrom(Person.class).distinct()
      .with(Person_.gender).equalTo("F")
    .andFrom(Address.class).joinOn(Person_.id, Address_.personId)
      .with(Address_.state).equalTo("CA")
      .resultList();

This utility is intended to simplify the JPA queries for 80% of the use cases. It is not intended 
to cover all possible use cases.
