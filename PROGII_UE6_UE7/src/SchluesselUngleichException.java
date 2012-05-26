public class SchluesselUngleichException extends UngueltigerSchluesselException
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8691555963619614473L;

	public SchluesselUngleichException(String schluessel){
        super(schluessel);
    }
    
    public String toString(){
        return "Der angegebene Schlüssel '"
                + schluessel + "' ist anders als die Schluessel in Kontakt.";
    }
}
