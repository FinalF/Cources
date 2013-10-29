package TodoList;

public class ToDoTask {

	String taskname=null;
	String description=null;
	int dueDay=0;
	int dueMonth=0;
	int dueYear=0;
        String dueDate=null;

        ToDoTask(){
    
        }
        
        ToDoTask(String name, String des, String date){
            this.taskname = name;
            this.description = des;
            this.dueDate = date;
        }
	
	void givename(String name){
		taskname = name;
	}
	
	void givedescription(String des){
		description = des;
	}
	
        void giveDateasString(String date){
            dueDate = date;
        }
        
	void giveDate(int day, int month, int year){
		dueDay = day;
		dueMonth = month;
		dueYear = year;
	}

        
	String returnName(){
		return taskname;
	}
	
	String returnDescription(){
		return description;
	}
	
        String returnDate(){
//            if(dueDate.equals(" ")){
//                return dueDate;
//            }else{
//            String[] date = dueDate.split("/");
//                dueDate = date[2]+date[1]+date[0];
//		return dueDate;
//            }
            return dueDate;
	}
	
        String sortDate(){
             if(dueDate.equals(" ")){
                 return "99/99/9999";
             }else{
                 return dueDate;
             }
        }
        
	int returnDay(){
		return dueDay;
	}
	
	int returnMonth(){
		return dueMonth;
	}
	
	int returnYear(){
		return dueYear;
	}
        
        void printOut(){
            System.out.println("# "+taskname+" # "+description+" # "+dueDate);
        }
        
        boolean contain( String x){
            if(taskname.equals(x) || description.equals(x)||dueDate.equals(x)){
                return true;
            }else{
                return false;
            }
        }
	
}
