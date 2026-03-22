package org.example.dataModel;

import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private int idEmployee;
    private String name;
    private static int counter;

    public Employee(){}

    public Employee(String name)
    {
        this.idEmployee=counter;
        counter++;
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

    public int getIdEmployee() {
        return idEmployee;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Employee.counter=counter;
    }

    @Override
    public int hashCode() {
        int aux=this.idEmployee*7;
        int index=1;

        for(char c: this.name.toCharArray())
        {
            if(c!=' ')
            {
                aux=aux*5+index*this.idEmployee*(int)c;
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
            return this.idEmployee+" "+ this.name+"\n";
        }

}

