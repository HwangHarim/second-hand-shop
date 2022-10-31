package com.capstone.skone.auth.domain.member;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import com.capstone.skone.auth.domain.member.vo.Gender;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static javax.persistence.EnumType.STRING;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Member implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  // 인증
  @Column(name = "auth")
  private String auth;

<<<<<<< HEAD
  @Column(name = "userName")
  private String userName;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "birth")
  private String birth;

  @Column(name = "gender")
  @Enumerated(STRING)
  private Gender gender;

=======
  @Column(name = "nickname")
  private String nickname;

>>>>>>> 627844bdc0d322ec6a31ae4d5310db661a3757ab
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> roles = new HashSet<>();
    for (String role : auth.split(",")) {
      roles.add(new SimpleGrantedAuthority(role));
    }
    return roles;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public String getNickname() {
    return nickname;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
