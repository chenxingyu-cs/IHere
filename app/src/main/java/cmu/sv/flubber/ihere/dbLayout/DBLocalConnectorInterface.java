package cmu.sv.flubber.ihere.dbLayout;

import cmu.sv.flubber.ihere.entities.User;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public interface DBLocalConnectorInterface {
    /*
    This Connector to used to connect to local sqlite db on device,
    which only save user name and email for showing
    */

    String getUserEmail();

    String getUserName();

    String getUserPassword();

    void setUserName(String name);

    void setUserEmail(String email);

    void setUserPassword(String email);

    void setUser(String name, String email, String password);
}


