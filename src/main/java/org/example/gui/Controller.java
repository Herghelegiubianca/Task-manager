package org.example.gui;

import org.example.businessLogic.TaskManagement;
import org.example.dataAccess.SerializationOperations;
import org.example.dataModel.ComplexTask;
import org.example.dataModel.Employee;
import org.example.dataModel.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private View view;

    public Controller()
    {
        Model m=new Model();
        try{
        m.getTm().getInfo();}
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        this.view=new View(m);

        this.view.addEmployeeActionListener(new AddEmployeeListener());
        this.view.showTasksActionListener(new ShowTasksActionListener());
        this.view.addTaskActionListener(new AddTaskActionListener());
        this.view.assignTaskActionListener(new AssignTaskActionListener());
        this.view.deleteTaskActionListener(new DeleteTaskActionListener());
        this.view.filterEmployeesActionListener(new FilterEmployeesActionListener());
        this.view.workDurationActionListener(new WorkDurationActionListener());
        this.view.statisticsActionListener(new StatisticsActionListener());


        this.view.getFrame().addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                try{
                    m.getTm().saveInfo();
                }catch(Exception e){
                    System.err.println(e);
                }
                System.exit(0);
            }

        });
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    class AddEmployeeListener implements ActionListener {
        public AddEmployeeListener(){}

        @Override
        public void actionPerformed(ActionEvent e) {
            String name=view.getEmployeeName();

            if(name.isEmpty() || name.equals("Employee Name") || name.equals(" "))
            {
                view.displayErrorMessages("Nu ati introdus un nume!");
                view.clearEmployeeName();
                return;
            }

            int index=0;
            for(char c: name.toCharArray())
            {
                if(!Character.isLetter(c) && c!=' ')
                {
                    view.displayErrorMessages("Numele introdus nu este valid!");
                    view.clearEmployeeName();
                    return;
                }

                if(c==' ')
                {
                   char [] aux= name.toCharArray();
                   boolean ok=false;

                   if(index>0 && Character.isLetter(aux[index-1]))
                   {
                       ok=true;
                   }

                    if(index<(aux.length-1) && Character.isLetter(aux[index+1]))
                    {
                        ok=true;
                    }

                    if(!ok)
                    {
                        view.displayErrorMessages("Numele introdus nu este valid!");
                        view.clearEmployeeName();
                        return;
                    }

                }
                index++;
            }
            view.getM().addEmployee(name);
            view.getEmploy().clear();
            view.getEmploy().addAll(view.getM().getTm().getEmployees());

        }
    }

    class ShowTasksActionListener implements ActionListener {
        public ShowTasksActionListener(){}

        @Override
        public void actionPerformed(ActionEvent e) {

            Task selectedTask=view.selectedTask();
            Employee selectedEmployee=view.selectedEmployee();
            if(selectedTask==null && selectedEmployee==null)
            {
                view.displayErrorMessages("Nu ati selectat niciun task si niciun angajat!");
                return;
            }else if(selectedTask!=null && selectedEmployee!=null)
            {
                view.displayErrorMessages("Trebuie sa selectati fie un task, fie un angajat!");
                view.clearSels();

                return;
            }

            if(selectedTask!=null)
            {
                if(selectedTask instanceof ComplexTask)
                {
                    ComplexTask c=(ComplexTask)selectedTask;
                    ComplexTaskView secondView=new ComplexTaskView(c);

                    secondView.getFrame().addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent)
                        {
                            view.getToDo().clear();
                            view.getToDo().addAll(view.getM().getTm().getTasks());
                        }});
                    ComplexTaskController secondController=new ComplexTaskController(secondView,view.getM());

                    secondView.showFrame();
                    view.clearTSel();
                }else{
                    view.displayErrorMessages("SIMPLE TASK! Nu exista nimic de afisat!");
                }
                return;
            }

                EmployeeView secondView=new EmployeeView(selectedEmployee,view.getM());

            secondView.getFrame().addWindowListener(new java.awt.event.WindowAdapter(){
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent)
                {
                    view.getToDo().clear();
                    view.getToDo().addAll(view.getM().getTm().getTasks());
                }

            });
                EmployeeController secondController=new EmployeeController(secondView,view.getM());
                secondView.showFrame();
                view.clearESel();

        }}

    class AddTaskActionListener implements ActionListener {
        public AddTaskActionListener(){}

        @Override
        public void actionPerformed(ActionEvent e) {

            boolean simple=view.isSimpleTask();
            boolean complex=view.isComplexTask();

            String type=" ";
            if(simple)
            {
                type="Simple";
            }else if(complex)
            {
                type="Complex";
            }else{
                view.displayErrorMessages("Trebuie sa selectati fie simpleTask, fie complexTask!");
                return;
            }

            try{
                view.getM().addTask(type,view.getStartHour(),view.getEndHour(),null);
                view.getToDo().clear();
                view.getToDo().addAll(view.getM().getTm().getTasks());
            }
            catch(Exception ex)
            {
                view.displayErrorMessages(ex.getMessage());
            }

        }}

    class AssignTaskActionListener implements ActionListener {
        public AssignTaskActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Task selectedTask=view.selectedTask();
            Employee selectedEmployee=view.selectedEmployee();
            if(selectedTask==null || selectedEmployee==null)
            {
                view.displayErrorMessages("Trebuie sa selectati atat un task, cat si un angajat!");
                view.clearSels();
                return;
            }

            try{
            view.getM().assignTask(selectedEmployee,selectedTask);
            view.getToDo().clear();
            view.getToDo().addAll(view.getM().getTm().getTasks());
            view.clearSels();
            }
            catch(Exception ex)
            {
                view.displayErrorMessages(ex.getMessage());
                view.clearSels();
            }
        }
    }

    class DeleteTaskActionListener implements ActionListener {

        public DeleteTaskActionListener(){}

        @Override
        public void actionPerformed(ActionEvent e) {

            Task selectedTask=view.selectedTask();
            if(selectedTask==null)
            {
                view.displayErrorMessages("Trebuie sa selectati un task!");
                return;
            }

            try{
            view.getM().deleteTask(selectedTask);
            view.getToDo().clear();
            view.getToDo().addAll(view.getM().getTm().getTasks());
            view.clearTSel();}
            catch(Exception ex)
            {
                view.displayErrorMessages(ex.getMessage());
                view.clearTSel();
            }
        }
    }

    class FilterEmployeesActionListener implements ActionListener {

        public FilterEmployeesActionListener(){

        }

        @Override
        public void actionPerformed(ActionEvent e) {

            try{
               String emp=view.getM().filterEmployees();
               view.displayFilteredEmployees(emp);
            }
            catch (Exception ex) {
                view.displayErrorMessages(ex.getMessage());
            }

        }
        }

    class WorkDurationActionListener implements ActionListener {

        public WorkDurationActionListener(){}

            @Override
            public void actionPerformed(ActionEvent e){
                   Employee emp=view.selectedEmployee();
                   if(emp==null)
                   {
                       view.displayErrorMessages("Trebuie sa selectati un angajat!");
                   }
                   else{
                       try{
                           int nrH=view.getM().calculateWorkDuration(emp);
                           view.displayEmployeeWH(emp.toString(),nrH);
                           view.clearESel();
                       }catch(Exception ex)
                       {
                           view.displayErrorMessages(ex.getMessage());
                           view.clearESel();
                       }

                   }

            }
    }

    class StatisticsActionListener implements ActionListener {

        public StatisticsActionListener(){}

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String rez=view.getM().statistics();
                view.displayStatistics(rez);

            }catch(Exception ex)
            {
                view.displayErrorMessages(ex.getMessage());
            }
         }
    }

}
