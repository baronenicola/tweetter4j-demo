package com.tweeter.demo.domain;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor @EqualsAndHashCode @ToString
@Entity
public class TweetsTimeLine {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String userName;

  @Column( length = 100000 )
  private String text;

  private String location;

  private boolean validation;

}
