package model.database;

//Thrown when DB init fails, or some other unwanted state
public class InitException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9062549249869814045L;
	public EInitExceptionCodes code = EInitExceptionCodes.NONE;

	public InitException(EInitExceptionCodes code) {
		this.code = code;
	}

}
