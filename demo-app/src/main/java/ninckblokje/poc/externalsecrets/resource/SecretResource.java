package ninckblokje.poc.externalsecrets.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/secrets")
public class SecretResource {

    @ConfigProperty(name = "test-secret-1")
    String testSecret1;

    @GET
    @Path("/test-secret-1")
    public String getTestSecret1() {
        return testSecret1;
    }
}
