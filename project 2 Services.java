@Service
public class UserService implements UserDetailsService {
  @Autowired UserRepository repo;
  public User register(User u) { return repo.save(u); }
  @Override public UserDetails loadUserByUsername(String u) throws UsernameNotFoundException {
    User user = repo.findByUsername(u).orElseThrow(...);
    return new User(User.getUsername(), User.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole())));
  }
}