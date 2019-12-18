package org.cokebook.graphql.convert;

import org.cokebook.graphql.TypeConverter;

/**
 * @date 2019/12/18 17:37
 */
public class String2FloatConverter implements TypeConverter<String, Float> {

    @Override
    public Float convert(String value) {
        return Float.parseFloat(value);
    }
}
