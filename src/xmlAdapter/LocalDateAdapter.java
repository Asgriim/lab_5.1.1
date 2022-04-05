package xmlAdapter;

import com.sun.istack.internal.NotNull;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

/**
 *local date adapter for jaxb
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDateTime> {
    @NotNull
    @Override
    public LocalDateTime unmarshal(String v) throws Exception {
        if (LocalDateTime.parse(v) == null || v == null){
            throw new Exception();
        }
        else {return LocalDateTime.parse(v);}

    }

    @Override
    public String marshal(LocalDateTime v) {
        return v.toString();
    }
}
