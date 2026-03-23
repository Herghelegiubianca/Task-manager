package org.example.businessLogic;

import org.example.dataModel.Employee;

import java.util.*;

public class Utility{

    public Utility(){}

    //nu am nevoie de getteri si setteri intrucat nu am atribute

    public String filterEmployees(TaskManagement tm) throws NonexistentEmployee, NoEmployeesException
    {if(tm!=null && tm.getMap()!=null)
        {    ArrayList<Employee> employees=new ArrayList<>();
             boolean inserted=false;
             for(Employee e: tm.getMap().keySet())
             {if(e!=null)
                 {int wd=tm.calculateEmployeeWorkDuration(e.getIdEmployee());
                     if(wd>40)
                     {   employees.add(e);
                         inserted=true;}}}
            if(inserted)
            {   Collections.sort(employees,new Comparator<Employee>(){
                @Override
                public int compare(Employee o1, Employee o2) {
                    if(o1==null || o2==null) return 1;
                    try{int wd1=tm.calculateEmployeeWorkDuration(o1.getIdEmployee());
                        int wd2=tm.calculateEmployeeWorkDuration(o2.getIdEmployee());
                        if(wd1<wd2) return -1;
                        else if(wd1==wd2) return 0;
                        else return 1;}
                    catch (Exception e)
                    {System.out.println("Nu s-a putut realiza sortarea");}
                    return 1;}
            });
                String rez=" ";
                for(Employee aux: employees)
                {rez+=aux.getName()+"("+tm.calculateEmployeeWorkDuration(aux.getIdEmployee())+")"+",\n";}
                return rez;}
            else{throw new NoEmployeesException("Niciun angajat nu indeplineste conditiile pentru filtrare!");}}
        throw new NoEmployeesException("Nu exista angajati pentru care sa se efectueze filtrarea!");}

    public Map<String,Map<String,Integer>> statistics (TaskManagement tm) throws NoEmployeesException
    {
        Map<String,Map<String,Integer>> rez=new HashMap<>();

        if(tm.getMap()==null || tm.getMap().size()==0)
        {
            throw new NoEmployeesException("Nu exista angajati pentru care sa se afiseze statisticile!");
        }

        for(Employee e:tm.getMap().keySet())
        {
            String key=e.getName()+" "+e.getIdEmployee();
            rez.put(key,new HashMap<>());
            int c=tm.nrOfTasks(e,tm.getMap().get(e),"Completed");
            rez.get(key).put("Completed",c);
            c=tm.nrOfTasks(e,tm.getMap().get(e),"Uncompleted");
            rez.get(key).put("Uncompleted",c);
        }

        return rez;
    }


}
