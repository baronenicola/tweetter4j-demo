package com.tweeter.demo.service;


import com.tweeter.demo.domain.TweetsTimeLine;

import java.util.List;

public interface TweetsTimeLineService {

  List<TweetsTimeLine> findAll();
  TweetsTimeLine findById(long id);
  List <TweetsTimeLine> findByUser(String user);
  TweetsTimeLine edit(TweetsTimeLine t);
}
