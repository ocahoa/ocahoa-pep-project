package Service;
import DAO.MessageDAO;
import Model.Message;
import DAO.AccountDAO;
//import Model.Account;
import java.util.*;


public class MessageService {
    MessageDAO  messageDAO;
    AccountDAO accountDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }
    public Message createMessage(Message message){
        if((message.getMessage_text()).length() == 0 || (message.getMessage_text()).length()>254 ){
            return null;
        }
        else{
            return messageDAO.createMessage(message);
        }
    }


    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }
   
    public Message getMessageByID(int id){
        return messageDAO.getMessageByID(id);
    }

    public Message deleteMessageByID(int id){
        return messageDAO.deleteMessageByID(id);
    }

    public Message updateMessageByID(int id,Message message){
        if(message.getMessage_text().length() == 0 || message.getMessage_text().length()>254|| messageDAO.getMessageByID(id)==null) return null;
        this.messageDAO.updateMessageByID(id, message);
        return messageDAO.getMessageByID(id);
    }

    public List<Message> getMessagesByUser(int user){
        return messageDAO.getMessagesByUser(user);
    }
}


