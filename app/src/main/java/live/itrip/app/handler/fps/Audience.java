package live.itrip.app.handler.fps;

/**
 * Created by IntelliJ IDEA.
 * <p>
 * Description:
 *
 * @author JianF
 *         Date:  2017/10/24
 *         Time:  11:09
 *         Modify:
 */
public interface Audience {
    /**
     * 心跳
     *
     * @param fps fps
     */
    void heartbeat(String fps);
}
