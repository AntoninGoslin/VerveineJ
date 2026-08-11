package sun.rmi.server;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MarshalInputStream extends InputStream {
	public MarshalInputStream (InputStream inputStream) throws IOException {/*nothing*/}
	public int read() throws IOException { return 0;}
}