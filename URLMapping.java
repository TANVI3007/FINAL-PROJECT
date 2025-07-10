import jakarta.persistence.*;
@Entity
public class URLMapping{
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long id;
private String originalUrl;
private String shortCode;
private int clickCount=0;
//Getters and setters
public Long getId(){
return id;
}
public String getOriginalUrl(){
return OriginalUrl;
}
public void setOriginalUrl(String originalUrl){
this.originalUrl=orignalUrl;
}
public String getShortCode(){
return shortCode;
}
public void setShortCode(String shortCode){
this.shortCode=shortCode;
}
public int getClickCount(){
return clickCount;
}
public void setClickCount(int clickCount){
this.clickCount=clickCount;
}
}



