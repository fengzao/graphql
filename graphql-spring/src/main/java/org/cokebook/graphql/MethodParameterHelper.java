package org.cokebook.graphql;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Method Param  Helper : To get the method param's name
 *
 * @date 2019/11/29 16:14
 * @see Param
 */
public class MethodParameterHelper {

    private static final Map<Method, List<MethodParameter>> METHOD_PARAM_NAME_CACHE = new ConcurrentHashMap<>(32);
    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * Get the method param's name
     *
     * @param method
     * @return
     */
    public static List<MethodParameter> getParams(Method method) {

        if (METHOD_PARAM_NAME_CACHE.containsKey(method)) {
            return METHOD_PARAM_NAME_CACHE.get(method);
        }

        METHOD_PARAM_NAME_CACHE.computeIfAbsent(method, new Function<Method, List<MethodParameter>>() {
            @Override
            public List<MethodParameter> apply(Method method) {
                Parameter[] parameters = method.getParameters();
                final String[] candidateNames = PARAMETER_NAME_DISCOVERER.getParameterNames(method);
                final MethodParameter[] methodParameters = new MethodParameter[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    Param param = parameters[i].getAnnotation(Param.class);
                    String paramName = null;
                    if (param != null && param.value() != null) {
                        paramName = param.value();
                    } else if (candidateNames[i] != null) {
                        paramName = candidateNames[i];
                    } else {
                        paramName = parameters[i].getName();
                    }
                    methodParameters[i] = new MethodParameter(method, parameters[i], paramName);
                }
                return Arrays.asList(methodParameters);
            }
        });
        return METHOD_PARAM_NAME_CACHE.get(method);
    }
}
