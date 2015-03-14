import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ContactManagerMeetingTest {

	ContactManager cm;
	
	/**Sets up a new ContactManager with 10 contacts to use
	 * 
	 * @throws Exception
	 */
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
		Calendar meetingdate = TestTools.createCalendarMonths(1);
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
		Calendar meetingdate = TestTools.createCalendarMonths(1);
		cm.addFutureMeeting(cm.getContacts(0,1,2,3), meetingdate);
		FutureMeeting expected = new FutureMeetingImpl(0, meetingdate, cm.getContacts(0,1,2,3));
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	/**AFMTest3- Empty set of contacts, date in the future
	 */
	@Test
	public void AFM3emptySetFutureDate(){
		Calendar meetingdate = TestTools.createCalendarMonths(1);
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
		Calendar meetingdate = TestTools.createCalendarMonths(1);
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
		//Note that the below contact is equal but not identical to contacts in CM. Need to be the same object
		contacts.add(new ContactImpl(3, "Name 3", "Notes 3"));
		cm.addFutureMeeting(contacts, TestTools.createCalendarMonths(1));
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
		cm.addFutureMeeting(contacts, TestTools.createCalendarMonths(1));
	}

	/**AFMTest7- Contact set works, but the date is in the past.
	 * 
	 *Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void AFM7PastDate(){
		cm.addFutureMeeting(cm.getContacts(0,1,2), TestTools.createCalendarMonths(-1));
	}
	
	/**AFMTest8- Contact set works, but the date is today.
	 * 
	 *Should result in IllegalArgumentException
	 */
	//TODO - double check this is the case. Might be ok.
	@Test(expected = IllegalArgumentException.class)
	public void AFM8TodaysDate(){
		cm.addFutureMeeting(cm.getContacts(0,1,2), TestTools.createCalendarMonths(0));
	}
	
	/**AFMTest9 - Multiple meetings added. Tests that the meeting ID increases,
	 * and that the AFM method returns correctly.
	 * 
	 * Should result in an array of numbers 0 - 9
	 */
	@Test
	public void AFM9MultipleMeetings(){

		int[] actual = new int[10];
		
		for (int i = 0; i < 10 ; i ++)
			actual[i] = cm.addFutureMeeting(cm.getContacts(i), TestTools.createCalendarMonths(1));

		int[] expected = {0,1,2,3,4,5,6,7,8,9};
		
		assertArrayEquals(expected, actual);
	}
	
	//***AddPastMeeting() Tests (APM for short)***
	
	/**APMTest1 - Tests the base case - a  single PastMeeting in the past with a single contact
	 * 
	 * Should result in a meeting matching the test parameters
	 */
	@Test
	public void APM1singleContactPastDate(){
		Calendar meetingdate = TestTools.createCalendarMonths(-1);
		cm.addNewPastMeeting(cm.getContacts(0), meetingdate, "Notes");
		PastMeeting expected = new PastMeetingImpl(0, meetingdate, cm.getContacts(0), "Notes");
		assertEquals(expected, cm.getMeeting(0));
	}
	
	/**APMTest2 - a  single PastMeeting in the past with multiple contacts
	 * 
	 * Should result in a meeting matching the test parameters
	 */
	@Test
	public void APM2MultiContactPastDate(){
		Calendar meetingdate = TestTools.createCalendarMonths(-1);
		cm.addNewPastMeeting(cm.getContacts(0), meetingdate, "Notes");
		PastMeeting expected = new PastMeetingImpl(0, meetingdate, cm.getContacts(0), "Notes");
		assertEquals(expected, cm.getMeeting(0));
	}
	
	/**APMTest3 - Empty set of contacts, date in the past, notes work
	 * 
	 * Should result in am illegal argument exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void APM3EmptyContactsPastDate(){
		Set<Contact> emptySet = new HashSet<Contact>();
		cm.addNewPastMeeting(emptySet,TestTools.createCalendarMonths(-1),"Notes");
	}	
	
	/**APMTest4 -null for contact set, date in the past, notes work
	 * 
	 * Should result in a null pointer exception
	 */
	@Test(expected = NullPointerException.class)
	public void APM4NullContactsPastDate(){
		cm.addNewPastMeeting(null, TestTools.createCalendarMonths(-1), "Notes");
	}
	
	/**APMTest5 -null for calendar, contacts and notes work
	 * 
	 * Should result in a null pointer exception
	 */
	@Test(expected = NullPointerException.class)
	public void APM5NullCalendarPastDate(){
		cm.addNewPastMeeting(cm.getContacts(0), null, "Notes");
	}	
	
	/**APMTest6 -null notes, date and contacts work
	 * 
	 * Should result in a null pointer exception
	 */
	@Test(expected = NullPointerException.class)
	public void APM6NullNotesPastDate(){
		cm.addNewPastMeeting(cm.getContacts(0), TestTools.createCalendarMonths(-1),null);
	}	
	
	/**APMTest7- Some of the Contacts added are not in CM, date in the past
	 * 
	 * Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void APM7SomeContactsNotInCM(){
		Set<Contact> contacts = cm.getContacts(0,1,2);
		//Note that the below contact is equal but not identical to contacts in CM.
		contacts.add(new ContactImpl(3, "Name 3", "Notes 3"));
		cm.addNewPastMeeting(contacts, TestTools.createCalendarMonths(-1), "Notes");
	}

	/**APMTest8- all contacts to add are not in CM, date in the past
	 * 
	 * Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void APM8NoContactsNotInCM(){
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(new ContactImpl(100, "Notin CRM1", "This Should Break"));
		contacts.add(new ContactImpl(101, "Notin CRM2", "This Should Break"));
		cm.addNewPastMeeting(contacts, TestTools.createCalendarMonths(-1), "Notes");
	}
	
	/**APMTest9- Date is in the future
	 * 
	 * Should result in IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void APM9DateInFuture(){
		cm.addNewPastMeeting(cm.getContacts(0), TestTools.createCalendarMonths(1), "Notes");
	}
	
	/**APMTest10- Date is today
	 * 
	 * Should parse as normal
	 */
	@Test(expected = IllegalArgumentException.class)
	public void APM10DateIsToday(){
		Calendar meetingdate = TestTools.createCalendarMonths(0);
		cm.addNewPastMeeting(cm.getContacts(0), meetingdate, "Notes");
		PastMeeting expected = new PastMeetingImpl(0, meetingdate, cm.getContacts(0), "Notes");
		assertEquals(expected, cm.getFutureMeeting(0));
	}
	
	//TODO - Add Past meetings Tests
	
	/**APMTest11 - Multiple Meetings added
	 * 
	 * Should result in the final meeting ID matching the correct meeting
	 */
	@Test
	public void APM11MultipleMeetings(){
	
		for (int i = 0; i < 10 ; i ++)
			cm.addNewPastMeeting(cm.getContacts(i), TestTools.createCalendarMonths(-1),"Notes"+i);
		
		Meeting actual = cm.getMeeting(9);
		Meeting expected = new PastMeetingImpl(9, TestTools.createCalendarMonths(-1), cm.getContacts(9) ,"Notes9");

		assertEquals(expected, actual);
	}
	
	//TODO - Add Get Meeting Tests
	
	/**
	 * Returns the meeting with the requested ID, or null if it there is none.
	 *
	 * @param id
	 *            the ID for the meeting
	 * @return the meeting with the requested ID, or null if it there is none.
	 */
	
	/**GetMeetingTest1 - tests itworks for a PastMeeting
	 * 
	 * Should return a meeting 
	 */
	@Test
	public void GMTest1MeetingInPast(){
		this.cm.addNewPastMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(-1), "text");
		Meeting expected = new PastMeetingImpl(0, TestTools.createCalendarMonths(-1), cm.getContacts(0,1), "text");
		assertEquals(expected, this.cm.getMeeting(0));
	}
	
	/**GetMeetingTest2 - tests itworks for a FutureMeeting
	 * 
	 * Should return a meeting 
	 */
	@Test
	public void GMTest2MeetingInFuture(){
		this.cm.addFutureMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(1));
		Meeting expected = new PastMeetingImpl(0, TestTools.createCalendarMonths(1), cm.getContacts(0,1), "text");
		assertEquals(expected, this.cm.getMeeting(0));
	}
	
	/**GetMeetingTest3 - tests it returns null if not present
	 * 
	 * should result in null	  
	 */
	@Test
	public void GMTest3MeetingDoesNotExist(){
		assertEquals(null, this.cm.getMeeting(0));
	}

	//Get Future Meeting Tests
	
	/**GetFutureMeetingTest1 - tests for a future meeting
	 * 
	 */
	@Test
	public void GFMTest1MeetingInFuture(){
		this.cm.addFutureMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(1));
		Meeting expected = new MeetingImpl(0, TestTools.createCalendarMonths(1), cm.getContacts(0,1));
		assertEquals(expected, this.cm.getFutureMeeting(0));
	}

	/**GetFutureMeetingTest2 - tests for a past meeting
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void GFMTest2MeetingInPast(){
		this.cm.addNewPastMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(-1), "text");
		this.cm.getFutureMeeting(0);
	}
	
	/**GetFutureMeetingTest3 - tests if no meeting with ID exists
	 * 
	 */
	@Test
	public void GFMTest3MeetingDoesNotExist(){
		assertEquals(null, this.cm.getFutureMeeting(0));
	}

	
	//TODO - Get Past meeting tests
	/**GetPastMeetingTest1 - tests for a future meeting
	 * 
	 */
	@Test
	public void GPMTest1MeetingInPast(){
		this.cm.addNewPastMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(-1), "notes");
		PastMeeting expected = new PastMeetingImpl(0, TestTools.createCalendarMonths(-1), cm.getContacts(0,1), "notes");
		assertEquals(expected, this.cm.getPastMeeting(0));
	}

	/**GetPastMeetingTest2 - tests for a future meeting
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void GpMTest2MeetingInFuture(){
		this.cm.addFutureMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(1));
		this.cm.getPastMeeting(0);
	}
	
	/**GetFutureMeetingTest3 - tests if no meeting with ID exists
	 * 
	 */
	@Test
	public void GPMTest3MeetingDoesNotExist(){
		assertEquals(null, this.cm.getFutureMeeting(0));
	}
	
	//TODO - getFutureMeetingList(Contact) tests (Note: Check order)

	/**GetFutureMeetingList(Contact)Test1 - no future meetings with contact
	 * 
	 * Should return empty list
	 */
	@Test
	public void GFMLTest1ContactNoMeetings(){
		Contact contact = (Contact)cm.getContacts(0).toArray()[0];
		cm.addNewPastMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(-1), "Notes");
		List<Meeting> expected = new ArrayList<Meeting>();
		List<Meeting> actual = cm.getFutureMeetingList(contact);
		assertEquals(expected, actual);
	}
	
	/**GetFutureMeetingList(Contact)Test2 - one future meetings with contact
	 * 
	 * Should return list of 1 meeting
	 */
	@Test
	public void GFMLTest2ContactOneMeeting(){
		
		cm.addNewPastMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(-1), "Notes");
		cm.addFutureMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(1));
		cm.addFutureMeeting(cm.getContacts(1,2), TestTools.createCalendarMonths(1));

		
		Contact contact = (Contact)cm.getContacts(0).toArray()[0];
		List<Meeting> expected = new ArrayList<Meeting>();
		expected.add(new FutureMeetingImpl(1, TestTools.createCalendarMonths(1),cm.getContacts(0,1)));
		
		List<Meeting> actual = cm.getFutureMeetingList(contact);
		assertEquals(expected, actual);
	}

	
	/**GetFutureMeetingList(Contact)Test3 - multiple future meetings with contact
	 * 
	 * Should return list of 5 meetings, sorted
	 */
	@Test
	public void GFMLTest3ContactMultipleMeetings(){
		
		cm.addNewPastMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(-1), "Notes");
		
		List<Meeting> expected = new ArrayList<Meeting>();
		
		//This data is used to both add meetings to the CM and to create the expected list
		Object[][] meetingData = {{1, cm.getContacts(0,1), TestTools.createCalendarMonths(2)},
								  {2, cm.getContacts(0,1), TestTools.createCalendarMonths(5)},		
							      {3, cm.getContacts(0,1), TestTools.createCalendarMonths(4)},
							      {4, cm.getContacts(0,1), TestTools.createCalendarMonths(1)},
							      {5, cm.getContacts(0,1), TestTools.createCalendarMonths(3)},
							      {6, cm.getContacts(1,2), TestTools.createCalendarMonths(1)}
							      };
		
		for (Object[] row : meetingData){
			cm.addFutureMeeting((Set<Contact>)row[1], (Calendar)row[2]);
		}
		//This is the order in chronological order needed for the expected list
		int[] listOrder = {4,1,5,3,2};
		
		for (int i = 0; i < 5 ; i++){
			for (Object[] row : meetingData){
				if (row[0].equals(listOrder[i]))
					expected.add(new FutureMeetingImpl((int)row[0], (Calendar)row[2], (Set<Contact>)row[1]));
			}
		}
		Contact contact = (Contact)cm.getContacts(0).toArray()[0];
		List<Meeting> actual = cm.getFutureMeetingList(contact);
		assertEquals(expected, actual);
	}
	
	/**GetFutureMeetingList(Contact)Test4 - contact does not exist
	 * 
	 * Should return an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void GFMLTest4ContactDoesNotExist(){
		Contact fakecontact = new ContactImpl(11, "Fake Name", "Fake Notes");
		cm.getFutureMeetingList(fakecontact);		
	}
	
	//GetPastMeetingList(Contact) tests (GPML)
	
	/**GetPastMeetingList(Contact)Test1 - no future meetings with contact
	 * 
	 * Should return empty list
	 */
	@Test
	public void GPMLTest1ContactNoMeetings(){
		Contact contact = (Contact)cm.getContacts(0).toArray()[0];
		cm.addFutureMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(1));
		cm.addNewPastMeeting(cm.getContacts(1,2), TestTools.createCalendarMonths(-1), "Notes");
		List<PastMeeting> expected = new ArrayList<PastMeeting>();
		List<PastMeeting> actual = cm.getPastMeetingList(contact);
		assertEquals(expected, actual);
	}
	
	/**GetFutureMeetingList(Contact)Test2 - one future meetings with contact
	 * 
	 * Should return list of 1 meeting
	 */
	@Test
	public void GPMLTest2ContactOneMeeting(){
		
		cm.addFutureMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(1));
		cm.addNewPastMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(-1), "Notes");
		cm.addNewPastMeeting(cm.getContacts(1,2), TestTools.createCalendarMonths(-1), "Notes");

		Contact contact = (Contact)cm.getContacts(0).toArray()[0];
		List<PastMeeting> expected = new ArrayList<PastMeeting>();
		expected.add(new PastMeetingImpl(1, TestTools.createCalendarMonths(-1),cm.getContacts(0,1)));
		
		List<PastMeeting> actual = cm.getPastMeetingList(contact);
		assertEquals(expected, actual);
	}

	
	/**GetFutureMeetingList(Contact)Test3 - multiple future meetings with contact
	 * 
	 * Should return list of 5 meetings, sorted
	 */
	@Test
	public void GPMLTest3ContactMultipleMeetings(){
		
		cm.addFutureMeeting(cm.getContacts(0,1), TestTools.createCalendarMonths(1));
		
		List<PastMeeting> expected = new ArrayList<PastMeeting>();
		//TODO - Refactor this to use build meeting
		//This data is used to both add meetings to the CM and to create the expected list
		Object[][] meetingData = {{1, cm.getContacts(0,1), TestTools.createCalendarMonths(-2)},
								  {2, cm.getContacts(0,1), TestTools.createCalendarMonths(-5)},		
							      {3, cm.getContacts(0,1), TestTools.createCalendarMonths(-4)},
							      {4, cm.getContacts(0,1), TestTools.createCalendarMonths(-1)},
							      {5, cm.getContacts(0,1), TestTools.createCalendarMonths(-3)},
							      {6, cm.getContacts(1,2), TestTools.createCalendarMonths(-1)}
							      };
		
		for (Object[] row : meetingData){
			cm.addNewPastMeeting((Set<Contact>)row[1], (Calendar)row[2], "Notes");
		}
		//This is the order in chronological order needed for the expected list
		int[] listOrder = {2,3,5,1,4};
		
		for (int i = 0; i < 5 ; i++){
			for (Object[] row : meetingData){
				if (row[0].equals(listOrder[i]))
					expected.add(new PastMeetingImpl((int)row[0], (Calendar)row[2], (Set<Contact>)row[1],"Notes"));
			}
		}
		Contact contact = (Contact)cm.getContacts(0).toArray()[0];
		List<PastMeeting> actual = cm.getPastMeetingList(contact);
		assertEquals(expected, actual);
	}
	
	/**GetFutureMeetingList(Contact)Test4 - contact does not exist
	 * 
	 * Should return an IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void GPMLTest4ContactDoesNotExist(){
		Contact fakecontact = new ContactImpl(11, "Fake Name", "Fake Notes");
		cm.getPastMeetingList(fakecontact);		
	}
	

	//getFutureMeetingList(Date) tests (Note: Check order)
	/**GetFutureMeetingList(Date)Test1 - tests when multiple future meetings match the date
	 * 
	 * Should result in a list of 2 meetings
	 */
	@Test
	public void GFMLDateTest1MultipleFutureMeetings(){
		Object[][] meetingData = {
				  {0, cm.getContacts(0,1), TestTools.createCalendarHours(22)},		
			      {1, cm.getContacts(0,1), TestTools.createCalendarMonths(4)},
			      {2, cm.getContacts(0,1), TestTools.createCalendarHours(20)},
			      {3, cm.getContacts(0,1), TestTools.createCalendarMonths(3)},
			      {4, cm.getContacts(0,1), TestTools.createCalendarHours(21)}
			      };
		
		int[] meetingOrder = {2,4,0};
		List<Meeting> expected  = buildFutureMeetingSetup(this.cm, meetingData, meetingOrder);
		
		assertEquals(expected,cm.getFutureMeetingList(TestTools.createCalendarHours(24)));
	}
	
	@Test
	public void GFMLDateTest2MultiplePastAndFutureMeetings(){
		Object[][] meetingData = {
				  {0, cm.getContacts(0,1), TestTools.createCalendarHours(-2), "Notes"},		
			      {1, cm.getContacts(0,1), TestTools.createCalendarMonths(4)},
			      {2, cm.getContacts(0,1), TestTools.createCalendarHours(1)},
			      {3, cm.getContacts(0,1), TestTools.createCalendarMonths(3)},
			      {4, cm.getContacts(0,1), TestTools.createCalendarHours(-3), "Notes"}
			      };
		
		int[] meetingOrder = {4,0,2};
		List<Meeting> expected  = buildFutureMeetingSetup(this.cm, meetingData, meetingOrder);
		
		assertEquals(expected,cm.getFutureMeetingList(TestTools.createCalendarHours(0)));
	}
		
	@Test
	public void GFMLDateTest3NoMatch(){
		Object[][] meetingData = {
				  {0, cm.getContacts(0,1), TestTools.createCalendarHours(-2), "Notes"},		
			      {1, cm.getContacts(0,1), TestTools.createCalendarMonths(4)},
			      {2, cm.getContacts(0,1), TestTools.createCalendarHours(1)},
			      {3, cm.getContacts(0,1), TestTools.createCalendarMonths(3)},
			      {4, cm.getContacts(0,1), TestTools.createCalendarHours(-3), "Notes"}
			      };
		
		int[] meetingOrder = {5};
		buildFutureMeetingSetup(this.cm, meetingData, meetingOrder);
		List<Meeting> expected = new ArrayList<Meeting>();
		
		assertEquals(expected,cm.getFutureMeetingList(TestTools.createCalendarMonths(5)));
	}
	
	//addMeetingNotes tests
	//Test1 - Add notes to past meeting, check they add
	//Test2 - Add notes to past meeting multiple times
	//Test3 - Add null notes to past meeting
	//Test4 - Add notes to future meeting with past date, check it converts (Force date change?)
	//Test5 - add notes to future meeting, check consistency of list
	//Test6 - add notes to future meeting in future, check exception
	//Test7 - add null notes to future meeting
	//TODO  add after AddNotes is included - GFMLTest5- convert future meeting to past, get list
	//TODO  add after AddNotes is included - GPMLTest5- convert future meeting to past, get list
	
	
	
	/**Takes an Object[][] with parameters for a series of meeting to add to the CM. If notes are present, it will create a past meeting, else future
	 * Will return a List<Meeting> of meetings matching the IDs, in the order entered, to allow easy testing 
	 * 
	 * @param cm ContactManager to add meetings to
	 * @param meetingData Object[][] containing for each row {int expectedID, Set<Contact> meetingContacts, Calendar date, (Optional) String(Notes)} 
	 * @param orderedIDs IDs of meethings to return in the List<Meeting>
	 * @return List of meetings in the order of the orderedIDs
	 */
	public static List<Meeting> buildFutureMeetingSetup(ContactManager cm, Object[][] meetingData, int[] orderedIDs){
		List<Meeting> expected = new ArrayList<Meeting>();
		
		for (Object[] row: meetingData)
			try{
				cm.addNewPastMeeting((Set<Contact>)row[1], (Calendar)row[2], (String)row[3]);
			}catch (ArrayIndexOutOfBoundsException ex){
				cm.addFutureMeeting((Set<Contact>)row[1], (Calendar)row[2]);
			}
		
		for (int i = 0; i < orderedIDs.length ; i++){
			for (Object[] row : meetingData){
				if (row[0].equals(orderedIDs[i]))
					try{
						expected.add(new PastMeetingImpl((int)row[0], (Calendar)row[2], (Set<Contact>)row[1], (String)row[3]));
					}catch (ArrayIndexOutOfBoundsException ex){
						expected.add(new FutureMeetingImpl((int)row[0], (Calendar)row[2], (Set<Contact>)row[1]));
					}
			}
		}
		return expected;
	}
}
