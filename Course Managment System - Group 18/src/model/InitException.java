package model;

public class InitException extends Exception {

	public EInitExceptionCodes code = EInitExceptionCodes.NONE;

	public InitException(EInitExceptionCodes code) {
		this.code = code;
	}

}
