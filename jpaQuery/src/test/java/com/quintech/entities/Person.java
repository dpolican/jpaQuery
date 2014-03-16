package com.quintech.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "Person")
public class Person
{
    @Id
    @GeneratedValue
    @Column(name = "PersonId")
    private Integer id;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "DOB")
    private Date birthDate;
    @Column(name = "Gender")
    private String gender;

    @OneToMany
    @JoinColumn(name = "PersonId", referencedColumnName = "PersonId")
    private Set<Phone> phones = new HashSet<Phone>(0);

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public Set<Phone> getPhones()
    {
        return phones;
    }

    public void setPhones(Set<Phone> phones)
    {
        this.phones = phones;
    }

}
