package manager;

public class ContactImpl implements Contact {

	
	int id;
	String name;
	String notes;
	
	/**TODO - Add full javadoc
	 * 
	 * @param id
	 * @param name
	 * @param notes
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
}
