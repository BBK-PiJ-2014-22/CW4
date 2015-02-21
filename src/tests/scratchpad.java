package tests;

import java.util.Calendar;
import java.util.GregorianCalendar;
import manager.ContactImpl;

public class scratchpad {

	public static void main(String[] args){
		ContactImpl contact = new ContactImpl(1, "Bob Geldoff", "Some Notes");
		System.out.println(contact);
		System.out.println(contact.getNotes());
		contact.addNotes("Some More Notes");
		System.out.println(contact.getNotes());
	
	}
	
}


