package pinto.cleo.userdemo.common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cleo on 5/6/18.
 */
public class ResponseDTO<T> {

    private List<T> messages = new ArrayList<>();

    public ResponseDTO(){
    }

    public ResponseDTO(T message){
        messages.add(message);
    }

    public ResponseDTO(List<T> messages) {
        this.messages = messages;
    }

    public void addMessage(T message){
        messages.add(message);
    }

    public List<T> getMessages() {
        return messages;
    }

    public void setMessages(List<T> messages) {
        this.messages = messages;
    }
}
