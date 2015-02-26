package agu.thesis2015.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import agu.thesis2015.domain.Song;

/**
 * 
 * @author ltduoc
 *
 */
@Repository
public interface SongRepo extends MongoRepository<Song, String> {

	@Query("{$and:[{'username':?0},{$or : [{'name' : { $regex: ?1, $options: 'i' }}, {'genre' : { $regex: ?1, $options: 'i' } }, {'artist' : { $regex: ?1, $options: 'i' } },"
			+ "{'musician' : { $regex: ?1, $options: 'i' } }  ] }]}")
	public Page<Song> findAllCriteria(Pageable page, String username,
			String criteria);

	@Query("{'username':?0}")
	public List<Song> findAll(String username);

	@Query("{'username':?0}")
	public Page<Song> findAll(Pageable pageRepuest, String username);

	@Query("{$and:[{'username':?0},{'id':?1}]}")
	public Song findById(String username, String id);
}
