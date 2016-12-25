package twitterapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import twitterapp.exception.IORuntimeException;

	/*
	ストリーム関係のユーティリティー
	*/

public class StreamUtil {

	/*
	inputからoutputにデータをコピー

	@param input
	@param output
	*/

	public static void copy(InputStream input, OutputStream output) {

		byte[] buffer = new byte[4096];
		try {
			for(int i = 0; -1 != (i = input.read(buffer));) {
				output.write(buffer, 0, i);
			}
		} catch(IOException e) {
			throw new IORuntimeException(e);

		}
	}

}
