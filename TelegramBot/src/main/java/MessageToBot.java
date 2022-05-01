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
            if(DataBase.add(String.valueOf(msg.getChatId()), msg.getFrom().getFirstName())){
                return "Возвращает тоже самое, что и /help";  //TODO Отправить пользователю справочную информацию по типу /help;
            }else{
                return "Бот уже работает!";
            }
        }
        if(request.equals("/setcity")){
            return "Напишите команду в формате '/setcity [Название города]'";
        }else if(request.split(" ")[0].equals("/setcity")){
            String answer = DataBase.changeFavouriteCity(String.valueOf(msg.getChatId()), request); //TODO Предусмотреть вариант, когда пишут город из двух слов (Великий Новгород) и обрабатывать билеберду.
            return answer;                                                                            //TODO Возможно делегировать Ярику сделать метод парсера, для проверки существования города
        }
        if(request.equals("/admin")){
            return "Команды админа и статистика";
        }
        if(request.equals("/weathernow")){
            return "Напишите команду в формате '/weathernow [Номер города из предложенных или название]'";
        }
        if(request.equals("/help")){
            return "Справочная инфа";
        }
        return "Такую команду не знаю";
    }
}
