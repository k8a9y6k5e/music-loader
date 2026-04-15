package com.krev.musicloader.music;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Long>{
}
