package org.example.DataModel;

public class Employee {
    private int idEmployee;
    private String name;

    public Employee(){}

    public Employee(int idEmployee, String name)
    {
        this.idEmployee=idEmployee;
        this.name=name;
    }

    public void setIdEmployee(int idEmployee)
    {
        this.idEmployee=idEmployee;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public int getId()
    {
        return this.idEmployee;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public int hashCode() {
        int aux=this.idEmployee*7;
        int index=1;

        for(char c: this.name.toCharArray())
        {
            if(c!=' ')
            {
                aux=aux*5+index*(int)c;
                index++;
            }
        }
        return aux;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Employee)
        {
            Employee e=(Employee)o;
            if(this.idEmployee==e.idEmployee && this.name.equals(e.name))
            {
               return true;
            }
        }
        return false;
    }

        @Override
        public String toString()
        {
            return "Nume: "+this.name+", id: "+this.idEmployee;
        }

}

