package me.antoniocaccamo.player.rx.service;

import me.antoniocaccamo.player.rx.model.Model;
import me.antoniocaccamo.player.rx.model.preference.LoadedSequence;
import me.antoniocaccamo.player.rx.model.sequence.Sequence;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface SequenceService {

    Iterable<LoadedSequence> getLoadedSequences();

    Optional<LoadedSequence> getLoadedSequenceByName(String sequenceName);

    Optional<LoadedSequence> loadFromSource(LoadedSequence loadedSequence) throws IOException;

    Optional<LoadedSequence> add(LoadedSequence loadedSequence);

    LoadedSequence save(LoadedSequence loadedSequence) throws IOException;

    /* @TODO :  remove below */

    Optional<Sequence> read( Path path);

    Optional<Sequence> read(Model.Location location, Path path);

    Sequence save(Sequence sequence, Path path) throws IOException;

}
