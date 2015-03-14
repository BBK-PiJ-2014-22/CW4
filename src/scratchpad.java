import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class scratchpad {

	public static void main(String[] args){
		
		

		System.out.println(ContactManagerImpl.sameDate(TestTools.createCalendarHours(0), TestTools.createCalendarHours(0)));
		System.out.println(ContactManagerImpl.sameDate(TestTools.createCalendarHours(25), TestTools.createCalendarHours(26)));
		System.out.println(ContactManagerImpl.sameDate(TestTools.createCalendarHours(-25), TestTools.createCalendarHours(-26)));
		System.out.println(ContactManagerImpl.sameDate(TestTools.createCalendarMonths(48), TestTools.createCalendarHours(0)));
		
		
		
		ContactManager cm = new ContactManagerImpl();
			for (int i = 0; i < 10 ; i++){
				cm.addNewContact("Name "+i, "Notes "+i);
			}		
		
		Object[][] meetingData = {
				  {0, cm.getContacts(0,1), TestTools.createCalendarHours(2)},		
			      {1, cm.getContacts(0,1), TestTools.createCalendarMonths(1)},
			      {2, cm.getContacts(0,1), TestTools.createCalendarHours(3)},
			      {3, cm.getContacts(0,1), TestTools.createCalendarMonths(2)},
			      {4, cm.getContacts(0,1), TestTools.createCalendarHours(4)},
			      {5, cm.getContacts(0,1), TestTools.createCalendarHours(-1), "Notes"},
			      {6, cm.getContacts(0,1), TestTools.createCalendarHours(-2), "Notes"}
			      };
		
		int[] orderedIDs = {6,5,0,3,4};
		
		List<Meeting> expected = ContactManagerMeetingTest.buildFutureMeetingSetup(cm, meetingData, orderedIDs);
		
		System.out.println(expected);
		
		
		/*
		Contact contact0 = new ContactImpl(0,"Name 0", "Notes 0");
		Contact contact1 = new ContactImpl(1,"Name 1", "Notes 1");
		Contact contact0b = new ContactImpl(0,"Name 0", "Notes 0");
		Contact contact1b = new ContactImpl(1,"Name 1", "Notes 1");
		
		Set<Contact> setreference1 = new HashSet<Contact>();
		Set<Contact> setreference2 = new HashSet<Contact>();
		Set<Contact> setvalue = new HashSet<Contact>();
		
		setreference1.add(contact0);
		setreference1.add(contact1);

		setreference2.add(contact0);
		setreference2.add(contact1);
		
		setvalue.add(contact1b);
		setvalue.add(contact0b);
		
		System.out.println(setreference1.equals(setreference2));
		System.out.println(setreference1.equals(setvalue));
		System.out.println(contact0.equals(contact0b));
		System.out.println(contact1.equals(contact1b));
		*/
		
		/*List<Contact> original = Arrays.asList(new ContactImpl(0,"0","0"),
											   new ContactImpl(1,"1","1"),
											   new ContactImpl(2,"2","2"),
											   new ContactImpl(3,"3","3"));
		Integer[] filter = {2,3};
		
		
		System.out.println(Arrays.asList(filter).get(0));
		
		for (Contact contact : original){
			System.out.println(Arrays.asList(filter).contains(contact.getId()));
		}
		
		for (Contact contact : original){
			System.out.println(contact.getId() == 0);
		}
		
		ContactManagerImpl cm = new ContactManagerImpl();
		
		System.out.println(cm.contactInList(original.get(2), Arrays.asList(filter)));
		
		Set<Object> result = original.stream()
                .filter(number -> true)
                .collect(Collectors.toSet());

		System.out.println(result);
		*/
		
		/*
		ContactManagerImpl cm = new ContactManagerImpl();
		
		cm.addNewContact("Bob", "Notes");
		
		System.out.println(cm.contactlist);
	*/
		
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
}


