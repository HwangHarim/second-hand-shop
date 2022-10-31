package com.capstone.skone.auction.util;

import com.capstone.skone.auction.domain.Auction;
import com.capstone.skone.auction.infrastructure.AuctionRepository;
import com.capstone.skone.mail.MailSender;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 새롭게 고안한 Auction 종료시간 체크
 */
public class AuctionTimeThread {


    public AuctionTimeThread(Auction auction, AuctionRepository auctionRepository) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("옥션 종료");
                    MailSender mailSender = new MailSender();
                    mailSender.sendMail();

                    auctionRepository.deleteById(auction.getAuctionNumber());

                }
                catch (EmptyResultDataAccessException e){
                    System.out.println("이미 종료된 경매글입니다. = " + e);
                }
            }
        };

        //1000 ==1초
        //예시로 1분 지정
        // 7일을 사용한다면 604800000
        long delay= 1000L*60 ;

        Timer timer = new Timer();

        timer.schedule(task,delay);

    }


}
