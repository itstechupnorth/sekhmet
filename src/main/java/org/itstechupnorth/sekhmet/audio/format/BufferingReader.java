package org.itstechupnorth.sekhmet.audio.format;

import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;

import org.itstechupnorth.sekhmet.audio.data.AudioData;


abstract class BufferingReader extends DefaultReader {

	public BufferingReader() {
		super();
	}

	protected AudioData build(final AudioFormat format, final long startFrame, final byte[] bytes, int numberOfBytesRead) {
		final long startMillis = Math.round((startFrame * 1000) / format.getFrameRate());
		final long numberOfFrames = numberOfBytesRead / format.getFrameSize();
		final long durationMillis =  Math.round((numberOfFrames * 1000d) / format.getFrameRate()) ;
		
		final ByteBuffer buffer = ByteBuffer.wrap(bytes, 0, numberOfBytesRead);
		return new AudioData(buffer, format, startFrame,startMillis, numberOfFrames, durationMillis);
	}

	protected long nextFrame(final AudioData data) {
		return data.getStartFrame() + data.getNumberOfFrames();
	}

}