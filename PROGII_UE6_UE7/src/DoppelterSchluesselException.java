public class DoppelterSchluesselException extends UngueltigerSchluesselException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2688646560179103463L;

	public DoppelterSchluesselException(String schluessel){
        super(schluessel);
    }
    
    public String toString(){
        return "Den Schlüssel '"
                + schluessel + "' gibt es im Adressbuch bereits.";
    }
}