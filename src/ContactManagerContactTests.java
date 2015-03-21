import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**Tests all contact elements of ContactManger
 * 
 * @author Jamie
 *
 */
public class ContactManagerContactTests {

	ContactManager cm;
	
	/** Creates blank instance of a ContactManager
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.cm = new ContactManagerImpl();	
	}
	
	/**Tests that a normal, valid contact can be added and retrieved
	 */
	@Test
	public void addAndGetNormalContact(){
		
		Object[] testdata = {0,"Test Name", "Test Notes"};
		Object[] expected = {new ContactImpl((Integer)testdata[0], (String)testdata[1], (String)testdata[2])};
		cm.addNewContact((String)testdata[1], (String)testdata[2]);
		Object[] actual = cm.getContacts(0).toArray();		
	
		assertArrayEquals(expected, actual);
	}
	
	/**Tests that multiple contacts are added, and that the final contact added is at the expected ID
	 * 
	 */
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
	
	/**Tests that you cannot add a contact with a null name
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void addContactNullName(){
		cm.addNewContact(null, "Test Notes");
	}
	
	/**Tests that you cannot add a contact with null notes
	 */
	@Test(expected = NullPointerException.class)
	public void addContactNullNotes(){
		cm.addNewContact("Test Name", null);
	}
	
	/**Tests that when no contact has the matching ID IllegalArgumentException is thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getContactNoMatchingSingleID(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		cm.getContacts(10);
	}
	
	/**Tests that when multiple ids (all that don't match) are passed, an IllegalArgumentException is thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getContactNoMatchingMultipleIDs(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		cm.getContacts(10,11,12,13);
	}

	/**Tests that when multiple ids (with some match and some not) are passed, 
	 * an IllegalArgumentException is thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getContactSomeMatchingMultipleIDs(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		cm.getContacts(7,8,9,10);
	}
	
	/**Tests that when all IDs match, the correct list is returned
	 */
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
	
	/**Tests that when trying to search on null string, a nullPointerException is thrown
	 */
	@Test(expected = NullPointerException.class)
	public void getContactNullString(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		
		String search = null;
		cm.getContacts(search);
	}
	
	/**Tests that when the string matches, the correct contact is returned.
	 */
	public void getContactStringMatches1(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		
		Set<Contact> returned = cm.getContacts("9");
		
		Object[] expected = {new ContactImpl(9, "Test Name9", "Test Notes9")};
		Object[] actual = returned.toArray();
		
		assertArrayEquals(expected, actual);
	}

	/**Tests that when the string matches multiple contacts, all the correct contacts are returned.
	 */
	@Test
	public void getContactStringMatchesMultiple(){
		
		cm.addNewContact("Search Name0" , 	"Search Notes0");
		cm.addNewContact("NoMatch Name1", 	"NoMatch Notes1");
		cm.addNewContact("Search Name2" , 	"Search Notes2");
		cm.addNewContact("Search Name3" , 	"Search Notes3");
		cm.addNewContact("NoMatch Name4", 	"NoMatch Notes4");
		cm.addNewContact("Search Name5" , 	"Search Notes5");
		
		List<Contact> returned = new ArrayList<Contact>();
		returned.addAll(cm.getContacts("Search"));
		
		List<Contact> expected = Arrays.asList(new ContactImpl(0, "Search Name0", "Search Notes0"),
							 new ContactImpl(2, "Search Name2", "Search Notes2"),
							 new ContactImpl(3, "Search Name3", "Search Notes3"),
							 new ContactImpl(5, "Search Name5", "Search Notes5"));
		
		List<Contact> missingContacts = new ArrayList<Contact>();
		List<Contact> extraContacts = new ArrayList<Contact>();
		
		for (Contact returnedContact : returned){
			if (!expected.contains(returnedContact))
				missingContacts.add(returnedContact);
		}
		
		for (Contact expectedContact : expected){
			if (!returned.contains(expectedContact))
				extraContacts.add(expectedContact);
		}
		
		assertTrue((String)("Missing Contacts: "+missingContacts +
				            "Extra Contacts: "+extraContacts),
				            missingContacts.size() == 0 && extraContacts.size() == 0);			               
	}

	/**Tests that when the string matches nothing, nothing is returned.
	 */
	@Test
	public void getContactStringMatchesNone(){
		addXContacts(cm, 9, "Test Name", "Test Notes");
		
		Set<Contact> expected = new HashSet<Contact>();
		Set<Contact> actual = cm.getContacts("NeverGoingToFindThis");
		
		assertEquals(expected, actual);
	}

	/**Tests that when the contact list is empty, no exception is thrown
	 */
	@Test
	public void getContactStringNoContacts(){
		
		Set<Contact> expected = new HashSet<Contact>();
		Set<Contact> actual = cm.getContacts("NeverGoingToFindThis");
		
		assertEquals(expected, actual);
	}
	
	//Helper function, that adds contacts to the CM.
	private static void addXContacts(ContactManager cm, int x, String namestem, String notestem){
		for (int i = 0; i <= x ; i++){
			cm.addNewContact(namestem+i, notestem+i);
		}
	}
	
}
