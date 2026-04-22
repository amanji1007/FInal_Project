package sait.ems.problemdomain;

public class ManagerEmployee extends Employee
{
    public ManagerEmployee(int id, String name, int age, String department, double salary)
    {
        super(id, name, age, department, salary);
    }

    @Override
    public double calculateBonus()
    {
        return getSalary() * 0.20;
    }

    @Override
    public void display()
    {
        System.out.println("Manager -> " + getId() + " | " + getName() + " | " + getAge() + " | " + getDepartment() + " | " + getSalary());
    }
}