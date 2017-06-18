package ru.xidv.drankov.fassist.ser.shared_jsons;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PureTokenJSON {

    @Getter
    private String token;

    public PureTokenJSON(String token) {
        this.token = token;
    }
}
