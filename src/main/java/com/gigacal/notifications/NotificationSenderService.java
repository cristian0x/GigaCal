package com.gigacal.notifications;

import com.gigacal.entity.EventEntity;
import com.gigacal.entity.SettingEntity;
import com.gigacal.entity.UserEntity;
import com.gigacal.repository.CalendarRepository;
import com.gigacal.repository.EventRepository;
import com.gigacal.repository.SettingRepository;
import com.gigacal.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

import java.nio.file.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.gigacal.enums.NotificationChannelType.EMAIL;
import static com.gigacal.enums.NotificationChannelType.SMS;
import static com.gigacal.enums.SettingType.*;

@Service
@AllArgsConstructor
public class NotificationSenderService {

    private final SettingRepository settingRepository;
    private final EventRepository eventRepository;
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;

    // basic notification parameters:
    private final String SENDER_NAME = "GigaCal";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // SMS API parameters:
    private final String SMS_API_KEY = "";
    private final String SMS_WWW_PASSWORD = "";
    private final String SMS_MESSAGE_INTRO = "Reminder: ";

    // EMAIL API parameters:
    private final ApiClient emailClient = Configuration.getDefaultApiClient();
    private final String EMAIL_API_KEY = "";
    private final String SENDER_EMAIL = "gigacal@gmail.com";
    private final String EMAIL_SUBJECT = "GigaCal - reminder";
    private final String EMAIL_HTML_PATTERN = loadHtmlContentFromFile();

//    @Scheduled(cron = "0 */1 * ? * *")
//    public void checkEvents() {
//        List<Long> usersWithNotificationsEnabled = settingRepository.findAllByType(NOTIFICATION_ENABLED).stream()
//                .filter(s -> s.getValue().equals(Boolean.TRUE.toString()))
//                .map(SettingEntity::getUserId)
//                .toList();
//        List<EventEntity> events = eventRepository.findAllByUsers(usersWithNotificationsEnabled);
//        List<EventEntity> cyclicEvents = events.stream().filter(EventEntity::getIsCyclic).toList();
//        List<EventEntity> oneTimeEvents = events.stream().filter(e -> !e.getIsCyclic()).toList();
//
//        for (EventEntity event : cyclicEvents) {
//            LocalDate startDate = event.getStartDate();
//            LocalDate endDate = event.getEndDate();
//            LocalTime time = event.getTime();
//            if (eventDateIsBetween(startDate, endDate) && isMatchingDayOfWeek(event.getDays())
//                    && time.minusMinutes(15).equals(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))) {
//                sendNotification(event);
//            }
//        }
//
//        for (EventEntity event : oneTimeEvents) {
//            LocalDateTime dateTime = LocalDateTime.of(event.getStartDate(), event.getTime());
//            if (dateTime.minusMinutes(15).isEqual(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))) {
//                sendNotification(event);
//            }
//        }
//
//    }

    private boolean eventDateIsBetween(LocalDate min, LocalDate max) {
        LocalDate eventDate = LocalDateTime.now().plusMinutes(15).toLocalDate();
        return eventDate.isEqual(min) || (eventDate.isAfter(min) && eventDate.isBefore(max)) || eventDate.isEqual(max);
    }

    private boolean isMatchingDayOfWeek(String days) {
        DayOfWeek dayOfWeek = LocalDateTime.now().plusMinutes(15).getDayOfWeek();
        return days.charAt(dayOfWeek.getValue() - 1) == '1';
    }

//    private void sendNotification(EventEntity event) {
//        Long userId = calendarRepository.findById(event.getCalendarId()).orElseThrow().getUser().getId();
//        String channel = settingRepository.findByUserIdAndType(userId, NOTIFICATION_CHANNEL).orElseThrow().getValue();
//        UserEntity user = userRepository.findById(userId).orElseThrow();
//
//        String eventName = event.getName();
//        String eventDate = LocalDateTime.now().plusMinutes(15).format(formatter);
//        String eventStart = event.getTime().toString();
//        String eventEnd = event.getTime().plusHours(event.getDuration().getHour()).plusMinutes(event.getDuration().getMinute()).toString();
//
//        if (channel.equals(SMS.toString())) {
//            String message = SMS_MESSAGE_INTRO + eventName + " on " + eventDate + " at " + eventStart + "-" + eventEnd;
//            sendSms(user.getPhoneNumber(), message);
//        }
//        if (channel.equals(EMAIL.toString())) {
//            sendEmail(user.getEmail(), eventName, eventDate, eventStart, eventEnd);
//        }
//    }

    private void sendSms(String phoneNumber, String message) {
        SmsPlanetRequest request = SmsPlanetRequest.sendSMS(
                SMS_API_KEY,
                SMS_WWW_PASSWORD,
                SENDER_NAME,
                message,
                phoneNumber);
        try {
            SmsPlanetResponse response = request.execute();
            if (response.getErrorCode() == 0) {
                System.out.println("SMS sent - ID: " + response.getMessageId());
            } else {
                System.err.println("Error during processing: " + response.getErrorMessage());
            }
        } catch (IOException e) {
            System.err.println("Connection establishment failed.");
        }
    }

    private void sendEmail(String email, String... params) {
        ApiKeyAuth apiKey = (ApiKeyAuth) emailClient.getAuthentication("api-key");
        apiKey.setApiKey(EMAIL_API_KEY);
        TransactionalEmailsApi apiInstance = new TransactionalEmailsApi();

        SendSmtpEmail emailRequest = new SendSmtpEmail();
        emailRequest.setSender(new SendSmtpEmailSender().name(SENDER_NAME).email(SENDER_EMAIL));
        emailRequest.setTo(List.of(new SendSmtpEmailTo().email(email)));
        emailRequest.setSubject(EMAIL_SUBJECT);
        emailRequest.setHtmlContent(EMAIL_HTML_PATTERN
                .replaceFirst("\\{}", params[0])
                .replaceFirst("\\{}", params[1])
                .replaceFirst("\\{}", params[2])
                .replaceFirst("\\{}", params[3])
                .replaceFirst("\\{}", "www.agh.edu.pl")
        );

        try {
            CreateSmtpEmail result = apiInstance.sendTransacEmail(emailRequest);
            System.out.println("Email sent - ID: " + result.getMessageId());
        } catch (ApiException e) {
            System.err.println("Exception when email is sending: " + e.getResponseBody());
        }
    }

    private String loadHtmlContentFromFile() {
        try {
            return new String(Files.readAllBytes(Paths.get("src/main/java/com/gigacal/mock/email_template.txt")));
        } catch (IOException e) {
            return "";
        }
    }

}