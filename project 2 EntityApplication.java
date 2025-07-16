@Entity
public class Application {
  @Id @GeneratedValue private Long id;
  @ManyToOne User applicant;
  @ManyToOne Job job;
  @Enumerated(EnumType.STRING) private Status status;
  public enum Status { APPLIED, ACCEPTED, REJECTED }
}