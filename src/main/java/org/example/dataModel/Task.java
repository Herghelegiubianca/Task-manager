package org.example.dataModel;

import java.io.Serializable;

public sealed abstract class Task implements Serializable permits SimpleTask,ComplexTask{
    private static final long serialVersionUID = 1L;
    private int idTask;
    private String statusTask;
    private Task parent;
    private static int counter;

    public Task()
    {
        this.idTask=counter;
        counter++;
        this.statusTask="Uncompleted";
        this.parent=null;
    }

    public Task getParent() {
        return parent;
    }

    public void setParent(Task parent) {
        this.parent = parent;
    }

    public int getIdTask()
    {
        return this.idTask;
    }

    public String getStatusTask()
    {
        return this.statusTask;
    }

    public void setIdTask(int idTask)
    {
        this.idTask=idTask;
    }

    public void setStatusTask(String statusTask)
    {
        this.statusTask=statusTask;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Task.counter=counter;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this instanceof Task && o instanceof Task)
        {
            Task t=(Task)o;
            if(this.idTask==t.idTask)
            {
                return true;
            }
        }

        return false;
    }


    public abstract int estimateDuration();

    @Override
    public abstract String toString();

}
