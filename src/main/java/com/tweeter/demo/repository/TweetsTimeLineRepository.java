package com.tweeter.demo.repository;


import com.tweeter.demo.domain.TweetsTimeLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetsTimeLineRepository extends JpaRepository<TweetsTimeLine, Long> {

}
