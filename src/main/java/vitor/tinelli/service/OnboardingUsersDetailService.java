package vitor.tinelli.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vitor.tinelli.repository.OnboardingUsersRepository;


@Service
@RequiredArgsConstructor
public class OnboardingUsersDetailService implements UserDetailsService {

  private final OnboardingUsersRepository onboardingUsersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return Optional.ofNullable(onboardingUsersRepository.findByUsername(username))
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
