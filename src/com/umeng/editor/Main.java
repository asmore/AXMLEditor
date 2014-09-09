package com.umeng.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;

import com.umeng.editor.decode.AXMLDoc;

public class Main {
	/**
	 * In:AndroidManifest.xml channel1,channel2,channel3...
	 * Out:channel1.xml,channel2.xml,channel3.xml...
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			Args arg = Args.parseMetaArgs(args);
		
			if(arg == null){
				System.err.println("Usage:");
				System.err.println("AndroidManifest.xml dir  xxx [xxx] [xxx]");
				System.exit(0);
			}
			
			//cloneAXML(new File(arg.mAXML), arg.mDir, arg.mChannels);
			
			cloneAXML(new File(arg.mAXML), arg.mDir, arg.mMetaKey,arg.mMetaValue);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void cloneAXML(File axml, String dir, String[] channels) throws Exception{
		if(dir != null){
			File f = new File(dir);
			if(f.isFile() || !f.exists()){
				f.mkdirs();
			}
		}
		
		AXMLDoc doc = new AXMLDoc();
		doc.parse(new FileInputStream(axml));
		
		ChannelEditor editor = new ChannelEditor(doc);
		
		for(String chan : channels){
			editor.setChannel(chan);
			editor.commit();
			doc.build(new FileOutputStream(new File(dir,String.format("axml_%s.xml", chan))));
		}	
	}
	
	private static void cloneAXML(File axml, String dir, String channel,String chanValue) throws Exception{
		if(dir != null){
			File f = new File(dir);
			if(f.isFile() || !f.exists()){
				f.mkdirs();
			}
		}
		
		AXMLDoc doc = new AXMLDoc();
		doc.parse(new FileInputStream(axml));
		
		ChannelEditor editor = new ChannelEditor(doc);
		
		editor.setChannelName(channel);
		editor.setChannel(chanValue);
		editor.commit();
		doc.build(new FileOutputStream(new File(dir,String.format("axml_%s.xml", chanValue))));
		
	}
	
	static class Args {
		public String mAXML;
		public String mDir;
		public String[] mChannels;
		
		public String mMetaKey;
		public String mMetaValue;
		
		public static Args parseArgs(String[] args){
			Args arg = new Args();
			
			if(args == null || args.length < 2){
				return null;
			}
			
			arg.mAXML = args[0];
			int chanIndex = 1;
			if(args[1].equals("-dir")){
				if(args.length < 4){
					return null;
				}
				arg.mDir = args[2];
				chanIndex = 3;
			}
			
			String[] chans = new String[args.length-chanIndex];
			for(int i= chanIndex ; i< args.length; i++){
				chans[i-chanIndex] = args[i];
			}
			
			arg.mChannels = chans;
			return arg;
		}
		
		public static Args parseMetaArgs(String[] args){
			Args arg = new Args();
			
			if(args == null || args.length < 4){
				return null;
			}
			
			arg.mAXML = args[0];
			int chanIndex = 1;
			if(args[1].equals("-dir")){
				if(args.length < 4){
					return null;
				}
				arg.mDir = args[2];
				chanIndex = 3;
			}

			arg.mMetaKey = args[3];
			arg.mMetaValue = args[4];
			
			return arg;
		}		
	}
}
