import java.net.URI;
import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping("/api")
public class URLController {
    @Autowired
    private URLRepository urlRepository;
    @PostMapping("/shorten")
    public String shortenUrl(@RequestBody String originalUrl) {
        String shortCode = UUID.randomUUID().toString().substring(0, 6);
        URLMapping mapping = new URLMapping();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortCode(shortCode);
        urlRepository.save(mapping);
        return "http://localhost:8080/api/" + shortCode;
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortCode) {
        Optional<URLMapping> mapping = urlRepository.findByShortCode(shortCode);
        if (mapping.isPresent()) {
            URLMapping map = mapping.get();
            map.setClickCount(map.getClickCount() + 1);
            urlRepository.save(map);
            return ResponseEntity.status(302).location(URI.create(map.getOriginalUrl())).build();
        }
        return ResponseEntity.notFound().build();
    }
}