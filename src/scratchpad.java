import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

public class scratchpad {

	public static void main(String[] args){
	
	
		Object[] data = collateTestData("1",1,'1');
		
		for (Object datum : data)
			System.out.println(datum);
		
		
		ContactManager cm = new ContactManagerImpl();
		
		cm.addNewContact("Bob", "Notes");
		
		
	
		
	//Calendar old = new GregorianCalendar();
	//	old.add(Calendar.MONTH, -1);	
	//Calendar now = new GregorianCalendar();
	//System.out.println(old.compareTo(now));
		
		
	//Object test = new Integer(1);
	//Contact contact = (Contact)test; 
		
		
		
	//	Class classParam = MeetingImpl.class;
		
	//	Set<Integer> integerset = new Set<Integer>(1,2,3,4);
		/*
		Calendar calendar = new GregorianCalendar();

		System.out.println(displayCalendar(calendar));
		
		calendar.add(Calendar.MONTH, -1);
		
		System.out.println(displayCalendar(calendar));
		
	*/
	
	}
	
	public static String displayCalendar(Calendar calendar){
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH)+1;
		
		return day + "/"+month+"/"+year;
				
	}
	
	private static Object[] collateTestData(Object... data){
		return data;
	}
	
}


