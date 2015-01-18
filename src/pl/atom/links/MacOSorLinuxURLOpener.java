package pl.atom.links;

import java.io.IOException;

/**
 * Created by Atom on 2015-01-19.
 */
public class MacOSorLinuxURLOpener implements URLOpener {
    @Override
    public void openURL(String url) throws NoDesktopSupportedException {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();
        try{
        if (os.indexOf( "mac" ) >= 0) {
                rt.exec("open " + url);

            }else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {

                // Do a best guess on unix until we get a platform independent way
                // Build a list of browsers to try, in this order.
                String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                        "netscape", "opera", "chrome","links", "lynx"};

                // Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++) {
                    cmd.append((i == 0 ? "" : " || ") + browsers[i] + " \"" + url + "\" ");
                }
                    rt.exec(new String[]{"sh", "-c", cmd.toString()});
            } else {
               throw new NoDesktopSupportedException("Niewspierany system");
            }
        }catch (IOException e){
                e.printStackTrace();
            }
    }
}
