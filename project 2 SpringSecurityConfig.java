@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired UserService userService;
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
      .authorizeRequests()
        .antMatchers("/jobs/new").hasRole("EMPLOYER")
        .antMatchers("/jobs/*", "/apply/*").authenticated()
        .anyRequest().permitAll()
      .and().formLogin().defaultSuccessUrl("/", true)
      .and().logout();
  }
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());
  }
}