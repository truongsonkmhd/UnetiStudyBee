package com.truongsonkmhd.unetistudy.service.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PocketBaseService {

    @Value("${pocketbase.url:http://localhost:8090}")
    private String pbUrl;

    @Value("${pocketbase.external-url:http://localhost:8090}")
    private String pbExternalUrl;

    @Value("${pocketbase.admin-email:admin@example.com}")
    private String adminEmail;

    @Value("${pocketbase.admin-password:password}")
    private String adminPassword;

    private RestTemplate restTemplate;
    private String authToken;
    private boolean isRetrying = false;

    @PostConstruct
    public void init() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(20);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofSeconds(60))
                .setResponseTimeout(Timeout.ofMinutes(10))
                .build();

        HttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        this.restTemplate = new RestTemplate(factory);
    }

    private void authenticate() {
        try {
            String authUrl = pbUrl + "/api/collections/_superusers/auth-with-password";
            Map<String, String> body = Map.of(
                    "identity", adminEmail,
                    "password", adminPassword);

            ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, body, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                authToken = (String) response.getBody().get("token");
                log.info("✅ Authentication successful");
            } else {
                log.error("❌ Authentication failed");
                authToken = null;
            }
        } catch (Exception e) {
            log.error("❌ Authentication error: {}", e.getMessage());
            authToken = null;
        }
    }

    public String uploadFile(String collectionName, MultipartFile file) {
        isRetrying = false;
        return uploadFileInternal(collectionName, file);
    }

    private String uploadFileInternal(String collectionName, MultipartFile file) {
        if (authToken == null) {
            authenticate();
            if (authToken == null) {
                log.error("❌ Cannot authenticate");
                return null;
            }
        }

        try {
            String originalFilename = file.getOriginalFilename();
            log.info("📤 Uploading: {} ({} bytes) → {}",
                    originalFilename, file.getSize(), collectionName);

            String uploadUrl = pbUrl + "/api/collections/" + collectionName + "/records";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setBearerAuth(authToken);

            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return originalFilename;
                }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", resource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    uploadUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                String id = (String) responseBody.get("id");

                log.info("✅ Upload OK, id={}", id);

                if (id != null) {
                    // Fetch lại record để lấy tên file chính xác
                    String actualFileName = getActualFileName(collectionName, id);

                    if (actualFileName != null) {
                        String fileUrl = String.format("%s/api/files/%s/%s/%s",
                                pbExternalUrl, collectionName, id, actualFileName);
                        log.info("✅ File URL: {}", fileUrl);
                        return fileUrl;
                    }
                }
            }

        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden e) {
            if (!isRetrying) {
                isRetrying = true;
                log.info("🔄 Re-authenticating...");
                authToken = null;
                authenticate();
                if (authToken != null) {
                    return uploadFileInternal(collectionName, file);
                }
            }
        } catch (Exception e) {
            log.error("❌ Error: {}", e.getMessage(), e);
        }

        return null;
    }

    private String getActualFileName(String collectionName, String recordId) {
        try {
            String getUrl = pbUrl + "/api/collections/" + collectionName + "/records/" + recordId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    getUrl,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> record = response.getBody();

                // Thử field "file" trước
                Object fileField = record.get("file");
                if (fileField instanceof String) {
                    log.info("📁 Found file: {}", fileField);
                    return (String) fileField;
                }

                // Tìm field có extension
                for (Map.Entry<String, Object> entry : record.entrySet()) {
                    if (entry.getValue() instanceof String) {
                        String value = (String) entry.getValue();
                        if (value.matches(".*\\.(mp4|mov|avi|wmv|mkv|flv|webm|jpg|jpeg|png|gif|pdf|zip)$")) {
                            log.info("📁 Found in '{}': {}", entry.getKey(), value);
                            return value;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("❌ Cannot fetch record: {}", e.getMessage());
        }

        return null;
    }

    public String getFileUrl(String collectionName, String recordId, String fileName) {
        return String.format("%s/api/files/%s/%s/%s", pbExternalUrl, collectionName, recordId, fileName);
    }

    public String toDisplayUrl(String url) {
        if (url == null || url.isBlank()) {
            return url;
        }
        // Replace internal docker hostname with external URL if present
        if (url.contains("pocketbase:8090")) {
            return url.replace(pbUrl, pbExternalUrl);
        }
        return url;
    }
}