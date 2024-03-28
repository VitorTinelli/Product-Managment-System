package vitor.tinelli.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import vitor.tinelli.domain.OnboardingUsers;

public interface OnboardingUsersRepository extends JpaRepository<OnboardingUsers, Long> {
  UserDetails findByUsername(String username);

}
