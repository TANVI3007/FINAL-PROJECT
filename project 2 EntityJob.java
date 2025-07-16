@Entity
public class Job {
  @Id @GeneratedValue private Long id;
  private String title, description;
  private String location;
  @ManyToOne User employer;
  // getters & setters
}