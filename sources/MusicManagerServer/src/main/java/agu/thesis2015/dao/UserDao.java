package agu.thesis2015.dao;

import java.util.List;

import agu.thesis2015.domain.User;
import agu.thesis2015.model.Response;

/**
 * 
 * @author ltduoc
 *
 */
public interface UserDao {

	public Response insert(User newUser);

	public Response update(User newUser);

	public Response delete(List<String> listUsername);

	public Response deleteAll();

	public Response getAll();

	public Response getByUserName(String username);

	public String userProcessor(String context);
}
