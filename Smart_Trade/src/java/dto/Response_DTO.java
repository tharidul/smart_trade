
package dto;

import com.google.gson.annotations.Expose;
import java.io.Serializable;


public class Response_DTO implements Serializable{
    @Expose
    private boolean success;
    @Expose
    private Object content;

   
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
    
}
