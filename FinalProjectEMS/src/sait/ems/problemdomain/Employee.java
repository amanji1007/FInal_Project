package sait.ems.problemdomain;

import sait.ems.utility.Payable;

public class Employee extends Person implements Payable
{
    private String department;
    private double salary;

    public Employee(int id, String name, int age, String department, double salary)
    {
        super(id, name, age);
        this.department = department;
        this.salary = salary;
    }

    @Override
    public double calculateBonus()
    {
        return salary * 0.1;
    }

    @Override
    public void display()
    {
        System.out.println(id + " | " + name + " | " + age + " | " + department + " | " + salary);
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getDepartment()
    {
        return department;
    }

    public double getSalary()
    {
        return salary;
    }
}