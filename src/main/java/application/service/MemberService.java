package application.service;

import application.entity.security.Member;
import application.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public final class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository
                .findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found."));
    }
    
    public void save(Member member) {
        if (memberRepository.existsById(member.getUsername())) {
            throw new AuthenticationServiceException("Member already exists.");
        }
        memberRepository.save(member);
    }
}