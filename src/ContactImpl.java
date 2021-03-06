

/**{@inheritDoc}
 * 
 * This implementation does NOT handle unique IDs for contacts directly. Uniqueness of IDs must be
 * handled by the client program using this implementation, by passing a unique ID to the constructor.
 * 
 * @author Jamie MacIver
 *
 */
public class ContactImpl implements Contact {

	private int id;
	private String name;
	private String notes;
	
	/**Constructs a new contact with id, name and notes attached to it
	 * 
	 * @param id contact ID
	 * @param name Name of the contact
	 * @param notes Notes about interaction with contact
	 * @throws NullPointerException if  Name or Notes is null
	 */
	public ContactImpl(int id, String name, String notes){
		if (name == null || notes == null)
			throw new NullPointerException();
		
		this.id = id;
		this.name = name;
		this.notes = notes;
	}
	
	/**{@inheritDoc} 
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**{@inheritDoc} 
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**{@inheritDoc} 
	 */
	@Override
	public String getNotes() {
		return this.notes;
	}

	/**{@inheritDoc} 
	 */
	@Override
	public void addNotes(String note) {
		this.notes += "\n\n"+note;
	}
	
	/**{@inheritDoc} 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		return result;
	}

	/**{@inheritDoc}
	 * 
	 * Contacts are equal to other contacts if all fields are equal, else not
	 * They will only be equal to other contacts.
	 */
	@Override
	public boolean equals(Object object){
		try{
			Contact compare = (Contact) object;
			if (this.getId() == compare.getId() &&
				this.getName().equals(compare.getName()) &&
				this.getNotes().equals(compare.getNotes())
				)
				return true;		
		}catch (ClassCastException ex){
			//return statement below - any object apart from a class implementing Contact is not equal to a contact
		}
		return false;
	}
	/**{@inheritDoc}
	 * 
	 * Will display as [ID, Contact Name, Notes]
	 */
	@Override
	public String toString(){
		return "["+this.id+", "+this.name+", "+this.notes+"]";
	}
}
