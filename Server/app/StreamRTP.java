import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.medialist.MediaList;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.list.MediaListPlayer;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventAdapter;
import uk.co.caprica.vlcj.player.list.MediaListPlayerMode;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

/**
 * 
 * RTP Streaming using VLCJ
 * @author Mohamed AIT MANSOUR
 *
 */
public class StreamRTP {
    
    /**
     * 
     * @param serverAddress
     * @param serverPort
     * @return  mediaOptions for rtp stream wih vlc
     */
    private static String formatRtspStream(String serverAddress, int serverPort, String id) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{sdp=rtsp://@");
        sb.append(serverAddress);
        sb.append(':');
        sb.append(serverPort);
        sb.append('/');
        sb.append(id);
        sb.append("}");
        return sb.toString();
    }

    /**
     * Start streaming of music by adding music to the playList
     * 
     * @param dir
     * @param address
     * @param port
     * @param musique
     * @throws Exception
     */
     void start(String address, int port, String music,String type) throws Exception {
        String media = music;
        String options = formatRtspStream(address, port, type);

        System.out.println("Streaming '" + media + "' to '" + options + "'");

        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
        HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        mediaPlayer.setRepeat(true);
        mediaPlayer.playMedia(media,
            options,
            ":no-sout-rtp-sap",
            ":no-sout-standard-sap",
            ":sout-all",
            ":sout-keep"
        );
    }

}