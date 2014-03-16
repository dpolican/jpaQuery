package com.quintech.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Phone")
public class Phone
{
    @Id
    @GeneratedValue
    @Column(name = "PhoneId")
    private Integer id;
    @Column(name = "PersonId")
    private Integer personId;
    @Column(name = "Number")
    private String number;
    @Column(name = "Type")
    private String type;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getPersonId()
    {
        return personId;
    }

    public void setPersonId(Integer personId)
    {
        this.personId = personId;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

}
