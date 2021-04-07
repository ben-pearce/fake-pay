package com.pay.fakepay.ejb;

import com.pay.fakepay.thrift.TimestampHandler;
import com.pay.fakepay.thrift.TimestampResponse;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.thrift.server.TServer;
import com.pay.fakepay.thrift.TimestampService;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

@Startup
@Singleton public class TimeService {
    public static TimestampHandler handler;
    public static TimestampService.Processor processor;
    public static TServerTransport serverTransport;
    public static TServer server;
    
    @PostConstruct
    public void init() {
        handler = new TimestampHandler();
        processor = new TimestampService.Processor(handler);

        Runnable listen = () -> { listen(processor); };
        new Thread(listen).start();
    }
    
    public static void listen(TimestampService.Processor processor) {
        TNonblockingServerSocket socket;
        try {
            socket = new TNonblockingServerSocket(10001);
            TNonblockingServer.Args arg = new TNonblockingServer.Args(socket);
            arg.protocolFactory(new TBinaryProtocol.Factory());
            arg.transportFactory(new TFramedTransport.Factory());
            arg.processorFactory(new TProcessorFactory(processor));
            server = new TNonblockingServer(arg);

            server.serve();
        } catch (TTransportException ex) {
            Logger.getLogger(TimestampService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public LocalDateTime get() {
        TTransport transport;
        transport = new TSocket("127.0.0.1", 10001);
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(
                    new TFramedTransport(transport));
            TimestampService.Client client = 
                    new TimestampService.Client(protocol);

            TimestampResponse ts = client.timestamp();
            transport.close();
            
            return LocalDateTime.of(
                    ts.getYear(), 
                    ts.getMonth(),
                    ts.getDate(), 
                    ts.getHrs(), 
                    ts.getMin(), 
                    ts.getSec());
        } catch (TTransportException ex) {
            Logger.getLogger(TimeService.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(TimeService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @PreDestroy
    public void shutdown() {
        server.stop();
    }
}
