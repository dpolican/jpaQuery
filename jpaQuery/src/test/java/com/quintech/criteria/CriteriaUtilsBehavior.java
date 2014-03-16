package com.quintech.criteria;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.quintech.criteria.Query;
import com.quintech.entities.Address;
import com.quintech.entities.Address_;
import com.quintech.entities.Person;
import com.quintech.entities.Person_;
import com.quintech.entities.Phone;
import com.quintech.entities.Phone_;


public class CriteriaUtilsBehavior
{
    EntityManagerFactory emFactory;

    @BeforeClass
    public static void setupTestAssessment()
    {
        new TestDatabase("schema.ddl", "CriteriaUtilsBehaviorTestData.xml").setup();
    }

    @Before
    public void setup()
    {
        emFactory = Persistence.createEntityManagerFactory("openjpa", System.getProperties());
    }

    @After
    public void teardown()
    {
        emFactory.close();
    }

    @Test
    public void selectFromWithFieldEqualToShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            String value = "F";
            int expectedRecords = 2;

            // When
            List<Person> peeps = Query.using(em).selectFrom(Person.class)
                .with(Person_.gender).equalTo(value).resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
            for (Person person : peeps)
            {
                assertThat(person.getGender(), is(equalTo(value)));
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromWithFieldEqualToIgnoreCaseShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            String value = "F";
            int expectedRecords = 2;

            // When
            List<Person> peeps = Query.using(em).selectFrom(Person.class)
                .with(Person_.gender).equalToIgnoreCase(value).resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
            for (Person person : peeps)
            {
                assertThat(person.getGender().toUpperCase(), is(equalTo(value.toUpperCase())));
            }
        }
        finally
        {
            em.close();
        }
    }
    
    @Test
    public void selectFromWithFieldStartsWithShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            String value = "B";
            int expectedRecords = 3;

            // When
            List<Person> peeps = Query.using(em).selectFrom(Person.class)
                .with(Person_.lastName).startsWith(value).resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
            for (Person person : peeps)
            {
                assertThat(person.getLastName().indexOf(value), is(equalTo(0)));
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromWithFieldStartsWithIgnoreCaseShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            String value = "b";
            int expectedRecords = 3;

            // When
            List<Person> peeps = Query.using(em).selectFrom(Person.class)
                .with(Person_.lastName).startsWithIgnoreCase(value).resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
            for (Person person : peeps)
            {
                assertThat(person.getLastName().toLowerCase().indexOf(value.toLowerCase()), is(equalTo(0)));
            }
        }
        finally
        {
            em.close();
        }
    }
    
    @Test
    public void selectFromWithFieldContainsShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            String value = "B";
            int expectedRecords = 3;

            // When
            List<Person> peeps = Query.using(em).selectFrom(Person.class)
                .with(Person_.lastName).contains(value).resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
            for (Person person : peeps)
            {
                assertThat(person.getLastName().indexOf(value), is(greaterThanOrEqualTo(0)));
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromWithFieldContainsIgnoreCaseShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            String value = "b";
            int expectedRecords = 3;

            // When
            List<Person> peeps = Query.using(em).selectFrom(Person.class)
                .with(Person_.lastName).startsWithIgnoreCase(value).resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
            for (Person person : peeps)
            {
                assertThat(person.getLastName().toLowerCase().indexOf(value.toLowerCase()), is(greaterThanOrEqualTo(0)));
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromWithFieldInShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            List<String> values = Arrays.asList("M", "F");
            int expectedRecords = 6;

            // When
            List<Person> peeps = Query.using(em).selectFrom(Person.class)
                .with(Person_.gender).in(values).resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
            for (Person person : peeps)
            {
                assertThat(values, hasItem(person.getGender()));
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromWithFieldInBlankValuesShouldNotMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            List<String> values = Collections.emptyList();

            // When
            List<Person> peeps = Query.using(em).selectFrom(Person.class)
                .with(Person_.gender).in(values).resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(true));
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromSingleResultShouldReturnASingleRecord()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given

            // When
            Person person = Query.using(em).selectFrom(Person.class).singleResult();

            // Then
            assertThat(person, is(notNullValue()));
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromWithFieldIsNullOrNotBeforeShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            Calendar cal = Calendar.getInstance();
            cal.set(1985, 11, 8, 17, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date cutoffDate = cal.getTime();
            int expectedRecords = 4;

            // When
            List<Person> peeps =
                Query.using(em).selectFrom(Person.class).with(Person_.birthDate).isNullOrNotBefore(cutoffDate)
                    .resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
            for (Person person : peeps)
            {
                if (person.getBirthDate() != null)
                {
                    assertThat(person.getBirthDate(), is(greaterThanOrEqualTo(cutoffDate)));
                }
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromWithFieldIsNullOrNotBeforeWithNullValueShouldMatchAllRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            Date cutoffDate = null;
            int expectedRecords = 6;

            // When
            List<Person> peeps =
                Query.using(em).selectFrom(Person.class).with(Person_.birthDate).isNullOrNotBefore(cutoffDate)
                    .resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromWithFieldNotBeforeShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            Calendar cal = Calendar.getInstance();
            cal.set(1985, 11, 8, 17, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date cutoffDate = cal.getTime();
            int expectedRecords = 3;

            // When
            List<Person> peeps =
                Query.using(em).selectFrom(Person.class).with(Person_.birthDate).notBefore(cutoffDate)
                    .resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
            for (Person person : peeps)
            {
                assertThat(person.getBirthDate(), is(greaterThanOrEqualTo(cutoffDate)));
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromWithFieldNotAfterShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            Calendar cal = Calendar.getInstance();
            cal.set(1985, 11, 8, 17, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date cutoffDate = cal.getTime();
            int expectedRecords = 3;

            // When
            List<Person> peeps =
                Query.using(em).selectFrom(Person.class).with(Person_.birthDate).notAfter(cutoffDate)
                    .resultList();

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
            for (Person person : peeps)
            {
                assertThat(person.getBirthDate(), is(lessThanOrEqualTo(cutoffDate)));
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectAllFromShouldMatchAllRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            int expectedRecords = 6;

            // When
            List<Person> peeps = Query.using(em).selectAllFrom(Person.class);

            // Then
            assertThat(peeps, is(notNullValue()));
            assertThat(peeps.isEmpty(), is(false));
            assertThat(peeps.size(), is(equalTo(expectedRecords)));
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromAscendingByShouldSortTheRecordsAccordingly()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            int expectedRecords = 6;

            // When
            List<Person> peepsByDob =
                Query.using(em).selectFrom(Person.class).ascendingBy(Person_.birthDate).resultList();

            // Then
            assertThat(peepsByDob, is(notNullValue()));
            assertThat(peepsByDob.isEmpty(), is(false));
            assertThat(peepsByDob.size(), is(equalTo(expectedRecords)));
            Date previousDob = null;
            for (Person person : peepsByDob)
            {
                if (previousDob != null && person.getBirthDate() != null)
                {
                    assertThat(person.getBirthDate(), is(greaterThanOrEqualTo(previousDob)));
                }
                previousDob = person.getBirthDate();
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromAscendingByMultipleFieldsShouldSortTheRecordsAccordingly()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            int expectedRecords = 6;

            // When
            List<Person> peepsByDob =
                Query.using(em).selectFrom(Person.class).ascendingBy(Person_.gender)
                    .ascendingBy(Person_.birthDate).resultList();

            // Then
            assertThat(peepsByDob, is(notNullValue()));
            assertThat(peepsByDob.isEmpty(), is(false));
            assertThat(peepsByDob.size(), is(equalTo(expectedRecords)));
            String previousGender = null;
            Date previousDob = null;
            for (Person person : peepsByDob)
            {
                if (previousGender == null)
                {
                    previousGender = "";
                }
                String personGender = person.getGender();
                if (personGender == null)
                {
                    personGender = "";
                }

                assertThat(person.getGender().compareTo(previousGender), is(greaterThanOrEqualTo(0)));
                if (previousGender.equals(personGender))
                {
                    if (previousDob != null && person.getBirthDate() != null)
                    {
                        assertThat(person.getBirthDate(), is(greaterThanOrEqualTo(previousDob)));
                    }
                }
                previousGender = person.getGender();
                previousDob = person.getBirthDate();
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromDescendingByShouldSortTheRecordsAccordingly()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            int expectedRecords = 6;

            // When
            List<Person> peepsByDob =
                Query.using(em).selectFrom(Person.class).descendingBy(Person_.birthDate).resultList();

            // Then
            assertThat(peepsByDob, is(notNullValue()));
            assertThat(peepsByDob.isEmpty(), is(false));
            assertThat(peepsByDob.size(), is(equalTo(expectedRecords)));
            Date previousDob = null;
            for (Person person : peepsByDob)
            {
                if (previousDob != null && person.getBirthDate() != null)
                {
                    assertThat(person.getBirthDate(), is(lessThanOrEqualTo(previousDob)));
                }
                previousDob = person.getBirthDate();
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromDescendingByMultipleFieldsShouldSortTheRecordsAccordingly()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            int expectedRecords = 6;

            // When
            List<Person> peepsByDob =
                Query.using(em).selectFrom(Person.class).descendingBy(Person_.gender)
                    .descendingBy(Person_.birthDate).resultList();

            // Then
            assertThat(peepsByDob, is(notNullValue()));
            assertThat(peepsByDob.isEmpty(), is(false));
            assertThat(peepsByDob.size(), is(equalTo(expectedRecords)));
            String previousGender = null;
            Date previousDob = null;
            for (Person person : peepsByDob)
            {
                if (previousGender == null)
                {
                    previousGender = "Z";
                }
                String personGender = person.getGender();
                if (personGender == null)
                {
                    personGender = "";
                }

                assertThat(person.getGender().compareTo(previousGender), is(lessThanOrEqualTo(0)));
                if (previousGender.equals(personGender))
                {
                    if (previousDob != null && person.getBirthDate() != null)
                    {
                        assertThat(person.getBirthDate(), is(lessThanOrEqualTo(previousDob)));
                    }
                }
                previousGender = person.getGender();
                previousDob = person.getBirthDate();
            }
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromSingleJoinShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            int expectedRecords = 1;

            // When
            List<Person> peepsWithAddress =
                Query.using(em).selectFrom(Person.class).distinct().descendingBy(Person_.gender)
                    .descendingBy(Person_.birthDate).andFrom(Address.class).joinOn(Person_.id, Address_.personId)
                    .ascendingBy(Address_.city).resultList();

            // Then
            assertThat(peepsWithAddress, is(notNullValue()));
            assertThat(peepsWithAddress.isEmpty(), is(false));
            assertThat(peepsWithAddress.size(), is(equalTo(expectedRecords)));
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromMultipleJoinsShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            int expectedRecords = 1;

            // When
            List<Person> peepsWithAddressAndPhone =
                Query.using(em).selectFrom(Person.class).distinct()
                    .descendingBy(Person_.birthDate).andFrom(Address.class).joinOn(Person_.id, Address_.personId)
                    .ascendingBy(Address_.city).andFrom(Phone.class).joinOn(Person_.id, Phone_.personId)
                    .resultList();

            // Then
            assertThat(peepsWithAddressAndPhone, is(notNullValue()));
            assertThat(peepsWithAddressAndPhone.isEmpty(), is(false));
            assertThat(peepsWithAddressAndPhone.size(), is(equalTo(expectedRecords)));
        }
        finally
        {
            em.close();
        }
    }
    @Test
    public void selectFromSingleJoinToMappedOneToManyShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            int expectedRecords = 1;

            // When
            List<Person> peepsWithAddress =
                Query.using(em).selectFrom(Person.class).with(Person_.lastName).equalTo("Smith")
                    .descendingBy(Person_.birthDate).andFrom(Phone.class).joinOn(Person_.id, Phone_.personId)
                    .with(Phone_.type).equalTo("Home")
                    .ascendingBy(Phone_.type).resultList();

            // Then
            assertThat(peepsWithAddress, is(notNullValue()));
            assertThat(peepsWithAddress.isEmpty(), is(false));
            assertThat(peepsWithAddress.size(), is(equalTo(expectedRecords)));
        }
        finally
        {
            em.close();
        }
    }

    @Test
    public void selectFromSingleWithCriteriaShouldMatchRecords()
    {
        EntityManager em = emFactory.createEntityManager();
        try
        {
            // Given
            int expectedRecords = 1;

            // When
            List<Person> peepsWithAddress =
                Query.using(em).selectFrom(Person.class).with(Person_.lastName)
                .equalTo("Smith")
                    .andFrom(Address.class).joinOn(Person_.id, Address_.personId)
                    .with(Address_.city).equalTo("Westminster").resultList();

            // Then
            assertThat(peepsWithAddress, is(notNullValue()));
            assertThat(peepsWithAddress.isEmpty(), is(false));
            assertThat(peepsWithAddress.size(), is(equalTo(expectedRecords)));
        }
        finally
        {
            em.close();
        }
    }
}
