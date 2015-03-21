

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**UnitTests for ContactImpl
 * 
 * Tests:
 * 1-2 - Tests that passing null name or notes will throw NullPointerException
 * 3-5 - Tests getters for Contact
 * 6   - Tests addNotes (setter)
 * 7-9 - Test .equals(object) method
 * 10  - Test .toString() method
 * 
 * @author Jamie
 *
 */
public class ContactTest {
	
	Contact contact;
	int targetID;
	String targetName;
	String targetNotes;
	
	public ContactTest(){
		this.targetID = 1;
		this.targetName = "Bob Geldoff";
		this.targetNotes = "First Contact";
	}
	
	@Before
	public void setUp() throws Exception {
		contact = new ContactImpl(targetID, targetName, targetNotes);
	}

	@Test(expected = NullPointerException.class)
	public void test1NullNameConstructor(){
		new ContactImpl(targetID, null, targetNotes);
	}

	@Test(expected = NullPointerException.class)
	public void test2NullNotesConstructor(){
		new ContactImpl(targetID, targetName, null);
	}
	
	@Test
	public void test3GetID() {
		assertEquals(targetID, contact.getId());
	}

	@Test
	public void test4GetName() {
		assertEquals(targetName, contact.getName());
	}
	
	@Test
	public void test5GetNotes() {
		assertEquals(targetNotes, contact.getNotes());
	}
	
	@Test
	public void test6AddNotes1(){
		String addNote = "Second Contact";
		String resultNote = targetNotes+"\n\n"+addNote;
		contact.addNotes(addNote);
		assertEquals(resultNote, contact.getNotes());
	}

	@Test
	public void test7EqualToEquals(){
		Contact comparedContact = new ContactImpl(targetID, targetName, targetNotes);
		assertEquals(true, contact.equals(comparedContact));
	}
	
	@Test
	public void test8EqualToNotEqual(){
		Contact comparedContact = new ContactImpl(targetID+1, "Other Contact", "New Notes");
		assertEquals(false, contact.equals(comparedContact));
	}
	
	@Test
	public void test9EqualToRandomObject(){
		Integer compare = new Integer(1);
		assertEquals(false,contact.equals(compare));		
	}
	
	@Test
	public void test10ToString(){
		String targetString = "["+this.targetID+", "+this.targetName+", "+this.targetNotes+"]";
		assertEquals(targetString, contact.toString());
	}
}
