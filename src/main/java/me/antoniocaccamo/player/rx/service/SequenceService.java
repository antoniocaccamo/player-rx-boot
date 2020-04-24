package me.antoniocaccamo.player.rx.service;

import me.antoniocaccamo.player.rx.model.Model;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;

public interface SequenceService {

    Optional<Sequence> read( Path path);

    Optional<Sequence> read(Model.Location location, Path path);

    Sequence save(Sequence sequence, Path path) throws IOException;

    Optional<Sequence> getSequenceByName(String seqeunceName);

    Collection<Sequence> getLoadedSequences();

    //Sequence dummy();
}
