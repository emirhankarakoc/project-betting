BAYKAR 2024 YAZ stajı için  projeyi ekstra revizeledim. readme'yi de revizelenmiş haline göre değiştiriyorum..
I updated thıs project for 2024 summer internship at BAYKAR. and changing readme again.

All in one.
![image](https://github.com/emirhankarakoc/betting/assets/101813995/d3c1b84e-fde4-49fc-bc56-6469b187d1c7)
<!--emirhanenginner -->

# EN - BETTING

This project is a replica of the backend of the Spor Toto module.<br/>
[You can check the live version here.](https://www.nesine.com/sportoto)<br/>
[Also check deployed version with swagger.](https://bettting.ey.r.appspot.com/swagger-ui/index.html#/)
</br>

I started to change admin-user betting TO admin- team managers - team members (like kahoot) + UI like fat princess theme ( old ps3 game. )<br/>
currently i have 7 days ill-streak (yeah. im veeeeeery ill) current:24.02.2024<br/>

To run the project, you need MYSQL8 and a special key for sending emails.<br/>
You can download MYSQL8 from here: https://dev.mysql.com/downloads/workbench/ (You also need Workbench.)<br/>
You can get the mail key from here: https://youtu.be/BeNw2wutFkw?si=8-SMNji9pXGDQWZL

# ABOUT THE PROJECT

When you start the project and if there are no users in the database, it adds one admin and one user.<br/>
The admin's token is 1, and the user's token is 2.<br/>
You can create a new account with your own email address to play with your token.<br/>
If you use your own email address, the number of correct predictions will be sent to you by email when the bets are
closed. (That's why you need the mail key.)(as user , just create a betround with admin token and give them betroundID's. no need any mailkey + workbench. i deployed all settings is ready)

# SCENARIO

Admin:<br/>
Create a BetRound (Bulletin) from the "/postBetRound" endpoint.<br/>
Add new games by providing the id of the betround you created at the "/createGame" endpoint.<br/>
When you add 13 games, the betround will automatically open for predictions.// no more static number. changed to dynamic, named GAME_MAX_COUNT. go change to 123141341, go change to 1...fully freedom<br/> 
When all users enter their predictions;<br/>
First, close the bulletin for users' predictions at "/endBetRound".<br/>
Then enter the results of the matches at "/putGame".<br/>
After entering the results, send a mail with congratulations and the number of correct predictions to those who filled
out their coupons correctly using "/finish".<br/>

User:<br/>
Create a user from the "/account/register" endpoint.<br/>
Get your token from the "/account/login" endpoint.<br/>
Create a UserBetRound (Coupon) from the "/createUserBetRound" endpoint.<br/>
Play new predictions by providing the id of the userbetround you added at the "/createBet" endpoint.<br/>
<br/><br/>
** If you don't play all matches, your prediction will be invalid.<br/>
** You can play a prediction for each match at most once.<br/>
** You can make different coupons for the same bulletin to increase your chances.<br/><br/><br/>

emirhan karakoç<br/>
2023 denizli

# TR - BETTING

Bu proje , Spor Toto modülünün backendinin kopyasıdır.<br/>
[Bu linkten çalışan haline göz atabilirsiniz.](https://www.nesine.com/sportoto)<br/>
[Deploylanmış ve çalışan hali](https://bettting.ey.r.appspot.com/swagger-ui/index.html#/)

Projeyi çalıştırmak için MYSQL8'e ve Mail gönderimi için özel bir key'e ihtiyacınız var.<br/>
MYSQL8'i buradan indirebilirsiniz: https://dev.mysql.com/downloads/workbench/   (Ayrıca workbench'e de ihtiyacınız
var.)<br/>
Mail keyini de buradan alabilirsiniz: https://youtu.be/BeNw2wutFkw?si=8-SMNji9pXGDQWZL<br/>

# PROJE HAKKINDA

Projeyi başlattığınız zaman eğer veritabanında hiç kullanıcı yoksa, bir tane admin, bir tane de user ekler.<br/>
Adminin tokeni 1, userin tokeni 2'dir.<br/>
Kendi mail adresiniz ile yeni hesap açıp, kendi tokeninizi de kullanabilirsiniz.<br/>
Eğer kendi mail adresinizi kullanırsanız , iddialar kapandığında kaç doğru yaptığınız mail ile tarafınıza
ulaştırılacaktır. (Bu yüzden mail keyine ihtiyacınız var.) //eger sadece bet yapmak ve sonuclandirmak istiyorsaniz mail tokenine ihtiyaciniz yok. Product'ta benim mail tokenim var.<br/>

# SENARYO

Admin:<br/>
"/postBetRound" adresinden bir BetRound oluşturun. (Bülten)<br/>
"/createGame" adresinden, oluşturduğunuz betround'un id'sini vererek yeni oyunlar ekleyin.<br/>
13 tane oyun eklediğinizde, betround otomatik olarak iddialara açılcaktır. //eskiden 13'tu. daha sonra onu statik bir degisken yaptim. 
Tüm kullanıcılar iddialarını girdiğinde;<br/>
Ilk önce maçları "/bet/admin/endBetRound" adresinden bülteni, kullanıcıların iddialarına kapatın.<br/>
Daha sonra da "/bet/admin/putGame" adresinden maçların sonuçlarını girin.<br/>
Maçların sonuçları girildikten sonra , "/bet/admin/sendMailToParticipants" adresinden kuponlarını eksiksiz dolduran
kişilere, kaç tane doğru yaptığını ve tebrik içeren mesajınızı mail yoluyla gönderin.<br/>

Kullanıcı:<br/>
"/account/register" adresinden bir kullanıcı oluşturun.<br/>
"/account/login" adresinden tokeninizi alın.<br/>
"/createUserBetRound" adresinden bir UserBetRound oluşturun. (Kupon)<br/>
"/createBet" adresinden, eklediğiniz userbetround'un id'sini vererek yeni iddialar oynayın.<br/>
<br/><br/>
** Eğer tüm maçlara da oynamazsanız , iddianız geçersiz olacaktır.<br/>
** Bir maça, en fazla 1 kere iddia oynayabilirsiniz.<br/>
** Aynı bültene farklı kuponlar yapabilirsiniz. Böylece şansınızı arttırabilirsiniz.<br/><br/><br/>

emirhan karakoç
2023 denizli
