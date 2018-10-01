/*
 * Copyright 2018, EnMasse authors.
 * License: Apache License 2.0 (see the file LICENSE or http://apache.org/licenses/LICENSE-2.0.html).
 */
package io.enmasse.api.v1.http;

import io.enmasse.api.auth.RbacSecurityContext;
import io.enmasse.api.common.Exceptions;
import io.enmasse.api.v1.types.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.Arrays;

@Path("/apis")
public class HttpApiRootService {
    private static final APIGroup apiGroup =
            new APIGroup("enmasse.io", Arrays.asList(
                    new APIGroupVersion("enmasse.io/v1alpha1", "v1alpha1")),
                    new APIGroupVersion("enmasse.io/v1alpha1", "v1alpha1"),
                    null);

    private static final APIGroup userApiGroup =
            new APIGroup("user.enmasse.io", Arrays.asList(
                    new APIGroupVersion("user.enmasse.io/v1alpha1", "v1alpha1")),
                    new APIGroupVersion("user.enmasse.io/v1alpha1", "v1alpha1"),
                    null);

    private static final APIGroup adminApiGroup =
            new APIGroup("admin.enmasse.io", Arrays.asList(
                    new APIGroupVersion("admin.enmasse.io/v1alpha1", "v1alpha1")),
                    new APIGroupVersion("admin.enmasse.io/v1alpha1", "v1alpha1"),
                    null);

    private static final APIGroupList apiGroupList = new APIGroupList(Arrays.asList(apiGroup, userApiGroup, adminApiGroup));

    private static void verifyAuthorized(SecurityContext securityContext, String method, String path) {
        if (!securityContext.isUserInRole(RbacSecurityContext.rbacToRole(path, method))) {
            throw Exceptions.notAuthorizedException();
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public APIGroupList getApiGroupList(@Context SecurityContext securityContext, @Context UriInfo uriInfo) {
        verifyAuthorized(securityContext, "get", uriInfo.getPath());
        return apiGroupList;
    }

    @GET
    @Path("enmasse.io")
    @Produces({MediaType.APPLICATION_JSON})
    public APIGroup getApiGroup(@Context SecurityContext securityContext, @Context UriInfo uriInfo) {
        verifyAuthorized(securityContext, "get", uriInfo.getPath());
        return apiGroup;
    }


    private static final APIResourceList apiResourceList = new APIResourceList("enmasse.io/v1alpha1",
        Arrays.asList(
                new APIResource("addressspaces", "", true, "AddressSpace",
                    Arrays.asList("create", "delete", "get", "list")),
                new APIResource("addresses", "", true, "Address",
                                Arrays.asList("create", "delete", "get", "list"))));

    @GET
    @Path("enmasse.io/v1alpha1")
    @Produces({MediaType.APPLICATION_JSON})
    public APIResourceList getApiGroupV1(@Context SecurityContext securityContext, @Context UriInfo uriInfo) {
        // verifyAuthorized(securityContext, "get", uriInfo.getPath());
        return apiResourceList;
    }

    @GET
    @Path("user.enmasse.io")
    @Produces({MediaType.APPLICATION_JSON})
    public APIGroup getUserApiGroup(@Context SecurityContext securityContext, @Context UriInfo uriInfo) {
        verifyAuthorized(securityContext, "get", uriInfo.getPath());
        return userApiGroup;
    }

    private static final APIResourceList userApiResourceList = new APIResourceList("user.enmasse.io/v1alpha1",
            Arrays.asList(
                    new APIResource("messagingusers", "", true, "MessagingUser",
                            Arrays.asList("create", "delete", "get", "list", "update"))));

    @GET
    @Path("user.enmasse.io/v1alpha1")
    @Produces({MediaType.APPLICATION_JSON})
    public APIResourceList getUserApiGroupV1(@Context SecurityContext securityContext, @Context UriInfo uriInfo) {
        // verifyAuthorized(securityContext, "get", uriInfo.getPath());
        return userApiResourceList;
    }

    @GET
    @Path("admin.enmasse.io")
    @Produces({MediaType.APPLICATION_JSON})
    public APIGroup getAdminApiGroup(@Context SecurityContext securityContext, @Context UriInfo uriInfo) {
        verifyAuthorized(securityContext, "get", uriInfo.getPath());
        return adminApiGroup;
    }

    private static final APIResourceList adminApiResourceList = new APIResourceList("admin.enmasse.io/v1alpha1",
            Arrays.asList(
                    new APIResource("addressspaceplans", "", true, "AddressSpacePlan",
                            Arrays.asList("create", "delete", "get", "list", "update")),
                    new APIResource("addressplans", "", true, "AddressPlan",
                            Arrays.asList("create", "delete", "get", "list", "update")),
                    new APIResource("brokeredinfraconfig", "", true, "BrokeredInfraConfig",
                                    Arrays.asList("create", "delete", "get", "list", "update")),
                    new APIResource("standardinfraconfig", "", true, "StandardInfraConfig",
                                    Arrays.asList("create", "delete", "get", "list", "update"))));

    @GET
    @Path("admin.enmasse.io/v1alpha1")
    @Produces({MediaType.APPLICATION_JSON})
    public APIResourceList getAdminApiGroupV1(@Context SecurityContext securityContext, @Context UriInfo uriInfo) {
        // verifyAuthorized(securityContext, "get", uriInfo.getPath());
        return adminApiResourceList;
    }
}
