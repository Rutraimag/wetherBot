import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class weatherBot extends TelegramLongPollingBot {
    private final String NAME = "weather";
    private final String TOKEN = "5171567953:AAFIwRL5AVdNgLKZJdwV8qfSqVPWEQ7N_j8";

    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            MessageToBot mToB = new MessageToBot(update.getMessage());
            String text = mToB.getAnswer();
            String id = String.valueOf(update.getMessage().getChatId());

            try {
                this.execute(SendMessage.builder().chatId(id).text(text).build());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}