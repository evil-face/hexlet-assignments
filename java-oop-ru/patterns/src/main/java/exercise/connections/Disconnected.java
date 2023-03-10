package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Disconnected implements Connection {
    private TcpConnection tcpConnection;

    public Disconnected(TcpConnection tcpConnection) {
        this.tcpConnection = tcpConnection;
    }
    @Override
    public void connect() {
        TcpConnection tc = this.tcpConnection;
        tc.setState(new Connected(tc));
        System.out.println("Connection established.");
    }

    @Override
    public void disconnect() {
        System.out.println("Error: already disconnected.");
    }

    @Override
    public void write(String data) {
        System.out.println("Error: disconnected, cannot write data.");
    }

    @Override
    public String getCurrentState() {
        return "disconnected";
    }
}
// END
