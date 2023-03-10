package exercise.connections;

import exercise.TcpConnection;

// BEGIN
public class Connected implements Connection {

    private TcpConnection tcpConnection;

    public Connected(TcpConnection tcpConnection) {
        this.tcpConnection = tcpConnection;
    }
    @Override
    public void connect() {
        System.out.println("Error: already connected.");
    }

    @Override
    public void disconnect() {
        TcpConnection tc = this.tcpConnection;
        tc.setState(new Disconnected(tc));
        System.out.println("Successfully disconnected.");
    }

    @Override
    public void write(String data) {
        tcpConnection.writeData(data);
        System.out.println("Data was written to the buffer.");
    }

    @Override
    public String getCurrentState() {
        return "connected";
    }
}
// END
