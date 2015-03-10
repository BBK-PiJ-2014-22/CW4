import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class ContactManagerImpl implements ContactManager {

	
	List<Contact> contactlist;
	List<Meeting> meetinglist;
	
	
	public ContactManagerImpl(){
		this.contactlist = new ArrayList<Contact>();
		this.meetinglist = new ArrayList<Meeting>();
	}
	
	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		
		//TODO - Refactor to use ContactSetInCRM
		//Tests to see if each contact in the set is in the CRM system
		if (contacts != null)
		for (Contact contact : contacts){
			if (!contactIsInCRM(contact)){
				throw new IllegalArgumentException();
			}		
		}
		
		Meeting toAdd = new FutureMeetingImpl(this.meetinglist.size(), date, contacts);
		this.meetinglist.add(toAdd);
		return toAdd.getId();
	}
	
	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		Meeting toReturn = this.getMeeting(id);
		if (toReturn == null)
			return null;
		else{
			try{			
				return (FutureMeeting) this.getMeeting(id);
			}catch (ClassCastException ex){
				throw new IllegalArgumentException();
			}
		}
	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		if (contacts == null ||	date == null ||	text == null)
			throw new NullPointerException();
		else if (this.contactSetInCRM(contacts))
			this.meetinglist.add(new PastMeetingImpl(this.meetinglist.size(), date, contacts, text));
		else{
			throw new IllegalArgumentException();
		}
	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public PastMeeting getPastMeeting(int id) {
		//TODO Refactor for DRY with getFutureMeeting (perhaps use generics?)	
		Meeting toReturn = this.getMeeting(id);
		if (toReturn == null)
			return null;
		else{
			try{			
				return (PastMeeting) this.getMeeting(id);
			}catch (ClassCastException ex){
				throw new IllegalArgumentException();
			}
		}
	}	


	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public Meeting getMeeting(int id) {
		Optional<Meeting> returned = this.meetinglist.stream()
				 .filter(meeting -> meeting.getId() == (id))
				 .findFirst();
			try{
				return returned.get();
			}catch (NoSuchElementException ex){
				return null;
			}
	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub

	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public void addNewContact(String name, String notes) {
		this.contactlist.add(new ContactImpl(this.contactlist.size(), name, notes));

	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public Set<Contact> getContacts(int... ids) {
	
		List<Integer> filter = new ArrayList<Integer>();
		
		for (int i : ids)
			filter.add(i);
		
		Set<Contact> result = this.contactlist.stream()
											  .filter(contact -> findContactsWithIDs(contact, filter))			       
											  .collect(Collectors.toSet());
		if (ids.length > result.size())
			throw new IllegalArgumentException();
		else
			return result;              
	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public Set<Contact> getContacts(String name) {
		if (name == null)
			throw new NullPointerException();
		else{
			return this.contactlist.stream()
								   .filter(contact -> contact.getName().contains((CharSequence)name))
								   .collect(Collectors.toSet());
		}		
	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 
	 */
	private boolean findContactsWithIDs(Contact contact, List<Integer> idList){
		return idList.contains(contact.getId());
	}
	
	/**
	 * 
	 * @param contact
	 * @return
	 */
	private boolean contactIsInCRM(Contact contact){
		for (Contact contactInCRM : this.contactlist){
			if (contact == contactInCRM)
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param contacts
	 * @return
	 * @throws IllegalArgumentException
	 */
	private boolean contactSetInCRM(Set<Contact> contacts){
		
		if (contacts == null || contacts.isEmpty())
				throw new IllegalArgumentException();
		
		for (Contact contact : contacts)
			if (!contactIsInCRM(contact))
				return false;
		return true;
	}
	
	
}

