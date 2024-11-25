package Chanal;

public class ChannelProgram {
    private String channel;
    private String dayOfWeek;
    private String time;
    private String programName;
    private String genre;

    public ChannelProgram(String channel, String dayOfWeek, String time, String programName, String genre) {
        this.channel = channel;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.programName = programName;
        this.genre = genre;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return String.format("Канал: %s, День: %s, Время: %s, Программа: %s, Жанр: %s",
                channel, dayOfWeek, time, programName, genre);
    }
}
