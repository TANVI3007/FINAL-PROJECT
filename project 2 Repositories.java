public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String u);
}
public interface JobRepository extends JpaRepository<Job, Long> {
  List<Job> findByTitleContainingOrLocationContaining(String t, String l);
}
public interface ApplicationRepository extends JpaRepository<Application, Long> {
  List<Application> findByApplicant(User u);
}