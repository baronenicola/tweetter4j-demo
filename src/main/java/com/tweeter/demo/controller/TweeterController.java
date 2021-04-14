package com.tweeter.demo.controller;


import com.tweeter.demo.domain.TweetsTimeLine;
import com.tweeter.demo.service.TweetsTimeLineService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TweeterController {

  @Autowired
  TweetsTimeLineService service;


  /**
   * Returns the top 10 trending topics for a specific WOEID, if trending information is available for it. <br>
   * If you do not specify a WOEID, the world topics will be listed.<br>
   * Example WOEID locations include: Worldwide: 1, UK: 23424975, Brazil: 23424768,
   * Germany: 23424829, Mexico: 23424900, Canada: 23424775, United States: 23424977, New York: 2459115
   * @param woeId Can be null.
   * @return
   * @throws TwitterException
   */
  @GetMapping(value = { "/trends", "/trends/{woeId}" })
  public Stream<String> twitterTrends(@PathVariable(value="woeId",required = false) final Integer woeId) throws TwitterException {
    if(log.isDebugEnabled())log.debug("method execution twitterTrends, parameter woeId= {}",woeId==null?"default 1":woeId.toString());
//    Twitter twitter = getTwitterFactory();
//    return  Arrays.stream(twitter.getPlaceTrends(woeId==null?1:woeId).getTrends()).map(Trend::getName);
    return null;
  }

  /**
   * Return the list of all tweets in the memory
   * @return
   */
//  @GetMapping("/tweets/")
  @GetMapping(value = { "/tweets/", "/tweets/{id}" })
  public ResponseEntity<List<TweetsTimeLine>> getTweets(@PathVariable(value="id",required = false) final Long id) {
    if(null!=id){
      return new ResponseEntity(service.findById(id), HttpStatus.OK);
    }else{
      return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
  }


  @GetMapping(value = { "/tweets/user/{user}" })
  public ResponseEntity getTweetByUser(@PathVariable(value="user",required = true) final String user) {
    return new ResponseEntity(service.findByUser(user), HttpStatus.OK);
  }

  /**
   *
   * @param id
   * @return
   * @throws NotFoundException
   */
  @PutMapping(value = { "/tweets/{id}" })
  public TweetsTimeLine  validateTweet(@PathVariable(value="id",required = true) final Long id) throws NotFoundException {
    TweetsTimeLine t=service.findById(id);
    t.setValidation(true);
    service.edit(t);
    return t;
  }

}
