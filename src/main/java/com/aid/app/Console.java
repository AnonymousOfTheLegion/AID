package com.aid.app;

import com.aid.app.dvach.Downloader2ch;
import com.aid.app.eightchan.Downloader8chan;
import com.aid.app.fourchan.Downloader4chan;
import com.aid.app.futabachannel.DownloaderFutabaChannel;

public class Console {
    /**
     *
     * @param args - Command line arguments
     * @return - Object with settings
     * @throws ParseException if argument list is empty
     */
    public static Settings parseConsoleArguments(String[] args) throws ParseException {
        if (args.length == 0) {
            throw new ParseException("Args not specified");
        }

        String[] filetypes = null;
        String url = null, destinationDir = null;

        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "-f" : case "--filetypes": {
                    if (i + 1 < args.length) {
                        filetypes = args[i + 1].split("\\s+");
                    }
                    break;
                }
                case "-u" : case "--url": {
                    if (i + 1 < args.length) {
                        url = args[i + 1];
                    }
                    break;
                }
                case "-d" : case "--destination": {
                    if (i + 1 < args.length) {
                        destinationDir = args[i + 1];
                        if (destinationDir.lastIndexOf("\\") == destinationDir.length()) {
                            destinationDir = destinationDir.substring(0, destinationDir.length() - 1);
                        }
                        destinationDir = destinationDir.replace("\\", "\\\\").replace("\"", "");
                    }
                    break;
                }
            }
        }

        return new Settings(url, destinationDir, filetypes);
    }

    public static void printTitle() {
        System.out.println("#######################################\n"
                + "### Anonymous Imageboard Downloader ###\n"
                + "#######################################");
    }

    public static void printHelp() {
        final String jarFileName =
                new java.io.File(Console.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();

        String linuxExample =
                "  user@linux:~$ java -jar "
                        + jarFileName
                        + " -d \"/home/user/Downloads/2ch/\""
                        + " -u https://2ch.hk/gg/res/694216.html -f \"mp4 avi mp3\"\n";
        String winExample =
                "java -jar "
                + jarFileName
                + " -u https://2ch.hk/gg/res/694216.html -d \"D:/2ch\"" + " -f \"webm jpg\"\n";
        System.out.println("  HOW TO:\n"
                + "Arguments:\n"
                + "-d \"destination_directory\" (mandatory)\n"
                + "-f \"filetypes\" (optional, put spaces between filetypes\n"
                + "   if not chosen, automatically will set mp4, webm and jpeg)\n"
                + "EXAMPLE:\n"
                + (System.getProperty("os.name").toLowerCase().contains("windows")
                ? winExample : linuxExample)
        );
    }
}

/**
 * Class Settings
 *
 * Contains settings needed to begin downloading contents from website,
 * such like URL of imageboard, directory where to save contents
 * and array of file types needed to be saved
 */
class Settings {
    private String url, dir;
    private String[] filetypes;
    private final String[] knownImageBoards = {
            "dec.2chan.net",
            "2ch.hk",
            "4chan.org",
            "8ch.net"
    };

    public Settings(String url, String dir, String[] filetypes) throws ParseException {
        this.url = url;
        this.dir = dir;
        this.filetypes = filetypes;

        if (dir == null || url == null) {
            throw new ParseException("Directory or url is not specified. Please, specify it.");
        }

        if (this.filetypes == null) {
            this.filetypes = new String[] {
              "webm",
              "jpg",
              "jpeg",
              "png",
              "gif",
              "mp4",
              "avi", "mkv", "flv"
            };
        } else {
            for (String f : this.filetypes) {
                f = f.toLowerCase();
            }
        }
    }

    /**
     * If you want to implement another imageboard support, you have to specify it in switch-case statement here:
     * @return - returns Downloader object implemented for concrete imageboard.
     * @throws ParseException - if no known imageboards recognized in url string.
     */
    public Downloader getDownloader() throws ParseException {
        for (String knownImageBoard : knownImageBoards) {
            if (url.contains(knownImageBoard)) {
                switch (knownImageBoard) {
                    case "dec.2chan.net": {
                        System.out.println("Detected imageboard: FutabaChannel");
                        return new DownloaderFutabaChannel();
                    }
                    case "2ch.hk": {
                        System.out.println("Detected imageboard: 2ch.hk");
                        return new Downloader2ch();
                    }
                    case "4chan.org": {
                        System.out.println("Detected imageboard: 4chan.org");
                        return new Downloader4chan();
                    }
                    case "8ch.net": {
                        System.out.println("Detected imageboard: 8ch.net");
                        return new Downloader8chan();
                    }
                }
            }
        }

        throw new ParseException("Unrecognized imageboard: \"" + url +"\"");
    }

    public String getDir() { return this.dir; }
    public String getUrl() { return this.url; }
    public String[] getFiletypes() { return this.filetypes; }
    public String[] getKnownImageBoards() { return this.knownImageBoards; }
}
