package com.betting.karakoc.service.manager;

import com.betting.karakoc.exceptions.GeneralException;

import com.betting.karakoc.model.real.*;
import com.betting.karakoc.repository.*;
import com.betting.karakoc.security.SecurityContextUtil;
import com.betting.karakoc.service.repo.MailService;
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
@Data@AllArgsConstructor
public class MailSenderManager implements MailService {
    private final UserEntityRepository userRepository;
    private final BetRoundEntityRepository betRepository;
    private final UserBetRoundRepository userBetRoundRepository;
    private final JavaMailSender mailSender;
    private final SecurityContextUtil securityContextUtil;
    private final UserBetRepository userBetRepository;
    private final GameRepository gameRepository;

    BetSummaryManager betSummaryManager;
    public String mailSender2(Long betroundId){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("shopifyemirhan6@gmail.com");
        simpleMailMessage.setSubject("Spor Toto Sonuç");


        //adminkontrolu
        String response="";
        List<UserBetRoundEntity> kapatilacakBetRoundlar = new ArrayList<>();



        List<UserBetRoundEntity> gezilecekListe = userBetRoundRepository.findAllByBetRoundEntityId(betroundId);
        List<UserEntity> mailAdamlar = new ArrayList<>();
        for (int i = 0;i<gezilecekListe.size();i++){
            if (gezilecekListe.get(i).getBetRoundEntityId()==betroundId){
                if (gezilecekListe.get(i).getUserBetList().size()==gameMaxCount) kapatilacakBetRoundlar.add(gezilecekListe.get(i));

            }
        }

        for (int i = 0;i<kapatilacakBetRoundlar.size();i++){
            Optional<UserEntity> eklenecekUSer =  userRepository.findById(kapatilacakBetRoundlar.get(i).getUserEntityId());
            if (mailAdamlar.contains(eklenecekUSer.get())){
                //ekleme yani :D
            }
            else {
                mailAdamlar.add(eklenecekUSer.get());
            }
        }
        for (int i = 0;i<kapatilacakBetRoundlar.size();i++){
            Optional<UserEntity> eklenecekUSer =  userRepository.findById(kapatilacakBetRoundlar.get(i).getUserEntityId());


       //     response+=(betSummaryManager.summaryBetsForTwoTeams((kapatilacakBetRoundlar.get(i).getId()))) + "\n";
            simpleMailMessage.setTo(eklenecekUSer.get().getUsername());
            simpleMailMessage.setText(response);
            try{
                mailSender.send(simpleMailMessage);

            }
            catch (Exception e){
                throw new GeneralException("Bad internet connection. Try again later.",400);
            }
        }

        return mailAdamlar.size() +" tane mail gonderildi.";

    }

    public String forgotPassword(String username){
        UserEntity user = securityContextUtil.getCurrentUser();
        Random random = new Random();
        user.setPassword(""+ random.nextDouble(9999999));
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("shopifyemirhan6@gmail.com");
        simpleMailMessage.setSubject("Şifre yenileme işlemi");
        simpleMailMessage.setTo(user.getUsername());
        simpleMailMessage.setText("Merhaba Sayın ".toUpperCase() + user.getFirstname().toUpperCase() +" Bey, şifre sıfırlama isteğinizi aldık.\nYeni şifreniz: ".toUpperCase()+user.getPassword()+"\n\n!!!!!...Yeni şifreniz ile giriş yaptıktan sonra şifrenizi güncellemeyi unutmayınız...!!!!!!!".toUpperCase());
        userRepository.save(user);
        mailSender.send(simpleMailMessage);
        return "Sayın "+user.getFirstname().toUpperCase()+" Bey, yeni şifreniz mail adresinize gönderilmiştir. Lütfen posta kutunuzu kontrol ediniz.\n\n!!!!!...Yeni şifreniz ile giriş yaptıktan sonra şifrenizi güncellemeyi unutmayınız...!!!!!!!".toUpperCase();
    }


}
