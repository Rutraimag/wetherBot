import org.telegram.telegrambots.meta.api.objects.Message;

public class MessageToBot {
    private final Message msg;
    MessageToBot(Message _msg){
        msg = _msg;
    }

    public String getAnswer(){
        if(!msg.hasText()){
            return "Я работаю только с текстом!";
        }
        String request = msg.getText();
        if(request.equals("/start")){
            DataBase.add(String.valueOf(msg.getChatId()), msg.getFrom().getFirstName());    //TODO сделать возварат булл значения. True добавил. False уже есть пользователь
                                                                                            //TODO если true, то вернуть сообщение как в /help. если False, то вернуть по типу "бот уже работает"
            return "При первом нажатии по сути будет возвращать, что и /help. Потом ничего не будет возвращать.";
        }
        if(request.equals("/setcity")){
            return "Напишите команду в формате '/setcity [город1] [город2] [город3] [город4] [город5]'";
        }else if(request.contains("/setcity")){
            String answer = DataBase.changeFavouriteCities(String.valueOf(msg.getChatId()), request); //TODO Предусмотреть вариант, когда пишут город из двух слов (Великий Новгород) и обрабатывать билеберду.
            return answer;                                                                            //TODO Возможно делегировать Ярику сделать метод парсера, для проверки существования города
        }
        if(request.equals("/admin")){
            return "Команды админа и статистика";
        }
        if(request.equals("/weathernow")){
            return "Напишите команду в формате '/setcity [Номер города из предложенных или название]'";
        }
        if(request.equals("/help")){
            return "Справочная инфа";
        }
        return "Такую команду не знаю";
    }
}
