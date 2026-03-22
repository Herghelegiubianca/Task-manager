package org.example.dataModel;

public final class SimpleTask extends Task{
    private int startHour;
    private int endHour;

    public SimpleTask(){}

    public SimpleTask(int startHour, int endHour)
    {
        super();
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
    public int estimateDuration()
    {
        int aux=this.endHour;

        if(aux<this.startHour)
        {
            aux+=24;
        }
        return aux-this.startHour;
    }

    @Override
    public String toString()
    {
        return "SimpleTask "+this.getIdTask()+", estimatedDuration="+this.estimateDuration()+", status= "+this.getStatusTask()+"\n";
    }


}
