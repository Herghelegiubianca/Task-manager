package org.example.businessLogic;
import org.example.dataAccess.SerializationOperations;
import org.example.dataModel.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagement {
    private Map<Employee, List<Task>> map;
    private ArrayList<Task> tasks;
    private ArrayList<Employee> employees;
    private transient SerializationOperations serialization;

    public TaskManagement(){

        this.serialization=new SerializationOperations();
        this.map=new HashMap<>();
        this.tasks=new ArrayList<>();
        this.employees=new ArrayList<>();

    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public void setMap(Map<Employee,List<Task>> map)
    {
        this.map=map;
    }

    public Map<Employee,List<Task>> getMap()
    {
        return this.map;
    }

    public Employee searchEmployeeById(int idEmployee)
    {
        if(this.employees==null)
        {
            return null;
        }

        for(Employee e:this.employees)
        {
            if(e!=null && e.getId()==idEmployee)
            {
                return e;
            }
        }
        return null;
    }

    public boolean validComplexTask(ComplexTask c)
    {
        if(c.getTasks().size()>=2)
        {
            for(Task t: c.getTasks())
            {
                if(t instanceof ComplexTask)
                {
                    if(!validComplexTask((ComplexTask)t)) return false;
                }
            }

            return true;
        }
        else return false;
    }

    public void assignTaskToEmployee(int idEmployee, Task task) throws NonexistentEmployee, InvalidComplexTask
    {
         Employee e=this.searchEmployeeById(idEmployee);
         if(e==null)
         {
             throw new NonexistentEmployee("Angajatul caruia vreti sa ii asignati un task nu exista!");
         }

         if(task instanceof ComplexTask)
         {
             if(!validComplexTask((ComplexTask)task))
             {
                 throw new InvalidComplexTask("Acest complexTask nu este complet!");
             }
         }
         List<Task> t=this.map.get(e);

         if(t==null)
         {
             t=new ArrayList<Task>();
             this.map.put(e,t);
         }

        t.add(task);
        task.setStatusTask("Uncompleted");

        this.tasks.remove(task);

    }

    public int calculateEmployeeWorkDuration(int idEmployee) throws NonexistentEmployee
    {
        Employee e=searchEmployeeById(idEmployee);
        if(e==null)
        {
           throw new NonexistentEmployee("Angajatul pentru care doriti sa calculati durata de lucru nu exista!");
        }

        int sum=0;

        if(this.map.get(e)==null||this.map.get(e).size()==0)
        {
            return 0;
        }

        for(Task t:this.map.get(e))
        {
            if(t.getStatusTask().equals("Completed"))
            {
                sum+=t.estimateDuration();
            }else if(t instanceof ComplexTask)
            {
                sum+=((ComplexTask)t).nrHoursForCompletedTasks();
            }
        }

        return sum;

    }

    public Task searchTaskById(Employee e, int idTask)
    {
        List<Task>tasks=this.map.get(e);
        if(tasks==null||tasks.size()==0)
        {
            return null;
        }

        for(Task t: tasks)
        {
            if(t.getIdTask()==idTask)
            {
                return t;
            }
            else if(t instanceof ComplexTask)
            {
                Task aux=((ComplexTask)t).searchTaskOnId(idTask);
                if(aux!=null)
                {
                    return aux;
                }
            }
        }
        return null;
    }

    public void changeStatusTopDown(Task t, String status, Employee e)
    {
        Task temp=searchTaskById(e,t.getIdTask());
        if(temp==null)return;

        t.setStatusTask(status);
        if(t instanceof ComplexTask)
        {
            for(Task aux: ((ComplexTask)t).getTasks())
            {
                changeStatusTopDown(aux,status,e);
            }

        }
    }

    public void changeStatusBottomUp(Task t, String status, Employee e)
    {
        Task temp=searchTaskById(e,t.getIdTask());
        if(temp==null)return;
        Task parent=t.getParent();
        if(parent!=null && !parent.getStatusTask().equals(status))
        {   boolean changed=false;
            if(status.equals("Uncompleted"))
            {   parent.setStatusTask(status);
                changed=true;
            }else {
                int nr = 0;
                for (Task aux : ((ComplexTask) parent).getTasks()) {
                    if (aux.getStatusTask().equals(status)) nr++;}
                if (nr == ((ComplexTask) parent).getTasks().size()) {
                    parent.setStatusTask(status);
                    changed=true;}
            }
            if(changed)
            {changeStatusBottomUp(parent,status, e);}
        }
    }

    public void modifyTaskStatus(int idEmployee, int idTask) throws NonexistentEmployee,NonexistentTask
    {Employee e=searchEmployeeById(idEmployee);
        if(e !=null)
        {Task t=searchTaskById(e,idTask);
           if(t!=null) {
               String changedStatus = null;
               String status = t.getStatusTask();
               if (status.equals("Completed")) {
                   changedStatus = "Uncompleted";
               } else {
                   changedStatus = "Completed";
               }
               changeStatusTopDown(t, changedStatus,e);
               changeStatusBottomUp(t,changedStatus,e);
               return;
           }
            throw new NonexistentTask("Sarcina pe care doriti sa o modificati nu face parte din atributiile angajatului curent!");}
        throw new NonexistentEmployee("Angajatul pentru care doriti sa modificati un task nu exista!");}


    public int nrOfTasks (Employee e, List<Task> tasks, String state)
    {
        if(this.map.keySet().contains(e)) {
            if (tasks != null && tasks.size() > 0) {
                    int nr=0;
                    for (Task t: tasks)
                    {
                        if(t.getStatusTask().equals(state))
                        {
                            nr++;
                        }
                    }
                    return nr;
            }
        }
        return 0;
    }

    //metoda de mai jos calculeaza totalitatea taskurilor simple care sunt in starea state (Completed / Uncompleted)

/*
    public int nrOfTasks(Employee e, List<Task> tasks, String state)
    {
        if(this.map.keySet().contains(e))
        {if(tasks!=null && tasks.size()>0)
            {int counter=0;
                for(Task t: tasks)
                {if(t instanceof ComplexTask)
                    {counter+=nrOfTasks(e,((ComplexTask)t).getTasks(),state);}
                    else if(t.getStatusTask().equals(state))
                    {counter++;}
                }
                return counter;
            }
        }
        return 0;
    }*/

    public void addEmployee(String name)
    {
        Employee e=new Employee(name);
        this.employees.add(e);
        this.map.put(e,new ArrayList<Task>());
    }

    public void addTask(String typeOfTask, int startHour, int endHour,ComplexTask parent) throws Exception
    {
        Task t;
        if(typeOfTask.equals("Complex"))
        {
            t=new ComplexTask();
        }else
        {
            t=new SimpleTask(startHour,endHour);
        }
        if(parent!=null)
        {
            try{
                parent.addTask(t);}
            catch(Exception e){
                throw e;
            }
        }else{
            this.tasks.add(t);
        }
    }

    public void deleteTask(Task t) throws NonexistentTask
    {
        if(t!=null)
        {this.tasks.remove(t);}
        else{
            throw new NonexistentTask("Trebuie sa selectati un task!");
        }
    }

    public void getInfo() throws Exception
    {
        try{
           this.serialization.getData(this.map, this.tasks, this.employees);

        }
        catch(FileNotFoundException e)
        {
           System.out.println("Prima rulare!");
        }catch (Exception e)
        {
            throw e;
        }
    }

    public void saveInfo()throws Exception
    {
        try{
        this.serialization.saveData(this.map, this.tasks, this.employees);}
        catch(Exception e)
        {
            throw e;
        }
    }



}
