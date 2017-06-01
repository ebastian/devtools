package br.com.devtools.apidevtools.core.adapters;

import java.time.ZonedDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ZonedDateTimerAdapter extends XmlAdapter<String, ZonedDateTime> {
	
    public ZonedDateTime unmarshal(String v) throws Exception {
        return ZonedDateTime.parse(v);
    }

    public String marshal(ZonedDateTime v) throws Exception {
        return v.toString();
    }
}
