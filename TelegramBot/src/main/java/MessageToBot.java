import org.glassfish.grizzly.utils.Pair;
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
            String answer = DataBase.setFavouriteCity(String.valueOf(msg.getChatId()), request);
            return answer;
        }
        if(request.equals("/admin")){
            return "Команды админа и статистика";
        }
        if(request.equals("/weathernow")){
            var city = DataBase.getFavouriteCity(msg.getChatId().toString());
            if(city == null){
                return "Город по умолчанию не задан";
            }
            var info = GetInfFrOWM.getWeatherInCity(city);
            return String.format("Погодные условия: %s" +
                    "\nТемпература сейчас: %s градусов цельсия" +
                    "\nОщущается как: %s градусов цельсия", info.get("description"), info.get("temp"), info.get("feels_like"));
        }else if(request.split(" ")[0].equals("/weathernow")){
            var city = request.split(" ", 2)[1];
            var check = GetInfFrOWM.checker(city);
            if(check == null){
                var info = GetInfFrOWM.getWeatherInCity(city);
                return String.format("Погодные условия: %s" +
                        "\nТемпература сейчас: %s градусов цельсия" +
                        "\nОщущается как: %s градусов цельсия", info.get("description"), info.get("temp"), info.get("feels_like"));
            }else{
                return check;
            }
        }
        if(request.equals("/help")){
            return "Справочная инфа";
        }
        return "Такую команду не знаю";
    }
}
