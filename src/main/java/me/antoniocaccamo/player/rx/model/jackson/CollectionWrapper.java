package me.antoniocaccamo.player.rx.model.jackson;

import lombok.*;
import lombok.experimental.SuperBuilder;
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
public abstract class CollectionWrapper<T> {

    private Collection<T> collection ;

    public void clear(){
        if ( collection != null )
            collection.clear();
    }
}
