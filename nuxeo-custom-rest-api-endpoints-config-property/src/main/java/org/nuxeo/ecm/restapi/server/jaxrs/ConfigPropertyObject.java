package org.nuxeo.ecm.restapi.server.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.AbstractResource;
import org.nuxeo.ecm.webengine.model.impl.ResourceTypeImpl;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.services.config.ConfigurationService;

@WebObject(type = "config-property")
public class ConfigPropertyObject extends AbstractResource<ResourceTypeImpl> {

    private static final Log LOG = LogFactory.getLog(ConfigPropertyObject.class);

    @GET @Path("{property}") public Object doGetProperty(@PathParam("property") String propertyName) {
        ConfigurationService cs = Framework.getLocalService(ConfigurationService.class);
        String propertyValue = cs.getProperty(propertyName);
        if (propertyValue == null){
            LOG.warn("Configuration variable '" + propertyName + "' is not defined.");
            return Response.status(Status.NO_CONTENT).build();
        } else {
            LOG.debug("Configuration variable '" + propertyName + "':'" + propertyValue+ "'");
            return Response.ok(propertyValue).build();
        }
    }

    @GET public Object doGet() {
        return Response.ok("Endpoint deployed.").build();
    }
}
