import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ContactManagerImpl implements ContactManager {

	
	List<Contact> contactlist;
	
	
	public ContactManagerImpl(){
		this.contactlist = new ArrayList<Contact>();
	}
	
	
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNewContact(String name, String notes) {
		this.contactlist.add(new ContactImpl(0, name, notes));

	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		Set<Contact> result = contactlist.stream()
				                         .filter(contact -> contact.getId() == 0)
				                         .collect(Collectors.toSet());
										 
		return result;              
	}

	@Override
	public Set<Contact> getContacts(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

}
