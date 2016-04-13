package cmu.sv.flubber.ihere.dbLayout;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public interface DBLocalConnector {
    /*
    This Connector to used to connect to local sqlite db on device,
    which only save user name and email for showing
    */

    String getUserEmail();

    String getUserName();

    void setUserName(String name);

    void setUserEmail(String email);

    void updateuserEmail(String email);


}
