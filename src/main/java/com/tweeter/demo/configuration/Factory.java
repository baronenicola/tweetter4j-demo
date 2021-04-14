package com.tweeter.demo.configuration;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Slf4j
@NoArgsConstructor
@Service
public class Factory {

    @Autowired
    private Environment env;

    @Bean
    public  Twitter getTwitterFactory() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

        configurationBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(env.getProperty("spring.social.twitter.ConsumerKey"))
                .setOAuthConsumerSecret(env.getProperty("spring.social.twitter.OAuthConsumerSecret"))
                .setOAuthAccessToken(env.getProperty("spring.social.twitter.OAuthAccessToken"))
                .setOAuthAccessTokenSecret(env.getProperty("spring.social.twitter.OAuthAccessTokenSecret"));
        if(log.isDebugEnabled()){log.debug("Created Twitter configuration");}
        return new TwitterFactory(configurationBuilder.build()).getInstance();
    }
}
