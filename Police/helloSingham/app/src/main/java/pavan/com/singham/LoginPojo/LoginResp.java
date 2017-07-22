package pavan.com.singham.LoginPojo;

/**
 * Created by pavan on 19/8/16.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginResp {

    @SerializedName("Result")
    @Expose
    private String result;
    @SerializedName("UserID")
    @Expose
    private String userID;

    /**
     *
     * @return
     * The result
     */
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The Result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     *
     * @return
     * The userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     * The UserID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

}