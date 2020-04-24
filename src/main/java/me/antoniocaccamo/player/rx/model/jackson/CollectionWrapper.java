package me.antoniocaccamo.player.rx.model.jackson;

import lombok.*;
import me.antoniocaccamo.player.rx.model.resource.Resource;

import java.util.Collection;

/**
 * @author antoniocaccamo on 22/04/2020
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CollectionWrapper<T> {

    private Collection<Resource> collection ;
}
