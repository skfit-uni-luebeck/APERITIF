package patientenrekrutierung.datafetching;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * With the method of this class you can write the content of a String
 * into a text file
 * @author Alexandra Banach
 *
 */
public class FileWriter {
	
	/**
	 * With this method you can write the content of a String into 
	 * a text file.
	 * @param string content which should be written into text file
	 * @param path location where the text file should be stored
	 * @throws IOException is thrown if something goes wrong during writing the content into the text file
	 */
	public void writeTxtFile(String string, String path) throws IOException {
		RandomAccessFile stream = new RandomAccessFile(path, "rw");
		FileChannel channel = stream.getChannel();
		byte[] strBytes = string.getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);
		buffer.put(strBytes);
		buffer.flip();
		channel.write(buffer);
		stream.close();
		channel.close();
	}
	
}
