package me.antoniocaccamo.player.rx.repository;

import me.antoniocaccamo.player.rx.config.Constants;
import me.antoniocaccamo.player.rx.model.resource.Resource;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository // extends CrudRepository<Resource, Long>
{
    Resource findByType(Constants.Resource.Type type);
}
