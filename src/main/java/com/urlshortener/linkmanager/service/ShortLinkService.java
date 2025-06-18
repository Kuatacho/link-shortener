package com.urlshortener.linkmanager.service;

import com.urlshortener.linkmanager.repository.ShortLinkRepository;
import com.urlshortener.linkmanager.model.ShortLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

@Service
public class ShortLinkService {

    @Autowired
    private ShortLinkRepository repository;

    public ShortLink createShortLink(String targetUrl, int minutesValid){
       String slug= UUID.randomUUID().toString().substring(0, 8);
       LocalDateTime expiry  = LocalDateTime.now().plusMinutes(minutesValid);
       ShortLink shortLink = new ShortLink(slug,targetUrl,expiry);
       return repository.save(shortLink);
    }

    public Optional<ShortLink> getValidLink(String slug) {
        Optional<ShortLink> opt = repository.findById(slug);

        if (opt.isPresent()) {
            ShortLink link = opt.get();

            if (!link.isUsed() && link.getExpiry().isAfter(LocalDateTime.now())) {
                return Optional.of(link);
            }
        }

        return Optional.empty();
    }

    public void markAsUsed(String slug) {
        repository.findById(slug).ifPresent(link -> {
            link.setUsed(true);
            repository.save(link);
        });
    }
}


