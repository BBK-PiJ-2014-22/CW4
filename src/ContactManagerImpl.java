import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
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
		
		if (contactSetInCRM(contacts)){
			Meeting toAdd = new FutureMeetingImpl(this.meetinglist.size(), date, contacts);
			this.meetinglist.add(toAdd);
			return toAdd.getId();			
		}else
			throw new IllegalArgumentException();
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
		else if (date.after(new GregorianCalendar()))
			throw new IllegalArgumentException();			
		else if (this.contactSetInCRM(contacts))
			this.meetinglist.add(new PastMeetingImpl(this.meetinglist.size(), date, contacts, text));
		else
			throw new IllegalArgumentException();
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
		return getMeetingList(contact, FutureMeeting.class);
	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<Meeting> toReturn = this.meetinglist.stream()
				       		   					 .filter(meeting ->  sameDate( meeting.getDate() , date))
				       		   					 .collect(Collectors.toList());
		sortChronologically(toReturn);
		return toReturn;
	}

	/**{@inheritDoc} 
	 * 
	 */
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		List<PastMeeting> toReturn = new ArrayList<PastMeeting>();
		for  (Meeting meeting : getMeetingList(contact, PastMeeting.class))
			toReturn.add((PastMeeting) meeting);
		return toReturn;
	}


	/**Helper function for getFutureMeetingList
	 * 
	 * @param contact contact to search for. Should match parent method
	 * @param meetingType Type of meeting to be found
	 * @return A sorted list<Meeting> of meetings of meetings of the correct type with that contact.
	 */
	private List<Meeting> getMeetingList(Contact contact, Class<? extends Meeting> meetingType) {
		
		if (!this.contactIsInCRM(contact))
			throw new IllegalArgumentException();
		
		List<Meeting> toReturn = this.meetinglist.stream()
 											     .filter(meeting -> meeting.getContacts().contains(contact))
  											     .filter(meeting -> meetingType.isInstance(meeting))
											     .collect(Collectors.toList());
		
		Collections.sort(toReturn, (meeting1, meeting2) -> meeting1.getDate().compareTo(meeting2.getDate()));
		return toReturn;
	}
	
	/**{@inheritDoc} 
	 * 
	 * Warning: will create a new meeting with the same value rather than update the existing meeting,
	 * even if the notes are being added to a PastMeeting. Direct references to the object will be lost.
	 * This is an unavoidable consequence of the interface.
	 */
	@Override
	public void addMeetingNotes(int id, String text) {
		Meeting meeting = this.getMeeting(id);
		
		if (meeting == null){
			throw new IllegalArgumentException();
		}else if (meeting.getDate().compareTo(new GregorianCalendar()) == 1){
			throw new IllegalStateException();
		}else if (text == null){
			throw new NullPointerException();
		}else{
			String notes = "";
			try{
				PastMeeting pm = (PastMeeting)meeting;
				notes = pm.getNotes()+"\n"+text;
			}catch (ClassCastException ex){
				notes = text;
			}finally{
				int position = this.meetinglist.indexOf(meeting);
				PastMeeting newMeeting = new PastMeetingImpl(meeting.getId(), meeting.getDate(), meeting.getContacts(), notes);
				this.meetinglist.remove(position);
				this.meetinglist.add(position, newMeeting);
			}
		}		
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
	
	public static boolean sameDate(Calendar c1, Calendar c2){
		return ((c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && 
				(c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)));
	}
	
	
	private void sortChronologically(List<Meeting> list){
		Collections.sort(list, (meeting1, meeting2) -> meeting1.getDate().compareTo(meeting2.getDate()));
	}
}

