package model;

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
