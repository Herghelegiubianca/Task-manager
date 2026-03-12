package org.example.DataModel;

import java.util.ArrayList;

public final class ComplexTask extends Task{
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

           if(this.getIdTask()!=c.getIdTask())
           {
               return false;
           }

            if(!this.getStatusTask().equals(c.getStatusTask()))
            {
                return false;
            }
           for(Task t: this.tasks)
           {
               if(!c.tasks.contains(t))
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

    public Task searchTask(Task task)
    {
        for(Task t: this.tasks)
        {
            if(t.equals(task))
            {
                return t;
            }
            else if(t instanceof ComplexTask)
            {
                ComplexTask c=(ComplexTask)t;
                Task aux=c.searchTask(task);
                if(aux!=null)
                {
                    return aux;
                }
            }
        }

        return null;
    }

    public Task searchTaskOnId(int idTask)
    {
        for(Task t: this.tasks)
        {
            if(t.getIdTask()==idTask)
            {
                return t;
            }
            else if(t instanceof ComplexTask)
            {
                ComplexTask c=(ComplexTask)t;
                Task aux=c.searchTaskOnId(idTask);
                if(aux!=null)
                {
                    return aux;
                }
            }
        }

        return null;
    }

    public void addTask(Task task) throws ElementAlreadyExists
    {
        if(this.searchTask(task)==null)
        {
            this.tasks.add(task);
            return;
        }

        throw new ElementAlreadyExists("Taskul pe care incercati sa il adaugati in ComplexTask-ul curent exista deja!");

    }

    public void deleteTask(Task task) throws NonexistentElement
    {
        Task aux=this.searchTask(task);
        if(aux!=null)
        {
            if(this.tasks.remove(aux))
            {
                return;
            }

            for(Task t: this.tasks)
            {
                if(t instanceof ComplexTask)
                {
                    Task aux1=((ComplexTask)t).searchTask(task);
                    if(aux1!=null)
                    {
                        ((ComplexTask)t).deleteTask(task);
                    }
                }
            }
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
