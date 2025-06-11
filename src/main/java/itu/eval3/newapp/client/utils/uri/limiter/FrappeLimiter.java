package itu.eval3.newapp.client.utils.uri.limiter;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;

@Data
public class FrappeLimiter {
    public static final FrappeLimiter NOLIMITER = new FrappeLimiter(-1, 0);

    public int limitStart;
    public int limitLength;
    public FrappeLimiter() {}
    public FrappeLimiter(int limitStart, int limitLength) {
        this.limitStart = limitStart;
        this.limitLength = limitLength;
    }

    public void setUri(UriComponentsBuilder uriComponentsBuilder){
        if (limitStart >= 0) {
            uriComponentsBuilder.queryParam("limit_start", limitStart);
        }
        
        if (limitLength >= 0) {
            uriComponentsBuilder.queryParam("limit_page_length", limitLength);
        }
    }
}
