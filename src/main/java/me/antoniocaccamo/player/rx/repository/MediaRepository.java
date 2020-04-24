package me.antoniocaccamo.player.rx.repository;

import me.antoniocaccamo.player.rx.model.sequence.Media;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaRepository extends CrudRepository<Media, Long> {
    // Resource find(String title);
}
