package net.sunnycore.intercontent.util;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

public class SecurityUtil {
    public static final String RESPONSE_KEY = "responce_thrlocal_k";
    public static final String REQUEST_KEY = "request_thrlocal_k";
    
    // TODO document me
    public static boolean isUserInRole(UserDetails user, String role) {
        if (user != null && !"".equals(role)) {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                final String authorityString = authority.getAuthority();
                if (role.equals(authorityString)) {
                    return true;
                }
            }
        }
        return false;
    }

    // TODO document me
    public static UserDetails getCurrentUser() {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (context != null && authentication != null) {
            Object principal = authentication.getPrincipal();
            UserDetails user;
            if (principal instanceof String || "roleAnonymous".equals(principal)) {
                user = null;
            } else {
                user = (UserDetails) principal;
            }
            return user;
        } else {
            return null;
        }
    }

    /**
     * creates authentication object for the web applications The users that is
     * represented by userDetails should be available from userDetailsService at
     * this point because we will need to load his permissions
     * 
     * @param userDetails
     * @param request
     * @return
     */
    public static Authentication createAuthentication(UserDetails userDetails) {
        throw new RuntimeException("not implemented");
    }

    private static SessionRegistry getSessionRegistry() {
        SessionRegistry sessionRegistry = (SessionRegistry) IocUtil.getSpringBean("_sessionRegistry", SessionRegistry.class);
        return sessionRegistry;
    }

    /**
     * logs out currently authenticated user if any
     */
    public static void doLogoutCurrentUser() {
        SessionRegistry sessionRegistry = getSessionRegistry();
        doLogoutCurrentUser(sessionRegistry);
    }

    /**
     * logs out currently authenticated user if any
     */
    public static void doLogoutCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        SessionRegistry sessionRegistry = getSessionRegistry();
        doLogoutCurrentUser(sessionRegistry, request, response);
    }

    /**
     * logs out current user using spring security api
     * 
     * @param sessionRegistry
     */
    public static void doLogoutCurrentUser(SessionRegistry sessionRegistry, HttpServletRequest request,
            HttpServletResponse response) {
        LogoutHandler rememberMeLogoutHanlder = (LogoutHandler) IocUtil.getSpringBean("rememberMeServicesAlias",
                TokenBasedRememberMeServices.class);
        LogoutHandler defaultLogoutHandler = (LogoutHandler) IocUtil.getSpringBean("defaultLogoutHanlder",
                SecurityContextLogoutHandler.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (rememberMeLogoutHanlder != null) {
            rememberMeLogoutHanlder.logout(request, response, authentication);
        }
        if (defaultLogoutHandler != null) {
            defaultLogoutHandler.logout(request, response, authentication);
        }
        /*
         * HttpSession session = request.getSession(); if (session != null) {
         * SessionInformation sessionInformation =
         * sessionRegistry.getSessionInformation(session.getId()); if
         * (sessionInformation != null) { sessionInformation.expireNow(); Cookie
         * cookie = new Cookie(SPRING_SECURITY_COOKIE_NAME,null);
         * cookie.setMaxAge(0);
         * cookie.setPath(StringUtils.hasLength(request.getContextPath()) ?
         * request.getContextPath() : "/"); respons.addCookie(cookie);
         * SecurityContextHolder.getContext().setAuthentication(null); } }
         */
    }

    /**
     * logs out current user using spring security api
     * 
     * @param sessionRegistry
     */
    public static void doLogoutCurrentUser(SessionRegistry sessionRegistry) {
        doLogoutCurrentUser(sessionRegistry, getRequest(), getResponse());
    }

    /**
     * logouts the user with the entered username from the site the session
     * registry is received from the webapplication using faces context. If jsf
     * is not available at the point of usage of this method then nothing will
     * work
     * 
     * @param login
     */
    public static void logoutUser(String login) {
        SessionRegistry sessionRegistry = getSessionRegistry();
        logoutUser(login, sessionRegistry);
    }

    /**
     * logouts user
     * 
     * @param login
     * @param sessionRegistry
     */
    public static void logoutUser(String login, SessionRegistry sessionRegistry) {
        if (sessionRegistry != null) {
            List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
            for (Object principal : allPrincipals) {
                if (principal.equals(login)) {
                    List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principal, true);
                    for (SessionInformation info : allSessions) {
                        info.expireNow();
                    }
                    break;
                }
            }
        }
    }

    public static boolean isUserInRole(String role) {
        if (isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = getUser().getAuthorities();
            if (authorities != null) {
                for (GrantedAuthority grantedAuthority : authorities) {
                    if (grantedAuthority.getAuthority().equalsIgnoreCase(role)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return currently authenticated user
     */
    public static boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !auth.getPrincipal().getClass().isAssignableFrom(String.class);
    }

    /**
     * @return currently authenticated user
     */
    public static UserDetails getUser() {
        if (isAuthenticated()) {
            return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } else {
            return null;
        }
    }

    /**
     * returns current HttpServlet request
     * 
     * @return
     */
    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) ThreadLocalStorage.get(RESPONSE_KEY);
    }

    /**
     * returns current HttpServlet request
     * 
     * @return
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) ThreadLocalStorage.get(REQUEST_KEY);
    }

    public static String getRemoteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return auth.getName();
        }
        return null;
    }

    public static String getUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        }
        return null;
    }

    public static String[] parseRoleList(String roles) {
        String[] roleArray = roles.split(",");
        for (int i = 0; i < roleArray.length; i++) {
            roleArray[i] = roleArray[i].trim();
        }
        return roleArray;
    }
}
