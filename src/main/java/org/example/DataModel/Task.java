package org.example.DataModel;

public sealed abstract class Task permits SimpleTask,ComplexTask{
    private int idTask;
    private String statusTask;

    public Task(){}

    public Task(int idTask, String statusTask)
    {
        this.idTask=idTask;
        this.statusTask=statusTask;
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

    @Override
    public abstract boolean equals(Object o);

    public abstract int estimateDuration();

}
