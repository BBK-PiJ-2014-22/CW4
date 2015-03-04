import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ContactManagerContactTests {

	ContactManager cm;
	
	@Before
	public void setUp() throws Exception {
		
		this.cm = new ContactManagerImpl();	
	}
	
	@Test
	public void addAndGetNormalContact(){
	
		Object[] expected = {0,"Test Name", "Test Notes"};
	
		cm.addNewContact((String)expected[1], (String)expected[2]);
		
		Contact firstcontact = (Contact) cm.getContacts((Integer) expected[0]).toArray()[0];
		
		Object[] actual = {firstcontact.getId(), firstcontact.getName(), firstcontact.getNotes()};
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void addMultipleContactsAndGetOne(){
	
		int contactsToAdd = 10;
		String testNameStem = "Test Name";
		String testNotesStem = "Test Notes";
		
		Object[] expected = {contactsToAdd,testNameStem+contactsToAdd, testNotesStem+contactsToAdd};
		addXContacts(cm, contactsToAdd, testNameStem, testNotesStem);	
		Contact lastcontact = (Contact) cm.getContacts(contactsToAdd).toArray()[0];
		Object[] actual = {lastcontact.getId(), lastcontact.getName(), lastcontact.getNotes()};
		
		assertArrayEquals(expected, actual);
	}
	
	@Test(expected = NullPointerException.class)
	public void addContactNullName(){
		cm.addNewContact(null, "Test Notes");
	}
	
	@Test(expected = NullPointerException.class)
	public void addContactNullNotes(){
		cm.addNewContact("Test Name", null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getContactNoMatchingID(){
		cm.addNewContact("Test Name", "Test notes");
		cm.getContacts(10);
	}
	
	
	 //get contact(int) test
	 //2) 1 id, no matches
	 //3) Multiple ids, all match
	 //4) mulltiple IDs, none match
	
	
	 
	 /*
	 * get contact(string) tests
	 * 
	 * 1) Null string
	 * 2) String matches 1 contact
	 * 3) String matches multiple contacts
	 * 4) String does not match contacts
	 * 5) There are no contact
	 * 
	 */
	
	private static void addXContacts(ContactManager cm, int x, String namestem, String notestem){
		for (int i = 0; i <= x ; i++){
			cm.addNewContact(namestem+i, notestem+i);
		}
	}
	
}
