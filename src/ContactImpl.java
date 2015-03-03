
public class ContactImpl implements Contact {

	
	int id;
	String name;
	String notes;
	
	/**Constructs a new contact with id, name and notes attached to it
	 * 
	 * @param id contact ID
	 * @param name Name of the contact
	 * @param notes Notes about interaction with contact
	 */
	public ContactImpl(int id, String name, String notes){
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
	 * 
	 * Contacts are equal to other contacts with the same ID else not
	 */
	@Override
	public boolean equals(Object object){
		try{
			Contact compare = (Contact) object;
			if (this.getId() == compare.getId())
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
