package org.example.dataModel;

import java.util.ArrayList;

public final class ComplexTask extends Task{
    private ArrayList<Task> tasks=new ArrayList<>();

    public ComplexTask() {}

    public void setTasks(ArrayList<Task> tasks)
    {
        this.tasks=tasks;
    }

    public ArrayList<Task> getTasks()
    {
        return this.tasks;
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

    public void addTask(Task task)  throws NonexistentElement
    {
        if(task==null)
        {
            throw new NonexistentElement("Task null!!!");
        }
            this.tasks.add(task);
            task.setParent(this);

    }

    public void deleteTask(Task task) throws NonexistentElement
    {
        if(task==null)
        {
            throw new NonexistentElement("Trebuie sa selectati un task!");
        }

        if(this.tasks.remove(task))
            {
                task.setParent(null);
                return;
            }
        else{
            throw new NonexistentElement("Taskul selectat nu exista printre subtaskurile ComplexTaskului curent!");
        }
    }

    public int nrHoursForCompletedTasks()
    {
        if(this.tasks!=null)
        {
            int aux=0;
            for(Task t: this.tasks)
            {
                if(t.getStatusTask().equals("Completed"))
                {
                    aux+=t.estimateDuration();
                }else if(t instanceof ComplexTask)
                {
                    aux+=((ComplexTask)t).nrHoursForCompletedTasks();
                }
            }
            return aux;
        }
        else return 0;
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

    @Override
    public String toString()
    {
        String rez="ComplexTask "+this.getIdTask()+", estimatedDuration="+this.estimateDuration()+", status= "+this.getStatusTask()+"\n";
        /*for(Task t: this.tasks)
        {
            rez+=" "+t.toString();
        }*/
        return rez;
    }
}
