package agu.thesis2015.dao;

import java.util.List;

import agu.thesis2015.domain.Song;
import agu.thesis2015.model.RequestData.Direction;
import agu.thesis2015.model.Response;

/**
 * 
 * @author ltduoc
 *
 */
public interface SongDao {

	public Response insert(Song newSong);

	public Response update(Song newSong);

	public Response delete(String username, List<String> listId);

	public Response deleteAll(String username);

	public Response getAll(String username);

	public Response getById(String username, String id);

	public Response search(String username,String keyword, String field, int page, int size, Direction direction);

	public Response Paging(String username, String field, int page, int size, Direction direction);

	public String songProcessor(String context);

}
