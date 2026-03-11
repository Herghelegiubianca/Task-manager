package org.example.BusinessLogic;
import org.example.DataModel.ComplexTask;
import org.example.DataModel.Employee;
import org.example.DataModel.SimpleTask;
import org.example.DataModel.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskManagement {
    private Map<Employee, ArrayList<Task>> map;

    public TaskManagement(){
        this.map=new HashMap<>();
    }

    public TaskManagement(Map<Employee,ArrayList<Task>> map)
    {
        this.map=map;
    }

    public void setMap(Map<Employee,ArrayList<Task>> map)
    {
        this.map=map;
    }

    public Map<Employee,ArrayList<Task>> getMap()
    {
        return this.map;
    }

    public Employee searchEmployeeById(int idEmployee)
    {
        if(this.map==null)
        {
            return null;
        }

        for(Employee e:this.map.keySet())
        {
            if(e!=null && e.getId()==idEmployee)
            {
                return e;
            }
        }
        return null;
    }

    public void assignTaskToEmployee(int idEmployee, Task task) throws NonexistentEmployee, DuplicateTask
    {
         Employee e=this.searchEmployeeById(idEmployee);
         if(e==null)
         {
             throw new NonexistentEmployee("Angajatul caruia vreti sa ii asignati un task nu exista!");
         }

         ArrayList<Task> tasks=this.map.get(e);

         if(tasks==null)
         {
             tasks=new ArrayList<Task>();
             this.map.put(e,tasks);
             tasks.add(task);
             task.setStatusTask("Uncompleted");
         }
         else{
             for(Task t: tasks)
             {
                 if(t.equals(task))
                 {
                     throw new DuplicateTask("Angajatul curent se ocupa deja de sarcina pe care doriti sa i-o alocati!");
                 }
                 else if(t instanceof ComplexTask)
                 {
                     ComplexTask c=(ComplexTask)t;
                     Task aux=c.searchTask(task);

                     if(aux!=null)
                     {
                         throw new DuplicateTask("Angajatul curent se ocupa deja de sarcina pe care doriti sa i-o alocati!");
                     }
                 }
             }
             tasks.add(task);
             task.setStatusTask("Uncompleted");
         }
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
            sum+=t.estimateDuration();
        }

        return sum;

    }

    public Task searchTaskById(Employee e, int idTask)
    {
        ArrayList<Task>tasks=this.map.get(e);
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


    public void modifyTaskStatus(int idEmployee, int idTask) throws NonexistentEmployee,NonexistentTask
    {
        Employee e=searchEmployeeById(idEmployee);
        if(e !=null)
        {
           Task t=searchTaskById(e,idTask);
           if(t!=null)
           {
               String status=t.getStatusTask();
               if(status.equals("Completed"))
               {
                   t.setStatusTask("Uncompleted");
               }
               else {
                   t.setStatusTask("Completed");
               }
               return;
           }

           throw new NonexistentTask("Sarcina pe care doriti sa o modificati nu face parte din atributiile angajatului curent!");
        }

        throw new NonexistentEmployee("Angajatul pentru care doriti sa modificati un task nu exista!");
    }

}
