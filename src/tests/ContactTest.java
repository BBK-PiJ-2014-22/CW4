package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import manager.Contact;
import manager.ContactImpl;

@RunWith(Parameterized.class)
public class ContactTest {
	
	Contact contact;
	int targetID;
	String targetName;
	String targetNotes;
	
	@Parameters
	public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
                 { 1, "Bob Geldoff", "First Contact"}  
           });
    }
	
	public ContactTest(int id, String name, String notes){
		this.targetID = id;
		this.targetName = name;
		this.targetNotes = notes;
	}
	
	@Before
	public void setUp() throws Exception {
		contact = new ContactImpl(targetID, targetName, targetNotes);
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
	
	
}
