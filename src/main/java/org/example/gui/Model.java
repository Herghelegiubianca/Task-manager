package org.example.gui;

import org.example.businessLogic.*;
import org.example.dataModel.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Model{
    private TaskManagement tm;
    private Utility u;

    public Model(){
        this.tm=new TaskManagement();
        this.u=new Utility();
    }


    public TaskManagement getTm() {
        return tm;
    }

    public void setTm(TaskManagement tm) {
        this.tm = tm;
    }

    public void addEmployee(String name)
    {
        this.tm.addEmployee(name);
    }

    public void addTask(String typeOfTask, int startHour, int endHour,ComplexTask parent) throws Exception
    {
        this.tm.addTask(typeOfTask, startHour, endHour, parent);
    }

    public int calculateWorkDuration(Employee e) throws NonexistentEmployee
    {
        int rez=this.tm.calculateEmployeeWorkDuration(e.getIdEmployee());
        return rez;
    }

    public void assignTask(Employee e, Task t) throws NonexistentEmployee, InvalidComplexTask
    {
        this.tm.assignTaskToEmployee(e.getId(),t);
    }

    public String filterEmployees() throws NonexistentEmployee, NoEmployeesException
    {
        return u.filterEmployees(tm);
    }

    public void modifyStatus(Employee e, Task t) throws NonexistentEmployee,NonexistentTask
    {
        this.tm.modifyTaskStatus(e.getIdEmployee(),t.getIdTask());
    }

    public void deleteTask(Task t) throws NonexistentTask
    {
        this.tm.deleteTask(t);
    }

    public String statistics() throws Exception {
        String rez=" ";
            Map<String,Map<String,Integer>> employeesStatistics=this.u.statistics(this.tm);
            for(String name: employeesStatistics.keySet())
            {
                rez+="Employee "+name+":\n"+"    Uncompleted="+employeesStatistics.get(name).get("Uncompleted")+",\n     Completed="+employeesStatistics.get(name).get("Completed")+"\n";
            }
            return rez;
    }

}
