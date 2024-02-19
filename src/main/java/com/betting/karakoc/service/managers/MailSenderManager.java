package com.betting.karakoc.service.managers;

import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.models.real.UserBetRoundEntity;
import com.betting.karakoc.models.real.UserEntity;
import com.betting.karakoc.models.requests.MailSenderByBetRoundIdRequest;
import com.betting.karakoc.models.requests.SummaryRequest;
import com.betting.karakoc.repository.*;
import com.betting.karakoc.service.interfaces.MailSenderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.betting.karakoc.KarakocApplication.GAME_MAX_COUNT;
import static com.betting.karakoc.models.real.UserEntity.onlyAdminValidation;


@Service
@Data
@AllArgsConstructor
public class MailSenderManager implements MailSenderService {
    private final UserEntityRepository userRepository;
    private final BetRoundEntityRepository betRepository;
    private final UserBetRoundRepository userBetRoundRepository;
    private final JavaMailSender mailSender;
    private final UserBetRepository userBetRepository;
    private final GameRepository gameRepository;

    private final BetSummaryManager betSummaryManager;

    @Transactional
    public String mailSenderByBetRoundId(MailSenderByBetRoundIdRequest request) {
        Optional<UserEntity> user = userRepository.findByToken(request.getAdminToken());
        // If token is not admin's token, throw exception. if not, welcome. keep continue please
        onlyAdminValidation(user);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("shopifyemirhan6@gmail.com");
        simpleMailMessage.setSubject("SPOR TOTO RESULT");
        StringBuilder response = new StringBuilder();

        List<UserBetRoundEntity> dummyList = userBetRoundRepository.findAllByBetRoundEntityId(request.getBetroundId());
        List<UserBetRoundEntity> userBetroundsWillClose = new ArrayList<>();

        for (UserBetRoundEntity userBetRoundEntity : dummyList) {
            if (Objects.equals(userBetRoundEntity.getBetRoundEntityId(), request.getBetroundId()) && userBetRoundEntity.getUserBetList().size() == GAME_MAX_COUNT) {
                userBetroundsWillClose.add(userBetRoundEntity);
            }
        }
       // System.out.println(userBetroundsWillClose.get(0).getUserToken());


        List<UserEntity> bettedUsersForThisBetround = new ArrayList<>();

        for (UserBetRoundEntity userBetRound : userBetroundsWillClose) {
            Optional<UserEntity> usersWillAdded = userRepository.findByToken(userBetRound.getUserToken());
            if (!bettedUsersForThisBetround.contains(usersWillAdded.get())) {
                bettedUsersForThisBetround.add(usersWillAdded.get());
            }
        }
        for (UserBetRoundEntity userBetRound : userBetroundsWillClose) {
            Optional<UserEntity> mailReceiver = userRepository.findByToken(userBetRound.getUserToken());

            response.append(betSummaryManager.summary(userBetRound.getId())).append("\n");
            simpleMailMessage.setTo(mailReceiver.get().getUsername());
            simpleMailMessage.setText(response.toString());
            try {
                mailSender.send(simpleMailMessage);

            } catch (Exception e) {
                throw new BadRequestException("Bad internet connection. Try again later.");
            }
        }
        return bettedUsersForThisBetround.size() + " mail sent.";

    }

    public String forgotPassword(String mail) {
        Optional<UserEntity> user = userRepository.findByUsername(mail);
        Random random = new Random();
        if (user.isPresent()) {

            user.get().setPassword("" + random.nextDouble(9999999));
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("shopifyemirhan6@gmail.com");
            simpleMailMessage.setSubject("FORGET PASSWORD REQUEST");
            simpleMailMessage.setTo(user.get().getUsername());
            simpleMailMessage.setText("Hello Mr/Mrs ".toUpperCase() + user.get().getFirstname().toUpperCase() + ", we got a forgot request.\nYour new password: ".toUpperCase() + user.get().getPassword() + "\n\n!!!!!...Don't forget change your password after log in with new password...!!!!!!!".toUpperCase());
            userRepository.save(user.get());
            mailSender.send(simpleMailMessage);
            return "Mr/Mrs. " + user.get().getFirstname().toUpperCase() + " , new password sent to your mail adress. Please check your mailbox.\n\n!!!!!...Don't forget change your password after log in with new password....!!!!!!!".toUpperCase();
        } else return "Wrong mail adress.";
    }


}
