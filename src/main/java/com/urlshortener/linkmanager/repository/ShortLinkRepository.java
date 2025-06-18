package com.urlshortener.linkmanager.repository;
import com.urlshortener.linkmanager.model.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ShortLinkRepository extends JpaRepository<ShortLink, String> {
}
