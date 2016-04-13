package cmu.sv.flubber.ihere.exception;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public class IhereException extends Exception implements FixException{
    /** Error Number*/
    private  int errorNumber;
    /** Error Message*/
    private String errorMessage;

    public IhereException(int errorNumber) {
        this.errorNumber = errorNumber;
        this.errorMessage = "Can not get the location, Please check your device!";
    }

    @Override
    public String toString() {
        return "MyException: errorNumber = " + errorNumber
                + ", errorMessgae = " + errorMessage;

    }

    @Override
    public void fix() {
        
    }
}
