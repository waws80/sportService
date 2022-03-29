package edu.wj.sport.service.utils;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

public class SportResponse extends ResponseEntity<Object> {


    private SportResponse(HttpStatus status) {
        super(status);
    }

    private SportResponse(Object body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    public static SportResponse success(Object data){
        return new SportBuilder(HttpStatus.OK).body(data);
    }

    public static SportResponse paramsError(String msg){
        return new SportBuilder(HttpStatus.BAD_REQUEST).body(msg);
    }

    public static SportResponse serviceError(){
        return new SportBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器异常，请稍后再试...");
    }

    public static SportResponse black(){
        return new SportBuilder(HttpStatus.FORBIDDEN).body("当前用户已被拉黑...");
    }

    public static SportResponse unAuth(){
        return new SportBuilder(HttpStatus.UNAUTHORIZED).body("非法访问...");
    }


    @SuppressWarnings("unchecked")
    private static class SportBuilder implements BodyBuilder {

        private final HttpStatus statusCode;

        private final HttpHeaders headers = new HttpHeaders();

        public SportBuilder(HttpStatus statusCode) {
            this.statusCode = statusCode;
        }

        @Override
        public BodyBuilder header(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.add(headerName, headerValue);
            }
            return this;
        }

        @Override
        public BodyBuilder headers(@Nullable HttpHeaders headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        @Override
        public BodyBuilder headers(Consumer<HttpHeaders> headersConsumer) {
            headersConsumer.accept(this.headers);
            return this;
        }

        @Override
        public BodyBuilder allow(HttpMethod... allowedMethods) {
            this.headers.setAllow(new LinkedHashSet<>(Arrays.asList(allowedMethods)));
            return this;
        }

        @Override
        public BodyBuilder contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }

        @Override
        public BodyBuilder contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return this;
        }

        @Override
        public BodyBuilder eTag(String etag) {
            if (!etag.startsWith("\"") && !etag.startsWith("W/\"")) {
                etag = "\"" + etag;
            }
            if (!etag.endsWith("\"")) {
                etag = etag + "\"";
            }
            this.headers.setETag(etag);
            return this;
        }

        @Override
        public BodyBuilder lastModified(ZonedDateTime date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public BodyBuilder lastModified(Instant date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public BodyBuilder lastModified(long date) {
            this.headers.setLastModified(date);
            return this;
        }

        @Override
        public BodyBuilder location(URI location) {
            this.headers.setLocation(location);
            return this;
        }

        @Override
        public BodyBuilder cacheControl(CacheControl cacheControl) {
            this.headers.setCacheControl(cacheControl);
            return this;
        }

        @Override
        public BodyBuilder varyBy(String... requestHeaders) {
            this.headers.setVary(Arrays.asList(requestHeaders));
            return this;
        }

        @Override
        public SportResponse build() {
            return body(null);
        }

        @Override
        public  SportResponse body(@Nullable Object body) {
            return new SportResponse(body, this.headers, this.statusCode);
        }
    }
}
