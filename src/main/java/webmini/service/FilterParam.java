package webmini.service;

import java.io.Serializable;

/**
 * Abstract class representing a field to filter in conjunction with a query.
 * @param field table attribute to filter by
 * @param value literal value to match results against
 */
public abstract class FilterParam<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String field;
	private T value;
	
	public FilterParam(String field,T value) {
		this.field = field;
		this.value = value;
	}

	public String getField() {
		return field;
	}

    public T getValue() {
		return value;
	}

}
