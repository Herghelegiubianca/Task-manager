package org.example.gui;

import org.example.dataModel.ComplexTask;
import org.example.dataModel.NonexistentElement;
import org.example.dataModel.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComplexTaskController {
    private ComplexTaskView ctv;
    private Model m;

    public ComplexTaskController(ComplexTaskView view, Model mod)
    {
        this.ctv=view;
        this.m=mod;

        this.ctv.showTasksActionListener(new ShowSubTasksListener());
        this.ctv.addTaskActionListener(new AddSubTaskListener());
        this.ctv.deleteTaskActionListener(new DeleteSubTaskListener());

    }

    class ShowSubTasksListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Task selectedTask=ctv.selectedTask();

            if(selectedTask!=null)
            {
                if(selectedTask instanceof ComplexTask)
                {
                    ComplexTask ct=(ComplexTask)selectedTask;

                    ComplexTaskView secondView=new ComplexTaskView(ct);

                    secondView.getFrame().addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent)
                        {
                            ctv.getModelTasks().clear();

                            if(ctv.getCurrentTask()!=null)
                            {
                                ctv.getModelTasks().addAll(((ComplexTask)ctv.getCurrentTask()).getTasks());
                            }else ctv.getModelTasks().addAll(m.getTm().getTasks());
                        }

                    });

                    ComplexTaskController secondController=new ComplexTaskController(secondView,m);
                    secondView.showFrame();
                    ctv.clearTSel();

                    return;
                }else{
                    ctv.displayErrorMessages("SIMPLE TASK! Nu exista nimic de afisat!");
                    ctv.clearTSel();
                    return;
                }
            }else{
                ctv.displayErrorMessages("Nu ati selectat nimic!");
                return;
            }
        }
    }

    class AddSubTaskListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
               boolean simple=ctv.isSimpleTask();
               boolean complex=ctv.isComplexTask();

               String type=" ";
               if(simple)
               {
                   type="Simple";
               }else if(complex)
               {
                   type="Complex";
               }else{
                   ctv.displayErrorMessages("Trebuie sa selectati fie simpleTask, fie complexTask!");
                   return;
               }

               try{
               m.addTask(type,ctv.getStartHour(),ctv.getEndHour(),(ComplexTask)ctv.getCurrentTask());
               ctv.getModelTasks().clear();
               ctv.getModelTasks().addAll(((ComplexTask)ctv.getCurrentTask()).getTasks());
               }
               catch(Exception ex)
               {
                   ctv.displayErrorMessages(ex.getMessage());
               }
        }
    }

    class DeleteSubTaskListener implements ActionListener{

        public DeleteSubTaskListener(){}

        @Override
        public void actionPerformed(ActionEvent e){
            Task selectedTask=ctv.selectedTask();

            if(selectedTask==null) {
                ctv.displayErrorMessages("Nu ati selectat nimic!");
                return;
            }

            ComplexTask parent=(ComplexTask)selectedTask.getParent();
            try{
            parent.deleteTask(selectedTask);}
            catch (NonexistentElement ex) {ctv.displayErrorMessages(ex.getMessage());
            }

            ctv.getModelTasks().clear();
            ctv.getModelTasks().addAll(parent.getTasks());
        }
    }

}
