package ru.nsu.ccfit.gemuev.model;

import org.jetbrains.annotations.NotNull;
import ru.nsu.ccfit.gemuev.LoadPropertiesException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;

public class GameLevels {

    private static final GameLevels INSTANCE = new GameLevels();

    private GameLevels(){
        try(InputStream inputStream = GameLevels.class
                .getResourceAsStream("game_levels.properties")) {

            settings = new HashMap<>();
            var tmp = new Properties();
            tmp.load(inputStream);

            for (var i : tmp.entrySet()) {
                settings.put((String) i.getKey(), (String)i.getValue());
            }
        }
        catch (IOException e){
            throw new LoadPropertiesException("Terminate!!!!!!!", e);
        }
    }

    private final HashMap<String, String> settings;

    public static Optional<String> getSetting(@NotNull String level){

        String setting = INSTANCE.settings.get(level);
        if(setting==null){
            return Optional.empty();
        }
        else{
            return Optional.of(setting);
        }
    }

    public static @NotNull Optional<String> getLevelNameByID(int levelID){

        String level = Integer.toString(levelID);

        for(var i : INSTANCE.settings.entrySet()){
            String[] tmp = i.getValue().split(" ");
            if(tmp[4].equals(level)){
                return Optional.of(i.getKey());
            }
        }

        return Optional.empty();
    }

}
