import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ContactManagerMeetingTest {

	ContactManager cm;
	
	@Before
	public void setUp() throws Exception {
		
		this.cm = new ContactManagerImpl();
		for (int i = 0; i < 10 ; i++){
			cm.addNewContact("Name "+i, "Notes "+i);
		}
	}

	//addFutureMeeting() Tests (AFM for short)
	/**AFMTest1 - Tests the base case - a  single FutureMeeting in the Future with a single contact
	 * 
	 * Should result in a meeting matching the test parameters
	 */
	@Test
	public void AFM1singleContactFutureDate(){
		Calendar meetingdate = TestTools.createCalendar(1);
		cm.addFutureMeeting(cm.getContacts(0), meetingdate);
		FutureMeeting expected = new FutureMeetingImpl(0, meetingdate, cm.getContacts(0));
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	/**AFMTest2 - Tests a meeting that has had multiple, existing contacts added to it. Date in the future
	 * 
	 * Should result in a meeting with several contacts allocated to it.
	 */
	@Test
	public void AFM2multiContactFutureDate(){
		Calendar meetingdate = TestTools.createCalendar(1);
		cm.addFutureMeeting(cm.getContacts(0,1,2,3), meetingdate);
		FutureMeeting expected = new FutureMeetingImpl(0, meetingdate, cm.getContacts(0,1,2,3));
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	/**AFMTest3- Empty set of contacts, date in the future
	 */
	@Test
	public void AFM3emptySetFutureDate(){
		Calendar meetingdate = TestTools.createCalendar(1);
		cm.addFutureMeeting(new HashSet<Contact>(), meetingdate);
		FutureMeeting expected = new FutureMeetingImpl(0, meetingdate, new HashSet<Contact>());
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	/**AFMTest4 - Contact set is null, date is in the future
	 * 
	 *Should work (by spec)
	 * 
	 */
	//TODO - may need changing if answer on Forum comes back that this should result in null pointer exception
	@Test
	public void AFM4NullSetFutureMeeting(){
		Calendar meetingdate = TestTools.createCalendar(1);
		cm.addFutureMeeting(null, meetingdate);
		FutureMeeting expected = new FutureMeetingImpl(0, meetingdate, null);
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	/**AFMTest5- Some of the Contacts added are not in CM, date in the future
	 * 
	 * Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void AFM5SomeContactsNotInCM(){
		Set<Contact> contacts = cm.getContacts(0,1,2);
		//Note below contact is equal but not identical to contacts in CM. Need to be the same object
		contacts.add(new ContactImpl(0, "Name 0", "Notes 0"));
		cm.addFutureMeeting(contacts, TestTools.createCalendar(1));
	}

	/**AFMTest6- all contacts to add are not in CM, date in the future
	 * 
	 * Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void AFM6NoContactsNotInCM(){
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl(100, "Notin CRM1", "This Should Break"));
		contacts.add(new ContactImpl(101, "Notin CRM2", "This Should Break"));
		cm.addFutureMeeting(contacts, TestTools.createCalendar(1));
	}

	/**AFMTest7- Contact set works, but the date is in the past.
	 * 
	 *Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void AFM7PastDate(){
		cm.addFutureMeeting(cm.getContacts(0,1,2), TestTools.createCalendar(-1));
	}
	
	/**AFMTest8- Contact set works, but the date is today.
	 * 
	 *Should result in IllegalArgumentException
	 */
	//TODO - double check this is the case. Might be ok.
	@Test(expected = IllegalArgumentException.class)
	public void AFM8TodaysDate(){
		cm.addFutureMeeting(cm.getContacts(0,1,2), TestTools.createCalendar(0));
	}
	
	/**AFMTest9 - Multiple meetings added. Tests that the meeting ID increases
	 * 
	 * Should result in final meeting matching the last added with and ID of 10
	 */
	@Test
	public void AFM9MultipleMeetings(){

		for (int i = 0; i < 10 ; i ++)
			cm.addFutureMeeting(cm.getContacts(i), TestTools.createCalendar(i));

		Meeting expected = new MeetingImpl(9, TestTools.createCalendar(9), cm.getContacts(9));
		assertEquals(expected, cm.getFutureMeeting(9));
	}
	
	
	//TODO - Add Past meetings Tests
	//Test1- Single existing contact, date in the past, notes work
	//Test2- multiple existing contacts, date in the past, notes work
	//Test3- Empty set of contacts, date in the past, notes work
	//Test4- null, date in the past, notes work
	//Test5- Some Contacts not in CM, date in the past, notes work
	//Test6- all contacts not in CM, date in the past, notes work
	//Test5- Working set,  date in the future, notes work
	//Test5- Working set,  date is today, notes work
	//Test6- multiple working meetings, test ID increases
	//Test7- multiple working meetings with some failures, test no extra IDs
	//Test9- Single existing contact, date in the past, null notes
	//Test10-Single existing contact, null date, notes work
	
	//TODO - Add Get Meeting Tests
	
	
	//TODO - Get Future Meeting Tests
	//Test2- ID matches past meeting
	//Test3- ID Matches no meeting
	
	//TODO - Get Past meeting tests
	//Test1- ID Matches past meeting
	//Test2- ID matches future meeting
	//Test3- ID Matches no meeting
	
	//TODO - getFutureMeetingList(Contact) tests (Note: Check order)
	//Test1- no meetings with contact
	//Test2- one meeting with contact
	//Test3- multiple meetings with contact
	//Test4- convert future meeting to past, get list
	//Test5- contact does not exist
	
	
	//TODO - getFutureMeetingList(Date) tests (Note: Check order)
	//Test1- Date matches to future meeting
	//Test2- date matches to multiple future meetings
	//Test3- Date does not match to any future meetings
	//Test4- Date is in the past
	
	//TODO - getPastMeetingList(Contact) tests (Note: Check order)
	//Test1- no meetings with contact
	//Test2- one meeting with contact
	//Test3- multiple meetings with contact
	//Test4- convert future meeting to past, get list
	//Test5- contact does not exist
	
	
	//TODO - getPasteMeetingList(Date) tests (Note: Check order)
	//Test1- Date matches to past meeting
	//Test2- date matches to multiple past meetings
	//Test3- Date does not match to any past meetings
	//Test4- Date is in the future
	
	//TODO - addMeetingNotes tests
	//Test1 - Add notes to past meeting, check they add
	//Test2 - Add notes to past meeting multiple times
	//Test3 - Add null notes to past meeting
	//Test4 - Add notes to future meeting with past date, check it converts (Force date change?)
	//Test5 - add notes to future meeting, check consistency of list
	//Test6 - add notes to future meeting in future, check exception
	//Test7 - add null notes to future meeting
	

}
