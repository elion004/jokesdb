package ch.bbw.m151.jokesdb.viewmodel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JokesVM {
    boolean error;
    String category;
    String type;
    String joke;
    //String setup;
    //String delivery;
    FlagsVM flags;
    int id;
    boolean safe;
    String lang;
}
