package com.traveloveapi.utility;

import java.util.HashMap;
import java.util.Map;

public class FileSupporter {
    static public Map<String, String> video = new HashMap<String, String>();

//    static public String getTypeByExtension(String file_name) {
//        String extension = file_name.lastIndexOf(".") > 0 ? file_name.substring(file_name.lastIndexOf(".")) : "";
//        switch (extension) {
//            case "mp4", "mkv" -> {
//                return "video";
//            }
//            case "jpg", "png", "icon", "webp" -> {
//                return "image";
//            }
//            case "zip", "rar" -> {
//                return "archive";
//            }
//            case "txt", "js", "cpp", "css", "sql" -> {
//                return "text";
//            }
//            default -> {
//                return "unknown";
//            }
//        }
//    }

    static public String getExtensionName(String file_name) {
        return file_name.lastIndexOf(".") > 0 ? file_name.substring(file_name.lastIndexOf(".")+1) : "";
    }

    static public String getContentTypeByExtension(String extension) {
        Map<String, String> MIME_types = new HashMap<String, String>();
        MIME_types.put("aac", "audio/aac");
        MIME_types.put("arc", "application/x-freearc");
        MIME_types.put("avi", "video/x-msvideo");
        MIME_types.put("bin", "application/octet-stream");
        MIME_types.put("bmp", "image/bmp");
        MIME_types.put("css", "text/css");
        MIME_types.put("csv", "text/csv");
        MIME_types.put("gif", "image/gif");
        MIME_types.put("html", "text/html");
        MIME_types.put("ico", "image/vnd.microsoft.icon");
        MIME_types.put("jar", "application/java-archive");

        MIME_types.put("js", "text/javascript");
        MIME_types.put("jpeg", "image/jpeg");
        MIME_types.put("jpg", "image/jpeg");
        MIME_types.put("json", "application/json");
        MIME_types.put("mp3", "audio/mpeg");
        MIME_types.put("mp4", "video/mp4");
        MIME_types.put("mpeg", "video/mpeg");
        MIME_types.put("png", "image/png");
        MIME_types.put("pdf", "application/pdf");
        MIME_types.put("rar", "application/vnd.rar");
        MIME_types.put("svg", "image/svg+xml");
        MIME_types.put("ts", "video/mp2t");
        MIME_types.put("txt", "text/plain");
        MIME_types.put("wav", "audio/wav");
        MIME_types.put("weba", "audio/webm");
        MIME_types.put("webm", "video/webm");
        MIME_types.put("webp", "image/webp");
        MIME_types.put("zip", "application/zip");
        MIME_types.put("3gp", "video/3gpp; audio/3gpp");
        MIME_types.put("3g2", "video/3gpp2");

        return MIME_types.get(extension);
    }
}