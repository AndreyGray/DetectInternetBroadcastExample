package com.dron.detectinternetbroadcastexample.model;

public class User {
    private String name;

    private String age;

    /**
     * generate empty constructor
     */
    public User() {
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getAge ()
    {
        return age;
    }

    public void setAge (String age)
    {
        this.age = age;
    }

    /**
     * overrides method toString for logging our data
     * @return
     */
    @Override
    public String toString()
    {
        return "ClassUser [name = "+name+", age = "+age+"]";
    }
}
