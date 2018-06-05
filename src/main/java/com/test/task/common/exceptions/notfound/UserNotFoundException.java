package com.test.task.common.exceptions.notfound;


import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 7923082219985294750L;
    private static final String ENTITY = "user";
    private static final String LOGIN_FILED = "login";

    public UserNotFoundException(long id) {
        super(ENTITY, id);
    }

    public UserNotFoundException(Collection<Long> ids) {
        super(ENTITY, ids);
    }

    public UserNotFoundException(String field, String value) {
        super(ENTITY, field, value);
    }

    public static UserNotFoundException byLogin(String login) {
        return new UserNotFoundException(LOGIN_FILED, login);
    }

    public UserNotFoundException(Set<Long> foundIds, Set<Long> expectedIds) {
        super(ENTITY, foundIds, expectedIds);
    }

    public static UserNotFoundException fromIds(Collection<Long> foundIds, Collection<Long> requestedIds) {
        List<Long> ids = foundIds.
                stream().filter(foundId -> !requestedIds.contains(foundId)).collect(Collectors.toList());
        return new UserNotFoundException(ids);
    }
}
