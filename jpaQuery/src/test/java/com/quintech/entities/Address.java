package com.quintech.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Address")
public class Address
{
    @Id
    @GeneratedValue
    @Column(name = "AddressId")
    private Integer id;
    @Column(name="PersonId")
    private Integer personId;
    @Column(name = "Street1")
    private String street1;
    @Column(name = "Street2")
    private String street2;
    @Column(name = "City")
    private String city;
    @Column(name = "State")
    private String state;
    @Column(name = "Zip")
    private String zip;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getStreet1()
    {
        return street1;
    }

    public void setStreet1(String street1)
    {
        this.street1 = street1;
    }

    public String getStreet2()
    {
        return street2;
    }

    public void setStreet2(String street2)
    {
        this.street2 = street2;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public Integer getPersonId()
    {
        return personId;
    }

    public void setPersonId(Integer personId)
    {
        this.personId = personId;
    }
}
