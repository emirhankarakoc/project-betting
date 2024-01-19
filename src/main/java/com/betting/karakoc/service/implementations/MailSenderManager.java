package com.betting.karakoc.service.implementations;

import com.betting.karakoc.exceptions.general.BadRequestException;
import com.betting.karakoc.model.real.UserBetRoundEntity;
import com.betting.karakoc.model.real.UserEntity;
import com.betting.karakoc.repository.*;
import com.betting.karakoc.security.SecurityContextUtil;
import com.betting.karakoc.service.abstracts.MailSenderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.betting.karakoc.KarakocApplication.gameMaxCount;


@Service
@Data
@AllArgsConstructor
public class MailSenderManager implements MailSenderService {
    private final UserEntityRepository userRepository;
    private final BetRoundEntityRepository betRepository;
    private final UserBetRoundRepository userBetRoundRepository;
    private final JavaMailSender mailSender;
    private final SecurityContextUtil securityContextUtil;
    private final UserBetRepository userBetRepository;
    private final GameRepository gameRepository;

    private final BetSummaryManager betSummaryManager;

    public String mailSenderByBetRoundId(Long betroundId) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("shopifyemirhan6@gmail.com");
        simpleMailMessage.setSubject("Spor Toto Sonuç");


        //adminkontrolu
        String response = "";
        List<UserBetRoundEntity> kapatilacakBetRoundlar = new ArrayList<>();


        List<UserBetRoundEntity> gezilecekListe = userBetRoundRepository.findAllByBetRoundEntityId(betroundId);
        List<UserEntity> mailAdamlar = new ArrayList<>();
        for (UserBetRoundEntity userBetRoundEntity : gezilecekListe) {
            if (userBetRoundEntity.getBetRoundEntityId() == betroundId && userBetRoundEntity.getUserBetList().size() == gameMaxCount) {
                kapatilacakBetRoundlar.add(userBetRoundEntity);
            }
        }

        for (UserBetRoundEntity userBetRound : kapatilacakBetRoundlar) {
            Optional<UserEntity> eklenecekUSer = userRepository.findById(userBetRound.getUserEntityId());
            if (!mailAdamlar.contains(eklenecekUSer.get())) {
                mailAdamlar.add(eklenecekUSer.get());
            }
        }
        for (int i = 0; i < kapatilacakBetRoundlar.size(); i++) {
            Optional<UserEntity> eklenecekUSer = userRepository.findById(kapatilacakBetRoundlar.get(i).getUserEntityId());


            response += (betSummaryManager.hesapla((kapatilacakBetRoundlar.get(i).getId()))) + "\n";
            simpleMailMessage.setTo(eklenecekUSer.get().getUsername());
            simpleMailMessage.setText(response);
            try {
                mailSender.send(simpleMailMessage);

            } catch (Exception e) {
                throw new BadRequestException("Bad internet connection. Try again later.");
            }
        }

        return mailAdamlar.size() + " tane mail gonderildi.";

    }

    public String forgotPassword(String mail) {
        UserEntity user = securityContextUtil.getCurrentUser();
        Random random = new Random();
        user.setPassword("" + random.nextDouble(9999999));
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("shopifyemirhan6@gmail.com");
        simpleMailMessage.setSubject("Şifre yenileme işlemi");
        simpleMailMessage.setTo(user.getUsername());
        simpleMailMessage.setText("Merhaba Sayın ".toUpperCase() + user.getFirstname().toUpperCase() + " Bey, şifre sıfırlama isteğinizi aldık.\nYeni şifreniz: ".toUpperCase() + user.getPassword() + "\n\n!!!!!...Yeni şifreniz ile giriş yaptıktan sonra şifrenizi güncellemeyi unutmayınız...!!!!!!!".toUpperCase());
        userRepository.save(user);
        mailSender.send(simpleMailMessage);
        return "Sayın " + user.getFirstname().toUpperCase() + " Bey, yeni şifreniz mail adresinize gönderilmiştir. Lütfen posta kutunuzu kontrol ediniz.\n\n!!!!!...Yeni şifreniz ile giriş yaptıktan sonra şifrenizi güncellemeyi unutmayınız...!!!!!!!".toUpperCase();
    }


}
