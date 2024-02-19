package com.betting.karakoc.utils;


import lombok.Data;

@Data
public class Deneme {
    public String betroundId = "";

    public  void betroundDegistir(){
        //birtakim api islemleri
        //donen response'dan id'i cekmek
        String response_id = "DUASIG"; // dummy data
        setBetroundId(response_id);
    }

    public  void betrounduCekVeKullan(){
        //bir nesne var, bu nesnenin bir fieldi betroundId
        //bu id'yi nesneye setleyip apiye gondericek.
        //surekli bos veri gidiyor.
        System.out.println(getBetroundId());
    }

    public static void main(String[] args) {
        Deneme classi = new Deneme();
        classi.betroundDegistir();

        classi.betrounduCekVeKullan();
    }

}
