
public class KeinPassenderKontaktException extends UngueltigerSchluesselException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 932988763076551598L;

	public KeinPassenderKontaktException(String schluessel){
        super(schluessel);
    }
    
    public String toString(){
        return "Zu dem Schlüssel '"
                + schluessel + "' gibt es keinen Kontakt.";
    }
}
