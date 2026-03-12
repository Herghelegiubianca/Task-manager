package org.example.DataModel;

public final class SimpleTask extends Task{
    private int startHour;
    private int endHour;

    public SimpleTask(){}

    public SimpleTask(int idTask, String statusTask, int startHour, int endHour)
    {
        super(idTask,statusTask);
        this.startHour=startHour;
        this.endHour=endHour;
    }

    public int getStartHour()
    {
        return this.startHour;
    }

    public int getEndHour()
    {
        return this.endHour;
    }

    public void setStartHour(int startHour)
    {
        this.startHour=startHour;
    }

    public void setEndHour(int endHour)
    {
        this.endHour=endHour;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o instanceof SimpleTask)
        {
            SimpleTask s=(SimpleTask )o;
            if(this.getIdTask()==s.getIdTask()&&this.getStatusTask().equals(s.getStatusTask())&&this.startHour==s.startHour&&this.endHour==s.endHour)
            {
                return true;
            }
            else{
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    public int estimateDuration()
    {
        int aux=this.endHour;

        if(this.endHour<this.startHour)
        {
            aux+=24;
        }
        return aux-this.startHour;
    }

}
