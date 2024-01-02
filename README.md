![image](https://github.com/emirhankarakoc/betting/assets/101813995/d3c1b84e-fde4-49fc-bc56-6469b187d1c7)
<!--emirhanenginner -->



# EN - BETTING
This project is a replica of the backend of the Spor Toto module.<br/>
[You can check the live version here.](https://www.nesine.com/sportoto)

To run the project, you need MYSQL8 and a special key for sending emails.<br/>
You can download MYSQL8 from here: https://dev.mysql.com/downloads/workbench/ (You also need Workbench.)<br/>
You can get the mail key from here: https://youtu.be/BeNw2wutFkw?si=8-SMNji9pXGDQWZL

# ABOUT THE PROJECT
When you start the project and if there are no users in the database, it adds one admin and one user.<br/>
The admin's token is ADMINTOKEN, and the user's token is USERTOKEN.<br/>
You can create a new account with your own email address.<br/>
If you use your own email address, the number of correct predictions will be sent to you by email when the bets are closed. (That's why you need the mail key.)<br/>

# SCENARIO
Admin:<br/>
Create a BetRound (Bulletin) from the "/bet/admin/postBetRound" endpoint.<br/>
Add new games by providing the id of the betround you created at the "/bet/admin/createGame" endpoint.<br/>
When you add 13 games, the betround will automatically open for predictions.<br/>
When all users enter their predictions;<br/>
First, close the bulletin for users' predictions at "/bet/admin/endBetRound".<br/>
Then enter the results of the matches at "/bet/admin/putGame".<br/>
After entering the results, send a mail with congratulations and the number of correct predictions to those who filled out their coupons correctly using "/bet/admin/sendMailToParticipants".<br/>

User:<br/>
Create a user from the "/bet/account/register" endpoint.<br/>
Get your token from the "/bet/account/login" endpoint.<br/>
Create a UserBetRound (Coupon) from the "/bet/user/createUserBetRound" endpoint.<br/>
Play new predictions by providing the id of the userbetround you added at the "/bet/user/createBet" endpoint.<br/>
<br/><br/>
** If you don't play all 13 matches, your prediction will be invalid.<br/>
** You can play a prediction for each match at most once.<br/>
** You can make different coupons for the same bulletin to increase your chances.<br/><br/><br/>

emirhan karakoç<br/>
2023 denizli


# TR - BETTING
Bu proje , Spor Toto modülünün backendinin kopyasıdır.<br/>
[Bu linkten çalışan haline göz atabilirsiniz.](https://www.nesine.com/sportoto)<br/>

Projeyi çalıştırmak için MYSQL8'e ve Mail gönderimi için özel bir key'e ihtiyacınız var.<br/>
MYSQL8'i buradan indirebilirsiniz: https://dev.mysql.com/downloads/workbench/   (Ayrıca workbench'e de ihtiyacınız var.)<br/>
Mail keyini de buradan alabilirsiniz: https://youtu.be/BeNw2wutFkw?si=8-SMNji9pXGDQWZL<br/>

# PROJE HAKKINDA
Projeyi başlattığınız zaman eğer veritabanında hiç kullanıcı yoksa, bir tane admin, bir tane de user ekler.<br/>
Adminin tokeni ADMINTOKEN, userin tokeni USERTOKEN'dir.<br/>
Kendi mail adresiniz ile yeni hesap açıp da kullanabilirsiniz.<br/>
Eğer kendi mail adresinizi kullanırsanız , iddialar kapandığında kaç doğru yaptığınız mail ile tarafınıza ulaştırılacaktır. (Bu yüzden mail keyine ihtiyacınız var.)

# SENARYO
Admin:<br/>
"/bet/admin/postBetRound" adresinden bir BetRound oluşturun. (Bülten)<br/>
"/bet/admin/createGame" adresinden, oluşturduğunuz betround'un id'sini vererek yeni oyunlar ekleyin.<br/>
13 tane oyun eklediğinizde, betround otomatik olarak iddialara açılcaktır.
Tüm kullanıcılar iddialarını girdiğinde;<br/>
Ilk önce maçları "/bet/admin/endBetRound" adresinden bülteni, kullanıcıların iddialarına kapatın.<br/>
Daha sonra da "/bet/admin/putGame" adresinden maçların sonuçlarını girin.<br/>
Maçların sonuçları girildikten sonra , "/bet/admin/sendMailToParticipants" adresinden kuponlarını eksiksiz dolduran kişilere, kaç tane doğru yaptığını ve tebrik içeren mesajınızı mail yoluyla gönderin.<br/>


Kullanıcı:<br/>
"/bet/account/register" adresinden bir kullanıcı oluşturun.<br/>
"/bet/account/login" adresinden tokeninizi alın.<br/>
"/bet/user/createUserBetRound" adresinden bir UserBetRound oluşturun. (Kupon)<br/>
"/bet/user/createBet" adresinden, eklediğiniz userbetround'un id'sini vererek yeni iddialar oynayın.<br/>
<br/><br/> 
** Eğer 13 maçın 13'üne de oynamazsanız , iddianız geçersiz olacaktır.<br/>
** Bir maça, en fazla 1 kere iddia oynayabilirsiniz.<br/>
** Aynı bültene farklı kuponlar yapabilirsiniz. Böylece şansınızı arttırabilirsiniz.<br/><br/><br/>


emirhan karakoç
2023 denizli
