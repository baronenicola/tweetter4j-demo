package com.tweeter.demo.service;

import com.tweeter.demo.domain.TweetsTimeLine;
import com.tweeter.demo.repository.TweetsTimeLineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class TweetsTimeLineServiceImpl implements TweetsTimeLineService {

  private final TweetsTimeLineRepository tweetsTimeLineRepository;


  @Override
  public List<TweetsTimeLine> findAll() {
    return tweetsTimeLineRepository.findAll();
  }

  @Override
  public TweetsTimeLine findById(long id)  {
    return tweetsTimeLineRepository.findById(id).orElse(null);
  }

  @Override
  public List <TweetsTimeLine> findByUser(String user) {
    List <TweetsTimeLine> tweets= new ArrayList<>();
    for(TweetsTimeLine tweet:tweetsTimeLineRepository.findAll()){
      if(tweet.getUserName().equals(user)){
        tweets.add(tweet);
      }
    }
    return tweets;
  }

  TweetsTimeLineRepository repository;


  @Override
  public TweetsTimeLine edit(TweetsTimeLine t){
    return tweetsTimeLineRepository.save(t);
  }
}