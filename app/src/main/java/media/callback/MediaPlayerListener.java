package media.callback;

/**
 * @author 小陈
 * @time 2018/1/19  11:17
 * @desc 视频控制回调接口
 */
public interface MediaPlayerListener {
    void start();

    void pause();

    int getDuration();

    int getCurrentPosition();

    void seekTo(long pos);

    boolean isPlaying();

    int getBufferPercentage();

    boolean canPause();
}
