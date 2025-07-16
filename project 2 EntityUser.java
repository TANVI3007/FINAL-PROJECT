@Entity
public class User {
  @Id @GeneratedValue private Long id;
  private String username, password;
  @Enumerated(EnumType.STRING) private Role role;
  public enum Role { EMPLOYER, APPLICANT }
  // getters & setters
}