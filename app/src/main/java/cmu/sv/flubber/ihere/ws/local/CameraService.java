package cmu.sv.flubber.ihere.ws.local;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public interface CameraService {
    /*
    Call service from local camera and storage
     */
    void openCamera();

    void closeCamera();

    void showPhoto();

}
