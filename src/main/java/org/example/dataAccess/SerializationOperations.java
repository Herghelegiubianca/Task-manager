package org.example.dataAccess;

import org.example.dataModel.Employee;
import org.example.dataModel.Task;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SerializationOperations{
    private Map<Employee, List<Task>> map;
    private ArrayList<Task> tasks;
    private ArrayList<Employee> employees;

    public SerializationOperations(){}

    public SerializationOperations(Map<Employee, List<Task>> map, ArrayList<Task> tasks, ArrayList<Employee> employees) {
        this.map = map;
        this.tasks = tasks;
        this.employees = employees;
    }

    public void setMap(Map<Employee, List<Task>> map) {
        this.map = map;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public Map<Employee, List<Task>> getMap() {
        return map;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void saveData() throws
            ParseException, IOException,
            ClassNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream("john_doe.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeInt(Employee.getCounter());
        objectOutputStream.writeInt(Task.getCounter());
        objectOutputStream.writeObject(this.map);
        objectOutputStream.writeObject(this.tasks);
        objectOutputStream.writeObject(this.employees);
        objectOutputStream.flush();
        objectOutputStream.close();}

    public void getData() throws
            ParseException, IOException,
            ClassNotFoundException{

        FileInputStream fileInputStream = new FileInputStream("john_doe.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        int idE=(int) objectInputStream.readInt();
        int idT=(int) objectInputStream.readInt();

        Employee.setCounter(idE);
        Task.setCounter(idT);

        this.map = (Map<Employee, List<Task>>) objectInputStream.readObject();
        this.tasks = (ArrayList<Task>) objectInputStream.readObject();
        this.employees = (ArrayList<Employee>) objectInputStream.readObject();
        objectInputStream.close();
    }


}