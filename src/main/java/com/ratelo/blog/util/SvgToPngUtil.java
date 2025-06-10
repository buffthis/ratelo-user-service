package com.ratelo.blog.util;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

public class SvgToPngUtil {
    public static byte[] svgToPng(String svg) throws Exception {
        // SVG 원본 크기: 160x204, 3배 해상도(480x612)
        return svgToPng(svg, 480f, 612f);
    }

    public static byte[] svgToPng(String svg, float width, float height) throws Exception {
        PNGTranscoder transcoder = new PNGTranscoder();
        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
        transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height);
        TranscoderInput input = new TranscoderInput(new StringReader(svg));
        ByteArrayOutputStream pngOut = new ByteArrayOutputStream();
        TranscoderOutput output = new TranscoderOutput(pngOut);
        transcoder.transcode(input, output);
        return pngOut.toByteArray();
    }
} 