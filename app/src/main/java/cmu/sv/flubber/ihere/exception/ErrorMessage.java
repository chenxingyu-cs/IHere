package cmu.sv.flubber.ihere.exception;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public enum  ErrorMessage {
    CANNOT_GET_LOCATION(1);



    private final int errNo;
    private ErrorMessage(int errNo){
        this.errNo = errNo;

    }

    public int getErrorNo(){
        return this.errNo;
    }

}
