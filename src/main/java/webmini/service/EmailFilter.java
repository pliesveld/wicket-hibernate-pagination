package webmini.service;

import java.io.Serializable;
/**
 * Convience class to filter results by email address.
 */
public class EmailFilter extends FilterParam<String>
    implements Serializable {
    private static final long serialVersionUID = 1L;

    public EmailFilter(String value) {
        super("email", value);
    }
}
