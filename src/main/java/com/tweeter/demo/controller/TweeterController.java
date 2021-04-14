package com.tweeter.demo.controller;


import com.tweeter.demo.configuration.Factory;
import com.tweeter.demo.domain.TweetsTimeLine;
import com.tweeter.demo.service.TweetsTimeLineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitter4j.Trend;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TweeterController {

  @Autowired
  private TweetsTimeLineService service;


  @Autowired
  private Factory factory;

  @Autowired
  private Environment env;

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
    if(log.isDebugEnabled())log.debug("Method execution twitterTrends, parameter woeId= {}",woeId==null?"default 1":woeId.toString());
    Twitter twitter = factory.getTwitterFactory();
    return  Arrays.stream(twitter.getPlaceTrends(woeId==null?1:woeId).getTrends()).map(Trend::getName);
  }

  /**
   * Return the list of all tweets in the memory
   * @return
   */
  @GetMapping("/tweets/")
  public ResponseEntity<List<TweetsTimeLine>> getTweets() {
      return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
  }

  /**
   * Return the specific tweet by id
   * @param id
   * @return
   */
  @GetMapping("/tweets/{id}")
  public ResponseEntity<TweetsTimeLine> getTweet(@PathVariable(value="id") final Long id) {
      return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
  }



  @GetMapping(value = { "/tweets/user/{user}" })
  public ResponseEntity<List<TweetsTimeLine>> getTweetByUser(@PathVariable(value="user",required = true) final String user) {
    return new ResponseEntity<>(service.findByUser(user), HttpStatus.OK);
  }

  /**
   *
   * @param id
   * @return
   */
  @PutMapping(value = { "/tweets/{id}" })
  public TweetsTimeLine  validateTweet(@PathVariable(value="id",required = true) final Long id) {
    TweetsTimeLine t=service.findById(id);
    t.setValidation(true);
    service.edit(t);
    return t;
  }

}
