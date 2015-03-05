import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
		
		Object[] testdata = {0,"Test Name", "Test Notes"};
		Object[] expected = {new ContactImpl((Integer)testdata[0], (String)testdata[1], (String)testdata[2])};
		cm.addNewContact((String)testdata[1], (String)testdata[2]);
		Object[] actual = cm.getContacts(0).toArray();		
	
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
		int contact1 = 7;
		int contact2 = 8;
		
		addXContacts(cm, 9, namestem, notestem);
		Set<Contact> returned = cm.getContacts(contact1, contact2);
		
		Object[] actual = returned.toArray();
		Object[] expected = {new ContactImpl(contact1, namestem+contact1, notestem+contact1),
				             new ContactImpl(contact2, namestem+contact2, notestem+contact2)};
							
		assertArrayEquals(expected, actual);
	}
	
	@Test(expected = NullPointerException.class)
	public void getContactNullString(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		
		String search = null;
		cm.getContacts(search);
	}
	
	public void getContactStringMatches1(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		
		Set<Contact> returned = cm.getContacts("9");
		
		Object[] expected = {new ContactImpl(9, "Test Name9", "Test Notes9")};
		Object[] actual = returned.toArray();
		
		assertArrayEquals(expected, actual);
	}
	
	public void getContactStringMatchesMultiple(){
		
		cm.addNewContact("Search Name0" , 	"Search Notes0");
		cm.addNewContact("NoMatch Name1", 	"NoMatch Notes1");
		cm.addNewContact("Search Name2" , 	"Search Notes2");
		cm.addNewContact("Search Name3" , 	"Search Notes3");
		cm.addNewContact("NoMatch Name4", 	"NoMatch Notes4");
		cm.addNewContact("Search Name5" , 	"Search Notes5");
		
		Set<Contact> returned = cm.getContacts("Search");
		
		Object[] expected = {new ContactImpl(0, "Search Name0", "Search Notes 0"),
							 new ContactImpl(2, "Search Name2", "Search Notes 2"),
							 new ContactImpl(3, "Search Name3", "Search Notes 3"),
							 new ContactImpl(5, "Search Name5", "Search Notes 5")};
		
		Object[] actual = returned.toArray();
		
		assertArrayEquals(expected, actual);
					               
	}


	
	public void getContactStringMatchesNone(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		
		Set<Contact> expected = new HashSet<Contact>();
		Set<Contact> actual = cm.getContacts("NeverGoingToFindThis");
		
		assertEquals(expected, actual);
	}
	
	public void getContactStringNoContacts(){
		
		Set<Contact> expected = new HashSet<Contact>();
		Set<Contact> actual = cm.getContacts("NeverGoingToFindThis");
		
		assertEquals(expected, actual);

	}
	
	private static void addXContacts(ContactManager cm, int x, String namestem, String notestem){
		for (int i = 0; i <= x ; i++){
			cm.addNewContact(namestem+i, notestem+i);
		}
	}
	
}
