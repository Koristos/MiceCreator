package ru.geekbrains.micecreator.service.security;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.securiry.UserDto;
import ru.geekbrains.micecreator.models.security.Role;
import ru.geekbrains.micecreator.models.security.User;
import ru.geekbrains.micecreator.repository.security.UserRepo;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {
	private final UserRepo userRepository;

	public List<String> getAllManagerNames() {
		return userRepository.findAll().stream().map(User::getManager).collect(Collectors.toList());
	}

	public Optional<User> findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}

	public Optional<User> findByManager(String managerName) {
		return userRepository.findByManager(managerName);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	public UserDto getUserInfo (String userName) {
		return new UserDto(userRepository.findByUsername(userName).get());
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
