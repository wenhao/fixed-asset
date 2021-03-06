package com.thoughtworks.fam.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TABLE_USERS")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private long id;

    @Column(name = "USER_ACCOUNT")
    private String account;

    @Column(name = "USER_PASSWORD")
    private String password;

    public User()
    {
    }

    public User(String account, String password)
    {
        this.account = account;
        this.password = password;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAccount()
    {
        return account;
    }

    public String getPassword()
    {
        return password;
    }

    public long getId()
    {
        return id;
    }

}
