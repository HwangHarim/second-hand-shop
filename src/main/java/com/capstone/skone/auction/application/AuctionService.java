package com.capstone.skone.auction.application;

import com.capstone.skone.auction.domain.Auction;
import com.capstone.skone.auction.domain.BidInfo;
import com.capstone.skone.auction.infrastructure.AuctionRepository;
import com.capstone.skone.auction.infrastructure.BidInfoRepository;
import com.capstone.skone.auction.util.AuctionTimeThread;
import com.capstone.skone.auth.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidInfoRepository bidInfoRepository;

    public void createAuction(Auction auction){
        auctionRepository.save(auction);
        AuctionTimeThread auctionTimeThread = new AuctionTimeThread(auction, auctionRepository);
    }

    public Page<Auction> getAllAuction(Pageable pageable){
        return auctionRepository.findAll(pageable);
    }

    public Auction getSingleAuction(Long id){ return auctionRepository.findById(id).get();}

    /**
     입찰이 가능한지 판별하는 메소드
     최소 입찰 제한은..  1등가격 + 10%
     */
    public Boolean isBiding(Auction auction,int price){


        List<BidInfo> bidInfos = auction.getBidInfos();
        Collections.sort(bidInfos,(a, b)->b.getBid_Price()-a.getBid_Price());

        int first = bidInfos.get(0).getBid_Price();

        if(price<first+first/10){
            return true;
        }

        return false;
    }

    public void createBidInfo(Auction auction, Member member, int price){

        BidInfo bidInfo = new BidInfo();

        bidInfo.setAuction(auction);
        bidInfo.setBid_Price(price);
        bidInfo.setUserName(member.getUsername());
        bidInfoRepository.save(bidInfo);
    }
    public void createBidInfo(BidInfo bidInfo){
        bidInfoRepository.save(bidInfo);
    }

    /**
     *
     * @param ,user
     * 1000원씩 고정 입찰
     */
    public void fixed_bid(String user ,Auction auction){

        BidInfo bidInfo = new BidInfo();

        bidInfo.setAuction(auction);
        bidInfo.setUserName(user);
        bidInfo.setBid_Price(get_First_Bid()+1000);

        bidInfoRepository.save(bidInfo);

    }

    public void fixed_bidV2(String user ,Auction auction){

        BidInfo bidInfo = new BidInfo();

        // BidInfo -> Auction 연결
        bidInfo.setAuction(auction);
        bidInfo.setUserName(user);
        bidInfo.setBid_Price(get_First_Bid()+1000);
        bidInfoRepository.save(bidInfo);

        // Auction -> BidInfo  연결   양방향 안쓸거면 이 코드 삭제 ㄱㄱ
        auction.addBidInfos(bidInfo);

        auctionRepository.save(auction);
    }

    /**
     * 1등 입찰 값을 불러오는 메소드, 2등 입찰 값을 불러오는 메소드
     */
    public int get_First_Bid(){
        List<BidInfo> all = bidInfoRepository.findAll();

        if(all.size()==0){
            return 0;
        }

        Collections.sort(all,(a,b)->b.getBid_Price()-a.getBid_Price());
        return all.get(0).getBid_Price();
    }

    public int get_Second_Bid(){
        List<BidInfo> all = bidInfoRepository.findAll();

        if(all.size()>2){
            return 0;
        }
        Collections.sort(all,(a,b)->b.getBid_Price()-a.getBid_Price());

        return all.get(1).getBid_Price();
    }

    public Auction selectAuction(Long id){
        Auction auction = auctionRepository.findById(id).orElse(null);
        System.out.println("auction = " + auction);
        return auction;
    }

    public void deleteAuction(Long id){
        auctionRepository.deleteById(id);
    }




//    //입찰 메소드 (자유입찰?)
//    public void bid(Long Auction_num, int price ){
//
//        Auction auction = auctionTestRepository.findById(Auction_num);
//
//
//    }
//
//    //1000원 입찰 고정
//    public void bid_price_fix(Long Auction_num) {
//        Auction auction = auctionTestRepository.findById(Auction_num);
//
//    }



}
