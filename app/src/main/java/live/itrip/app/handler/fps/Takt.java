package live.itrip.app.handler.fps;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.text.DecimalFormat;

import live.itrip.common.util.AppLog;

/**
 * Created by IntelliJ IDEA.
 * <p>
 * Description:
 *
 * @author JianF
 *         Date:  2017/10/24
 *         Time:  11:10
 *         Modify:
 */
public class Takt {

    private final static Program program = new Program();

    private Takt() {
    }

    public static Program stock(Application application) {
        return program.prepare(application);
    }

    public static void finish() {
        program.stop();
    }

    public static class Program {
        private Metronome metronome;
        private boolean isPlaying = false;
        private boolean showSetting = false;
        private final DecimalFormat decimal = new DecimalFormat("#.0' fps'");

        public Program() {
        }

        private Program prepare(Application application) {
            metronome = new Metronome();
            return this;
        }

        public void play() {
            metronome.start();

            if (!isPlaying) {
                isPlaying = true;
            }
        }

        public void stop() {
            metronome.stop();
            isPlaying = false;
        }

        public Program interval(int ms) {
            metronome.setInterval(ms);
            return this;
        }

        public Program listener(Audience audience) {
            metronome.addListener(audience);
            return this;
        }
    }
}
