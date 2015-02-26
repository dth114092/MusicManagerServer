package agu.thesis2015.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agu.thesis2015.domain.User;
import agu.thesis2015.jms.message.Message;
import agu.thesis2015.jms.message.Message.MessageAction;
import agu.thesis2015.model.Response;
import agu.thesis2015.model.Response.ResponseStatus;
import agu.thesis2015.repo.SongRepo;
import agu.thesis2015.repo.UserRepo;

import com.google.gson.Gson;

@Service(value = "userDao")
public class UserDaoIml implements UserDao {

	@Autowired
	UserRepo userRepo;

	@Autowired
	SongRepo songRepo;

	private Response getResponse(ResponseStatus status, int statuscode, String message, Object response) {
		return new Response(status, statuscode, message, response);
	}

	@Override
	public String userProcessor(String context) {
		String response = null;
		Message message = Message.fromJson(context);
		switch (message.getMethod()) {
		case GET: {
			if (message.getAction().equals(MessageAction.GET_ALL)) {
				response = getAll().toJson();
				break;
			} else if (message.getAction().equals(MessageAction.GET_BY_ID)) {
				response = getByUserName(message.getData().toString()).toJson();
				break;
			}
		}
		case POST: {
			if (message.getAction().equals(MessageAction.INSERT)) {

				User newUser = User.fromJson(String.valueOf(message.getData()));
				response = insert(newUser).toJson();
				break;
			}
		}
		case PUT: {
			if (message.getAction().equals(MessageAction.UPDATE)) {

				User newUser = User.fromJson(String.valueOf(message.getData()));
				response = update(newUser).toJson();
				break;
			}
		}
		case DELETE: {
			if (message.getAction().equals(MessageAction.DELETE_ALL)) {
				response = deleteAll().toJson();
				break;
			} else if (message.getAction().equals(MessageAction.DELETE)) {
				List<String> listId = new Gson().fromJson(message.getData().toString(), List.class);
				response = delete(listId).toJson();
				break;
			}
		}
		default:
			break;
		}
		return response;
	}

	@Override
	public Response insert(User newUser) {
		try {
			if (newUser.getUsername() != null && userRepo.exists(newUser.getUsername())) {
				return getResponse(ResponseStatus.BAD_REQUEST, 400, "The user is exsits", null);
			} else {
				userRepo.save(newUser);
				return getResponse(ResponseStatus.OK, 200, "Insert success", null);
			}
		} catch (Exception e) {
			return getResponse(ResponseStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage(), null);
		}
	}

	@Override
	public Response update(User newUser) {
		try {
			if (newUser.getUsername() != null && userRepo.exists(newUser.getUsername())) {
				userRepo.save(newUser);
				return getResponse(ResponseStatus.OK, 200, "Update success", null);

			} else {
				return getResponse(ResponseStatus.BAD_REQUEST, 400, "The user isn't exsits", null);
			}
		} catch (Exception e) {
			return getResponse(ResponseStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage(), null);
		}
	}

	@Override
	public Response delete(List<String> listUsername) {
		try {
			for (String username : listUsername) {
				userRepo.delete(username);
			}
			return getResponse(ResponseStatus.OK, 200, "Delete success", null);
		} catch (Exception e) {
			return getResponse(ResponseStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage(), null);
		}
	}

	@Override
	public Response deleteAll() {
		try {
			userRepo.deleteAll();
			return getResponse(ResponseStatus.OK, 200, "Delete all success", null);
		} catch (Exception e) {
			return getResponse(ResponseStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage(), null);
		}
	}

	@Override
	public Response getAll() {
		try {
			List<User> listUser = userRepo.findAll();
			return getResponse(ResponseStatus.OK, 200, "Get list success", listUser);
		} catch (Exception e) {
			return getResponse(ResponseStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage(), null);
		}
	}

	@Override
	public Response getByUserName(String username) {
		try {
			User user = userRepo.findOne(username);
			return getResponse(ResponseStatus.OK, 200, "Get user success", user);
		} catch (Exception e) {
			return getResponse(ResponseStatus.INTERNAL_SERVER_ERROR, 500, e.getMessage(), null);
		}
	}

}
