package sait.ems.problemdomain;

public class RegularEmployee extends Employee
{
    public RegularEmployee(int id, String name, int age, String department, double salary)
    {
        super(id, name, age, department, salary);
    }

    @Override
    public double calculateBonus()
    {
        return getSalary() * 0.10;
    }

    @Override
    public void display()
    {
        System.out.println("Regular -> " + getId() + " | " + getName() + " | " + getAge() + " | " + getDepartment() + " | " + getSalary());
    }
}