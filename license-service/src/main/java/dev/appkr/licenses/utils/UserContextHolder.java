package dev.appkr.licenses.utils;

import org.springframework.util.Assert;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();

    public static final UserContext getContext() {
        UserContext context = UserContextHolder.userContext.get();
        if (context == null) {
            context = new UserContext();
            userContext.set(context);
        }

        return context;
    }

    public static final void setContext(UserContext context) {
        Assert.notNull(context, "UserContext must not be null");
        userContext.set(context);
    }
}
