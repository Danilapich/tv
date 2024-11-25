package Chanal;

import java.util.ArrayList;
import java.util.List;

public class ProgramManager {
    private List<ChannelProgram> programs = new ArrayList<>();
    private List<String> channels = new ArrayList<>();
    private List<String> genres = new ArrayList<>();

    public void addProgram(ChannelProgram program) {
        programs.add(program);
    }

    public void removeProgram(String channel, String dayOfWeek, String time) {
        programs.removeIf(p -> p.getChannel().equals(channel) &&
                p.getDayOfWeek().equals(dayOfWeek) && p.getTime().equals(time));
    }

    public void editProgram(String channel, String dayOfWeek, String time, ChannelProgram updatedProgram) {
        for (int i = 0; i < programs.size(); i++) {
            ChannelProgram p = programs.get(i);
            if (p.getChannel().equals(channel) && p.getDayOfWeek().equals(dayOfWeek) && p.getTime().equals(time)) {
                programs.set(i, updatedProgram);
                break;
            }
        }
    }

    public List<ChannelProgram> getPrograms() {
        return programs;
    }

    public void addChannel(String channel) {
        if (!channels.contains(channel)) {
            channels.add(channel);
        }
    }

    public void removeChannel(String channel) {
        channels.remove(channel);
    }

    public List<String> getChannels() {
        return channels;
    }

    public void addGenre(String genre) {
        if (!genres.contains(genre)) {
            genres.add(genre);
        }
    }

    public void removeGenre(String genre) {
        genres.remove(genre);
    }

    public List<String> getGenres() {
        return genres;
    }
}
