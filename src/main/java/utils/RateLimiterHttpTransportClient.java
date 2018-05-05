package utils;

import com.google.common.util.concurrent.RateLimiter;
import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import java.io.File;
import java.io.IOException;

public class RateLimiterHttpTransportClient implements TransportClient {
    private final TransportClient client;
    private final RateLimiter rateLimiter;

    public RateLimiterHttpTransportClient(TransportClient client, double rate) {
        this.client = client;
        this.rateLimiter = RateLimiter.create(rate);
    }

    @Override
    public ClientResponse get(String url) throws IOException {
        rateLimiter.acquire();
        return client.get(url);
    }

    @Override
    public ClientResponse get(String url, String contentType) throws IOException {
        rateLimiter.acquire();
        return client.get(url, contentType);
    }

    @Override
    public ClientResponse post(String url) throws IOException {
        rateLimiter.acquire();
        return client.post(url);
    }

    @Override
    public ClientResponse post(String url, String body) throws IOException {
        rateLimiter.acquire();
        return client.post(url, body);
    }

    @Override
    public ClientResponse post(String url, String body, String contentType) throws IOException {
        rateLimiter.acquire();
        return client.post(url, body, contentType);
    }

    @Override
    public ClientResponse post(String url, String fileName, File file) throws IOException {
        rateLimiter.acquire();
        return client.post(url, fileName, file);
    }

    @Override
    public ClientResponse delete(String url) throws IOException {
        rateLimiter.acquire();
        return client.delete(url);
    }

    @Override
    public ClientResponse delete(String url, String body) throws IOException {
        rateLimiter.acquire();
        return client.delete(url, body);
    }

    @Override
    public ClientResponse delete(String url, String body, String contentType) throws IOException {
        rateLimiter.acquire();
        return client.delete(url, body, contentType);
    }
}
