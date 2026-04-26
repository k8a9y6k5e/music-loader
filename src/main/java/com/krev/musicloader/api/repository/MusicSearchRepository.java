package com.krev.musicloader.api.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MusicSearchRepository extends JpaRepository<MusicSearchEntity, Long> {
    List<MusicSearchEntity> findByActivityTrue(Sort sort);
}
