public class UngueltigerSchluesselException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7454556970486368604L;
	protected String schluessel;

	public UngueltigerSchluesselException(String schluessel){
        this.schluessel = schluessel;
    }

	public String gibSchluessel(){
        return schluessel;
    }
    
	public String toString(){
        return "Der Schlüssel '"
                + schluessel + "' ist ungültig.";
    }
}
