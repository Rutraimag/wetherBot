import org.telegram.telegrambots.meta.api.objects.Message;

public class MessageToBot {
    private Message msg;
    MessageToBot(Message _msg){
        msg = _msg;
    }

    public String getAnswer(){
        if(!msg.hasText()){
            return "Я работаю только с текстом!";
        }
        String request = msg.getText();
        if(request.equals("/start")){
            DataBase.add(String.valueOf(msg.getChatId()), msg.getFrom().getFirstName());
            return "Добавление нового пользователя";
        }
        if(request.equals("/setcity")){
            return "Установка городов по умолчанию";
        }
        if(request.equals("/admin")){
            return "Админ панель";
        }
        if(request.equals("/weathernow")){
            return "Погода сейчас";
        }
        if(request.equals("/help")){
            return "Справочная инфа";
        }
        return "Test";
    }
}
