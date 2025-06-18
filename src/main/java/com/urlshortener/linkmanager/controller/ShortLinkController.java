package com.urlshortener.linkmanager.controller;

import com.urlshortener.linkmanager.model.ShortLink;
import com.urlshortener.linkmanager.repository.ShortLinkRepository;
import com.urlshortener.linkmanager.service.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ShortLinkController {

    @Autowired
    private ShortLinkService service;


    @PostMapping("/generate")
    @ResponseBody
    public ResponseEntity<String> generateLink(
            @RequestParam String url,
            @RequestParam (defaultValue = "60") int minutesValid

    ){
        ShortLink shortLink = service.createShortLink(url, minutesValid);
        String shortUrl = "http://localhost:8080/r/" + shortLink.getSlug();
        return ResponseEntity.ok(shortUrl);
    }


    @GetMapping("/r/{slug}")
    public ResponseEntity<?> redirect(@PathVariable String slug) {
        Optional<ShortLink> optionalLink = service.getValidLink(slug);

        if (optionalLink.isPresent()) {
            ShortLink link = optionalLink.get();
            service.markAsUsed(slug);
            return ResponseEntity.status(302)
                    .location(URI.create(link.getTargetUrl()))
                    .build();
        } else {
            return ResponseEntity.status(410)
                    .body("Este enlace ya fue usado o ha expirado.");
        }
    }

}
