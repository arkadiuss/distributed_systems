package services.rest;

import services.rest.model.Todo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/todosimple")
public class TODOResourceSimple {

    @GET
    @Produces({MediaType.TEXT_XML})
    public Todo getHTML(){
        Todo td = new Todo("sadsa", "sdasa");
        return td;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Todo getXML(){
        Todo td = new Todo("sadsa", "sdasa");
        return td;
    }
}
