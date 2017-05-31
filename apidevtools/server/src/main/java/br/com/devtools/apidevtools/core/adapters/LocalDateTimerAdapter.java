package br.com.devtools.apidevtools.core.adapters;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimerAdapter extends XmlAdapter<String, LocalDateTime> {
    public LocalDateTime unmarshal(String v) throws Exception {
    	LocalDateTime value = null;
    	try {
    		value = LocalDateTime.parse(v);
		} catch (Exception e) {
			value = ZonedDateTime.parse(v).toLocalDateTime();
		}
        return value;
    }

    public String marshal(LocalDateTime v) throws Exception {
        return v.toString();
    }
}
