package me.antoniocaccamo.player.rx.repository;

import me.antoniocaccamo.player.rx.model.sequence.Sequence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SequenceRepository extends CrudRepository<Sequence, Long> {

    @Override
    Optional<Sequence> findById( Long aLong);

    Optional<Sequence> findByName(String name);
}
