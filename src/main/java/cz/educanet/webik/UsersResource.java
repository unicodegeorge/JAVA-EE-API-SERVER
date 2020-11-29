package cz.educanet.webik;
import com.google.gson.Gson;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {
    Gson gson = new Gson();
    private static List<User> names = new ArrayList<User>();

    @GET
    public Response getAll() {

        return Response.ok(names).build();
    }

    @PUT
    @Path("/{username}")
    public Response changeUser(@PathParam("username") String username, @QueryParam("username") String changedUsername) {
        User tempUser = new User(username, "");
        if(doesUserExist(tempUser)){
            for(int i = 0; i < names.size(); i++) {
                if(names.get(i).getUsername().equals(tempUser.getUsername())) {
                    names.get(i).setUsername(changedUsername);
                    return Response.ok(username + " changed to " + changedUsername).build();
                }
            }
        } else {
            return Response.ok("User " + username + " doesn't exist!").build();
        }

        return Response.serverError().build();
    }

    @POST
    public Response createUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        User tempUser = new User(username,password);
        if(doesUserExist(tempUser)){
            return Response.status(406).build();
        } else {
            names.add(tempUser);
            return Response.ok("User Created").build();
        }
    }

    public Boolean doesUserExist(User user) {
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getUsername().equals(user.getUsername())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @DELETE
    public Response removeUser(User user) {
        if(doesUserExist(user)) {
            names.remove(user);
            return Response.ok().build();
        } else {
            return Response.status(406).build();
        }
    }


}
