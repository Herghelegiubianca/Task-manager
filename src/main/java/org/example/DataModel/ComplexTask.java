package org.example.DataModel;

import java.util.ArrayList;

public class ComplexTask extends Task{
    private ArrayList<Task> tasks=new ArrayList<>();

    public ComplexTask(){}

    public ComplexTask(int idTask, String statusTask)
    {
        super(idTask,statusTask);
    }

    public void setTasks(ArrayList<Task> tasks)
    {
        this.tasks=tasks;
    }

    public ArrayList<Task> getTasks()
    {
        return this.tasks;
    }


    @Override
    public boolean equals(Object o)
    {
        if(o instanceof ComplexTask)
        {
           ComplexTask c=(ComplexTask)o;

           if(this.tasks.size()!=c.tasks.size())
           {
               return false;
           }

           for(Task t: this.tasks)
           {
               int aux=c.searchTask(t);
               if(aux==-1)
               {
                   return false;
               }
           }

           return true;
        }
        else{
            return false;
        }
    }

    public int searchTask(Task task)
    {
        int index=0;
        for(Task t: this.tasks)
        {
            if(t.equals(task))
            {
                return index;
            }
            index++;
        }

        return -1;
    }

    public void addTask(Task task) throws ElementAlreadyExists
    {
        if(this.searchTask(task)==-1)
        {
            this.tasks.add(task);
            return;
        }

        throw new ElementAlreadyExists("Taskul pe care incercati sa il adaugati in ComplexTask-ul curent exista deja!");

    }

    public void deleteTask(Task task) throws NonexistentElement
    {
        int index=this.searchTask(task);
        if(index!=-1)
        {
            this.tasks.remove(index);
            return;
        }

        throw new NonexistentElement("In ComplexTask-ul curent nu exista taskul pe care incercati sa il stergeti");
    }

    @Override
    public int estimateDuration()
    {
        int aux=0;
        for(Task t: this.tasks)
        {
            aux+=t.estimateDuration();
        }
        return aux;
    }
}
