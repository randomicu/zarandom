package icu.random.slack.zarandom.repository;

import icu.random.slack.zarandom.entity.Shuffle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShuffleRepository extends CrudRepository<Shuffle, String> {

}
