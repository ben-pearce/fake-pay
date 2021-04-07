package com.pay.fakepay.thrift;

import java.time.LocalDateTime;
import org.apache.thrift.TException;

public class TimestampHandler implements TimestampService.Iface {

    @Override
    public TimestampResponse timestamp() throws TException {
        LocalDateTime dt = LocalDateTime.now();
        return new TimestampResponse(
                dt.getYear(), 
                dt.getMonthValue(), 
                dt.getDayOfMonth(), 
                dt.getHour(), 
                dt.getMinute(), 
                dt.getSecond());
    }
    
}
