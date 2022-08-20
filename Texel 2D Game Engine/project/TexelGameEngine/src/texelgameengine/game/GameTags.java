package texelgameengine.game;

import java.util.HashMap;

public class GameTags {
    private HashMap<String, Boolean> tags;

    public GameTags() {
        tags = new HashMap<>();
    }

    public boolean isTag(String s){
        return tags.get(s);
    }

    public void addTag(String s, boolean b){
        tags.put(s, b);
    }

    public HashMap<String, Boolean> getAllTags(){
        return tags;
    }
}
