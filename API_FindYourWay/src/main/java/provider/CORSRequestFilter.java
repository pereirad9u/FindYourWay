/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider;

import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Provider
@PreMatching
public class CORSRequestFilter implements ContainerRequestFilter {

    private final static Logger log = Logger.getLogger(CORSRequestFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestCtx) throws IOException {

        log.info("Exécution du filtre Request");


        if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
            requestCtx.abortWith(Response.status(Response.Status.OK).build());
        }
    }
}
