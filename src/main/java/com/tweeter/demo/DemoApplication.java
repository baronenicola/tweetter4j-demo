package com.tweeter.demo;

import com.tweeter.demo.configuration.Factory;
import com.tweeter.demo.domain.TweetsTimeLine;
import com.tweeter.demo.repository.TweetsTimeLineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import twitter4j.*;

import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
import java.util.Objects;

@EnableCaching
@Slf4j
@SpringBootApplication
public class DemoApplication {

	@Autowired
	private Environment env;

	@Autowired
	private Factory factory;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	/**
	 * Search the tweets and save them in memory
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner initData(TweetsTimeLineRepository repository) {
		if(log.isDebugEnabled()){log.debug("Start initData Method, writing the initial data to the database");}
		return (args) -> {

			Twitter twitter = factory.getTwitterFactory();
			List<Status> statuses = twitter.getHomeTimeline();
			int followers=Integer.parseInt(Objects.requireNonNull(env.getProperty("query.minNumberOfFollowers")));

			if(log.isDebugEnabled()){log.debug("Showing home timeline.");}
			for (Status status : statuses) {
				if(status.getUser().getFollowersCount()>=followers && (status.getLang().equals("it")|| status.getLang().equals("es")|| status.getLang().equals("fr"))){
					TweetsTimeLine tt= new TweetsTimeLine();
					tt.setUserName(status.getUser().getName());
					tt.setText(status.getText());
					tt.setLocation(status.getUser().getLocation());
					tt.setValidation(false);
					repository.save(tt);
				}
			}
		};
	}


}
