package TodoList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class main {

static	ArrayList<ToDoTask> todo = new ArrayList<ToDoTask>();;

public static void main(String args[]) throws FileNotFoundException{

File in = new File("myTasks.txt");
File out = new File("myTasks.out");
System.out.println("Now read todolist from file myTasks.txt:");
ReadFromFile(in);   
displayAllTasks() ;
System.out.println("Add an entry:");
add_Task("haircut", "get a cool new haircut", "9/20/2013");
System.out.println("Add an entry:");
add_Task("2168_deadline", "Finish Lab 3 - Its due on Sep 19th 10pm, no points after that", "9/19/2013");
System.out.println("Add an entry:");
add_Task("Dinner", "cook some food for tonight", "9/21/2013");
displayAllTasks() ;
System.out.println("Find an entry:");
findTask("haircut");
System.out.println("Find an entry:");
displayTask(findTask("9/16/2013") );
System.out.println("Find and remove an entry:");
remove_Task(findTask("Dinner"));
displayAllTasks() ;
System.out.println("Save current todolist to myTasks.out:");
saveToFile(out);

	
}


static void saveToFile(File file) throws FileNotFoundException{
    PrintWriter out = new PrintWriter(file);
    out.println("# Name # Description # Duedate");
    for(ToDoTask s : todo){
        out.print("# ");
        out.print(s.returnName());
        out.print(" # ");
        out.print(s.returnDescription());
        out.print(" # ");
        out.println(s.returnDate());       
    }
    out.close();
}

static void add_Task(String name, String description, String due_date){
    todo.add(new ToDoTask(name,description,due_date));
    Collections.sort(todo, new ListSortByDate() );
    System.out.println("New task added ");
    
}



static void remove_Task(ToDoTask x){
    System.out.println("\nRemove: ");
    x.printOut();
    System.out.println();
    todo.remove(x);
    Collections.sort(todo, new ListSortByDate() );
}


static ArrayList<ToDoTask> ReadFromFile(File file) throws FileNotFoundException{
	
	Scanner f = new Scanner(file);
	f.nextLine(); //skip the first line
	while(f.hasNextLine()){
		String[] infor = f.nextLine().split("#");
		ToDoTask task = new ToDoTask(infor[0],infor[1],infor[2]);
                todo.add(task);
	}
        System.out.println("Original TodoList: ");
        displayAllTasks();
        Collections.sort(todo, new ListSortByDate() );
	return todo;
}


static class ListSortByDate implements Comparator<ToDoTask> {
    // Override the compare method of Comparator
    @Override
    public int compare(ToDoTask list1, ToDoTask list2) {
        return list1.sortDate().compareTo(list2.sortDate());
    }
}

static void displayAllTasks(){
        System.out.println("Current sorted todolist");
       System.out.println("# name # description # date");
    for(ToDoTask s : todo){
        s.printOut();
    }
    System.out.println();
}

static void displayTask(ToDoTask t){
    t.printOut();
}


static ToDoTask findTask(String x){
    ToDoTask f = new ToDoTask("No","Record","Found\n");
    for(ToDoTask s : todo){
        if(s.contain(x)){
           f = s;
           System.out.println("Entry:\b");
           s.printOut();
           System.out.println("has been found!");
           break;
        }
    }
    return f;

    
}

}
