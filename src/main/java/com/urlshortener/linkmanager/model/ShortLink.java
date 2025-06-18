package com.urlshortener.linkmanager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ShortLink {

    @Id
    private String slug; // Código corto, puede ser UUID o algo personalizado

    @Column(nullable = false)
    private String targetUrl;

    private boolean used = false;

    private LocalDateTime expiry;

    public ShortLink() {}

    public ShortLink(String slug, String targetUrl, LocalDateTime expiry) {
        this.slug = slug;
        this.targetUrl = targetUrl;
        this.expiry = expiry;
        this.used = false;
    }

    // Getters y Setters

    public String getSlug() { return slug; }

    public void setSlug(String slug) { this.slug = slug; }

    public String getTargetUrl() { return targetUrl; }

    public void setTargetUrl(String targetUrl) { this.targetUrl = targetUrl; }

    public boolean isUsed() { return used; }

    public void setUsed(boolean used) { this.used = used; }

    public LocalDateTime getExpiry() { return expiry; }

    public void setExpiry(LocalDateTime expiry) { this.expiry = expiry; }
}
