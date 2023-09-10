package com.karafzar.payroll1402.model;

public class MonthModel
{
    private int id;
    private String Month;

    public MonthModel(){}

    public int getId()
    {
        return id;
    }

    public String getMonth()
    {
        return Month;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setMonth(String month)
    {
        Month = month;
    }
}