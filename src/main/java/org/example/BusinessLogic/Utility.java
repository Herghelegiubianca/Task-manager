package org.example.BusinessLogic;

import org.example.DataModel.Employee;
import org.example.DataModel.Task;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Utility {

    public Utility(){}

    //nu am nevoie de getteri si setteri intrucat nu am atribute

    public TreeMap<Integer,ArrayList<Employee>> filterEmployees(TaskManagement tm) throws NonexistentEmployee, NoEmployeesException
    {
        if(tm!=null && tm.getMap()!=null)
        {
            TreeMap<Integer,ArrayList<Employee>> sorted=new TreeMap<>();
            boolean inserted=false;
            for(Employee e:tm.getMap().keySet())
            {
                try{
                    if(e!=null)
                    {
                        int aux=tm.calculateEmployeeWorkDuration(e.getId());
                        if(aux>40)
                        {
                            ArrayList<Employee>temp=sorted.get((Integer)aux);
                            if(temp!=null)
                            {
                                sorted.get((Integer)aux).add(e);
                                inserted=true;
                            }else {
                                sorted.put((Integer)aux,new ArrayList<Employee>());
                                sorted.get((Integer)aux).add(e);
                                inserted=true;
                            }
                        }
                    }
                }catch(NonexistentEmployee exp)
                {
                    throw exp;
                }
            }
            if(inserted)
            {
                return sorted;
            }
            else{
                throw new NoEmployeesException("Niciun angajat nu indeplineste conditiile pentru filtrare!");
            }

        }
        throw new NoEmployeesException("Nu exista angajati pentru care sa se efectueze filtrarea!");
    }

}
