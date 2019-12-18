package org.cokebook.graphql.convert;

import org.cokebook.graphql.TypeConverter;

/**
 * @date 2019/12/18 17:49
 */
public class Number2StringConverter<T extends Number> implements TypeConverter<T, String> {
    @Override
    public String convert(Number value) {
        return value.toString();
    }
}
