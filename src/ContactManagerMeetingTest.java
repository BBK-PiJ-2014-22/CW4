import static org.junit.Assert.*;

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
		
		
		//TODO - Needs to create a CM instance
		//TODO - needs to create a list of 10 contacts to use for tests
	}

	//TODO - Add Future meetings Tests
	//Test1- Single existing contact, date in the future
	//Test2- multiple existing contacts, date in the future
	//Test3- Empty set of contacts, date in the future
	//Test4- null, date in the future
	//Test5- Some Contacts not in CM, date in the future
	//Test6- all contacts not in CM, date in the future
	//Test5- Working set,  date in the past
	//Test5- Working set,  date is today
	//Test6- multiple working meetings, test ID increases
	//Test7- multiple working meetings with some failures, test no extra IDs
	
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
	//Test8 Single existing contact, date in the past, null notes
	//Test8 Single existing contact, null date, notes work
	
	
	//TODO - Get Future Meeting Tests
	//Test1- ID Matches future meeting
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
