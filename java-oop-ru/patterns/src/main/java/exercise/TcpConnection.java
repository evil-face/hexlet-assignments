package exercise;
import exercise.connections.Connected;
import exercise.connections.Connection;
import exercise.connections.Disconnected;

import java.util.List;
import java.util.ArrayList;

// BEGIN
public class TcpConnection implements Connection {
    private Connection state;
    private final String IP;
    private final int port;
    private final List<String> buffer;

    TcpConnection(String ipaddr, int port) {
        this.IP = ipaddr;
        this.port = port;
        this.state = new Disconnected(this);
        this.buffer = new ArrayList<>();
    }

    public void setState(Connection state) {
        this.state = state;
    }

    public void writeData(String data) {
        this.buffer.add(data);
    }

    @Override
    public void connect() {
        this.state.connect();
    }

    @Override
    public void disconnect() {
        this.state.disconnect();
    }

    @Override
    public void write(String data) {
        this.state.write(data);
    }

    @Override
    public String getCurrentState() {
        return this.state.getCurrentState();
    }
}
// END
