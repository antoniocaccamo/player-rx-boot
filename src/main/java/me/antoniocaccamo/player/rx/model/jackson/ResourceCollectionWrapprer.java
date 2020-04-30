package me.antoniocaccamo.player.rx.model.jackson;

import lombok.AllArgsConstructor;
import me.antoniocaccamo.player.rx.model.resource.Resource;
import org.checkerframework.checker.units.qual.C;
import org.omg.CORBA.NVList;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author antoniocaccamo on 22/04/2020
 */


public class ResourceCollectionWrapprer extends CollectionWrapper<Resource>{

    private ResourceCollectionWrapprer(){
        setCollection( new HashSet<>());
    }

    public static ResourceCollectionWrapprerBuilder builder() {
        return new ResourceCollectionWrapprerBuilder();
    }

    public static final class ResourceCollectionWrapprerBuilder{
        private ResourceCollectionWrapprer wrapper= new ResourceCollectionWrapprer();

        public ResourceCollectionWrapprerBuilder item(Resource resource){
            wrapper.getCollection().add(resource);
            return this;
        }

        public ResourceCollectionWrapprerBuilder collection(Collection<Resource> resources){
            wrapper.clear();
            wrapper.setCollection(resources);
            return this;
        }

        public ResourceCollectionWrapprer build(){
            return wrapper;
        }
    }
}
