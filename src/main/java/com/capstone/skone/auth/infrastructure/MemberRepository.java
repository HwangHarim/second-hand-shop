package com.capstone.skone.auth.infrastructure;

import com.capstone.skone.auth.domain.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByEmail(String userEmail);
}
