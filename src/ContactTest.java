

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**UnitTests for ContactImpl
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
	public void testNullNameConstructor(){
		new ContactImpl(targetID, null, targetNotes);
	}

	@Test(expected = NullPointerException.class)
	public void testNullNotesConstructor(){
		new ContactImpl(targetID, targetName, null);
	}
	
	@Test
	public void testGetID() {
		assertEquals(targetID, contact.getId());
	}

	@Test
	public void testGetName() {
		assertEquals(targetName, contact.getName());
	}
	
	@Test
	public void testGetNotes() {
		assertEquals(targetNotes, contact.getNotes());
	}
	
	@Test
	public void testAddNotes1(){
		String addNote = "Second Contact";
		String resultNote = targetNotes+"\n\n"+addNote;
		contact.addNotes(addNote);
		assertEquals(resultNote, contact.getNotes());
	}

	
	@Test
	public void testEqualToEquals(){
		Contact comparedContact = new ContactImpl(targetID, targetName, targetNotes);
		assertEquals(true, contact.equals(comparedContact));
	}
	
	@Test
	public void testEqualToNotEqual(){
		Contact comparedContact = new ContactImpl(targetID+1, "Other Contact", "New Notes");
		assertEquals(false, contact.equals(comparedContact));
	}
	
	@Test
	public void testEqualToRandomObject(){
		Integer compare = new Integer(1);
		assertEquals(false,contact.equals(compare));		
	}
	
	@Test
	public void testToString(){
		String targetString = "["+this.targetID+", "+this.targetName+", "+this.targetNotes+"]";
		assertEquals(targetString, contact.toString());
	}
	
	
}
