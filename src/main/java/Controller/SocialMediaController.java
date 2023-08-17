package Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import Model.Account;
import Service.AccountService;
import DAO.AccountDAO;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Message;
import Service.MessageService;
import DAO.MessageDAO;
import java.util.*;






/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountDAO accountDAO = new AccountDAO();
    AccountService accountService = new AccountService(accountDAO);
    MessageDAO messageDAO = new MessageDAO();
    MessageService messageService = new MessageService(messageDAO);
    //AccountDAO accountDao;
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();


        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages",this::createMessageHandler);
        app.get("/messages",this::getMessagesHandler);
        app.get("/messages/{message_id}",this::getMessgeByIDHandler);
        app.delete("/messages/{message_id}",this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}",this::updateMessageByIDHandler);
        app.get("/accounts/{account_id}/messages",this::getMessagesByUserHandler);


        return app;
    }


    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerUserHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(),Account.class);
        Account newAccount = accountService.addAccount(account);
        if(newAccount==null){
            context.status(400);
        }
        else{
            context.json(newAccount);
        }
    }


    private void loginUserHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(),Account.class);
        Account isAccount = accountService.getAccount(account);
        if (isAccount == null){
            context.status(401);
        }
        else{
            context.json(isAccount);
        }  
    }


    private void createMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(),Message.class);
        Message newMessage = messageService.createMessage(message);
        if(newMessage == null){
            ctx.status(400);
        }
        else{
            ctx.json(newMessage);
        }
    }


    private void getMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
    }

    private void getMessgeByIDHandler(Context ctx) throws JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message foundM = messageService.getMessageByID(id);
        if(foundM != null) ctx.json(foundM);
    }

    private void deleteMessageByIDHandler(Context ctx) throws JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.deleteMessageByID(id);
        if (msg != null) ctx.json(msg);
    }

    private void updateMessageByIDHandler(Context ctx) throws JsonProcessingException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(),Message.class);
        //String newmsg = ctx.body();
        //messageService.updateMessageByID(id, message);
        Message updatedM = messageService.updateMessageByID(id,message);
        if (updatedM != null) ctx.json(updatedM);
        else ctx.status(400);

    }

    private void getMessagesByUserHandler(Context ctx){
        int user = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> msgs = messageService.getMessagesByUser(user);
        if(msgs!= null) ctx.json(messageService.getMessagesByUser(user));

    }




}


