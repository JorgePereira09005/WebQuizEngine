package WebQuizEngine.security;

import WebQuizEngine.dao.UserRepository;
import WebQuizEngine.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {

        try {
            User user = userRepository.findByEmail(email);
            return UserDetailsImpl.build(user);

        } catch (Exception e) {
            throw  new UsernameNotFoundException("User Not Found with email: " + email);
        }
    }
}
