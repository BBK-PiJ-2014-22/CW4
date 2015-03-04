import static org.junit.Assert.*;

import java.util.Set;

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
	public void getContactNoMatchingSingleID(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		cm.getContacts(10);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getContactNoMatchingMultipleIDs(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		cm.getContacts(10,11,12,13);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getContactSomeMatchingMultipleIDs(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		cm.getContacts(7,8,9,10);
	}
	
	@Test
	public void getContactAllMatchingMultipleIDs(){
		
		String namestem = "Test Name";
		String notestem = "Test Notes";
		int c1 = 7;
		int c2 = 8;
		
		addXContacts(cm, 9, namestem, notestem);
		Set<Contact> returned = cm.getContacts(c1, c2);
		Object[] actual = returned.toArray();
		Object[] expected = {new ContactImpl(c1, namestem+c1, notestem+c1),
							 new ContactImpl(c2, namestem+c2, notestem+c2)};
		
		assertArrayEquals(expected, actual);
		
	}
			
	 
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
