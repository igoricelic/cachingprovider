package core.model.metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@AllArgsConstructor @Getter
public class KeyMetadata {

    private final Object targetObject;

    private final Method targetMethod;

    private final Object[] args;

    private final LocalDateTime createdTime;

}
